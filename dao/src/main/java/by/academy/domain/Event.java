package by.academy.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 31.05.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 6587162487503777904L;

    private Integer id;
    private Performance performance;
    private Calendar startTime;
    private Calendar endTime;
    private Set<Ticket> tickets;

    public Event() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Set getTickets() {
        return tickets;
    }

    public void setTickets(Set tickets) {
        this.tickets = tickets;
    }

    public void setTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
