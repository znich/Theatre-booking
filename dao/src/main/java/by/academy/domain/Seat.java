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
    private static final long serialVersionUID = -6231252818283198055L;

    private Integer id;
    private int row;
    private int seatNumber;
    private TicketsPrice priceCategory;
    private int sector;

    public Seat() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public TicketsPrice getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(TicketsPrice priceCategory) {
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
