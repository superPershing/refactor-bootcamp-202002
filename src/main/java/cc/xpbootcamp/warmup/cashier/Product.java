package cc.xpbootcamp.warmup.cashier;

public class Product {
	private String desc;
	private double price;
	private int quantity;

	public Product(String desc, double price, int quantity) {
		this.desc = desc;
		this.price = price;
		this.quantity = quantity;
	}

	public String getDescription() {
		return desc;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

    public double totalAmount() {
        return price * quantity;
    }
}