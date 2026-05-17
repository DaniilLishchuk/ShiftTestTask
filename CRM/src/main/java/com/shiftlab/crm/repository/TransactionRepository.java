package com.shiftlab.crm.repository;

import com.shiftlab.crm.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
