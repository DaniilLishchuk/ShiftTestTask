package com.shiftlab.crm.service.transaction.algorithm;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrimePeriodSearchingTest {
    private final PrimePeriodSearching primePeriodSearching = new PrimePeriodSearching();

    @Test
    void shouldFindPrimePeriodSuccessfully() {
        Map<LocalDate, Long> transactionsPeriod = new HashMap<>();
        LocalDate start = LocalDate.of(2026, 5, 1);
        LocalDate end = LocalDate.of(2026, 5, 5);

        transactionsPeriod.put(start, 5L);
        transactionsPeriod.put(start.plusDays(1), 0L);
        transactionsPeriod.put(start.plusDays(2), 20L);
        transactionsPeriod.put(start.plusDays(3), 2L);
        transactionsPeriod.put(start.plusDays(4), 1L);

        PrimePeriodSearching.Result result = primePeriodSearching.find(transactionsPeriod, start, end);

        assertNotNull(result);
        assertNotNull(result.start());
        assertNotNull(result.end());
        assertTrue(result.amount().compareTo(BigDecimal.ZERO) >= 0);

        assertEquals(start.plusDays(2), result.start());
    }
}
