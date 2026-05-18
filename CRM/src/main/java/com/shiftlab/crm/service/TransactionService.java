package com.shiftlab.crm.service;

import com.shiftlab.crm.PaymentType;
import com.shiftlab.crm.Seller;
import com.shiftlab.crm.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public PrimePeriodDto getPrimePeriodById(Long sellerId) {
        Map<LocalDate,Long> transactionsPeriod = new HashMap<>();
        List<Transaction> sellerTransactionsList = getTransactions(sellerId, null);
        LocalDate startDate = sellerTransactionsList.get(0).getTransactionDate().toLocalDate();
        LocalDate endDate = sellerTransactionsList.getLast().getTransactionDate().toLocalDate();
        Map<LocalDate,Long> transactionsAmountByDay = sellerTransactionsList.stream().collect(Collectors.groupingBy(
                        Transaction -> Transaction.getTransactionDate().toLocalDate(), Collectors.counting())
        );
        Long totalSellesCount = 0L;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            Long sellesCountByDay = transactionsAmountByDay.get(date);
            if (sellesCountByDay == null) {
                sellesCountByDay = 0L;
            }
            totalSellesCount += sellesCountByDay;
            transactionsPeriod.put(date, sellesCountByDay);
        }
        BigDecimal fine = BigDecimal.valueOf(totalSellesCount).divide(
                BigDecimal.valueOf(ChronoUnit.DAYS.between(startDate, endDate)+1),
                8, RoundingMode.HALF_UP
        );

    }

}
