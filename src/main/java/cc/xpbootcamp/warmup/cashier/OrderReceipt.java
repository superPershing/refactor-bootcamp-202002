package cc.xpbootcamp.warmup.cashier;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

public class OrderReceipt {
    public static final int AMOUNT_SCALE = 2;
    public static final String SEPARATE_LINE = "-----------------------------------\n";

    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        return buildHeaders() +
                "\n" +
                buildDate() +
                "\n" +
                buildLineItems() +
                SEPARATE_LINE +
                buildPrice();
    }

    private String buildPrice() {
        return buildSalesTaxInfo() +
                buildDiscountInfo() +
                buildTotalAmountInfo();
    }

    private String buildHeaders() {
        return "===== 老王超市，值得信赖 ======\n";
    }

    private String buildDate() {
        LocalDate orderDate = order.getPurchasedDate();
        return String.format("%s%n",
                DateTimeFormatter.ofPattern("yyyy年M月dd日，EEEE", Locale.CHINA).format(orderDate));
    }

    private String buildLineItems() {
        return order.getLineItems().stream().map(this::buildProductInfo).collect(Collectors.joining());
    }

    private String buildProductInfo(LineItem lineItem) {
        return String.format("%s, %s x %d, %s%n",
                lineItem.getDescription(),
                lineItem.getPrice().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP),
                lineItem.getQuantity(),
                lineItem.totalAmount().setScale(AMOUNT_SCALE, RoundingMode.HALF_UP));
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
}