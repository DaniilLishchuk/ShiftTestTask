package com.shiftlab.crm.config;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(SellerRepository sellerRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (sellerRepository.count() == 0) {
                Seller admin = new Seller();
                admin.setName("Главный Администратор");
                admin.setContactInfo("admin@crm.com"); // Это его логин
                admin.setPassword(passwordEncoder.encode("admin123")); // Пароль: admin123
                admin.setRole("ROLE_ADMIN");
                admin.setRegistrationDate(LocalDateTime.now());
                admin.setBalance(BigDecimal.ZERO);
                sellerRepository.save(admin);

                System.out.println("✅ База инициализирована. Создан Админ (Логин: admin@crm.com, Пароль: admin123)");
            }
        };
    }
}