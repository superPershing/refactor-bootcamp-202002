package cc.xpbootcamp.warmup.cashier;

import java.util.List;

public class Order {
    private String customerName;
    private String customerAddress;
    private List<Product> products;

    public Order(String customerName, String customerAddress, List<Product> products) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.products = products;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public List<Product> getProducts() {
        return products;
    }
}
