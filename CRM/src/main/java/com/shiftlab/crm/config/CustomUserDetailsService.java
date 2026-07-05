package com.shiftlab.crm.config;

import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.repository.SellerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SellerRepository sellerRepository;

    public CustomUserDetailsService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerRepository.findByContactInfo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));

        return User.builder()
                .username(seller.getContactInfo())
                .password(seller.getPassword())
                .authorities(seller.getRole())
                .build();
    }
}