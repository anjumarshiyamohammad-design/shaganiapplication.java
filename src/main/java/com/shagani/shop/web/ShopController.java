package com.shagani.shop.web;

import com.shagani.shop.model.Categories;
import com.shagani.shop.model.Product;
import com.shagani.shop.repo.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShopController {

    private final ProductRepository products;

    public ShopController(ProductRepository products) {
        this.products = products;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String cat,
                       @RequestParam(required = false) String q,
                       Model model) {

        List<Product> all = products.findAll(Sort.by("id"));

        String selected = (cat != null && Categories.ALL.containsKey(cat)) ? cat : "all";
        String query = q == null ? "" : q.trim();
        String lower = query.toLowerCase();

        List<Product> shown = all.stream()
                .filter(p -> selected.equals("all") || p.hasCategory(selected))
                .filter(p -> lower.isEmpty() || p.getName().toLowerCase().contains(lower))
                .toList();

        Map<String, Long> counts = new LinkedHashMap<>();
        for (String key : Categories.ALL.keySet()) {
            counts.put(key, all.stream().filter(p -> p.hasCategory(key)).count());
        }

        model.addAttribute("products", shown);
        model.addAttribute("best", all.stream().filter(Product::isBest).toList());
        model.addAttribute("counts", counts);
        model.addAttribute("selected", selected);
        model.addAttribute("q", query);
        return "index";
    }
}
