package exam2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleStringProperty bookCode;
	private SimpleStringProperty name;
	private SimpleStringProperty author;
	private SimpleIntegerProperty price;
	private SimpleIntegerProperty quantity;
	
	
	public Book(String bookCode, String name, String author, int price, int quantity) {
		this.bookCode = new SimpleStringProperty(bookCode);
		this.name = new SimpleStringProperty(name);
		this.author = new SimpleStringProperty(author);
		this.price = new SimpleIntegerProperty(price);
		this.quantity = new SimpleIntegerProperty(quantity);
	}

	public void setBookCode(String bookCode) {
		this.bookCode.set(bookCode);
	}
	public String getBookCode() {
		return this.bookCode.get();
	}
	public SimpleStringProperty bookCodeProperty() {
		return this.bookCode;
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getName() {
		return this.name.get();
	}
	public SimpleStringProperty nameProperty() {
		return this.name;
	}
	public void setAuthor(String author) {
		this.author.set(author);
	}
	public String getAuthor() {
		return this.author.get();
	}
	public SimpleStringProperty authorProperty() {
		return this.author;
	}
	public void setPrice(int price) {
		this.price.set(price);
	}
	public int getPrice() {
		return this.price.get();
	}
	public SimpleIntegerProperty priceProperty() {
		return this.price;
	}
	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}
	public int getQuantity() {
		return this.quantity.get();
	}
	public SimpleIntegerProperty quantityProperty() {
		return this.quantity;
	}
	
}
