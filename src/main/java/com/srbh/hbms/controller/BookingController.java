package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.*;
import com.srbh.hbms.model.request.NewBookingRequest;
import com.srbh.hbms.service.booking.BookingService;
import com.srbh.hbms.service.generatePDF.GeneratePDF;
import com.srbh.hbms.service.payment.PaymentService;
import com.srbh.hbms.service.room.RoomService;
import com.srbh.hbms.service.transaction.TransactionService;
import com.srbh.hbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;


import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    RoomService roomService;

    @Autowired
    GeneratePDF generatePDF;

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable("id") int id) throws Exception {
        return bookingService.getBooking(id);
    }

    @PostMapping("/{userId}")
    public Booking addBooking(@PathVariable("userId") int userId, @RequestBody NewBookingRequest newBookingRequest) throws Exception {

        //Getting user for which it is booked
        User user = userService.getUser(userId);

        //Getting number of stays
        Date from = newBookingRequest.getBookedFrom();
        Date to = newBookingRequest.getBookedTo();

        int days= getNoOffDays(from, to);

        //Initializing entries
        List<Room> rooms = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        Set<Integer> hotelId = new HashSet<>();

        //Parsing the list of room IDs
        for( int roomId: newBookingRequest.getRoomIDs()){

            //fetching the room
            Room room = roomService.getRoom(roomId);

            //Adding hotel IDs to set of unique IDs
            hotelId.add(room.getHotel().getHotelId());

            //If set has more than one element: Rooms are chosen from more than one hotel
            if(hotelId.size()>1)
                throw new Exception("All rooms must be from same hotel");

            //If room is not available at the moment
            if(!room.getIsAvailable())
                throw new Exception("Room id: "+ roomId + " is not available");

            //Else changing rom status to not available
            room.setIsAvailable(false);

            //Updating the room in table
            roomService.addRoom(room);

            //Adding the room to the list
            rooms.add(room);
        }

        //Getting the hotel
        Hotel hotel = rooms.get(0).getHotel();

        //Initializing total amount
        double amount = getAmount(rooms);

        //Adding transaction for amount for total number days of stay
        Transaction transaction = Transaction.builder()
                .amount(amount*days)
                .build();

        //adding transaction to database
        transaction =transactionService.addTransaction(transaction);

        //Making payment of transaction
        Payment payment= Payment.builder()
                .transaction(transaction)
                .status(transaction.validate())
                .build();

        //Adding payment to database
        payments.add(paymentService.addPayment(payment));

        //making booking with details and payment
        Booking booking = Booking.builder()
                .bookedFrom(from)
                .bookedTo(to)
                .hotel(hotel)
                .user(user)
                .noOfAdults(newBookingRequest.getNoOfAdults())
                .noOfChildren(newBookingRequest.getNoOfChildren())
                .rooms(rooms)
                .payment(payments)
                .build();

        //Adding booking to the database
        booking =bookingService.addBooking(booking);

        //Sending mail
        emailBookingDetails(booking.getBookingId());

        return booking;

    }

    private double getAmount(List<Room> rooms) {

        //Initializing total amount
        double amount=0.0;

        //Adding up all the rooms amount for one day
        for( Room room: rooms)
            amount += room.getRatePerDay();

        return amount;
    }

    private int getNoOffDays(Date from, Date to) {
        long diff = to.getTime()- from.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @GetMapping("/bookingDetails/{bookingId}")
    public String generatePdfById(@PathVariable("bookingId") int bookingId) throws Exception {
        Booking booking = getBooking(bookingId);
        generatePdfFile(
                booking,
                getNoOffDays(booking.getBookedFrom(),booking.getBookedTo()),
                getAmount(booking.getRooms())
        );
        return "PDF generated";
    }

    private void generatePdfFile(Booking booking, int days, double amount) {

        Map<String, Object> data = new HashMap<>();
        data.put("booking",booking);
        data.put("salutation","Hi "+ booking.getUser().getUsername()+",");
        data.put("currentDate", new Date(System.currentTimeMillis()));
        data.put("oneDayAmount", amount);
        data.put("days",days);
        data.put("from", new Date(booking.getBookedFrom().getTime()));
        data.put("to",new Date(booking.getBookedTo().getTime()));
        data.put("amount",days*amount);
        data.put("paymentId",booking.getPayment().get(booking.getPayment().size()-1).getPaymentId());
        data.put("rooms",booking.getRooms());

        generatePDF.generatePdf("BookingTemplate", data, "Booking_"+booking.getBookingId()+".pdf");


    }

    @DeleteMapping("/{id}")
    public Booking removeBooking(@PathVariable("id") int id) throws Exception {
        return bookingService.removeBooking(id);
    }

    @GetMapping("/emailBookingDetails/{bookingId}")
    public String emailBookingDetails(@PathVariable("bookingId") int bookingId) throws Exception {

        //generating PDF
        Booking booking = bookingService.getBooking(bookingId);
        int days= getNoOffDays(booking.getBookedFrom(),booking.getBookedTo());
        double amount =getAmount(booking.getRooms());
        generatePdfFile(booking,days,amount);

        //Sending email
        try{
            //Generate email
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);

            //Add receiver's address, subject and body message
            helper.setTo(booking.getUser().getEmail());
            helper.setSubject("Booking Receipt");
            helper.setText("Thanks for booking. Please find the receipt attached");

            //Load file from local storage
            FileSystemResource receiptPDF = new FileSystemResource("E:\\HBMS\\"+"Booking_"+bookingId+".pdf");

            //Attach the file to email
            helper.addAttachment(receiptPDF.getFilename(),receiptPDF);

            //Send mail
            javaMailSender.send(msg);

        } catch (Exception e){
            return "Couldn't send mail: "+e.getMessage();
        }

        return "Sent mail to: "+booking.getUser().getEmail();

    }

}