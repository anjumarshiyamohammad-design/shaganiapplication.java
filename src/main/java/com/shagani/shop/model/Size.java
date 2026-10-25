package com.shagani.shop.model;

/** The pack sizes we sell. */
public enum Size {
    G250("250 g"),
    G500("500 g"),
    KG1("1 kg");

    private final String label;

    Size(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
