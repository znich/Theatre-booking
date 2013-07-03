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
    private Integer langId;

    private Property rootProperty;
    private Set<Property> childProperties = new HashSet<Property>();


    public Property() {
    }

    public PropertyNameEnum getName() {
        return name;
    }

    public void setName(PropertyNameEnum name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
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

        if (id != null ? !id.equals(property.id) : property.id != null) return false;
        if (langId != null ? !langId.equals(property.langId) : property.langId != null) return false;
        if (name != property.name) return false;
        if (rootProperty != null ? !rootProperty.equals(property.rootProperty) : property.rootProperty != null)
            return false;
        if (value != null ? !value.equals(property.value) : property.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (langId != null ? langId.hashCode() : 0);
        result = 31 * result + (rootProperty != null ? rootProperty.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name=" + name +
                ", value='" + value + '\'' +
                ", langId=" + langId +
                ", rootProperty=" + rootProperty +
                ", childProperties=" +
                '}';
    }
}
