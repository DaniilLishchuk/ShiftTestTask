package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.service.SellerService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerControllerCli {
    private final SellerService sellerService;

    private final SellerCreator sellerCreator;
    private final SellerGetter sellerGetter;
    private final SellersWithLessSalesGetter sellersWithLessSalesGetter;
    private final SellerRatingBySalesAmountGetter sellerRatingBySalesAmountGetter;

    public SellerControllerCli(SellerService sellerService) {
        this.sellerService = sellerService;

        this.sellerCreator = new SellerCreator(sellerService);
        this.sellerGetter = new SellerGetter(sellerService);
        this.sellersWithLessSalesGetter = new SellersWithLessSalesGetter(sellerService);
        this.sellerRatingBySalesAmountGetter = new SellerRatingBySalesAmountGetter(sellerService);
    }

    @PostMapping
    public Seller createSeller(@RequestBody SellerCreateDto sellerCreateDto) {
        return sellerCreator.createSeller(sellerCreateDto);
    }

    @GetMapping
    public List<Seller> getAllSellers() {
        return sellerGetter.getAllSellers();
    }

    @GetMapping("/{sellerId}")
    public Seller getSellerById(@PathVariable Long sellerId) {
        return sellerService.getSellerById(sellerId);
    }

    @GetMapping("/under-performing")
    public List<Seller> getSellersWithSalesLessThan(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam Double maxAmount
    ) {
        return sellersWithLessSalesGetter.getSellersWithSalesLessThan(dateFrom, dateTo, maxAmount);
    }

    @GetMapping("/seller-rating-by-sales")
    public List<SellerAnalyticsDto> getSellerRatingBySalesAmount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo
    ) {
        return sellerRatingBySalesAmountGetter.getTopSellersListBySalesAmount(dateFrom, dateTo);
    }

}
