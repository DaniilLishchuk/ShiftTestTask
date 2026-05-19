package com.shiftlab.crm.service.transaction;

import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.exception.ResourceNotFoundException;
import com.shiftlab.crm.repository.SellerRepository;

import java.time.LocalDateTime;

public class TransactionCreator {
    private final SellerRepository sellerRepository;

    public TransactionCreator(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        Seller seller = sellerRepository.findById(transactionCreateDto.sellerId()).orElseThrow(
                () -> new ResourceNotFoundException("Seller " + transactionCreateDto.sellerId() + " is not found")
        );
        return new Transaction(
                seller, transactionCreateDto.amount(), transactionCreateDto.paymentType(), LocalDateTime.now()
        );
    }
}
