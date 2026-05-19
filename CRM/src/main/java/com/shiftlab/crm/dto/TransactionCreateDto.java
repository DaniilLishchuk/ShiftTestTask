package com.shiftlab.crm.dto;

import com.shiftlab.crm.entity.PaymentType;

import java.math.BigDecimal;

public record TransactionCreateDto(Long sellerId, BigDecimal amount, PaymentType paymentType) {
}
