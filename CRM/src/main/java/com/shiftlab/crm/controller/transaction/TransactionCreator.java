package com.shiftlab.crm.controller.transaction;

import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.transaction.TransactionServiceImpl;

public class TransactionCreator {
    private final TransactionServiceImpl transactionServiceImpl;

    public TransactionCreator(TransactionServiceImpl transactionServiceImpl) {
        this.transactionServiceImpl = transactionServiceImpl;
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        return transactionServiceImpl.createTransaction(transactionCreateDto);
    }
}
