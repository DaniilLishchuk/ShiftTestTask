package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class SellerGetter {
    private final SellerService sellerService;

    public SellerGetter(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public List<Seller> getAllSellers() {
        return sellerService.getSellersList();
    }

    public Seller getSellerById(Long sellerId) {
        return sellerService.getSellerById(sellerId);
    }

}
