package cc.xpbootcamp.warmup.cashier;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    public static final double SALES_TAX_RATE = .10;

    private Order order;
    private StringBuilder receiptInfo;
    private double totalSalesTax;
    private double totalCost;

    public OrderReceipt(Order order) {
        this.order = order;
        receiptInfo = new StringBuilder();
        totalSalesTax = 0d;
        totalCost = 0d;
    }

    public String printReceipt() {
        buildHeaders();
        buildCustomerInfo();
        buildProductsInfo();
        calculateTaxAndCost();
        buildSalesTaxInfo();
        buildTotalAmountInfo();

        return receiptInfo.toString();
    }

    private void buildHeaders() {
        receiptInfo.append("======Printing Orders======\n");
    }

    private void buildCustomerInfo() {
        receiptInfo.append(order.getCustomerName());
        receiptInfo.append(order.getCustomerAddress());
    }

    private void buildProductsInfo() {
        order.getProducts().forEach(this::buildProductInfo);
    }

    private void calculateTaxAndCost() {
        order.getProducts().forEach(this::updateTotalSalesTaxAndTotalCostWith);
    }

    private void updateTotalSalesTaxAndTotalCostWith(Product product) {
        double productSalesTax = calculateProductSalesTax(product);

        totalSalesTax += productSalesTax;
        totalCost += calculateTotalCostWithSalesTax(product, productSalesTax);
    }

    private void buildProductInfo(Product product) {
        receiptInfo.append(product.getDescription());
        receiptInfo.append('\t');
        receiptInfo.append(product.getPrice());
        receiptInfo.append('\t');
        receiptInfo.append(product.getQuantity());
        receiptInfo.append('\t');
        receiptInfo.append(product.totalAmount());
        receiptInfo.append('\n');
    }

    private void buildSalesTaxInfo() {
        receiptInfo.append("Sales Tax").append('\t').append(totalSalesTax);
    }

    private void buildTotalAmountInfo() {
        receiptInfo.append("Total Amount").append('\t').append(totalCost);
    }

    private double calculateTotalCostWithSalesTax(Product product, double salesTax) {
        return product.totalAmount() + salesTax;
    }

    private double calculateProductSalesTax(Product product) {
        return product.totalAmount() * SALES_TAX_RATE;
    }
}