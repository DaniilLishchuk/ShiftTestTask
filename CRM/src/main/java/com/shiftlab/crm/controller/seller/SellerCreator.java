package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.seller.SellerServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class SellerCreator {
    private final SellerServiceImpl sellerServiceImpl;

    public SellerCreator(SellerServiceImpl sellerServiceImpl) {
        this.sellerServiceImpl = sellerServiceImpl;
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        return sellerServiceImpl.createSeller(sellerCreateDto);
    }

}
