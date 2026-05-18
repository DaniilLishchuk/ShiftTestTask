package com.shiftlab.crm.service;

import com.shiftlab.crm.Seller;
import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SellerService {
    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller createSeller(SellerCreateDto sellerCreateDto) {
        Seller seller = new Seller(
                sellerCreateDto.name(), sellerCreateDto.contactInfo(), LocalDateTime.now()
        );
        return sellerRepository.save(seller);
    }

    public List<Seller> getSellersList() {
        return sellerRepository.findAll();
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Seller " + id + " is not found")
        );
    }

    public List<SellerAnalyticsDto> getTopSellersListBySalesAmount(LocalDateTime from, LocalDateTime to) {
        return sellerRepository.findTopSellersByPeriod(from, to);
    }
}
