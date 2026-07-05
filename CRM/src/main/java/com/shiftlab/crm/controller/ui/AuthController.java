package com.shiftlab.crm.controller.ui;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class AuthController {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(SellerRepository sellerRepository, PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String contactInfo,
                               @RequestParam String password) {
        if (sellerRepository.findByContactInfo(contactInfo).isPresent()) {
            return "redirect:/register?error=email_exists";
        }

        Seller seller = new Seller();
        seller.setName(name);
        seller.setContactInfo(contactInfo);
        seller.setPassword(passwordEncoder.encode(password));
        seller.setRole("ROLE_SELLER");
        seller.setRegistrationDate(LocalDateTime.now());
        seller.setBalance(BigDecimal.ZERO);

        sellerRepository.save(seller);

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}