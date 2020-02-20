package cc.xpbootcamp.warmup.cashier;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private List<LineItem> lineItems;
    private LocalDate date;

    public Order(List<LineItem> lineItems, LocalDate date) {
        this.lineItems = lineItems;
        this.date = date;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public LocalDate getDate() {
        return date;
    }
}
