package com.shiftlab.crm.service;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.SellerDailyTransactionCountDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public PrimePeriodDto getPrimePeriodById(Long sellerId){
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

        BigDecimal currentTransactionAmount = BigDecimal.valueOf(0);
        LocalDate currentStartDate = startDate;
        LocalDate primePeriodStartDate = startDate;
        LocalDate primePeriodEndDate = startDate;
        BigDecimal primePeriodTransactionAmount = BigDecimal.valueOf(0);

        for(LocalDate currentEndDate = startDate;
            currentEndDate != endDate.plusDays(1);
            currentEndDate=currentEndDate.plusDays(1)
        ){
            currentTransactionAmount = currentTransactionAmount.add(
                    BigDecimal.valueOf(transactionsPeriod.get(currentEndDate)).subtract(fine));

            if(currentTransactionAmount.compareTo(BigDecimal.valueOf(0)) < 0){
                currentTransactionAmount = BigDecimal.valueOf(0);
                currentStartDate = currentEndDate.plusDays(1);
                continue;
            }

            if(currentTransactionAmount.compareTo(primePeriodTransactionAmount) > 0){
                primePeriodTransactionAmount = currentTransactionAmount;
                primePeriodStartDate = currentStartDate;
                primePeriodEndDate = currentEndDate;
            }
        }
        List<SellerDailyTransactionCountDto> primePeriodDaysList = new ArrayList<>();
        SellerDailyTransactionCountDto currentPrimePeriodDay;
        Long totalSellesInPrimePeriod = 0L;
        for(LocalDate currentEndDate = primePeriodStartDate;
            !currentEndDate.isAfter(primePeriodEndDate);
            currentEndDate=currentEndDate.plusDays(1)) {
            Long transactionsCountByDay = transactionsPeriod.get(currentEndDate);
            primePeriodDaysList.add(new SellerDailyTransactionCountDto(currentEndDate, transactionsCountByDay));
            totalSellesInPrimePeriod += transactionsCountByDay;
        }


        return new PrimePeriodDto(primePeriodStartDate, primePeriodEndDate, totalSellesInPrimePeriod, primePeriodDaysList);


    }

}
