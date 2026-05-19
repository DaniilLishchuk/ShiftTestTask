package com.shiftlab.crm.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sellers")
public class Seller {
    public Seller() {

    }

    public Seller(String name, String contactInfo, LocalDateTime registrationDate) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.registrationDate = registrationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "seller_name", nullable = false, length = 50)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Column(name = "seller_contact_info", nullable = false, length = 50)
    private String contactInfo;

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    @Column(name = "seller_registration_date", nullable = false)
    private LocalDateTime registrationDate;

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

}
