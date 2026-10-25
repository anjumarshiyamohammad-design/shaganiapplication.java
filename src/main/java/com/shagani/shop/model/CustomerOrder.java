package com.shagani.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/** One order placed on the website. */
@Entity
@Table(name = "customer_orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String phone;

    @Column(length = 1000)
    private String address;

    private String payment;

    /** One line per item, e.g. "1. Tomato Pickle (250 g) x 2 = ₹374" */
    @Column(length = 3000)
    private String details;

    private int total;

    /** NEW or DONE */
    private String status = "NEW";

    private LocalDateTime createdAt = LocalDateTime.now();

    protected CustomerOrder() {}

    public CustomerOrder(String customerName, String phone, String address,
                         String payment, String details, int total) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.payment = payment;
        this.details = details;
        this.total = total;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getPayment() { return payment; }
    public String getDetails() { return details; }
    public int getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    /** Date text for the admin page, e.g. "13 Oct 2026, 04:07 AM" */
    public String getCreatedAtText() {
        return createdAt.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", java.util.Locale.ENGLISH));
    }
}
