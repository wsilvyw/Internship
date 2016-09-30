package com.endava.internship.silviarudicov.fileparsing;

/**
 * Created by srudicov on 9/29/2016.
 */
public class Product implements Comparable<Product> {

    private String product;
    private int amount;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public Product(String product, int amount, int price) {
        this.product = product;
        this.amount = amount;
        this.price = price;
    }

    @Override
    public int compareTo(Product sl) {
        return this.product.compareTo(sl.product);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product='" + product + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                "}\n";
    }
}
