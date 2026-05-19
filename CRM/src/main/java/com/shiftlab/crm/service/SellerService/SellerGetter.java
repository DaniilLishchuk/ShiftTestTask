package com.shiftlab.crm.service.SellerService;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;

import java.util.List;

public class SellerGetter {
    private final SellerRepository sellerRepository;

    public SellerGetter(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public List<Seller> getSellersList() {
        return sellerRepository.findAll();
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Seller " + id + " is not found")
        );
    }
}
