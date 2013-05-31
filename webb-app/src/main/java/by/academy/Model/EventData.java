package by.academy.Model;

import java.io.Serializable;
import java.util.Date;


public class EventData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EventData() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   

    public PerformanceData getPerformance() {
        return performance;
    }


    public void setPerformance(PerformanceData performance) {
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


	@Override
    public String toString() {
        return "EventData{" +
                "endTime=" + endTime +
                ", startTime=" + startTime +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventData)) return false;

        EventData eventData = (EventData) o;

        if (id != eventData.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    private int id;
    private PerformanceData performance;
    private Date startTime;
    private Date endTime;
    private int minPrice;
    private int maxPrice;

}
