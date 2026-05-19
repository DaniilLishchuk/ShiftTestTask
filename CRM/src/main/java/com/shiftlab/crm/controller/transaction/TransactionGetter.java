package com.shiftlab.crm.controller.transaction;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.transaction.TransactionServiceImpl;

import java.util.List;

public class TransactionGetter {
    private final TransactionServiceImpl transactionServiceImpl;

    public TransactionGetter(TransactionServiceImpl transactionServiceImpl) {
        this.transactionServiceImpl = transactionServiceImpl;
    }

    public List<Transaction> getAllTransactions(Long sellerId, PaymentType paymentType) {
        return transactionServiceImpl.getTransactionsByFilter(sellerId, paymentType);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionServiceImpl.getTransactionById(transactionId);
    }
}
