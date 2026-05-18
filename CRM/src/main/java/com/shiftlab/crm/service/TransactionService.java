package com.shiftlab.crm.service;

import com.shiftlab.crm.PaymentType;
import com.shiftlab.crm.Seller;
import com.shiftlab.crm.Transaction;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    public TransactionService(TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        Seller seller = sellerRepository.findById(transactionCreateDto.sellerId()).orElseThrow(
                () -> new RuntimeException("Seller " + transactionCreateDto.sellerId() + " is not found")
        );
        Transaction transaction = new Transaction(
                seller, transactionCreateDto.amount(), transactionCreateDto.paymentType(), LocalDateTime.now()
        );
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Transaction " + id + " is not found")
        );
    }

    public List<Transaction> getTransactions(Long sellerId, PaymentType paymentType) {
        return transactionRepository.findByFilters(sellerId, paymentType);
    }

}
