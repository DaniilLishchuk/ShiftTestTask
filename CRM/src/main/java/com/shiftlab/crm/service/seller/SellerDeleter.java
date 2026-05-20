package com.shiftlab.crm.service.seller;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.exception.ResourceNotFoundException;
import com.shiftlab.crm.repository.SellerRepository;

public class SellerDeleter {
    private final SellerRepository sellerRepository;

    public SellerDeleter(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void delete(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Продавец с ID " + sellerId + " не найден")
                );
        seller.setDeleted(true);
        sellerRepository.save(seller);    }
}
