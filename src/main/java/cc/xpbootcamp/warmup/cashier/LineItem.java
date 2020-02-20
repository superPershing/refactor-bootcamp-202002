package cc.xpbootcamp.warmup.cashier;

import java.math.BigDecimal;

public class LineItem {
    private String desc;
    private BigDecimal price;
    private int quantity;

    public LineItem(String desc, double price, int quantity) {
        this.desc = desc;
        this.price = BigDecimal.valueOf(price);
        this.quantity = quantity;
    }

    public String getDescription() {
        return desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal totalAmount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}