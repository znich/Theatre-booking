package by.academy.domain.property_names;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/12/13
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public enum UserPropertyNames {
    FIRST_NAME(5), SURNAME(6), PHONE_NUMBER(7), CITY(8);

    private int id;
    private UserPropertyNames(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
