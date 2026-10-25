package com.shagani.shop.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** The details the customer types on the cart page. */
public class CheckoutForm {

    @NotBlank(message = "Please enter your name")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Please enter your phone number")
    @Pattern(regexp = "^[0-9+\\s-]{10,15}$", message = "Enter a valid phone number (10 to 15 digits)")
    private String phone;

    @NotBlank(message = "Please enter your delivery address")
    @Size(max = 500)
    private String address;

    @Pattern(regexp = "Cash|UPI", message = "Choose Cash or UPI")
    private String payment = "Cash";

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPayment() { return payment; }
    public void setPayment(String payment) { this.payment = payment; }
}
