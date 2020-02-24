/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.User;
import Utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author CDuffy
 */
public class UserDAO extends DataAccessObject<User>{


    private final String GET_USER = "SELECT * FROM user WHERE userName=? AND password=?";
    private String GET_ALL = "SELECT * FROM user;";
    
    public UserDAO(Connection connection) {
        super(connection);
        
    }

    /**
     *
     * @param id
     * @return
     */

    @Override
    public User findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<User> findAll() {
        ObservableList<User> Users = FXCollections.observableArrayList();
        

        
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = statement.executeQuery();  
            while(resultSet.next()){
                
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("password"));
                user.setActive(resultSet.getInt("active"));
                user.setCreateDate(resultSet.getTimestamp("createDate"));
                user.setCreatedBy(resultSet.getString("createdBy"));
                user.setLastUpdate(resultSet.getTimestamp("lastUpdate"));
                user.setLastUpdateBy(resultSet.getString("lastUpdateBy"));

                Users.add(user);
            }
            
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
        
        return Users;
        
    }

    @Override
    public User update(User dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User create(User dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
}
