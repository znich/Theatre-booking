package by.academy.Model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 17.05.13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class StatusData implements Serializable {
    int id;
    String value;

    public StatusData() {
    }

    @Override
    public String toString() {
        return "StatusData{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusData)) return false;

        StatusData StatusData = (StatusData) o;

        if (id != StatusData.id) return false;
        if (!value.equals(StatusData.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + value.hashCode();
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
