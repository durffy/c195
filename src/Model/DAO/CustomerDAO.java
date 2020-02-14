/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Customer;
import Utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author CDuffy
 */
public class CustomerDAO extends DataAccessObject<Customer>{

    private static final String INSERT = "INSERT INTO customer "
            + "(customerName, "
            + "addressId, "
            + "active, "
            + "createDate, "
            + "createdBy, "
            + "lastUpdate, "
            + "lastUpdateBy) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    
    private static final String GET_ONE = "SELECT "
            + "customerName, "
            + "addressId, "
            + "active, "
            + "createDate, "
            + "createdBy, "
            + "lastUpdate, "
            + "lastUpdateBy "
            + "FROM customer "
            + "WHERE customerId=?";
    
    private static final String GET_ALL = "SELECT "
            + "customerId"
            + "customerName, "
            + "addressId, "
            + "active, "
            + "createDate, "
            + "createdBy, "
            + "lastUpdate, "
            + "lastUpdateBy "
            + "FROM customer ";
    
    private static final String UPDATE = "UDPATE "
            + "customerName=?, "
            + "addressId=?, "
            + "active=?, "
            + "createDate=?, "
            + "createdBy=?, "
            + "lastUpdate=?, "
            + "lastUpdateBy=? "
            + "WHERE customerId=?";
    
    private static final String DELETE = "DELETE FROM customer "
            + "WHERE cusomterId = ?";
    
    public CustomerDAO(Connection connection) {
        super(connection);
        
    }
    
    @Override
    public Customer findById(int id) {
        
        Customer customer = new Customer();
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ONE)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setCustomerName(resultSet.getString("customerName"));
                customer.setAddressId(resultSet.getInt("addressId"));
                customer.setActive(resultSet.getInt("active"));
                customer.setCreateDate(resultSet.getTimestamp("createDate"));
                customer.setLastUpdate(resultSet.getTimestamp("lastUpdate"));
                customer.setCreatedBy(resultSet.getString("createdBy"));
                customer.setLastUpdateBy(resultSet.getString("lastUpdateBy"));
                
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
         
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List Customers = null;
        Customer customer = new Customer();
        
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)){

            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setCustomerName(resultSet.getString("customerName"));
                customer.setAddressId(resultSet.getInt("addressId"));
                customer.setActive(resultSet.getInt("active"));
                customer.setCreateDate(resultSet.getTimestamp("createDate"));
                customer.setLastUpdate(resultSet.getTimestamp("lastUpdate"));
                customer.setCreatedBy(resultSet.getString("createdBy"));
                customer.setLastUpdateBy(resultSet.getString("lastUpdateBy"));
                Customers.add(customer);
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
         
        return Customers;
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer = null;
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
            
            statement.setString(1, dto.getCustomerName());
            statement.setInt(2, dto.getAddressId());
            statement.setInt(3, dto.getActive());
            statement.setTimestamp(4, Timestamp.valueOf(dto.getCreateDate()));
            statement.setString(5, dto.getCreatedBy());
            statement.setTimestamp(6, Timestamp.valueOf(dto.getLastUpdate()));
            statement.setString(7, dto.getLastUpdateBy());
            statement.setInt(8, dto.getCustomerId());
            
            statement.execute();
            customer = this.findById(dto.getCustomerId());
            
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {

        try (PreparedStatement statement = this.connection.prepareStatement(INSERT);){
            //statement.setInt(1, dto.getCustomerId());
            statement.setString(1, dto.getCustomerName());
            statement.setInt(2, dto.getAddressId());
            statement.setInt(3, dto.getActive());
            statement.setTimestamp(4, Timestamp.valueOf(dto.getCreateDate()));
            statement.setString(5, dto.getCreatedBy());
            statement.setTimestamp(6, Timestamp.valueOf(dto.getLastUpdate()));
            statement.setString(7, dto.getLastUpdateBy());
            
            statement.execute();
            int id = this.getLastVal("CUSTOMER_SEQUENCE");
            return this.findById(id);
            
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE);){

            statement.setInt(1, id);
            statement.execute();
            
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }    
    }
    
}
