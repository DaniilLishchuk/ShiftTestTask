package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService.SellerServiceCli;

import java.util.List;

public class SellerGetter {
    private final SellerServiceCli sellerServiceCli;

    public SellerGetter(SellerServiceCli sellerServiceCli) {
        this.sellerServiceCli = sellerServiceCli;
    }

    public List<Seller> getAllSellers() {
        return sellerServiceCli.getSellersList();
    }

    public Seller getSellerById(Long sellerId) {
        return sellerServiceCli.getSellerById(sellerId);
    }

}
