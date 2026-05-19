package com.shiftlab.crm.service.transaction;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.entity.Transaction;
import com.shiftlab.crm.repository.SellerRepository;
import com.shiftlab.crm.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldReturnPrimePeriodDtoForSeller() {
        Long sellerId = 1L;

        Transaction tx1 = new Transaction(null, BigDecimal.TEN, null, LocalDateTime.now().minusDays(2));
        Transaction tx2 = new Transaction(null, BigDecimal.TEN, null, LocalDateTime.now().minusDays(1));

        List<Transaction> fakeTransactions = Arrays.asList(tx1, tx2);

        Mockito.when(transactionRepository.findByFilters(sellerId, null))
                .thenReturn(fakeTransactions);

        PrimePeriodDto result = transactionService.getPrimePeriodById(sellerId);

        assertNotNull(result);
    }
}
