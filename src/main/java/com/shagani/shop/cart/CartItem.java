package com.shagani.shop.cart;

import com.shagani.shop.model.Size;

import java.io.Serializable;

public class CartItem implements Serializable {

    private final String key;
    private final Long productId;
    private final String name;
    private final Size size;
    private final int unitPrice;
    private int qty;

    public CartItem(String key, Long productId, String name, Size size, int unitPrice, int qty) {
        this.key = key;
        this.productId = productId;
        this.name = name;
        this.size = size;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public int getLineTotal() {
        return unitPrice * qty;
    }

    public String getKey() { return key; }
    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public Size getSize() { return size; }
    public int getUnitPrice() { return unitPrice; }
    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }
}
