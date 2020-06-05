package com.revature.memestore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.memestore.web.dtos.InvoiceDto;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoice_id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private float total_cost;

    @Column(nullable = false)
    private String date_ordered;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "invoice_inventories",
            joinColumns = {@JoinColumn(name = "invoice_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private List<Inventory> items;

    public void addToItems(Inventory inventory){
        if (items == null) items = new ArrayList<Inventory>();
        this.items.add(inventory);

    }

    public Invoice() {
    }

    public Invoice(InvoiceDto invoiceDto){
        this.total_cost = invoiceDto.getTotal_cost();
        this.date_ordered = invoiceDto.getDate_ordered();
    }

    public Invoice(User user, float total_cost, String date_ordered) {
        this.user = user;
        this.total_cost = total_cost;
        this.date_ordered = date_ordered;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

    public String getDate_ordered() {
        return date_ordered;
    }

    public void setDate_ordered(String date_ordered) {
        this.date_ordered = date_ordered;
    }

    public List<Inventory> getItems() {
        return items;
    }

    public void setItems(List<Inventory> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoice_id == invoice.invoice_id &&
                Float.compare(invoice.total_cost, total_cost) == 0 &&
                Objects.equals(user, invoice.user) &&
                Objects.equals(date_ordered, invoice.date_ordered) &&
                Objects.equals(items, invoice.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, user, total_cost, date_ordered, items);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoice_id=" + invoice_id +
                ", user=" + user +
                ", total_cost=" + total_cost +
                ", date_ordered='" + date_ordered + '\'' +
                '}';
    }
}
