package com.shiftlab.crm.service.TransactionService;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
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
                () -> new RuntimeException("Transaction " + id + " is not found")
        );
    }

    public List<Transaction> getTransactionsByFilter(Long sellerId, PaymentType paymentType) {
        return transactionRepository.findByFilters(sellerId, paymentType);
    }
}
