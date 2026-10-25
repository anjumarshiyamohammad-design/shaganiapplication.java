package com.shagani.shop.web;

import com.shagani.shop.repo.OrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/** Owner-only page that lists the orders. Needs the admin password (see SecurityConfig). */
@Controller
public class AdminController {

    private final OrderRepository orders;

    public AdminController(OrderRepository orders) {
        this.orders = orders;
    }

    @GetMapping("/admin/orders")
    public String list(Model model) {
        model.addAttribute("orders", orders.findAllByOrderByIdDesc());
        return "admin-orders";
    }

    @PostMapping("/admin/orders/{id}/toggle")
    public String toggle(@PathVariable Long id) {
        orders.findById(id).ifPresent(o -> {
            o.setStatus("DONE".equals(o.getStatus()) ? "NEW" : "DONE");
            orders.save(o);
        });
        return "redirect:/admin/orders";
    }
}
