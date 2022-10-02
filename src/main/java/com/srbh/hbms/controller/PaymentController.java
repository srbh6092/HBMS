package com.srbh.hbms.controller;

import com.srbh.hbms.model.entity.Payment;
import com.srbh.hbms.model.entity.Transaction;
import com.srbh.hbms.model.request.NewPaymentRequest;
import com.srbh.hbms.service.payment.PaymentService;
import com.srbh.hbms.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable("id") int id) throws Exception {
        return paymentService.getPayment(id);
    }

    @PostMapping
    public Payment makePayment(@RequestBody NewPaymentRequest newPaymentRequest){
        Transaction transaction = Transaction.builder()
                .amount(newPaymentRequest.getAmount())
                .build();
        transaction = transactionService.addTransaction(transaction);
        Payment payment = Payment.builder()
                .transaction(transaction)
                .build();
        return paymentService.addPayment(payment);
    }

}