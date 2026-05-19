package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.service.TransactionService;
import org.springframework.web.bind.annotation.PathVariable;

public class PrimePeriodByIdGetter {
    private final TransactionService transactionService;

    public PrimePeriodByIdGetter(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    PrimePeriodDto getPrimePeriodById(Long sellerId) {
        return transactionService.getPrimePeriodById(sellerId);
    }
}
