package com.srbh.hbms.service.payment;

import com.srbh.hbms.model.entity.Payment;
import com.srbh.hbms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {

        //Returning list of all rows of Payment table
        return paymentRepository.findAll();
    }

    @Override
    public Payment getPayment(int id) throws Exception {

        //Fetching payment with id
        Optional<Payment> result = paymentRepository.findById(id);

        //If Payment fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No payment found with id: "+id);

        //Else return the Payment fetched after the call
        return result.get();
    }

    @Override
    public Payment addPayment(Payment payment) {

        //Adding a row to Payment table
        return paymentRepository.save(payment);
    }

    @Override
    public Payment removePayment(int id) throws Exception {

        //Fetching the payment with id from above created method
        Payment payment = getPayment(id);

        //Deleting the payment fetched
        paymentRepository.delete(payment);

        //Returning the payment fetched
        return payment;
    }

}