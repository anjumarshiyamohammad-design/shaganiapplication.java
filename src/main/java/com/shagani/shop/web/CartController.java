package com.shagani.shop.web;

import com.shagani.shop.cart.Cart;
import com.shagani.shop.cart.CartItem;
import com.shagani.shop.model.CustomerOrder;
import com.shagani.shop.model.Size;
import com.shagani.shop.repo.OrderRepository;
import com.shagani.shop.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

@Controller
public class CartController {

    private final Cart cart;
    private final ProductRepository products;
    private final OrderRepository orders;

    @Value("${shagani.whatsapp}")
    private String whatsapp;

    public CartController(Cart cart, ProductRepository products, OrderRepository orders) {
        this.cart = cart;
        this.products = products;
        this.orders = orders;
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam Long id, @RequestParam String size, RedirectAttributes ra) {
        try {
            Size s = Size.valueOf(size);
            products.findById(id).ifPresent(p -> {
                cart.add(p, s);
                ra.addFlashAttribute("toast", p.getName() + " (" + s.getLabel() + ") added to cart");
            });
        } catch (IllegalArgumentException ignored) {
            // unknown size value – ignore
        }
        return "redirect:/#shop";
    }

    @GetMapping("/cart")
    public String view(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new CheckoutForm());
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/cart/update")
    public String update(@RequestParam String key, @RequestParam int delta) {
        cart.change(key, delta > 0 ? 1 : -1);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam String key) {
        cart.remove(key);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("form") CheckoutForm form,
                           BindingResult errors,
                           Model model,
                           RedirectAttributes ra) {
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        if (errors.hasErrors()) {
            model.addAttribute("cart", cart);
            return "cart";
        }

        // Build the order text (one line per item)
        StringJoiner lines = new StringJoiner("\n");
        int n = 1;
        for (CartItem it : cart.getItems()) {
            lines.add(n++ + ". " + it.getName() + " (" + it.getSize().getLabel() + ") x " + it.getQty()
                    + " = ₹" + it.getLineTotal());
        }
        int total = cart.getTotal();

        CustomerOrder saved = orders.save(new CustomerOrder(
                form.getName().trim(), form.getPhone().trim(), form.getAddress().trim(),
                form.getPayment(), lines.toString(), total));

        // Message the customer can send to the shop on WhatsApp
        String msg = "*New order #" + saved.getId() + " – Shagani*\n\n"
                + lines + "\n\n*Total: ₹" + total + "*\nPayment: " + form.getPayment()
                + "\n\nName: " + form.getName().trim()
                + "\nPhone: " + form.getPhone().trim()
                + "\nAddress: " + form.getAddress().trim();
        String waUrl = "https://wa.me/" + whatsapp + "?text="
                + URLEncoder.encode(msg, StandardCharsets.UTF_8).replace("+", "%20");

        cart.clear();
        ra.addFlashAttribute("orderId", saved.getId());
        ra.addFlashAttribute("orderTotal", total);
        ra.addFlashAttribute("payment", form.getPayment());
        ra.addFlashAttribute("waUrl", waUrl);
        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String success(Model model) {
        // Only available right after placing an order (data comes from the redirect)
        if (!model.containsAttribute("orderId")) {
            return "redirect:/";
        }
        return "success";
    }
}
