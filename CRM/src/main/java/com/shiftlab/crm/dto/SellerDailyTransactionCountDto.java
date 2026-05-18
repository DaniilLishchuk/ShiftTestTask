package com.shiftlab.crm.dto;

import java.time.LocalDate;

public record SellerDailyTransactionCountDto(LocalDate date, Long salesCount) {
}
