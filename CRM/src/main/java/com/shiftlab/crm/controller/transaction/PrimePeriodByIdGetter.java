package com.shiftlab.crm.controller.transaction;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.service.transaction.TransactionServiceImpl;

public class PrimePeriodByIdGetter {
    private final TransactionServiceImpl transactionServiceImpl;

    public PrimePeriodByIdGetter(TransactionServiceImpl transactionServiceImpl) {
        this.transactionServiceImpl = transactionServiceImpl;
    }

    PrimePeriodDto getPrimePeriodById(Long sellerId) {
        return transactionServiceImpl.getPrimePeriodById(sellerId);
    }
}
