package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.TransactionService;
import org.springframework.web.bind.annotation.RequestBody;

public class TransactionCreator {
    private final TransactionService transactionService;

    public TransactionCreator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        return transactionService.createTransaction(transactionCreateDto);
    }
}
