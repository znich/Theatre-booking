package by.academy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class Performance implements Serializable, Comparable {
    private static final long serialVersionUID = -8226959475559515701L;

    private Integer id;

    private Calendar startDate;

    private Calendar endDate;

    private Category category;

    private Set<Event> events;

    private Set<Property> properties = new HashSet<Property>();

    private Set<TicketsPrice> ticketsPrices;

    public Performance() {
    }

    public Set<TicketsPrice> getTicketsPrices() {
        return ticketsPrices;
    }

    public void setTicketsPrices(Set<TicketsPrice> ticketsPrices) {
        this.ticketsPrices = ticketsPrices;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }
    public void setProperty(Property property) {
        this.properties.add(property);
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Performance)) return false;

        Performance that = (Performance) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Performance{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", category=" + category +
                ", events=" + events +
                ", properties=" + properties +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Performance entry = (Performance) o;

        int result = this.startDate.compareTo(entry.startDate);
        return result;
    }
}
