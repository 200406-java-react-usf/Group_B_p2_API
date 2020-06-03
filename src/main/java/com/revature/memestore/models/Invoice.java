package com.revature.memestore.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private User user_id;

    @Column(nullable = false)
    private float total_cost;

    @Column(nullable = false)
    private String date_ordered;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "inventory_invoices",
            joinColumns = @JoinColumn(name="item_id"),
            inverseJoinColumns = @JoinColumn(name="invoice_id")
    )
    private List<Inventory> inventories;

    public Invoice(User user_id, float total_cost, String date_ordered) {
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.date_ordered = date_ordered;
    }

    public int getItem_id() {
        return item_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public Invoice setUser_id(User user_id) {
        this.user_id = user_id;
        return this;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public Invoice setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
        return this;
    }

    public String getDate_ordered() {
        return date_ordered;
    }

    public Invoice setDate_ordered(String date_ordered) {
        this.date_ordered = date_ordered;
        return this;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return item_id == invoice.item_id &&
                Float.compare(invoice.total_cost, total_cost) == 0 &&
                Objects.equals(user_id, invoice.user_id) &&
                Objects.equals(date_ordered, invoice.date_ordered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, user_id, total_cost, date_ordered);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "item_id=" + item_id +
                ", user_id='" + user_id + '\'' +
                ", total_cost=" + total_cost +
                ", date_ordered='" + date_ordered + '\'' +
                '}';
    }
}
