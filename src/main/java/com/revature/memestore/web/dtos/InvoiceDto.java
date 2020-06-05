package com.revature.memestore.web.dtos;

import java.util.Objects;

public class InvoiceDto {

    private int user_id;

    private float total_cost;

    private String date_ordered;

    public InvoiceDto() {
    }

    public InvoiceDto(float total_cost, String date_ordered) {
        this.total_cost = total_cost;
        this.date_ordered = date_ordered;
    }

    public InvoiceDto(int user_id, float total_cost, String date_ordered) {
        this.user_id = user_id;
        this.total_cost = total_cost;
        this.date_ordered = date_ordered;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDto that = (InvoiceDto) o;
        return user_id == that.user_id &&
                Float.compare(that.total_cost, total_cost) == 0 &&
                Objects.equals(date_ordered, that.date_ordered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, total_cost, date_ordered);
    }

    @Override
    public String toString() {
        return "InvoiceDto{" +
                "user_id=" + user_id +
                ", total_cost=" + total_cost +
                ", date_ordered='" + date_ordered + '\'' +
                '}';
    }
}
