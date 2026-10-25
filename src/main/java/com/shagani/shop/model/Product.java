package com.shagani.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Short unique name, used as the start of the uploaded photo's file name. */
    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    /** Comma separated category keys, e.g. "healthy,sweets" */
    @Column(nullable = false)
    private String categories;

    /** Price in rupees for each size. null = that size is not sold. */
    private Integer price250;
    private Integer price500;
    private Integer price1000;

    private String tag;
    private boolean best;
    private boolean inStock = true;

    /** File name of the uploaded photo (inside the uploads/products folder). null = no photo yet. */
    private String imageFile;

    protected Product() {}

    public Product(String slug, String name, String categories,
                   Integer price250, Integer price500, Integer price1000,
                   String tag, boolean best) {
        this.slug = slug;
        this.name = name;
        this.categories = categories;
        this.price250 = price250;
        this.price500 = price500;
        this.price1000 = price1000;
        this.tag = tag;
        this.best = best;
    }

    public Integer priceFor(Size size) {
        return switch (size) {
            case G250 -> price250;
            case G500 -> price500;
            case KG1 -> price1000;
        };
    }

    public List<Size> getAvailableSizes() {
        List<Size> list = new ArrayList<>();
        for (Size s : Size.values()) {
            if (priceFor(s) != null) list.add(s);
        }
        return list;
    }

    public int getLowestPrice() {
        return getAvailableSizes().stream().mapToInt(this::priceFor).min().orElse(0);
    }

    public int getHighestPrice() {
        return getAvailableSizes().stream().mapToInt(this::priceFor).max().orElse(0);
    }

    public List<String> getCategoryKeys() {
        return Arrays.asList(categories.split(","));
    }

    public boolean hasCategory(String key) {
        return getCategoryKeys().contains(key);
    }

    public Categories.Cat getPrimaryCat() {
        return Categories.ALL.get(getCategoryKeys().get(0));
    }

    public String getCategoryLabels() {
        return getCategoryKeys().stream()
                .map(k -> Categories.ALL.get(k).getLabel())
                .reduce((a, b) -> a + ", " + b).orElse("");
    }

    public Long getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getTag() { return tag; }
    public boolean isBest() { return best; }
    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }
    public String getImageFile() { return imageFile; }
    public void setImageFile(String imageFile) { this.imageFile = imageFile; }
}
