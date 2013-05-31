package by.academy.DAO.performance;

import by.academy.Model.PerformanceData;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public interface PerformanceDAO {
    public PerformanceData getPerformanceById(int id, int langID);
    public ArrayList<PerformanceData> getAllPerformances(int langID);
    public ArrayList<PerformanceData> getPerformancesByCategory(int categoryId, int langId);
    public int addPerformance(PerformanceData performance);
    public int editPerformance(PerformanceData performance);
    public int deletePerformance (int id);
}
