package com.shiftlab.crm.service.seller;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SellerServiceImpl {
    private final SellerRepository sellerRepository;

    private final SellerCreator sellerCreator;
    private final SellerGetter sellerGetter;
    private final SellersWithSalesLessThanGetter sellersWithSalesLessThanGetter;
    private final TopSellersListBySalesAmountGetter topSellersListBySalesAmountGetter;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;

        this.sellerCreator = new SellerCreator();
        this.sellerGetter = new SellerGetter(sellerRepository);
        this.sellersWithSalesLessThanGetter = new SellersWithSalesLessThanGetter(sellerRepository);
        this.topSellersListBySalesAmountGetter = new TopSellersListBySalesAmountGetter(sellerRepository);
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        Seller seller = sellerCreator.createSeller(sellerCreateDto);
        return sellerRepository.save(seller);
    }

    public List<Seller> getSellersList() {
        return sellerGetter.getSellersList();
    }

    public Seller getSellerById(Long id) {
        return sellerGetter.getSellerById(id);
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(LocalDateTime from, LocalDateTime to) {
        return topSellersListBySalesAmountGetter.getTopSellersListBySalesAmount(from, to);
    }

    public List<Seller> getSellersWithSalesLessThan(LocalDateTime from, LocalDateTime to, Double maxAmount) {
        return sellersWithSalesLessThanGetter.getSellersWithSalesLessThan(from, to, maxAmount);
    }
}
