package com.gocgod.model;

import com.gocgod.cart.model.Saleable;

import java.math.BigDecimal;

/**
 * Created by Kevin on 8/24/2016.
 */
public class Product implements Saleable {
    private int id;
    private String name;
    private BigDecimal price;
    private String category;
    private String picture;

    public Product(){super();}

    public Product(int id, String name, BigDecimal price, String category, String picture)
    {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setCategory(category);
        this.setPicture(picture);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Product)) return false;

        return (this.getId() == ((Product) o).getId());
    }

    public int hashCode() {
        //final int prime = 31;
        final int prime = 15;
        int hash = 1;
        hash = hash * prime + id;
        hash = hash * prime + (name == null ? 0 : name.hashCode());
        hash = hash * prime + (price == null ? 0 : price.hashCode());

        return hash;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
