package by.academy.DAO.category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public CategoryData getCategoryById(int id, int langId){

        String сategoryByIdQuery = "SELECT * FROM CATEGORY WHERE PARENT_ID = ? AND LANG_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoryData category = new CategoryData();
        try {
            ps = connection.prepareStatement(сategoryByIdQuery);
            ps.setInt(1, id);
            ps.setInt(2, langId);

            rs = ps.executeQuery();
            if (rs.next()){
                category = createCategory(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }
        return category;
    }

   

	@Override
    public List<CategoryData> getAllCategories(int langId) {
		
		String allCategoriesQuery = "SELECT * FROM CATEGORY WHERE LANG_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoryData category = null;
        List<CategoryData> categoryList = new ArrayList<CategoryData>();
        try {
            ps = connection.prepareStatement(allCategoriesQuery);
            ps.setInt(1, langId);

            rs = ps.executeQuery();
            while(rs.next()){
                category = createCategory(rs);
                categoryList.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps);
        }
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

    public CategoryData createCategory(ResultSet rs) throws SQLException {
        CategoryData category = new CategoryData();

        category.setId(rs.getInt("PARENT_ID"));
        category.setName(rs.getString("CATEGORY_VALUE"));
        return category;
    }

    private void closeAll(ResultSet rs, PreparedStatement ps) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public void closeConnection () {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
