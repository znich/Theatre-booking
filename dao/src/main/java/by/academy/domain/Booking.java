package by.academy.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 19:33
 * To change this template use File | Settings | File Templates.
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = -7726509784741040741L;

    private Integer id;
    private Calendar expDate;
    private Calendar madeDate;
    private PaymentMethod paymentMethod;
    private User user;
    private Set<Ticket> tickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking() {
    }

    public Calendar getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Calendar madeDate) {
        this.madeDate = madeDate;
    }

    public Calendar getExpDate() {
        return expDate;
    }

    public void setExpDate(Calendar expDate) {
        this.expDate = expDate;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void deleteTicket(Ticket ticket){
        this.tickets.remove(ticket);
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;

        Booking booking = (Booking) o;

        if (!id.equals(booking.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
