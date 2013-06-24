package by.academy.domain;

import java.io.Serializable;
import java.util.Set;

/**
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 6587162487503777904L;

    private Integer id;
    private Performance performance;
    private long startTime;
    private long endTime;
    private Set<Ticket> tickets;

    private Integer freeTicketsCount;
    private Integer maxTicketPrice;
    private Integer minTicketPrice;

    public Event() {
    }

    public Integer getFreeTicketsCount() {
        return freeTicketsCount;
    }

    public void setFreeTicketsCount(Integer freeTicketsCount) {
        this.freeTicketsCount = freeTicketsCount;
    }

    public Integer getMaxTicketPrice() {
        return maxTicketPrice;
    }

    public void setMaxTicketPrice(Integer maxTicketPrice) {
        this.maxTicketPrice = maxTicketPrice;
    }

    public Integer getMinTicketPrice() {
        return minTicketPrice;
    }

    public void setMinTicketPrice(Integer minTicketPrice) {
        this.minTicketPrice = minTicketPrice;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
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
