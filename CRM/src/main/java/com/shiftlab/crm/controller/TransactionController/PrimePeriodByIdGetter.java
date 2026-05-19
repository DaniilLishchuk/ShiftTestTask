package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.service.TransactionService.TransactionServiceCli;

public class PrimePeriodByIdGetter {
    private final TransactionServiceCli transactionServiceCli;

    public PrimePeriodByIdGetter(TransactionServiceCli transactionServiceCli) {
        this.transactionServiceCli = transactionServiceCli;
    }

    PrimePeriodDto getPrimePeriodById(Long sellerId) {
        return transactionServiceCli.getPrimePeriodById(sellerId);
    }
}
