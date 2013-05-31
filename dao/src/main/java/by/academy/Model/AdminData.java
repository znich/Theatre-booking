package by.academy.Model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 18.05.13
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class AdminData extends UserData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer adminId;

    public AdminData() {
    }

    public AdminData(Integer id) {
        this.adminId = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer id) {
        this.adminId = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdminData other = (AdminData) obj;
        if (this.adminId != other.adminId && (this.adminId == null || !this.adminId.equals(other.adminId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.adminId != null ? this.adminId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Admin[id=" + adminId + "]";
    }
}
