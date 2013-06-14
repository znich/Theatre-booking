package by.academy.dao.util;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/8/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {

    public String classToTableName(String className){
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    public String propertyToColumnName(String propName){
        return "F_" + super.propertyToColumnName(propName);
    }

    public String columnName(String columnName){
        return columnName;
    }

    public String tableName(String tableName){
        return tableName;
    }

}
