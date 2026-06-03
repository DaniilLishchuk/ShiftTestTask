package com.shiftlab.crm.dto;

import java.math.BigDecimal;

public record SellerAnalyticsDto(Long sellerId, String name, BigDecimal salesAmount, Long salesCount) {
}
