package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.service.seller.SellerServiceImpl;

public class SellerDeleter {
    public final SellerServiceImpl sellerServiceImpl;

    public SellerDeleter(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public void deleteSeller(Long sellerId) {
        sellerServiceImpl.deleteSeller(sellerId);
    }
}
