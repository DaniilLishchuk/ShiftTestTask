package com.shiftlab.crm.repository;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.dto.SellerAnalyticsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SellerRepository  extends JpaRepository<Seller, Long> {
    @Query("SELECT new com.shiftlab.crm.dto.SellerAnalyticsDto(s.id, s.name, SUM(u.amount)) " +
            "FROM Transaction u " +
            "JOIN u.seller s " +
            "WHERE u.transactionDate BETWEEN :dateFrom AND :dateTo " +
            "GROUP BY s.id, s.name " +
            "ORDER BY SUM(u.amount) DESC")
    List<SellerAnalyticsDto> findTopSellersByPeriod(
            @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo
    );
}
