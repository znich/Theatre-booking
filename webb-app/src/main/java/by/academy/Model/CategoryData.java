package by.academy.Model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class CategoryData implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String name;
    private int language;

    @Override
	public String toString() {
		return "CategoryData [id=" + id + ", "
				+ (name != null ? "name=" + name + ", " : "") + "language="
				+ language + "]";
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CategoryData))
			return false;
		CategoryData other = (CategoryData) obj;
		if (id != other.id)
			return false;
		
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + language;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

    public int getId() {

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

    

    public CategoryData() {
    }

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	
	   public static final int PARENT_ID = 0;
	 
}
