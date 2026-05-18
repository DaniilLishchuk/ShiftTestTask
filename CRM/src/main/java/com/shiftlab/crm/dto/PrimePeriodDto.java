package com.shiftlab.crm.dto;

import java.time.LocalDate;
import java.util.List;

public record PrimePeriodDto(
        LocalDate periodFrom, LocalDate periodTo,
        Long totalSalesCount, List<SellerDailyTransactionCountDto> salesAmountByDay
) {

}
