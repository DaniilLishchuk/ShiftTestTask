package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.service.seller.SellerServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class SellerRatingBySalesAmountGetter {
    private final SellerServiceImpl sellerServiceImpl;

    public SellerRatingBySalesAmountGetter(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(
            LocalDateTime dateFrom, LocalDateTime dateTo
    ) {
        return sellerServiceImpl.getTopSellersListBySalesAmount(dateFrom, dateTo);
    }
}
