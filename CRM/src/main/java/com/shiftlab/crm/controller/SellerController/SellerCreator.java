package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class SellerCreator {
    private final SellerService sellerService;

    public SellerCreator(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        return sellerService.createSeller(sellerCreateDto);
    }

}
