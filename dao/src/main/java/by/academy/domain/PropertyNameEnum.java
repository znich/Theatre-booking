package by.academy.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 01.06.13
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
public enum PropertyNameEnum {
    NAME(1), SHORT_DESCRIPTION(2), DESCRIPTION(3), IMAGE(4), FIRST_NAME(5), SURNAME(6), PHONE_NUMBER(7), CITY(8);

    private int id;
    
    private PropertyNameEnum(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
