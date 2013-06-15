package by.academy.domain.property_names;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/12/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PerformancePropertyNames {
    NAME(1), SHORT_DESCRIPTION(2), DESCRIPTION(3), IMAGE(4);

    private int id;
    private PerformancePropertyNames(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
