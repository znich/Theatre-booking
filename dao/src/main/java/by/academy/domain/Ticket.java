package by.academy.domain;

import by.academy.Model.SeatData;
import by.academy.Model.StatusData;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
public class Ticket {
    private Integer id;
    private Event event;
    private int price;
    private Status status;
    private Booking booking;
    private Seat place;

    public Ticket() {
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
}
