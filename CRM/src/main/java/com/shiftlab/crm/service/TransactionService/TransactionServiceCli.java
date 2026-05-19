package com.shiftlab.crm.service.TransactionService;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.SellerDailyTransactionCountDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import com.shiftlab.crm.service.TransactionService.PrimePeriodAlgorithm.PrimePeriodAlgorithmCli;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceCli {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    private final TransactionCreator transactionCreator;
    private final TransactionGetter transactionGetter;

    public TransactionServiceCli(TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;

        this.transactionCreator = new TransactionCreator(sellerRepository);
        this.transactionGetter = new TransactionGetter(transactionRepository);
    }

    public Transaction createTransaction(TransactionCreateDto transactionCreateDto) {
        Transaction transaction = transactionCreator.createTransaction(transactionCreateDto);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionGetter.getAllTransactions();
    }

    public Transaction getTransactionById(long id) {
        return transactionGetter.getTransactionById(id);
    }

    public List<Transaction> getTransactionsByFilter(Long sellerId, PaymentType paymentType) {
        return transactionGetter.getTransactionsByFilter(sellerId, paymentType);
    }

    public PrimePeriodDto getPrimePeriodById(Long sellerId) {
        List<Transaction> sellerTransactionsList = getTransactionsByFilter(sellerId, null);
        return new PrimePeriodAlgorithmCli(sellerTransactionsList).processing(sellerId);
    }
}
