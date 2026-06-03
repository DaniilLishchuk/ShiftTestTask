package com.shiftlab.crm.config;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(SellerRepository sellerRepository) {
        return args -> {
            if (sellerRepository.count() == 0) {
                Seller seller = new Seller();
                seller.setName("Иван Иванов (Демо)");
                seller.setContactInfo("ivan@example.com");
                seller.setRegistrationDate(LocalDateTime.now());
                seller.setBalance(BigDecimal.ZERO);

                sellerRepository.save(seller);
                System.out.println("✅ База была пустой. Демонстрационный продавец успешно создан (ID: " + seller.getId() + ")!");
            }
        };
    }
}