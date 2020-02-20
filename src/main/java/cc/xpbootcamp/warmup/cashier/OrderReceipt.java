package cc.xpbootcamp.warmup.cashier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

public class OrderReceipt {
    public static final double SALES_TAX_RATE = .10;
    public static final int AMOUNT_SCALE = 2;
    public static final double DISCOUNT_RATE = 0.02;

    private Order order;
    private StringBuilder receiptInfo;
    private BigDecimal totalSalesTax;
    private BigDecimal discountCost;
    private BigDecimal totalCost;

    public OrderReceipt(Order order) {
        this.order = order;
        receiptInfo = new StringBuilder();
        totalSalesTax = BigDecimal.ZERO;
        totalCost = BigDecimal.ZERO;
        discountCost = BigDecimal.ZERO;
    }

    public String printReceipt() {
        buildHeaders();
        buildBlankLine();
        buildDateInfo();
        buildBlankLine();
        buildProductsInfo();
        buildSeparateLine();
        calculateTaxAndDiscountAndCost();
        buildSalesTaxInfo();
        buildDiscountInfo();
        buildTotalAmountInfo();

        return receiptInfo.toString();
    }

    private void buildHeaders() {
        receiptInfo.append("===== 老王超市，值得信赖 ======\n");
    }

    private void buildBlankLine() {
        receiptInfo.append('\n');
    }

    private void buildDateInfo() {
        LocalDate orderDate = order.getDate();
        receiptInfo.append(orderDate.getYear()).append("年")
                .append(orderDate.getMonthValue()).append("月")
                .append(orderDate.getDayOfMonth()).append("日，")
                .append(WeekDay.fromDay(orderDate.getDayOfWeek()).getDesc()).append("\n");
    }

    private void buildProductsInfo() {
        order.getLineItems().forEach(this::buildProductInfo);
    }

    private void buildSeparateLine() {
        receiptInfo.append("-----------------------------------\n");
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

    private void buildProductInfo(LineItem lineItem) {
        receiptInfo.append(lineItem.getDescription());
        receiptInfo.append(", ");
        receiptInfo.append(lineItem.getPrice().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
        receiptInfo.append(" x ");
        receiptInfo.append(lineItem.getQuantity());
        receiptInfo.append(", ");
        receiptInfo.append(lineItem.totalAmount().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
        receiptInfo.append('\n');
    }

    private void buildSalesTaxInfo() {
        receiptInfo.append("税额: ").append(totalSalesTax.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP)).append("\n");
    }

    private void buildDiscountInfo() {
        if (isDiscountDay()) {
            receiptInfo.append("折扣: ").append(discountCost.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP)).append("\n");
        }
    }

    private void buildTotalAmountInfo() {
        receiptInfo.append("总价: ").append(totalCost.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP)).append("\n");
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