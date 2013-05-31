package by.academy.Model;

import java.io.Serializable;

public class TicketData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicketData() {
	
	}

    public SeatData getPlace() {
        return place;
    }

    public void setPlace(SeatData place) {
        this.place = place;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public StatusData getStatus() {
		return status;
	}

	public void setStatus(StatusData status) {
		this.status = status;
	}

	public int getBooking() {
		return booking;
	}

	public void setBooking(int booking) {
		this.booking = booking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + booking;
		result = prime * result + event;
		result = prime * result + id;
		result = prime * result + price;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TicketData))
			return false;
		TicketData other = (TicketData) obj;
		if (booking != other.booking)
			return false;
		if (event != other.event)
			return false;
		if (id != other.id)
			return false;
		if (price != other.price)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TicketData [id=" + id + ", event=" + event + ", price=" + price
				+ ", status=" + status + ", booking=" + booking + "]";
	}

	private int id;
	private int event;
	private int price;
	private StatusData status;
	private int booking;
    private SeatData place;

}
