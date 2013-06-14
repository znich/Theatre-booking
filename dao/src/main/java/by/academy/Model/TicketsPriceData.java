package by.academy.Model;

import java.io.Serializable;

public class TicketsPriceData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7031218215128800091L;
	
	private int id;
	private int performanceId;
	private int priceCategory;
	private int price;

	public TicketsPriceData() {
			}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPerformanceId() {
		return performanceId;
	}

	public void setPerformanceId(int performanceId) {
		this.performanceId = performanceId;
	}

	public int getPriceCategory() {
		return priceCategory;
	}

	public void setPriceCategory(int priceCategory) {
		this.priceCategory = priceCategory;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "TicketsPriceData [id=" + id + ", performanceId="
				+ performanceId + ", priceCategory=" + priceCategory
				+ ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + performanceId;
		result = prime * result + price;
		result = prime * result + priceCategory;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TicketsPriceData))
			return false;
		TicketsPriceData other = (TicketsPriceData) obj;
		if (id != other.id)
			return false;
		if (performanceId != other.performanceId)
			return false;
		if (price != other.price)
			return false;
		if (priceCategory != other.priceCategory)
			return false;
		return true;
	}

}
