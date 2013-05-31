package by.academy.DAO.user;

import by.academy.Model.UserData;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 09.05.13
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class ORACLEUserDAO implements UserDAO {
    private Connection connection = null;

    public ORACLEUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserData getUserById(int id) {
        String userQuery = "SELECT USERS.*, PROPERTIES.PROPERTY_NAME_ID, PROPERTIES.PROPERTY_VALUE " +
                           "FROM USERS JOIN USERS_PROPERTIES ON USERS.USER_ID=USERS_PROPERTIES.USER_ID" +
                                                " JOIN PROPERTIES ON USER_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID " +
                           "WHERE  USERS.USER_ID= "+id;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserData userData = new UserData();
        try {
            ps = connection.prepareStatement(userQuery);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()){
                userData = createUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps, null);
        }
        return userData;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public List<UserData> getAllUsers(){
        String userQuery = "SELECT USERS.*, PROPERTIES.PROPERTY_NAME_ID, PROPERTIES.PROPERTY_VALUE " +
                           "FROM USERS JOIN USERS_PROPERTIES ON USERS.USER_ID=USERS_PROPERTIES.USER_ID" +
                                          " JOIN PROPERTIES ON USER_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID";
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserData userData = new UserData();
        ArrayList<UserData> allUsers = new ArrayList<UserData>();
        try {
            ps = connection.prepareStatement(userQuery);
            rs = ps.executeQuery();
            while (rs.next()){
                userData = createUser(rs);
                allUsers.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps, null);
        }
        return allUsers;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public UserData getUserByEmail(String email) {
        String userQuery = "SELECT USERS.*, PROPERTIES.PROPERTY_NAME_ID, PROPERTIES.PROPERTY_VALUE " +
                           "FROM USERS JOIN USERS_PROPERTIES ON USERS.USER_ID=USERS_PROPERTIES.USER_ID" +
                                         " JOIN PROPERTIES ON USER_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID " +
                           "WHERE  USER.EMAIL= "+ email;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserData userData = new UserData();
        try {
            ps = connection.prepareStatement(userQuery);
            rs = ps.executeQuery();
            if (rs.next()){
                userData = createUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps, null);
        }
        return userData;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public UserData getUserByEmailAndPassword(String email, String password) {
        String userQuery = "SELECT USERS.*, PROPERTIES.PROPERTY_NAME_ID, PROPERTIES.PROPERTY_VALUE " +
                           "FROM USERS JOIN USERS_PROPERTIES ON USERS.USER_ID=USERS_PROPERTIES.USER_ID" +
                                             " JOIN PROPERTIES ON USER_PROPERTIES.PROPERTY_ID = PROPERTIES.PARENT_ID " +
                           "WHERE  USER.EMAIL= " + email + " AND  USER.PASSWORD =" + password;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserData userData = new UserData();
        try {
            ps = connection.prepareStatement(userQuery);
            rs = ps.executeQuery();
            if (rs.next()){
                userData = createUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(rs, ps, null);
        }
        return userData;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public int addUser(UserData user) {
    	
    	int userId = addUsersBaseFields(user);
    	int[] propertyIdList  = addUserPropertiesValues(user);
    	
    	addUserProperties(propertyIdList, userId);
    	
    	return userId;
    	
    }

    
    public int addUsersBaseFields(UserData user){
    	String addQuery= "INSERT INTO USERS (EMAIL, PASSWORD) VALUES (?, ?)";
        PreparedStatement ps=null;
        int row = 0;
        int userId = 0;    
            try {
                ps = connection.prepareStatement(addQuery, new String [] {"USER_ID"});
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getPassword());
                row =   ps.executeUpdate();
                if (row>0)
                    userId = getGeneratedKey(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally{
                closeAll(null, ps, null);
            }
    	
		return userId;
    	
    }
    
    public int [] addUserPropertiesValues(UserData user){	
    	
    	PreparedStatement ps = null;
   	 
    	String userPropQuery = "INSERT INTO PROPERTIES (PROPERTY_NAME_ID, PROPERTY_VALUE, LANG_ID, PARENT_ID)" +
                 "VALUES (?,?,?,?) ";
    	
    	int addProp = UserData.PROPERTIES_COUNT;
    	
    	int [] propertyIdList = new int[addProp];
    	 
		try {    	
    		 // Подготовленный запрос для добавления новых строк в таблицу PROPERTIES
            
		ps = connection.prepareStatement(userPropQuery, new String [] {"PROPERTY_ID"} );
		
        
          int propID = 0;
          int langID =1;
         
         
          for (int i=0; i<addProp;i++) { //здесь addProp - число дополнительных свойств бина Users
        	  
          ps.setInt(4, propID); // внесение родительского проперти
          ps.setInt(3, langID); // язык берётся из полученного бина
          ps.setInt(4, 0); //  родительский ID = 0 для parent свойств

                  switch (i){
                      case 0:
                          ps.setInt(1,  UserData.FIRST_NAME);
                          ps.setString(2, user.getName());
                          break;
                      case 1:
                          ps.setInt(1,  UserData.SURNAME);
                          ps.setString(2, user.getSurname());                        
                          break;
                      case 2:
                          ps.setInt(1, UserData.PHONE_NUMBER);
                          ps.setString(2, user.getPhoneNumber());
                          break;
                      case 3:
                          ps.setInt(1,UserData.CITY);
                          ps.setString(2, user.getCity());               
                          break;
                  }
            ps.executeUpdate();
            
          	propertyIdList [i] = getGeneratedKey(ps);
          }
    	 } catch (SQLException e) {
    		 // TODO Auto-generated catch block
    		 e.printStackTrace();
    	 }
		finally{
			closeAll(null, ps, null);
				}
		
			return propertyIdList;    	
    	
    }
    
    public int addUserProperties(int[] propertyIdList, int userId){
    	
    	 String propListQuery = "INSERT INTO USERS_PROPERTIES (USER_ID,PROPERTY_ID) VALUES (?,?) ";
         // запрос для добавление строк в таблицу USERS_PROPERTIES
    	 PreparedStatement ps=null;
    	 int row=0;
    	 
         try {
			ps = connection.prepareStatement(propListQuery);
			 for (int propId :  propertyIdList){
		         ps.setInt(1, userId); // ID  USERS
		         ps.setInt(2, propId); // ID Property
		     row +=    ps.executeUpdate(); }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         finally{
        	 closeAll(null, ps, null);
         }
        return row;
    }
//----------------------------------------------------------------------------------------------------------------------
    @Override
    public int editUser(UserData user) {
        String queryUser = "UPDATE USERS SET EMAIL= ?, PASSWORD = ?,  WHERE USER_ID = " + user.getId();

        String queryProperList = "SELECT USERS_PROPERTIES.PROPERTY_ID, PROPERTY_NAME_ID  FROM USERS_PROPERTIES, PROPERTIES  WHERE USER_ID="+
                                  user.getId() + "AND USERS_PROPERTIES.PROPERTY_ID=PROPERTIES.PROPERTY_ID";

        String queryPropUpdate = "UPDATE PROPERTIES SET PROPERTY_VALUE= ? WHERE PROPERTY_ID= ? ";

        int row =0;
        int propRows = 0;
        PreparedStatement ps = null;
        ResultSet rsPropList = null;
        Statement statement = null;
        try {
            ps = connection.prepareStatement(queryUser);

            ps.setInt(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            row = ps.executeUpdate();
            statement = connection.createStatement();
            // выборка всех дополнительных свойств для USER из  USERS_PROPERTIES
            rsPropList = statement.executeQuery(queryProperList);

            while (rsPropList.next()){
                int propertyID = rsPropList.getInt(1); // ID текущего свойства
                int propNameId = rsPropList.getInt(2); // ID имени свойства
                // подготовленный запрос на обновление поля PROPERTY_VALUE таблицы USERS значением из полученного бина
                PreparedStatement ps2 = connection.prepareStatement(queryPropUpdate);
                ps.setInt(2, propertyID);
               // ps.setInt(3, langID);

                switch (propNameId){ // propNameId - ID имени свойтсва из таблицы PROPERTIES или PROPERTY_NAME_ID, можно задавать константами прямо в бине
                    case UserData.FIRST_NAME :
                        ps2.setString(1, user.getName());
                        break;
                    case UserData.SURNAME:
                        ps2.setString(1, user.getSurname());
                        break;
                    case UserData.PHONE_NUMBER:
                        ps2.setString(1, user.getPhoneNumber());
                        break;
                    case UserData.CITY:
                        ps2.setString(1, user.getCity());
                        break;
                }
                // внесение изменений в таблицу PROPERTIES (менятеся поле VALUE)
                propRows = ps2.executeUpdate();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally {
            closeAll(rsPropList, ps, statement);
        }
        if (propRows<=0)
            return -1;
        else
            return row;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public int deleteUser(int id) {
        String deleteUserQuery= "DELETE FROM USERS WHERE USER_ID=" + id;
        String selectQuery = "SELECT PROPERTY_ID FROM USERS_PROPERTIES WHERE USER_ID="+id;
        String deletePropQuery = "DELETE FROM PROPERTIES WHERE PROPERTY_ID =?";

        int rows = 0;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement pStatement = null;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(selectQuery);

			/*
	    	* если удалилась строка из таблицы USERS, удаляем соотвествующие ей PROPERTIES_ID
	    	* из таблицы PROPERTIES
	    	*/
            pStatement = connection.prepareStatement(deletePropQuery);
            while (rs.next()){
                pStatement.setInt(1, rs.getInt(1));
                pStatement.executeUpdate();
            }
            rows = statement.executeUpdate(deleteUserQuery);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            closeAll(rs, pStatement, statement);
        }
        return rows;
    }

//----------------------------------------------------------------------------------------------------------------------
    @Override
    public UserData createUser(ResultSet rs) throws SQLException {
        UserData user = new UserData();

        user.setId(rs.getInt("USER_ID"));
        user.setEmail(rs.getString("EMAIL"));
        user.setPassword(rs.getString("PASSWORD"));
        //Возможно надо добавить параметры

        return user;
    }

//----------------------------------------------------------------------------------------------------------------------
    private void closeAll(ResultSet rs, PreparedStatement ps, Statement statement) {
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

//---------------------------------------------------------------------------------------------------------------------
    private int getGeneratedKey (PreparedStatement pStatement) throws SQLException{

        ResultSet rsProp = pStatement.getGeneratedKeys(); // получние автоматических ключей

        int propId = 0; // автосгенерированный ID добавленного поля

        while (rsProp.next()){
            propId=	rsProp.getInt(1);
        }
        rsProp.close();
        return propId;
    }
}
