package com.shiftlab.crm.controller.seller;

import com.shiftlab.crm.dto.SellerAnalyticsDto;
import com.shiftlab.crm.dto.SellerCreateDto;
import com.shiftlab.crm.entity.Seller;
import com.shiftlab.crm.exception.ResourceNotFoundException;
import com.shiftlab.crm.service.seller.SellerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SellerControllerImpl.class)
class SellerControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SellerServiceImpl sellerServiceImpl;

    @Test
    @DisplayName("POST /api/sellers")
    void createSeller_ShouldReturnStatusOk() throws Exception {
        Seller mockSeller = mock(Seller.class);
        when(sellerServiceImpl.createSeller(any(SellerCreateDto.class))).thenReturn(mockSeller);

        mockMvc.perform(post("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Тестовый Продавец\",\"email\":\"test@mail.com\"}"))
                .andExpect(status().isOk());

        verify(sellerServiceImpl, times(1)).createSeller(any(SellerCreateDto.class));
    }

    @Test
    @DisplayName("GET /api/sellers")
    void getAllSellers_ShouldReturnStatusOk() throws Exception {
        Seller mockSeller = mock(Seller.class);
        when(sellerServiceImpl.getSellersList()).thenReturn(List.of(mockSeller));
        mockMvc.perform(get("/api/sellers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(sellerServiceImpl, times(1)).getSellersList();
    }

    @Test
    @DisplayName("GET /api/sellers/{sellerId}")
    void getSellerById_ShouldReturnStatusOk() throws Exception {
        Long sellerId = 1L;
        Seller mockSeller = mock(Seller.class);
        when(sellerServiceImpl.getSellerById(sellerId)).thenReturn(mockSeller);
        mockMvc.perform(get("/api/sellers/" + sellerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sellerServiceImpl, times(1)).getSellerById(sellerId);
    }

    @Test
    @DisplayName("GET /api/sellers/under-performing")
    void getSellersWithSalesLessThan_ShouldReturnStatusOk() throws Exception {
        String dateFromStr = "2026-01-01T00:00:00";
        String dateToStr = "2026-12-31T23:59:59";
        Double maxAmount = 5000.0;

        Seller mockSeller = mock(Seller.class);
        when(sellerServiceImpl.getSellersWithSalesLessThan(any(LocalDateTime.class), any(LocalDateTime.class), eq(maxAmount)))
                .thenReturn(List.of(mockSeller));

        mockMvc.perform(get("/api/sellers/under-performing")
                        .param("dateFrom", dateFromStr)
                        .param("dateTo", dateToStr)
                        .param("maxAmount", String.valueOf(maxAmount))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sellerServiceImpl, times(1))
                .getSellersWithSalesLessThan(any(LocalDateTime.class), any(LocalDateTime.class), eq(maxAmount));
    }

    @Test
    @DisplayName("GET /api/sellers/seller-rating-by-sales")
    void getSellerRatingBySalesAmount_ShouldReturnStatusOk() throws Exception {
        String dateFromStr = "2026-01-01T00:00:00";
        String dateToStr = "2026-12-31T23:59:59";

        SellerAnalyticsDto mockDto = mock(SellerAnalyticsDto.class);
        when(sellerServiceImpl.getTopSellersListBySalesAmount(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(mockDto));

        mockMvc.perform(get("/api/sellers/seller-rating-by-sales")
                        .param("dateFrom", dateFromStr)
                        .param("dateTo", dateToStr)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sellerServiceImpl, times(1))
                .getTopSellersListBySalesAmount(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("GET /api/sellers/under-performing")
    void getSellersWithSalesLessThan_ShouldReturn400_WhenMissingParameter() throws Exception {
        mockMvc.perform(get("/api/sellers/under-performing")
                        .param("dateFrom", "2026-01-01T00:00:00")
                        .param("dateTo", "2026-12-31T23:59:59")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(sellerServiceImpl, never()).getSellersWithSalesLessThan(any(), any(), any());
    }

    @Test
    @DisplayName("GET /api/sellers/{id}")
    void getSellerById_ShouldReturn404_WhenSellerNotFound() throws Exception {
        Long fakeId = 999L;
        when(sellerServiceImpl.getSellerById(fakeId))
                .thenThrow(new ResourceNotFoundException("Продавец с ID " + fakeId + " не найден"));

        mockMvc.perform(get("/api/sellers/{id}", fakeId))
                .andExpect(status().isNotFound()) // Проверяем статус 404
                .andExpect(jsonPath("$.message").value("Продавец с ID 999 не найден")); // Проверяем осмысленное сообщение
    }
}
