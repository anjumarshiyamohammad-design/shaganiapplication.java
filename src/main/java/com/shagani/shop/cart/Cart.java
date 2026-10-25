package com.shagani.shop.cart;

import com.shagani.shop.model.Product;
import com.shagani.shop.model.Size;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** The shopping cart. One per visitor (kept in the HTTP session). */
@Component
@SessionScope
public class Cart implements Serializable {

    private static final int MAX_QTY = 50;

    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(Product p, Size size) {
        Integer price = p.priceFor(size);
        if (price == null || !p.isInStock()) return;
        String key = p.getId() + "|" + size.name();
        CartItem existing = items.get(key);
        if (existing == null) {
            items.put(key, new CartItem(key, p.getId(), p.getName(), size, price, 1));
        } else if (existing.getQty() < MAX_QTY) {
            existing.setQty(existing.getQty() + 1);
        }
    }

    public void change(String key, int delta) {
        CartItem it = items.get(key);
        if (it == null) return;
        int q = it.getQty() + delta;
        if (q < 1) {
            items.remove(key);
        } else {
            it.setQty(Math.min(q, MAX_QTY));
        }
    }

    public void remove(String key) {
        items.remove(key);
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getCount() {
        return items.values().stream().mapToInt(CartItem::getQty).sum();
    }

    public int getTotal() {
        return items.values().stream().mapToInt(CartItem::getLineTotal).sum();
    }
}
