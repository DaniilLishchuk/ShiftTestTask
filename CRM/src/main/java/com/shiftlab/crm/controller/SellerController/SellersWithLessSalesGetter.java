package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService.SellerServiceCli;

import java.time.LocalDateTime;
import java.util.List;

public class SellersWithLessSalesGetter {
    private final SellerServiceCli sellerServiceCli;

    public SellersWithLessSalesGetter(SellerServiceCli sellerServiceCli) {
        this.sellerServiceCli = sellerServiceCli;
    }

    public List<Seller> getSellersWithSalesLessThan(
            LocalDateTime dateFrom, LocalDateTime dateTo, Double maxAmount
    ) {
        return sellerServiceCli.getSellersWithSalesLessThan(dateFrom, dateTo, maxAmount);
    }
}
