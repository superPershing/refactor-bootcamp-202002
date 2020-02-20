package cc.xpbootcamp.warmup.cashier;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderReceiptTest {

    @Test
    public void shouldPrintReceiptWithDiscountWhenWeekDayIsWednesday() {
        List<LineItem> lineItems = new ArrayList<LineItem>() {{
            add(new LineItem("巧克力", 21.50, 2));
            add(new LineItem("小白菜", 10.00, 1));
        }};
        LocalDate date = LocalDate.of(2020, 2, 19);
        OrderReceipt receipt = new OrderReceipt(new Order(lineItems, date));

        String output = receipt.printReceipt();
        assertThat(output).isEqualTo("===== 老王超市，值得信赖 ======\n" +
                "\n" +
                "2020年2月19日，星期三\n" +
                "\n" +
                "巧克力, 21.50 x 2, 43.00\n" +
                "小白菜, 10.00 x 1, 10.00\n" +
                "-----------------------------------\n" +
                "税额: 5.30\n" +
                "折扣: 1.17\n" +
                "总价: 57.13\n");
    }

    @Test
    public void shouldPrintReceiptWithDiscountWhenWeekDayIsNotWednesday() {
        List<LineItem> lineItems = new ArrayList<LineItem>() {{
            add(new LineItem("巧克力", 21.50, 2));
            add(new LineItem("小白菜", 10.00, 1));
        }};
        LocalDate date = LocalDate.of(2020, 2, 17);
        OrderReceipt receipt = new OrderReceipt(new Order(lineItems, date));

        String output = receipt.printReceipt();
        assertThat(output).isEqualTo("===== 老王超市，值得信赖 ======\n" +
                "\n" +
                "2020年2月17日，星期一\n" +
                "\n" +
                "巧克力, 21.50 x 2, 43.00\n" +
                "小白菜, 10.00 x 1, 10.00\n" +
                "-----------------------------------\n" +
                "税额: 5.30\n" +
                "总价: 58.30\n");
    }
}