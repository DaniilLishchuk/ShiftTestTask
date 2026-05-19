package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.seller.SellerServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class SellersWithLessSalesGetter {
    private final SellerServiceImpl sellerServiceImpl;

    public SellersWithLessSalesGetter(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public List<Seller> getSellersWithSalesLessThan(
            LocalDateTime dateFrom, LocalDateTime dateTo, Double maxAmount
    ) {
        return sellerServiceImpl.getSellersWithSalesLessThan(dateFrom, dateTo, maxAmount);
    }
}
