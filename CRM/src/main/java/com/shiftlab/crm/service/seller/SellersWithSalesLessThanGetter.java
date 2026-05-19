package com.shiftlab.crm.service.seller;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;

import java.time.LocalDateTime;
import java.util.List;

public class SellersWithSalesLessThanGetter {
    private final SellerRepository sellerRepository;

    public SellersWithSalesLessThanGetter(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getSellersWithSalesLessThan(LocalDateTime from, LocalDateTime to, Double maxAmount) {
        return sellerRepository.findSellersWithSalesLessThan(from, to, maxAmount);
    }
}
