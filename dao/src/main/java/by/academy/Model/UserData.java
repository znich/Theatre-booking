package by.academy.Model;

import java.io.Serializable;

public class UserData implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;

        UserData userData = (UserData) o;

        if (!id.equals(userData.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private static final long serialVersionUID = 1L;
	public UserData (){
		
	}
	
public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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

public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

private Integer id;
private String name;
private String surname;
private String email;
private String password;
private String phoneNumber;
private String city;

public static final int FIRST_NAME = 9;
public static final int SURNAME = 10;
public static final int PHONE_NUMBER=11;
public static final int CITY = 12;

public static final int PROPERTIES_COUNT=4;
}
