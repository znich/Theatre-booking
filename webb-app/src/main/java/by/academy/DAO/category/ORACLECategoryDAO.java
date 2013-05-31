package by.academy.DAO.category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.Model.CategoryData;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */



public class ORACLECategoryDAO implements CategoryDAO {
	
	
	private Connection connection;


	public ORACLECategoryDAO(Connection connection) {
		this.connection = connection;
	} 
	
    @Override
    public CategoryData getCategoryById(int id, int lang) throws SQLException {
    	
    	String query= "SELECT * FROM CATEGORY WHERE PARENT_ID ="+id + " AND LANG_ID="+ lang;
    	
    	List<CategoryData> categoryList = getCategories(query);
    	if (categoryList.size()>0){
    	CategoryData category = categoryList.get(0);
    	return category;
    	} else
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

   

	@Override
    public List<CategoryData> getAllCategories(int lang) throws SQLException {
		
		String query = "SELECT * FROM CATEGORY WHERE PARENT_ID IN " +
				"( SELECT CATEGORY_ID FROM CATEGORY WHERE PARENT_ID=  "+CategoryData.PARENT_ID+" ) " +
				"AND LANG_ID = "+lang;
		
		List<CategoryData> categoryList = getCategories(query);
        return categoryList;  
    }
    
	

	@Override
	public int addCategory(CategoryData category) throws SQLException {
		String query = "INSERT INTO STATIC_PROPERTIES ";
		return 0;
	}

	@Override
	public int editCategory(CategoryData category) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCategory(CategoryData category) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
private List<CategoryData> getCategories (String query) throws SQLException{
    	
    	ArrayList<CategoryData> categoryList= new ArrayList<CategoryData>();
    	
    	Statement statement = connection.createStatement();
    	
    	ResultSet rs = statement.executeQuery(query);
    	
    	while (rs.next()){
    		CategoryData category = new CategoryData();
    		category.setId(rs.getInt("PARENT_ID"));
    		category.setName(rs.getString("CATEGORY_VALUE"));   		
    		category.setLanguage(rs.getInt("LANG_ID"));
    		
    		categoryList.add(category);
    	}
    	
		return categoryList;
    }
    public CategoryData createCategory(ResultSet rs) throws SQLException {
        CategoryData category = new CategoryData();

        category.setId(rs.getInt("PARENT_ID"));
        category.setName(rs.getString("CATEGORY_VALUE"));
        return category;
    }
    
    public void closeConncetion (){
    	ConnectionPool pool = ConnectionPool.getInstance();
		pool.releaseConnection(connection);
    }
}
