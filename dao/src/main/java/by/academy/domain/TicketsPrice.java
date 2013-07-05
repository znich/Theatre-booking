package by.academy.domain;

import javax.persistence.*;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketsPrice)) return false;

        TicketsPrice that = (TicketsPrice) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
