package com.revature.memestore.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    @Column(nullable = false, unique = true)
    private String item_name;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private float cost;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String item_image;

    public Inventory(){

    }

    public Inventory(String item_name, String details, float cost, String category, String item_image) {
        this.item_name = item_name;
        this.details = details;
        this.cost = cost;
        this.category = category;
        this.item_image = item_image;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public Inventory setItem_name(String item_name) {
        this.item_name = item_name;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public Inventory setDetails(String details) {
        this.details = details;
        return this;
    }

    public float getCost() {
        return cost;
    }

    public Inventory setCost(float cost) {
        this.cost = cost;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Inventory setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getItem_image() {
        return item_image;
    }

    public Inventory setItem_image(String item_image) {
        this.item_image = item_image;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return item_id == inventory.item_id &&
                Float.compare(inventory.cost, cost) == 0 &&
                Objects.equals(item_name, inventory.item_name) &&
                Objects.equals(details, inventory.details) &&
                Objects.equals(category, inventory.category) &&
                Objects.equals(item_image, inventory.item_image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, item_name, details, cost, category, item_image);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "item_id=" + item_id +
                ", item_name='" + item_name + '\'' +
                ", details='" + details + '\'' +
                ", cost=" + cost +
                ", category='" + category + '\'' +
                ", item_image='" + item_image + '\'' +
                '}';
    }
}
