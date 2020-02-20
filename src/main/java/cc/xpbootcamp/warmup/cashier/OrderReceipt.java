package cc.xpbootcamp.warmup.cashier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OrderReceipt {
    public static final double SALES_TAX_RATE = .10;
    public static final int AMOUNT_SCALE = 2;
    public static final double DISCOUNT_RATE = 0.02;

    private Order order;
    private BigDecimal totalSalesTax;
    private BigDecimal discountCost;
    private BigDecimal totalCost;

    public OrderReceipt(Order order) {
        this.order = order;
        totalSalesTax = BigDecimal.ZERO;
        totalCost = BigDecimal.ZERO;
        discountCost = BigDecimal.ZERO;
    }

    public String printReceipt() {
        StringBuilder receiptInfo = new StringBuilder();
        receiptInfo.append(buildHeaders());
        receiptInfo.append(buildBlankLine());
        receiptInfo.append(buildDateInfo());
        receiptInfo.append(buildBlankLine());
        receiptInfo.append(buildProductsInfo());
        receiptInfo.append(buildSeparateLine());
        calculateTaxAndDiscountAndCost();
        receiptInfo.append(buildSalesTaxInfo());
        receiptInfo.append(buildDiscountInfo());
        receiptInfo.append(buildTotalAmountInfo());

        return receiptInfo.toString();
    }

    private String buildHeaders() {
        return "===== 老王超市，值得信赖 ======\n";
    }

    private String buildBlankLine() {
        return "\n";
    }

    private String buildDateInfo() {
        LocalDate orderDate = order.getDate();
        return String.format("%d年%d月%d日，%s%n", orderDate.getYear(), orderDate.getMonthValue(), orderDate.getDayOfMonth(), WeekDay.fromDay(orderDate.getDayOfWeek()).getDesc());
    }

    private String buildProductsInfo() {
        return order.getLineItems().stream().map(this::buildProductInfo).collect(Collectors.joining());
    }

    private String buildSeparateLine() {
        return "-----------------------------------\n";
    }

    private void calculateTaxAndDiscountAndCost() {
        order.getLineItems().forEach(this::updateTotalSalesTaxAndTotalCostWith);
        if (isDiscountDay()) {
            discountCost = totalCost.multiply(BigDecimal.valueOf(DISCOUNT_RATE));
            totalCost = totalCost.subtract(discountCost);
        }
    }

    private boolean isDiscountDay() {
        return order.getDate().getDayOfWeek() == DayOfWeek.WEDNESDAY;
    }

    private void updateTotalSalesTaxAndTotalCostWith(LineItem lineItem) {
        BigDecimal productSalesTax = calculateProductSalesTax(lineItem);

        totalSalesTax = totalSalesTax.add(productSalesTax);
        totalCost = totalCost.add(calculateTotalCostWithSalesTax(lineItem, productSalesTax));
    }

    private String buildProductInfo(LineItem lineItem) {
        return String.format("%s, %s x %d, %s%n", lineItem.getDescription(), lineItem.getPrice().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP), lineItem.getQuantity(), lineItem.totalAmount().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
    }

    private String buildSalesTaxInfo() {
        return String.format("税额: %s%n", totalSalesTax.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
    }

    private String buildDiscountInfo() {
        if (isDiscountDay()) {
            return String.format("折扣: %s%n", discountCost.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
        }
        return "";
    }

    private String buildTotalAmountInfo() {
        return String.format("总价: %s%n", totalCost.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
    }

    private BigDecimal calculateTotalCostWithSalesTax(LineItem lineItem, BigDecimal salesTax) {
        return lineItem.totalAmount().add(salesTax);
    }

    private BigDecimal calculateProductSalesTax(LineItem lineItem) {
        return lineItem.totalAmount().multiply(BigDecimal.valueOf(SALES_TAX_RATE));
    }

    public enum WeekDay {
        SUNDAY(DayOfWeek.SUNDAY, "星期日"),
        MONDAY(DayOfWeek.MONDAY, "星期一"),
        TUESDAY(DayOfWeek.TUESDAY, "星期二"),
        WEDNESDAY(DayOfWeek.WEDNESDAY, "星期三"),
        THURSDAY(DayOfWeek.THURSDAY, "星期四"),
        FRIDAY(DayOfWeek.FRIDAY, "星期五"),
        SATURDAY(DayOfWeek.SATURDAY, "星期六");

        private static HashMap<DayOfWeek, WeekDay> hashMap = new HashMap<>();

        static {
            for (WeekDay day : WeekDay.values()) {
                hashMap.put(day.dayOfWeek, day);
            }

        }

        private DayOfWeek dayOfWeek;
        private String desc;

        WeekDay(DayOfWeek dayOfWeek, String desc) {
            this.dayOfWeek = dayOfWeek;
            this.desc = desc;
        }

        public static WeekDay fromDay(DayOfWeek dayOfWeek) {
            return hashMap.get(dayOfWeek);
        }

        public String getDesc() {
            return desc;
        }
    }
}