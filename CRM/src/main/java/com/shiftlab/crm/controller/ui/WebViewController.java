package com.shiftlab.crm.controller.ui;

import com.shiftlab.crm.dto.PrimePeriodDto;
import com.shiftlab.crm.entity.PaymentType;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.service.seller.SellerServiceImpl;
import com.shiftlab.crm.service.transaction.TransactionServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;


import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class WebViewController {

    private final SellerServiceImpl sellerService;
    private final TransactionServiceImpl transactionService;

    public WebViewController(SellerServiceImpl sellerService, TransactionServiceImpl transactionService) {
        this.sellerService = sellerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/ui/route")
    public String routeUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/seller/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("sellers", sellerService.getSellersList());
        model.addAttribute("transactions", transactionService.getTransactionsByFilter(null, null));

        LocalDateTime startOfYear = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0);
        LocalDateTime endOfYear = LocalDateTime.now().withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59);
        model.addAttribute("rating", sellerService.getTopSellersListBySalesAmount(startOfYear, endOfYear));

        return "admin_dashboard";
    }

    @GetMapping("/seller/dashboard")
    public String sellerDashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String userEmail = principal.getName();
        Seller currentSeller = sellerService.getSellerByContactInfo(userEmail);
        Long sellerId = currentSeller.getId();

        model.addAttribute("seller", currentSeller);
        model.addAttribute("transactions", transactionService.getTransactionsByFilter(sellerId, null));
        model.addAttribute("paymentTypes", PaymentType.values());

        try {
            PrimePeriodDto primePeriod = transactionService.getPrimePeriodById(sellerId);
            model.addAttribute("primePeriod", primePeriod);
        } catch (Exception e) {
            model.addAttribute("primePeriod", null);
        }

        return "seller_dashboard";
    }
}