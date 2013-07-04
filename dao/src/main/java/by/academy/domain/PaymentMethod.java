package by.academy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 */
public class PaymentMethod implements Serializable {
    private static final long serialVersionUID = -7681565045835513129L;

    private Integer id;
    private String name;
    private int langId;
    private PaymentMethod parentPaymentMethod;
    private Set<PaymentMethod> childPaymentMethod;

    public PaymentMethod() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public PaymentMethod getParentPaymentMethod() {
        return parentPaymentMethod;
    }

    public void setParentPaymentMethod(PaymentMethod parentPaymentMethod) {
        this.parentPaymentMethod = parentPaymentMethod;
    }

    public Set<PaymentMethod> getChildPaymentMethod() {
        return childPaymentMethod;
    }

    public void setChildPaymentMethod(Set<PaymentMethod> childPaymentMethod) {
        this.childPaymentMethod = childPaymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentMethod)) return false;

        PaymentMethod that = (PaymentMethod) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
