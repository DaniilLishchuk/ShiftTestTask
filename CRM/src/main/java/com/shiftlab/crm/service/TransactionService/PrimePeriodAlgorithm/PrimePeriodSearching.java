package com.shiftlab.crm.service.TransactionService.PrimePeriodAlgorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class PrimePeriodSearching {
    public record Result(LocalDate start, LocalDate end, BigDecimal amount) {
    }

    public Result find(Map<LocalDate, Long> transactionsPeriod, LocalDate startDate, LocalDate endDate) {
        Long totalSellesCount = 0L;
        for (Long count : transactionsPeriod.values()) totalSellesCount += count;

        BigDecimal fine = BigDecimal.valueOf(totalSellesCount).divide(
                BigDecimal.valueOf(ChronoUnit.DAYS.between(startDate, endDate) + 1),
                8, RoundingMode.HALF_UP
        );

        BigDecimal currentTransactionAmount = BigDecimal.valueOf(0);
        LocalDate currentStartDate = startDate;
        LocalDate primePeriodStartDate = startDate;
        LocalDate primePeriodEndDate = startDate;
        BigDecimal primePeriodTransactionAmount = BigDecimal.valueOf(0);

        for (LocalDate currentEndDate = startDate;
             currentEndDate != endDate.plusDays(1);
             currentEndDate = currentEndDate.plusDays(1)
        ) {
            currentTransactionAmount = currentTransactionAmount.add(
                    BigDecimal.valueOf(transactionsPeriod.get(currentEndDate)).subtract(fine));

            if (currentTransactionAmount.compareTo(BigDecimal.valueOf(0)) < 0) {
                currentTransactionAmount = BigDecimal.valueOf(0);
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
