package by.academy.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/5/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TicketsPrice implements Serializable {
    private static final long serialVersionUID = 3226097319160868426L;

    private Integer id;
    private Performance perfId;
    private int priceCategory;
    private int price;

    public TicketsPrice() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Performance getPerfId() {
        return perfId;
    }

    public void setPerfId(Performance perfId) {
        this.perfId = perfId;
    }

    public int getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(int priceCategory) {
        this.priceCategory = priceCategory;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketsPrice)) return false;

        TicketsPrice that = (TicketsPrice) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
