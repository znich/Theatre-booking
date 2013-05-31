package by.academy.DAO.category;

import java.sql.SQLException;
import java.util.List;

import by.academy.Model.CategoryData;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryDAO {
    public CategoryData getCategoryById (int id, int lang);
    public List<CategoryData> getAllCategories(int lang);
    public int addCategory(CategoryData category) throws SQLException;
    public int editCategory(CategoryData category) throws SQLException;
    public int deleteCategory (CategoryData category) throws SQLException;
}
