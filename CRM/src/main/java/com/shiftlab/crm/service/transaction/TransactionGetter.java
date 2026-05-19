package com.shiftlab.crm.service.transaction;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.exception.ResourceNotFoundException;
import com.shiftlab.crm.repository.TransactionRepository;

import java.util.List;

public class TransactionGetter {
    private final TransactionRepository transactionRepository;

    public TransactionGetter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Transaction " + id + " is not found")
        );
    }

    public List<Transaction> getTransactionsByFilter(Long sellerId, PaymentType paymentType) {
        return transactionRepository.findByFilters(sellerId, paymentType);
    }
}
