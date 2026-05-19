package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.service.SellerService.SellerServiceCli;

import java.time.LocalDateTime;
import java.util.List;

public class SellerRatingBySalesAmountGetter {
    private final SellerServiceCli sellerServiceCli;

    public SellerRatingBySalesAmountGetter(SellerServiceCli sellerServiceCli) {
        this.sellerServiceCli = sellerServiceCli;
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(
            LocalDateTime dateFrom, LocalDateTime dateTo
    ) {
        return sellerServiceCli.getTopSellersListBySalesAmount(dateFrom, dateTo);
    }
}
