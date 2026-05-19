package com.shiftlab.crm.repository;

import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT u FROM Transaction u WHERE" +
            "(:sellerId IS NULL OR u.seller.id = :sellerId) AND " +
            "(:paymentType IS NULL OR u.paymentType = :paymentType)")
    List<Transaction> findByFilters(@Param("sellerId") Long sellerId,
                                    @Param("paymentType") PaymentType paymentType);

}
