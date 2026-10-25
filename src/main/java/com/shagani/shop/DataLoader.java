package com.shagani.shop;

import com.shagani.shop.model.Product;
import com.shagani.shop.repo.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adds the starting product list the first time the app runs (when the database is empty).
 * Prices are in rupees: (250 g, 500 g, 1 kg). Use null if a size is not sold.
 * CHECK THE 500 g PRICES - they were estimated from the 250 g and 1 kg prices.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository repo;

    public DataLoader(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) return;

        repo.saveAll(List.of(
            // ---- Best sellers ----
            new Product("healthy-bite", "Healthy Bite (Dry Fruits & Nuts Chikki)", "healthy", 350, 675, 1350, null, true),
            new Product("gondh-laddu", "Gondh Laddu", "healthy", 350, 675, 1350, null, true),
            new Product("beetroot-janthikalu", "Beetroot Janthikalu", "hots", 150, 300, 600, "New", true),
            new Product("tomato-pickle", "Tomato Pickle", "veg", 187, 374, 748, null, true),
            new Product("gavvalu", "Gavvalu", "sweets", 160, 300, 600, null, true),
            new Product("ragi-chakralu", "Ragi Chakralu / Murukulu", "hots", 150, 300, 600, null, true),
            new Product("flaxseed-laddu", "Flaxseed (Avisaginjala) Laddu", "healthy", 235, 475, 950, null, true),
            new Product("gongura-pickle", "Gongura Pickle", "veg", 187, 374, 748, null, true),
            new Product("boneless-chicken-pickle", "Boneless Chicken Pickle", "nonveg", 375, 750, 1499, null, true),
            new Product("prawns-pickle", "Prawn's Pickle", "nonveg", 500, 1000, 1999, null, true),

            // ---- Healthy snacks & sweets ----
            new Product("immunity-laddu", "Immunity Dry Fruit Laddu", "healthy", 350, 675, 1350, null, false),
            new Product("bellam-sunnunda", "Bellam Sunnunda", "healthy", 235, 450, 900, null, false),
            new Product("palli-chakki", "Palli Chakki", "healthy,sweets", 163, 325, 650, null, false),
            new Product("ariselu", "Ariselu (Regular)", "sweets", null, 300, 600, null, false),
            new Product("saggubiyyam-chekkalu", "Saggubiyyam Chekkalu", "hots", 160, 320, 640, null, false),

            // ---- Veg pickles ----
            new Product("lemon-pickle", "Lemon Pickle", "veg", 187, 374, 748, null, false),
            new Product("pandu-mirchi-pickle", "Pandu Mirchi Pickle", "veg", 187, 374, 748, null, false),
            new Product("usirikaya-pickle", "Usirikaya Pickle (Amla)", "veg", 187, 375, 750, null, false),
            new Product("kakarakaya-pickle", "Kakarakaya Pickle (Bitter Gourd)", "veg", 187, 374, 748, null, false),
            new Product("cauliflower-pickle", "Cauliflower Pickle", "veg", 187, 374, 748, "New", false),
            new Product("carrot-pickle", "Carrot Pickle", "veg", 187, 375, 750, "New", false),
            new Product("drumstick-pickle", "Drumstick (Mullakaya) Pickle", "veg", 187, 375, 750, "New", false),
            new Product("allam-pickle", "Allam Pickle (Ginger)", "veg", 200, 400, 800, null, false),

            // ---- Non-veg pickles ----
            new Product("chicken-pickle-with-bone", "Chicken Pickle (With Bone)", "nonveg", null, 600, 1200, null, false),
            new Product("mutton-boneless-pickle", "Mutton Boneless Pickle", "nonveg", 600, 1200, 2400, null, false),

            // ---- Powders & spices ----
            new Product("kandi-karam", "Kandi Karam", "powders", 250, 500, 1000, null, false),
            new Product("kakarakaya-karam", "Kakarakaya Karam", "powders", 250, 500, 1000, null, false),
            new Product("munagaku-karam", "Munagaku Karam Podi", "powders", 250, 500, 1000, null, false),
            new Product("nalla-karam", "Nalla Karam Podi", "powders", 250, 500, 1000, null, false),
            new Product("ginger-garlic-paste", "Ginger Garlic Paste", "spices", 187, 375, 750, null, false)
        ));
    }
}
