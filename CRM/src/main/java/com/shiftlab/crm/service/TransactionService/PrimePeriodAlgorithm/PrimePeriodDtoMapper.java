package com.shiftlab.crm.service.TransactionService.PrimePeriodAlgorithm;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.SellerDailyTransactionCountDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrimePeriodDtoMapper {
    public PrimePeriodDto toDto(Map<LocalDate, Long> transactionsPeriod, PrimePeriodSearching.Result period) {
        List<SellerDailyTransactionCountDto> primePeriodDaysList = new ArrayList<>();
        Long totalSellesInPrimePeriod = 0L;

        for (LocalDate currentEndDate = period.start();
             !currentEndDate.isAfter(period.end());
             currentEndDate = currentEndDate.plusDays(1)) {
            Long transactionsCountByDay = transactionsPeriod.get(currentEndDate);
            primePeriodDaysList.add(new SellerDailyTransactionCountDto(currentEndDate, transactionsCountByDay));
            totalSellesInPrimePeriod += transactionsCountByDay;
        }

        return new PrimePeriodDto(period.start(), period.end(), totalSellesInPrimePeriod, primePeriodDaysList);
    }
}

