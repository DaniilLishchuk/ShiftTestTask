package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.service.SellerService;

import java.time.LocalDateTime;
import java.util.List;

public class SellerRatingBySalesAmountGetter {
    private final SellerService sellerService;

    public SellerRatingBySalesAmountGetter(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(
            LocalDateTime dateFrom, LocalDateTime dateTo
    ) {
        return sellerService.getTopSellersListBySalesAmount(dateFrom, dateTo);
    }
}
