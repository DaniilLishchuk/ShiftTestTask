package com.shiftlab.crm.controller.TransactionController;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.dto.TransactionCreateDto;
import com.shiftlab.crm.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionControllerCli {
    TransactionService transactionService;

    private final TransactionCreator transactionCreator;
    private final TransactionGetter transactionGetter;
    private final PrimePeriodByIdGetter primePeriodByIdGetter;

    public TransactionControllerCli(TransactionService transactionService) {
        this.transactionService = transactionService;

        this.transactionCreator = new TransactionCreator(transactionService);
        this.transactionGetter = new TransactionGetter(transactionService);
        this.primePeriodByIdGetter = new PrimePeriodByIdGetter(transactionService);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionCreateDto transactionCreateDto) {
        return transactionCreator.createTransaction(transactionCreateDto);
    }

    @GetMapping
    public List<Transaction> getAllTransactions(
            @RequestParam(name = "sellerId", required = false) Long sellerId,
            @RequestParam(name = "paymentType", required = false) PaymentType paymentType
    ) {
        return transactionGetter.getAllTransactions(sellerId, paymentType);
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransactionById(@PathVariable Long transactionId) {
        return transactionGetter.getTransactionById(transactionId);
    }

    @GetMapping("/prime-period/{sellerId}")
    PrimePeriodDto getPrimePeriodById(@PathVariable Long sellerId) {
        return primePeriodByIdGetter.getPrimePeriodById(sellerId);
    }

}
