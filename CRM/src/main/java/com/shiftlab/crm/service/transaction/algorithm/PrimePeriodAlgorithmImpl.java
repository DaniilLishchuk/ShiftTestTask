package com.shiftlab.crm.service.transaction.algorithm;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.entity.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimePeriodAlgorithmImpl {
    private Map<LocalDate, Long> transactionsAmountByDay;

    private final TransactionQueryService transactionQueryService;
    private final PrimePeriodSearching primePeriodSearching;
    private final PrimePeriodDtoMapper primePeriodDtoMapper;

    public PrimePeriodAlgorithmImpl(List<Transaction> sellerTransactionsList) {
        this.transactionsAmountByDay = new HashMap<>();

        this.transactionQueryService = new TransactionQueryService(sellerTransactionsList);
        this.primePeriodSearching = new PrimePeriodSearching();
        this.primePeriodDtoMapper = new PrimePeriodDtoMapper();
    }

    public PrimePeriodDto processing(Long sellerId) {
        transactionsAmountByDay = transactionQueryService.getDailyTransactionCounts(sellerId);
        PrimePeriodSearching.Result primePeriod = primePeriodSearching.find(
                transactionsAmountByDay, transactionQueryService.startDate, transactionQueryService.endDate
        );
        return primePeriodDtoMapper.toDto(transactionsAmountByDay, primePeriod);
    }
}
