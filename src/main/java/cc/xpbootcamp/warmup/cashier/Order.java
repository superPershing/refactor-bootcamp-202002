package cc.xpbootcamp.warmup.cashier;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Order {
    public static final double SALES_TAX_RATE = .10;
    public static final double DISCOUNT_RATE = 0.02;
    public static final DayOfWeek DISCOUNT_DAY = DayOfWeek.WEDNESDAY;

    private BigDecimal totalSalesTax;
    private BigDecimal discountCost;
    private BigDecimal totalCost;

    private List<LineItem> lineItems;
    private LocalDate purchasedDate;

    public Order(List<LineItem> lineItems, LocalDate purchasedDate) {
        this.lineItems = lineItems;
        this.purchasedDate = purchasedDate;
        this.totalSalesTax = BigDecimal.ZERO;
        this.totalCost = BigDecimal.ZERO;
        this.discountCost = BigDecimal.ZERO;
        calculateTaxAndDiscountAndCost();
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public BigDecimal getTotalSalesTax() {
        return totalSalesTax;
    }

    public BigDecimal getDiscountCost() {
        return discountCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void calculateTaxAndDiscountAndCost() {
        lineItems.forEach(this::updateTotalSalesTaxAndTotalCostWith);
        if (isDiscountDay()) {
            discountCost = totalCost.multiply(BigDecimal.valueOf(DISCOUNT_RATE));
            totalCost = totalCost.subtract(discountCost);
        }
    }

    public boolean isDiscountDay() {
        return purchasedDate.getDayOfWeek() == DISCOUNT_DAY;
    }

    private void updateTotalSalesTaxAndTotalCostWith(LineItem lineItem) {
        BigDecimal productSalesTax = calculateProductSalesTax(lineItem);

        totalSalesTax = totalSalesTax.add(productSalesTax);
        totalCost = totalCost.add(calculateTotalCostWithSalesTax(lineItem, productSalesTax));
    }

    private BigDecimal calculateTotalCostWithSalesTax(LineItem lineItem, BigDecimal salesTax) {
        return lineItem.totalAmount().add(salesTax);
    }

    private BigDecimal calculateProductSalesTax(LineItem lineItem) {
        return lineItem.totalAmount().multiply(BigDecimal.valueOf(SALES_TAX_RATE));
    }
}
