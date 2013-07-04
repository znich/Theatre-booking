package by.academy.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 */
public class Ticket implements Serializable {
    private static final long serialVersionUID = -7762125850874268760L;
    private Integer id;
    private Event event;
    private Status status;
    private Booking booking;
    private Seat place;

    private int price;

    public Ticket() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Seat getPlace() {
        return place;
    }

    public void setPlace(Seat place) {
        this.place = place;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (price != ticket.price) return false;
        if (id != null ? !id.equals(ticket.id) : ticket.id != null) return false;
        if (place != null ? !place.equals(ticket.place) : ticket.place != null) return false;
        if (status != null ? !status.equals(ticket.status) : ticket.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + price;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        return result;
    }
}
