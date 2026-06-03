package com.shiftlab.crm.service.transaction;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import com.shiftlab.crm.service.transaction.algorithm.PrimePeriodAlgorithmImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl {
    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    private final TransactionCreator transactionCreator;
    private final TransactionGetter transactionGetter;

    public TransactionServiceImpl(TransactionRepository transactionRepository, SellerRepository sellerRepository) {
        this.transactionRepository = transactionRepository;
        this.sellerRepository = sellerRepository;

        this.transactionCreator = new TransactionCreator(sellerRepository);
        this.transactionGetter = new TransactionGetter(transactionRepository);
    }

    @Transactional
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
        return new PrimePeriodAlgorithmImpl(sellerTransactionsList).processing(sellerId);
    }
}
