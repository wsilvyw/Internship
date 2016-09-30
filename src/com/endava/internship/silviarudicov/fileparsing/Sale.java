package com.endava.internship.silviarudicov.fileparsing;

import java.util.Comparator;

/**
 * Created by srudicov on 9/29/2016.
 */
public class Sale implements Comparable<Sale> {
    private String customer;
    private String product;
    private int amount;

    public Sale(String customer, String product, int amount) {
        this.customer = customer;
        this.product = product;
        this.amount = amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;

        if (amount != sale.amount) return false;
        if (!customer.equals(sale.customer)) return false;
        return product.equals(sale.product);

    }

    @Override
    public int hashCode() {
        int result = customer.hashCode();
        result = 31 * result + product.hashCode();
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "customer='" + customer + '\'' +
                ", product='" + product + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int compareTo(Sale sl) {
        return this.customer.compareTo(sl.customer);
    }

    public static class OrderByAmount implements Comparator<Sale> {

        @Override
        public int compare(Sale sl1, Sale sl2) {
            return sl1.amount > sl2.amount ? 1 : (sl1.amount < sl2.amount ? -1 : 0);
        }
    }

    public static class OrderByProduct implements Comparator<Sale> {

        @Override
        public int compare(Sale sl1, Sale sl2) {
            return sl1.product.compareTo(sl2.product);
        }
    }

}
