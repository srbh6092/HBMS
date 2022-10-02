package com.srbh.hbms.service.payment;

import com.srbh.hbms.model.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentService {

    List<Payment> getAllPayments();

    Payment getPayment(int id) throws Exception;

    Payment addPayment(Payment payment);

    Payment removePayment(int id) throws Exception;

}