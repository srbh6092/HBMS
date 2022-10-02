package com.srbh.hbms.service.transaction;

import com.srbh.hbms.model.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionService {

    List<Transaction> getAllTransactions();

    Transaction getTransaction(int id) throws Exception;

    Transaction addTransaction(Transaction transaction);

}