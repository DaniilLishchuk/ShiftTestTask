package com.shiftlab.crm.controller.SellerController;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.SellerService.SellerServiceCli;
import org.springframework.stereotype.Component;

@Component
public class SellerCreator {
    private final SellerServiceCli sellerServiceCli;

    public SellerCreator(SellerServiceCli sellerServiceCli) {
        this.sellerServiceCli = sellerServiceCli;
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        return sellerServiceCli.createSeller(sellerCreateDto);
    }

}
