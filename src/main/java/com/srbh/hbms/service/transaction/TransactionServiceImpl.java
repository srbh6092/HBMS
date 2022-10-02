package com.srbh.hbms.service.transaction;

import com.srbh.hbms.model.entity.Transaction;
import com.srbh.hbms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(int id) throws Exception {

        //Fetching transaction with id
        Optional<Transaction> result = transactionRepository.findById(id);

        //If Transaction fetched with id is not present
        //Throw error
        if(!result.isPresent())
            throw new Exception("No transaction found with id: "+id);

        //Else return the Transaction fetched after the call
        return result.get();
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}