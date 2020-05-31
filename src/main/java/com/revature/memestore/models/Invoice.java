package com.revature.memestore.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoice_id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private float total_cost;

    @Column(nullable = false)
    private String date_ordered;

    public Invoice() {
    }

    public Invoice(String user_id, float total_cost, String date_ordered) {
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.date_ordered = date_ordered;
    }

    public int getItem_id() {
        return invoice_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Invoice setUser_id(String user_id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoice_id == invoice.invoice_id &&
                Float.compare(invoice.total_cost, total_cost) == 0 &&
                Objects.equals(user_id, invoice.user_id) &&
                Objects.equals(date_ordered, invoice.date_ordered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, user_id, total_cost, date_ordered);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "item_id=" + invoice_id +
                ", user_id='" + user_id + '\'' +
                ", total_cost=" + total_cost +
                ", date_ordered='" + date_ordered + '\'' +
                '}';
    }
}
