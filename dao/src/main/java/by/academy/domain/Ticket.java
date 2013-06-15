package by.academy.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class Ticket implements Serializable {
    private static final long serialVersionUID = -7762125850874268760L;

    private Integer id;
    private Event event;
    private int price;
    private Status status;
    private Booking booking;
    private Seat place;

    public Ticket() {
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

        if (!id.equals(ticket.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
