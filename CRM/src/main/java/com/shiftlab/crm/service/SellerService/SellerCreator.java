package com.shiftlab.crm.service.SellerService;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;

import java.time.LocalDateTime;

public class SellerCreator {
    public SellerCreator() {
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        Seller seller = new Seller(sellerCreateDto.name(), sellerCreateDto.contactInfo(), LocalDateTime.now());
        return seller;
    }
}
