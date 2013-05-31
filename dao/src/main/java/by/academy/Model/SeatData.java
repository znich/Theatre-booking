package by.academy.Model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 0:23
 * To change this template use File | Settings | File Templates.
 */
public class SeatData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
    int row;
    int seatNumber;
    int priceCategory;
    int sector;

    @Override
    public String toString() {
        return "SeatData{" +
                "id=" + id +
                ", row=" + row +
                ", seatNumber=" + seatNumber +
                ", priceCategory=" + priceCategory +
                ", sector=" + sector +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatData)) return false;

        SeatData seatData = (SeatData) o;

        if (id != seatData.id) return false;
        if (priceCategory != seatData.priceCategory) return false;
        if (row != seatData.row) return false;
        if (seatNumber != seatData.seatNumber) return false;
        if (sector != seatData.sector) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + row;
        result = 31 * result + seatNumber;
        result = 31 * result + priceCategory;
        result = 31 * result + sector;
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
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

    public SeatData() {

    }
}
