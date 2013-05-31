package by.academy.Model;

import java.io.Serializable;
import java.sql.Date;



public class PerformanceData implements Serializable {
    private static final long serialVersionUID = 1L;

    public PerformanceData() {
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


	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    public String toString() {
        return "PerformanceData{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerformanceData)) return false;

        PerformanceData that = (PerformanceData) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    public CategoryData getCategory() {
        return category;
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}


	private int id;
	private String name;
	private String shortDescription;
	private String description;
	private Date startDate;
	private Date endDate;
	private String image;
    private CategoryData category;
    private int language;
    
   public static final int NAME = 1;
   public static final int SHORT_DESCRIPTION = 2;
   public static final int DESCRIPTION = 3;
   public static final int IMAGE = 4;
  
   
   public static final int PROPERTIES_COUNT=4;
}
