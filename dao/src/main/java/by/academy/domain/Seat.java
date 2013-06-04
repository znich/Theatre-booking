package by.academy.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class Seat implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6231252818283198055L;
	Integer id;
    int row;
    int seatNumber;
    int priceCategory;
    int sector;

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    Set<Ticket> tickets;

    public Seat() {

    }

    public Integer getId() {

        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(int priceCategory) {
        this.priceCategory = priceCategory;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        if (!id.equals(seat.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
