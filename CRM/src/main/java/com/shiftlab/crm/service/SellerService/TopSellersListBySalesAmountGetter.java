package com.shiftlab.crm.service.SellerService;

import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TopSellersListBySalesAmountGetter {
    private final SellerRepository sellerRepository;

    public TopSellersListBySalesAmountGetter(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(LocalDateTime from, LocalDateTime to) {
        return sellerRepository.findTopSellersByPeriod(from, to);
    }
}
