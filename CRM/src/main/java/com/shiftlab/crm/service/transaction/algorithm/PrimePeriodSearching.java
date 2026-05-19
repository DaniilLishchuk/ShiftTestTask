package com.shiftlab.crm.service.transaction.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class PrimePeriodSearching {
    public record Result(LocalDate start, LocalDate end, BigDecimal amount) {
    }

    public Result find(Map<LocalDate, Long> transactionsPeriod, LocalDate startDate, LocalDate endDate) {
        long totalSalesCount = 0L;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            totalSalesCount += transactionsPeriod.getOrDefault(date, 0L);
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        BigDecimal fine = BigDecimal.valueOf(totalSalesCount).divide(
                BigDecimal.valueOf(daysBetween),
                8, RoundingMode.HALF_UP
        );

        BigDecimal currentTransactionAmount = BigDecimal.ZERO;
        LocalDate currentStartDate = startDate;

        LocalDate primePeriodStartDate = startDate;
        LocalDate primePeriodEndDate = startDate;
        BigDecimal primePeriodTransactionAmount = BigDecimal.ZERO;

        for (LocalDate currentEndDate = startDate; !currentEndDate.isAfter(endDate); currentEndDate = currentEndDate.plusDays(1)) {
            Long salesForDay = transactionsPeriod.getOrDefault(currentEndDate, 0L);

            currentTransactionAmount = currentTransactionAmount.add(
                    BigDecimal.valueOf(salesForDay).subtract(fine)
            );

            if (currentTransactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
                currentTransactionAmount = BigDecimal.ZERO;
                currentStartDate = currentEndDate.plusDays(1);
                continue;
            }

            if (currentTransactionAmount.compareTo(primePeriodTransactionAmount) > 0) {
                primePeriodTransactionAmount = currentTransactionAmount;
                primePeriodStartDate = currentStartDate;
                primePeriodEndDate = currentEndDate;
            }
        }

        return new Result(primePeriodStartDate, primePeriodEndDate, primePeriodTransactionAmount);
    }
}