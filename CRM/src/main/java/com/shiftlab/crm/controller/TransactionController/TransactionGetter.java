package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class TransactionGetter {
    private final TransactionService transactionService;

    public TransactionGetter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Transaction> getAllTransactions(Long sellerId, PaymentType paymentType) {
        return transactionService.getTransactions(sellerId, paymentType);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }
}
