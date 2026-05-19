package com.shiftlab.crm.controller;

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
public class SellerController {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public Seller createSeller(@RequestBody SellerCreateDto sellerCreateDto) {
        return sellerService.createSeller(sellerCreateDto);
    };

    @GetMapping
    public List<Seller> getAllSellers(){
        return sellerService.getSellersList();
    };

    @GetMapping("/{sellerId}")
    public Seller getSellerById(@PathVariable Long sellerId){
        return sellerService.getSellerById(sellerId);
    };

    @GetMapping("/seller-rating-by-sales")
    public List<SellerAnalyticsDto> getSellerRatingBySalesAmount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTo
    ){
        return sellerService.getTopSellersListBySalesAmount(dateFrom,dateTo);
    }
}
