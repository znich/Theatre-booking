package by.academy.DAO.performance;


import by.academy.DAO.category.ORACLECategoryDAO;
import by.academy.DAO.connectionpool.ConnectionPool;
import by.academy.Model.PerformanceData;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEPerformanceDAO implements PerformanceDAO {

    private Connection connection = null;

    public ORACLEPerformanceDAO(Connection connection)  {
        this.connection = connection;
        }    

	@Override
	public PerformanceData getPerformanceById (int id , int langID) {
        String performanceByIdQuery = "SELECT  PERFORMANCES.PERFORMANCE_ID , PERFORMANCES.START_DATE, PERFORMANCES.END_DATE, CATEGORY.CATEGORY_VALUE, CATEGORY.PARENT_ID,  " +
        		"PROPERTIES.PROPERTY_NAME_ID, PROPERTIES.PROPERTY_VALUE FROM PERFORMANCES INNER JOIN PERFORMANCES_PROPERTIES ON PERFORMANCES.PERFORMANCE_ID = PERFORMANCES_PROPERTIES.PERFORMANCE_ID " +
                "INNER JOIN CATEGORY ON PERFORMANCES.CATEGORY_ID = CATEGORY.PARENT_ID  " +
                "INNER JOIN PROPERTIES ON PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID AND PROPERTIES.LANG_ID = ? WHERE PERFORMANCES.PERFORMANCE_ID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PerformanceData performance = new PerformanceData();

        try{
            ps = connection.prepareStatement(performanceByIdQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, langID);           
            ps.setInt(2, id);
            rs = ps.executeQuery();
            if (rs.next()){
                performance = createPerformance(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
        	closeAll(rs, ps, null);
        }

	return performance;
	
	}

    @Override
    public List<PerformanceData> getAllPerformances( int langID)  {
        String getAllPerformancesQuery ="SELECT PERFORMANCE_ID FROM PERFORMANCES ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<PerformanceData> performanceList = new ArrayList<PerformanceData>();
        PerformanceData performance = new PerformanceData();

        try{
            ps = connection.prepareStatement(getAllPerformancesQuery);
            rs = ps.executeQuery();
            while (rs.next()){
                performance = getPerformanceById (rs.getInt(1) , langID);
                performanceList.add(performance);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
        	closeAll(rs, ps, null);
        }

        return performanceList;
		
    }

    @Override
    public int addPerformance(PerformanceData performance) {
    	/** Если возвращает ">0", то было добавлена новая строка в таблицу Performances
    	 * если возвращает "0", то не удалось добавить новую строку в таблицу Performances
    	 * если возвращает "<0", то были добавлены  строки в таблицу Properties без внесения в таблицу Performances  	 
    	 */
		PreparedStatement pStatement = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows=0;
		int  propRows=0;
		int rowsProp=0;
		int perfId = performance.getId();
		
		/*
		 * тут выполняется проверка, для чего вызван метод add: для добавления нового PERFORMANCE 
		 * или же для добавления новой записи в PROPERTIES на отсутвующем языке.
		 * Для новой записи id будет не задан либо равен "0"
		 * а для существующей будет передан его id
		 */
/** 1*/	
		if (perfId==0){ // PERFORMANCE отсутвует в базе   	
			
			String addQuery= "INSERT INTO PERFORMANCES (START_DATE, END_DATE, CATEGORY_ID) VALUES (?, ?. ?)";
    	
			try {
				pStatement = connection.prepareStatement(addQuery, new String [] {"PERFORMANCE_ID"} );
				
				pStatement.setDate(1, performance.getStartDate());    	
				pStatement.setDate(2, performance.getEndDate());	
				pStatement.setInt(3, performance.getCategory().getId());
				
				rows = pStatement.executeUpdate();
				
				perfId = getGeneratedKey(pStatement); // получение ID нового Performance
    		 	
				performance.setId(perfId);	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    
			finally {
				closeAll(null, pStatement,null);
			}
    		 	    		 	
/** 2*/	   		String parentPropQuery = "INSERT INTO PROPERTIES (PROPERTY_NAME_ID, PARENT_ID)" +
    					"VALUES (?,?) ";
				int addProp = PerformanceData.PROPERTIES_COUNT;
    	
    		 	// создание запроса на внесение в таблицу PROPERTIES
    		 	try {
					pStatement = connection.prepareStatement(parentPropQuery, new String [] {"PROPERTY_ID"} );
				
					 /* Далее заполняем таблицу PROPERTIES строками с родительскими свойствами бина
			    	    * он имеет 2 нужных нам поля : PROPERTY_NAME_ID и PARENT_ID	
			    	    * PROPERTY_ID генерируется автоматически
			    	    * остальные поля можно оставить пустыми	        		    
			    	    */
/** 3*/	    	for (int i=0; i<addProp;i++) { //здесь addProp - число дополнительных свойств бина Performance 
		    		    	
			    pStatement.setInt(2, 0); //  родительский ID = 0 для parent свойств
			    		 	
			    switch (i){
		    		    case 0:
		    		    	pStatement.setInt(1, PerformanceData.NAME);     		    		    		    		 	 	
		    		    	break;
		    		    case 1:
		    		    	pStatement.setInt(1, PerformanceData.SHORT_DESCRIPTION);  	    		    		 	
		    		    	break;
		    		    case 2:
		    		    	pStatement.setInt(1, PerformanceData.DESCRIPTION);     		    		  		    		 	
		    		    	break;
		    		    case 3:
		    		    	pStatement.setInt(1, PerformanceData.IMAGE);     		    			
		    		    	break;		    		 	
		    		    }
		    		    	
		    		    	//внесение данных из подготовленного запроса в таблицу PROPERTIES
		    		    	rowsProp = pStatement.executeUpdate();    	  
    		    	
/** 4*/    		    if (rowsProp>0)
	    							{ 		
    		    					// получение сгенерированного ID для строки таблицы PROPERTIES
    		    					int propId = getGeneratedKey(pStatement);
    		    					
    		    					String propListQuery = "INSERT INTO PERFORMANCES_PROPERTIES (PERFORMANCE_ID,PROPERTY_ID) " +
    		    							"VALUES (?,?) ";
	    			
    		    					// запрос для добавление строк в таблицу PERFORMANCES_PROPERTIES
    		    					ps = connection.prepareStatement(propListQuery);
	    			
    		    					ps.setInt(1, perfId); // ID  Performance
    		    					ps.setInt(2, propId); // ID Property
	    			
    		    					ps.executeUpdate();			 
	    							}
    		    	
    		    } // конец цикла FOR


    		 	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 		
    		 	finally {
    		 		closeAll( null, pStatement,null);
    		 		closeAll(null, ps,null);
    		 	}
		} // конец цикла if
		else rows =-1; 
		
		/* Далее вносим в таблицу PROPERTIES стоки с дочерними свойствами бина.
		 * Если мы вносим свойства на новом языке для существующего Performance, то метод начнёт
		 * работу именно с этого места, используя переданный с бином PERFORMANCE_ID
		 * Если мы создавали новое Performance, то метод будет использовать автоматичекси сгенерированный PERFORMANCE_ID		 
		 */
/** 5*/		String query = "SELECT PERFORMANCES_PROPERTIES.PROPERTY_ID, PROPERTY_NAME_ID FROM PERFORMANCES_PROPERTIES, PROPERTIES WHERE " +
				"PERFORMANCE_ID = "+perfId+" AND PERFORMANCES_PROPERTIES.PROPERTY_ID = PROPERTIES.PROPERTY_ID";
		
		String childPropQuery = "INSERT INTO PROPERTIES (PROPERTY_NAME_ID, PROPERTY_VALUE, LANG_ID, PARENT_ID)" +
				"VALUES (?,?,?,?) ";
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
			
			// Делаем выборку PROPERTY_ID и соответсвующих им PROPERTY_NAME_ID для переданного на добавление бина
			rs = statement.executeQuery(query);
			
			// Подготовленный запрос для добавления новых строк в таблицу PROPERTIES
/** 6*/		pStatement = connection.prepareStatement(childPropQuery);
			
/** 7-8*/	while (rs.next()){
				
				int propNameID = rs.getInt(2);
				int propID = rs.getInt(1);
				int langID = performance.getLanguage();
				
				pStatement.setInt(1, propNameID); // id имени дочернего проперти такое же как у родительского
				pStatement.setInt(4, propID); // внесение родительского проперти
				pStatement.setInt(3, langID); // язык берётся из полученного бина
				
				switch (propNameID){
				// внесение значение в поле VALUE  в зависимости от тима имени проперти	
				case PerformanceData.NAME:
					pStatement.setString(2, performance.getName());
					break;
				case PerformanceData.SHORT_DESCRIPTION :
					pStatement.setString(2, performance.getShortDescription());
					break;
				case PerformanceData.DESCRIPTION :	
					pStatement.setString(2, performance.getDescription());  
					break;
				case PerformanceData.IMAGE :
					pStatement.setString(2, performance.getImage()); 
					break;
					}
					
				  propRows += pStatement.executeUpdate();
				// возврат количества внесённых срок в таблицу PERFORMANCES
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			closeAll(rs, pStatement,statement);
		}
		 	
		if (propRows<=0 ){
			rows = 0;
		} 
    	  return rows; 
    }    
    
    	
    @Override
    public int editPerformance(PerformanceData performance)  { 
    	
    	/** Если возвращает ">0", то изменения прошли успешно
    	 * если возвращает "0", то не удалось найти PERFORMANCE с заданным ID
    	 * если возвращает "<0", то отсутсвуют параметры с заданным в бине языком    	 
    	 */
    	    	
    	String queryPerform = "UPDATE PERFORMANCES SET START_DATE= ?, END_DATE = ?, CATEGORY_ID= ? WHERE PERFORMANCE_ID = ?";    	
    	String queryProperList = "SELECT PERFORMANCES_PROPERTIES.PROPERTY_ID, PROPERTY_NAME_ID  FROM PERFORMANCES_PROPERTIES, PROPERTIES  WHERE PERFORMANCE_ID="+
    							performance.getId()+
    							" AND PERFORMANCES_PROPERTIES.PROPERTY_ID=PROPERTIES.PROPERTY_ID";     	
    	String queryPropUpdate = "UPDATE PROPERTIES SET PROPERTY_VALUE= ? WHERE PARENT_ID= ? AND LANG_ID = ?";
    			
    	int langID= performance.getLanguage();
    	int rows = 0;
    	int propRows = 0;
    	PreparedStatement psPerformance = null;
    	ResultSet rsPropList = null;
    	Statement statement = null;
    	
    	
		try {
			psPerformance = connection.prepareStatement(queryPerform);
			
			psPerformance.setDate(1, performance.getStartDate());
	    	psPerformance.setDate(2, performance.getEndDate());
	    	psPerformance.setInt(3, performance.getCategory().getId());
	    	psPerformance.setInt(4, performance.getId());
	    	
	    	// oбновление строк таблицы PERFORMANCES
	    	rows = psPerformance.executeUpdate();
	    	
	    	statement = connection.createStatement();
	    	// выборка всех дополнительных свойств для Performance из  PERFORMANCES_PROPERTIES
	    	rsPropList = statement.executeQuery(queryProperList);
	    	
	    	while (rsPropList.next()){
	    		
	    		int propertyID = rsPropList.getInt(1); // ID текущего свойства
	    		int propNameId = rsPropList.getInt(2); // ID имени свойства
	    			// подготовленный запрос на обновление поля PROPERTY_VALUE таблицы PROPERTIES значением из полученного бина
	    			PreparedStatement ps = connection.prepareStatement(queryPropUpdate);
	    			ps.setInt(2, propertyID);
	    			ps.setInt(3, langID);
	    	
	    			switch (propNameId){ // propNameId - ID имени свойтсва из таблицы PROPERTIES или PROPERTY_NAME_ID, можно задавать константами прямо в бине
	    			case PerformanceData.NAME :
	    				ps.setString(1, performance.getName());
	    				break;
	    			case PerformanceData.SHORT_DESCRIPTION:
	    				ps.setString(1, performance.getShortDescription());
	    				break;
	    			case PerformanceData.DESCRIPTION:
	    				ps.setString(1, performance.getDescription());
	    				break;
	    			case PerformanceData.IMAGE:
	    				ps.setString(1, performance.getImage());
	    				break;
	    				}
	    			// внесение изменений в таблицу PROPERTIES (менятеся поле VALUE)
	    			propRows = ps.executeUpdate();
	    		}
	    		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    finally {
    	closeAll(rsPropList, psPerformance,statement);
    }    		
    		
    	if (propRows<=0)
    		return -1;
    	else
        return rows; 
    }

    @Override
    public int deletePerformance(int id)  {
    	
    	String deletePerformanceQuery= "DELETE FROM PERFORMANCES WHERE PERFORMANCE_ID="+id;
    	String selectQuery = "SELECT PROPERTY_ID FROM PERFORMANCES_PROPERTIES WHERE PERFORMANCE_ID="+id;
    	String deletePropQuery = "DELETE FROM PROPERTIES WHERE PROPERTY_ID =?";
    	
    	int rows = 0;
    	
    	Statement statement = null;
    	ResultSet rs = null;
    	PreparedStatement pStatement = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(selectQuery); 
			
			/*
	    	* если удалилась строка из таблицы PERFORMANCES, удаляем соотвествующие ей PROPERTIES_ID
	    	* из таблицы PROPERTIES
	    	*/
	    		pStatement = connection.prepareStatement(deletePropQuery);
	        	
	    		while (rs.next()){
	    			
	    			pStatement.setInt(1, rs.getInt(1));   
	    			pStatement.executeUpdate();    			
	    			}
	    		
	    		rows = statement.executeUpdate(deletePerformanceQuery); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeAll(rs, pStatement, statement);
		}
    	     	   	
        return rows;  
    }
    
    
    public PerformanceData createPerformance (ResultSet rs){
        PerformanceData performance = new PerformanceData();
        ORACLECategoryDAO categoryDAO = new ORACLECategoryDAO(connection);

		try {				
                    performance.setId(rs.getInt("PERFORMANCE_ID"));
                    performance.setStartDate(rs.getDate("START_DATE"));
                    performance.setEndDate(rs.getDate("END_DATE"));
                    performance.setCategory(categoryDAO.createCategory(rs));
               

				//проверка имени свойства и присвоения его бину
                rs.beforeFirst();

                while (rs.next()){
					int propNameID = rs.getInt("PROPERTY_NAME_ID");
                    String propValue = rs.getString("PROPERTY_VALUE");

					switch (propNameID)
						{
						case  PerformanceData.NAME :
                            performance.setName(propValue);
							break;
						case  PerformanceData.SHORT_DESCRIPTION :
                            performance.setShortDescription(propValue);
							break;
						case PerformanceData.DESCRIPTION :
                            performance.setDescription(propValue);
							break;
						case PerformanceData.IMAGE :
                            performance.setImage(propValue);
							break;  						
						}    								
					
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 

        return performance;
    }
    
    
    private int getGeneratedKey (PreparedStatement pStatement) throws SQLException{       	
     	
    	ResultSet rsProp = pStatement.getGeneratedKeys(); // получние автоматических ключей		
 	
 	int propId = 0; // автосгенерированный ID добавленного поля 
 	
			while (rsProp.next()){
				
				propId=	rsProp.getInt(1); 
			
			}
			rsProp.close();
			return propId;
    }
    
    
    private void closeAll(ResultSet rs, PreparedStatement ps, Statement st) {
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
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
            }
        }    
}
    
    public void closeConncetion (){
    	ConnectionPool pool = ConnectionPool.getInstance();
		pool.releaseConnection(connection);
		
    }
    
}