package by.academy.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
public class Seat implements Serializable {
    private static final long serialVersionUID = -6231252818283198055L;
    private Integer id;
    private int row;
    private int seatNumber;
    private Integer priceCategory;
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

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(Integer priceCategory) {
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

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", seatNumber=" + seatNumber +
                ", sector=" + sector +
                '}';
    }
}
