package by.academy.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for Property entity.
 * @author Siarhei Poludvaranin
 *
 */
public class Property implements Serializable {
    private static final long serialVersionUID = 2606555166542056213L;

    private Integer id;
    private PropertyNameEnum name;
    private String value;
    private int langId;

    private Property rootProperty;
    private Set<Property> childProperties = new HashSet<Property>();


    public Property() {
    }

    public PropertyNameEnum getName() {
        return name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(int id) {
        for (PropertyNameEnum e : PropertyNameEnum.values()){
            if (e.getId() == id){
                this.name = e;
            }
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public Set<Property> getChildProperties() {
        return childProperties;
    }

    public void setChildProperties(Set<Property> childProperties) {
        this.childProperties = childProperties;
    }

    public Property getRootProperty() {
        return rootProperty;
    }

    public void setRootProperty(Property rootProperty) {
        this.rootProperty = rootProperty;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;

        Property property = (Property) o;

        if (!id.equals(property.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name=" + name +
                ", value='" + value + '\'' +
                ", langId=" + langId +
                ", rootProperty=" + rootProperty +
                ", childProperties=" + childProperties +
                '}';
    }
}
