package cc.xpbootcamp.warmup.cashier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
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
        receiptInfo.append(order.getDate().getYear()).append("年")
                .append(order.getDate().getMonthValue()).append("月")
                .append(order.getDate().getDayOfMonth()).append("日，")
                .append(WeekDay.fromDay(order.getDate().getDayOfWeek()).getDesc()).append("\n");
    }

    private void buildProductsInfo() {
        order.getProducts().forEach(this::buildProductInfo);
    }

    private void buildSeparateLine() {
        receiptInfo.append("-----------------------------------\n");
    }

    private void calculateTaxAndDiscountAndCost() {
        order.getProducts().forEach(this::updateTotalSalesTaxAndTotalCostWith);
        if (isDiscountDay()) {
            discountCost = totalCost.multiply(BigDecimal.valueOf(DISCOUNT_RATE));
            totalCost = totalCost.subtract(discountCost);
        }
    }

    private boolean isDiscountDay() {
        return order.getDate().getDayOfWeek() == DayOfWeek.WEDNESDAY;
    }

    private void updateTotalSalesTaxAndTotalCostWith(Product product) {
        BigDecimal productSalesTax = calculateProductSalesTax(product);

        totalSalesTax = totalSalesTax.add(productSalesTax);
        totalCost = totalCost.add(calculateTotalCostWithSalesTax(product, productSalesTax));
    }

    private void buildProductInfo(Product product) {
        receiptInfo.append(product.getDescription());
        receiptInfo.append(", ");
        receiptInfo.append(product.getPrice().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
        receiptInfo.append(" x ");
        receiptInfo.append(product.getQuantity());
        receiptInfo.append(", ");
        receiptInfo.append(product.totalAmount().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
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

    private BigDecimal calculateTotalCostWithSalesTax(Product product, BigDecimal salesTax) {
        return product.totalAmount().add(salesTax);
    }

    private BigDecimal calculateProductSalesTax(Product product) {
        return product.totalAmount().multiply(BigDecimal.valueOf(SALES_TAX_RATE));
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