package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.TransactionService.TransactionServiceCli;

public class TransactionCreator {
    private final TransactionServiceCli transactionServiceCli;

    public TransactionCreator(TransactionServiceCli transactionServiceCli) {
        this.transactionServiceCli = transactionServiceCli;
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        return transactionServiceCli.createTransaction(transactionCreateDto);
    }
}
