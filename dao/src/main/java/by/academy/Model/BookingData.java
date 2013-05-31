package by.academy.Model;

import java.io.Serializable;
import java.util.Date;

public class BookingData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookingData() {
		
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}




	public int getTicketCount() {
		return ticketCount;
	}


	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}


	public PaymentData getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentData paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public UserData getUser() {
		return user;
	}


	public void setUser(UserData user) {
		this.user = user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingData)) return false;

        BookingData that = (BookingData) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "BookingData{" +
                "id=" + id +
                '}';
    }

    public Date getForDate() {

        return forDate;
    }

    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    public Date getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Date madeDate) {
        this.madeDate = madeDate;
    }

    private int id;
	private Date forDate;
	private Date madeDate;
	private UserData user;
	private int ticketCount;
	private PaymentData paymentMethod;


	public static final int PROPERTIES_COUNT=0;
}
