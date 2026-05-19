package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.seller.SellerServiceImpl;

import java.util.List;

public class SellerGetter {
    private final SellerServiceImpl sellerServiceImpl;

    public SellerGetter(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public List<Seller> getAllSellers() {
        return sellerServiceImpl.getSellersList();
    }

    public Seller getSellerById(Long sellerId) {
        return sellerServiceImpl.getSellerById(sellerId);
    }

}
