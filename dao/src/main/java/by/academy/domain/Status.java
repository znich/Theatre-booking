package by.academy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 */
public class Status implements Serializable {
    private static final long serialVersionUID = 6845922418703306184L;

    private Integer id;
    private Status parentStatus;
    private Set<PaymentMethod> childStatus;
    private Integer langId;
    private String value;
    private Set<Ticket> tickets;
    public Status() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Status getParentStatus() {
        return parentStatus;
    }

    public void setParentStatus(Status parentStatus) {
        this.parentStatus = parentStatus;
    }

    public Set<PaymentMethod> getChildStatus() {
        return childStatus;
    }

    public void setChildStatus(Set<PaymentMethod> childStatus) {
        this.childStatus = childStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status = (Status) o;

        if (!id.equals(status.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
