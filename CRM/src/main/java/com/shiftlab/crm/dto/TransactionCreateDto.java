package com.shiftlab.crm.dto;

import com.shiftlab.crm.PaymentType;

import java.math.BigDecimal;

public record TransactionCreateDto(Long sellerId, BigDecimal amount, PaymentType paymentType) {
}
