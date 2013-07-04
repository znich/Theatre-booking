package by.academy.domain;

import java.io.Serializable;
import java.util.Set;

/**
 */
public class TicketsPrice implements Serializable, Comparable<Object> {
	private static final long serialVersionUID = 3226097319160868426L;

	private Integer id;
	private Performance perfId;
	private int priceCategory;
	private int price;
	private Set<Seat> seats;

	public TicketsPrice() {
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Performance getPerfId() {
		return perfId;
	}

	public void setPerfId(Performance perfId) {
		this.perfId = perfId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((perfId == null) ? 0 : perfId.hashCode());
		result = prime * result + price;
		result = prime * result + priceCategory;
		result = prime * result + ((seats == null) ? 0 : seats.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TicketsPrice))
			return false;
		TicketsPrice other = (TicketsPrice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (perfId == null) {
			if (other.perfId != null)
				return false;
		} else if (!perfId.equals(other.perfId))
			return false;
		if (price != other.price)
			return false;
		if (priceCategory != other.priceCategory)
			return false;
		if (seats == null) {
			if (other.seats != null)
				return false;
		} else if (!seats.equals(other.seats))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object obj) {
		TicketsPrice entry = (TicketsPrice) obj;

		int result = priceCategory - entry.priceCategory;
		if (result != 0) {
			return (int) result / Math.abs(result);
		}
		return 0;
	}

}