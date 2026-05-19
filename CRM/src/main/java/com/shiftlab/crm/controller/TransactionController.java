package com.shiftlab.crm.controller;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionCreateDto transactionCreateDto) {
        return transactionService.createTransaction(transactionCreateDto);
    }

    @GetMapping
    public List<Transaction> getAllTransactions(
            @RequestParam(name = "sellerId",  required = false) Long sellerId,
            @RequestParam(name = "paymentType", required = false) PaymentType paymentType
    ) {
        return transactionService.getTransactions(sellerId, paymentType);
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransactionById(@PathVariable Long transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @GetMapping("/prime-period/{sellerId}")
    PrimePeriodDto getPrimePeriodById(@PathVariable Long sellerId) {
        return null;
    }

}
