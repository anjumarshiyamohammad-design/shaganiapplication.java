package com.shagani.shop.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Category keys, display names, emoji and tile colours. */
public final class Categories {

    public static final class Cat {
        private final String label;
        private final String emoji;
        private final String bg;

        Cat(String label, String emoji, String bg) {
            this.label = label;
            this.emoji = emoji;
            this.bg = bg;
        }

        public String getLabel() { return label; }
        public String getEmoji() { return emoji; }
        public String getBg() { return bg; }
    }

    public static final Map<String, Cat> ALL;

    static {
        Map<String, Cat> m = new LinkedHashMap<>();
        m.put("veg", new Cat("Veg Pickles", "🫙", "#fde7c4"));
        m.put("nonveg", new Cat("Non-Veg Pickles", "🍗", "#fcd5cf"));
        m.put("sweets", new Cat("Sweets", "🍬", "#fbe0ef"));
        m.put("hots", new Cat("Hots", "🌶️", "#ffe0c2"));
        m.put("powders", new Cat("Powders", "🥄", "#f4e3c3"));
        m.put("healthy", new Cat("Healthy Snacks", "🥜", "#e3f0cf"));
        m.put("spices", new Cat("Spices", "🧄", "#efe9d0"));
        ALL = Collections.unmodifiableMap(m);
    }

    private Categories() {}
}
