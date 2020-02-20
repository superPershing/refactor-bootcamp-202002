package cc.xpbootcamp.warmup.cashier;

import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OrderReceipt {
    public static final int AMOUNT_SCALE = 2;

    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        return buildHeaders() +
                buildBlankLine() +
                buildDateInfo() +
                buildBlankLine() +
                buildProductsInfo() +
                buildSeparateLine() +
                buildSalesTaxInfo() +
                buildDiscountInfo() +
                buildTotalAmountInfo();
    }

    private String buildHeaders() {
        return "===== 老王超市，值得信赖 ======\n";
    }

    private String buildBlankLine() {
        return "\n";
    }

    private String buildDateInfo() {
        LocalDate orderDate = order.getPurchasedDate();
        return String.format("%d年%d月%d日，%s%n", orderDate.getYear(), orderDate.getMonthValue(), orderDate.getDayOfMonth(), WeekDay.fromDay(orderDate.getDayOfWeek()).getDesc());
    }

    private String buildProductsInfo() {
        return order.getLineItems().stream().map(this::buildProductInfo).collect(Collectors.joining());
    }

    private String buildSeparateLine() {
        return "-----------------------------------\n";
    }


    private String buildProductInfo(LineItem lineItem) {
        return String.format("%s, %s x %d, %s%n", lineItem.getDescription(), lineItem.getPrice().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP), lineItem.getQuantity(), lineItem.totalAmount().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
    }

    private String buildSalesTaxInfo() {
        return String.format("税额: %s%n", order.getTotalSalesTax().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
    }

    private String buildDiscountInfo() {
        if (order.isDiscountDay()) {
            return String.format("折扣: %s%n", order.getDiscountCost().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
        }
        return "";
    }

    private String buildTotalAmountInfo() {
        return String.format("总价: %s%n", order.getTotalCost().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
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