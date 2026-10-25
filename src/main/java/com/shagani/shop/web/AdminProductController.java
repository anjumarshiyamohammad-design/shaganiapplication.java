package com.shagani.shop.web;

import com.shagani.shop.model.Product;
import com.shagani.shop.repo.ProductRepository;
import com.shagani.shop.storage.ImageStorage;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/** Owner-only page for adding or changing product photos (needs the admin login). */
@Controller
public class AdminProductController {

    private final ProductRepository products;
    private final ImageStorage storage;

    public AdminProductController(ProductRepository products, ImageStorage storage) {
        this.products = products;
        this.storage = storage;
    }

    @GetMapping("/admin/products")
    public String list(Model model) {
        model.addAttribute("products", products.findAll(Sort.by("id")));
        return "admin-products";
    }

    @PostMapping("/admin/products/{id}/photo")
    public String upload(@PathVariable Long id,
                         @RequestParam("file") MultipartFile file,
                         RedirectAttributes ra) {
        Product p = products.findById(id).orElse(null);
        if (p == null) {
            return "redirect:/admin/products";
        }
        try {
            String newName = storage.save(p.getSlug(), file);
            String oldName = p.getImageFile();
            p.setImageFile(newName);
            products.save(p);
            storage.delete(oldName);
            ra.addFlashAttribute("msg", "Photo saved for " + p.getName());
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        } catch (IOException e) {
            ra.addFlashAttribute("error", "Could not save the photo. Please try again.");
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/{id}/photo/delete")
    public String deletePhoto(@PathVariable Long id, RedirectAttributes ra) {
        products.findById(id).ifPresent(p -> {
            String old = p.getImageFile();
            p.setImageFile(null);
            products.save(p);
            storage.delete(old);
            ra.addFlashAttribute("msg", "Photo removed for " + p.getName());
        });
        return "redirect:/admin/products";
    }
}
