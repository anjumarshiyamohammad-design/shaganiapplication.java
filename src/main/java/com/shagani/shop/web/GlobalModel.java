package com.shagani.shop.web;

import com.shagani.shop.cart.Cart;
import com.shagani.shop.model.Categories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

/** Values every page needs (header cart, phone number, footer details). */
@ControllerAdvice
public class GlobalModel {

    private final Cart cart;

    @Value("${shagani.whatsapp}")
    private String whatsapp;

    @Value("${shagani.phone-display}")
    private String phoneDisplay;

    @Value("${shagani.location}")
    private String location;

    @Value("${shagani.fssai:}")
    private String fssai;

    public GlobalModel(Cart cart) {
        this.cart = cart;
    }

    @ModelAttribute("cartCount")
    public int cartCount() { return cart.getCount(); }

    @ModelAttribute("cartTotal")
    public int cartTotal() { return cart.getTotal(); }

    @ModelAttribute("whatsapp")
    public String whatsapp() { return whatsapp; }

    @ModelAttribute("phoneDisplay")
    public String phoneDisplay() { return phoneDisplay; }

    @ModelAttribute("location")
    public String location() { return location; }

    @ModelAttribute("fssai")
    public String fssai() { return fssai; }

    @ModelAttribute("cats")
    public Map<String, Categories.Cat> cats() { return Categories.ALL; }
}
