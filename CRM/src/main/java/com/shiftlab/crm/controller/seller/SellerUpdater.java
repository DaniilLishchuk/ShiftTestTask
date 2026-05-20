package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.seller.SellerServiceImpl;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class SellerUpdater {
    private final SellerServiceImpl sellerServiceImpl;

    public SellerUpdater(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public Seller updateSeller(Long sellerId, SellerCreateDto sellerDto) {
        return sellerServiceImpl.updateSeller(sellerId, sellerDto);
    }
}
