package cc.xpbootcamp.warmup.cashier;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String customerName;
    private String customerAddress;
    private List<Product> products;
    private LocalDate date;

    public Order(String customerName, String customerAddress, List<Product> products, LocalDate date) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.products = products;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }
}
