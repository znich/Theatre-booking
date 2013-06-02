package by.academy.domain;

import java.io.Serializable;
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
    private Integer id;
    private int performance;
    private Date startTime;
    private Date endTime;
    private int minPrice;
    private int maxPrice;
    private int freeTicketsCount;
    private Set<Ticket> tickets;

    public Event() {
    }

    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getFreeTicketsCount() {
        return freeTicketsCount;
    }

    public void setFreeTicketsCount(int freeTicketsCount) {
        this.freeTicketsCount = freeTicketsCount;
    }

    public Set getTickets() {
        return tickets;
    }

    public void setTickets(Set tickets) {
        this.tickets = tickets;
    }
}
