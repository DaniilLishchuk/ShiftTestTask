package com.shiftlab.crm.service.transaction.algorithm;

import com.shiftlab.crm.entity.Transaction;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionQueryService {
    private final Map<LocalDate, Long> transactionsPeriod;
    private List<Transaction> sellerTransactionsList;

    public LocalDate startDate;
    public LocalDate endDate;

    public TransactionQueryService(List<Transaction> sellerTransactionsList) {
        this.transactionsPeriod = new HashMap<>();
        this.sellerTransactionsList = sellerTransactionsList;
        this.startDate = sellerTransactionsList.get(0).getTransactionDate().toLocalDate();
        this.endDate = sellerTransactionsList.getLast().getTransactionDate().toLocalDate();
    }

    public Map<LocalDate, Long> getDailyTransactionCounts(Long sellerId) {
        Map<LocalDate, Long> transactionsAmountByDay = sellerTransactionsList.stream().collect(
                Collectors.groupingBy(
                        Transaction -> Transaction.getTransactionDate().toLocalDate(), Collectors.counting())
        );
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Long sellesCountByDay = transactionsAmountByDay.get(date);
            if (sellesCountByDay == null) sellesCountByDay = 0L;
            transactionsPeriod.put(date, sellesCountByDay);
        }
        return transactionsPeriod;
    }
}
