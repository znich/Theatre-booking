package by.academy.domain;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/13/13
 * Time: 12:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 3944754053369867785L;

    private Integer id;
    private String email;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
