package com.shiftlab.crm.service.TransactionService.PrimePeriodAlgorithm;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.service.TransactionService.TransactionServiceCli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrimePeriodAlgorithmCli {
    private Map<LocalDate, Long> transactionsAmountByDay;

    private final TransactionQueryService transactionQueryService;
    private final PrimePeriodSearching primePeriodSearching;
    private final PrimePeriodDtoMapper primePeriodDtoMapper;

    public PrimePeriodAlgorithmCli(List<Transaction> sellerTransactionsList) {
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
