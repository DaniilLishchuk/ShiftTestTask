package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public class SellersWithLessSalesGetter {
    private final SellerService sellerService;

    public SellersWithLessSalesGetter(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public List<Seller> getSellersWithSalesLessThan(
            LocalDateTime dateFrom, LocalDateTime dateTo, Double maxAmount
    ) {
        return sellerService.getSellersWithSalesLessThan(dateFrom, dateTo, maxAmount);
    }
}
