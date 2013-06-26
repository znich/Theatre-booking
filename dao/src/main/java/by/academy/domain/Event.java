package by.academy.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 6587162487503777904L;

    private Integer id;
    private Performance performance;
    private long startTime;
    private long endTime;
    private Set<Ticket> tickets = new HashSet<Ticket>();

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

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (endTime != event.endTime) return false;
        if (startTime != event.startTime) return false;
        if (freeTicketsCount != null ? !freeTicketsCount.equals(event.freeTicketsCount) : event.freeTicketsCount != null)
            return false;
        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (maxTicketPrice != null ? !maxTicketPrice.equals(event.maxTicketPrice) : event.maxTicketPrice != null)
            return false;
        if (minTicketPrice != null ? !minTicketPrice.equals(event.minTicketPrice) : event.minTicketPrice != null)
            return false;
        if (performance != null ? !performance.equals(event.performance) : event.performance != null) return false;
        if (tickets != null ? !tickets.equals(event.tickets) : event.tickets != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (performance != null ? performance.hashCode() : 0);
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (endTime ^ (endTime >>> 32));
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        result = 31 * result + (freeTicketsCount != null ? freeTicketsCount.hashCode() : 0);
        result = 31 * result + (maxTicketPrice != null ? maxTicketPrice.hashCode() : 0);
        result = 31 * result + (minTicketPrice != null ? minTicketPrice.hashCode() : 0);
        return result;
    }
}
