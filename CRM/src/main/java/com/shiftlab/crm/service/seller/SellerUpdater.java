package com.shiftlab.crm.service.seller;

import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.exception.ResourceNotFoundException;
import com.shiftlab.crm.repository.SellerRepository;

public class SellerUpdater {
    private final SellerRepository sellerRepository;

    public SellerUpdater(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller updateSeller(Long sellerId, SellerCreateDto sellerDto) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Продавец с ID " + sellerId + " не найден")
                );

        seller.setName(sellerDto.name());
        seller.setContactInfo(sellerDto.contactInfo());

        return sellerRepository.save(seller);
    }
}
