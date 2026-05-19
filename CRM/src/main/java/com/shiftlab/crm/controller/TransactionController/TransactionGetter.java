package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.TransactionService.TransactionServiceCli;

import java.util.List;

public class TransactionGetter {
    private final TransactionServiceCli transactionServiceCli;

    public TransactionGetter(TransactionServiceCli transactionServiceCli) {
        this.transactionServiceCli = transactionServiceCli;
    }

    public List<Transaction> getAllTransactions(Long sellerId, PaymentType paymentType) {
        return transactionServiceCli.getTransactionsByFilter(sellerId, paymentType);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionServiceCli.getTransactionById(transactionId);
    }
}
