/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Address;
import Model.Customer;
import Utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressDAO extends DataAccessObject<Address>{

    private final String GET_ALL = "SELECT * FROM address;";

    public AddressDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Address findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Address> findAll() {
        ObservableList<Address> Addresses = FXCollections.observableArrayList();
        
        
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)){

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Address address = new Address();
                address.setAddressId(resultSet.getInt("addressId"));
                address.setAddress(resultSet.getString("address"));
                address.setAddress2(resultSet.getString("address2"));
                address.setCityId(resultSet.getInt("cityId"));
                address.setPostalCode(resultSet.getString("postalCode"));
                address.setPhone(resultSet.getString("phone"));
                address.setCreateDate(resultSet.getTimestamp("createDate"));
                address.setLastUpdate(resultSet.getTimestamp("lastUpdate"));
                address.setCreatedBy(resultSet.getString("createdBy"));
                address.setLastUpdateBy(resultSet.getString("lastUpdateBy"));

                
                Addresses.add(address);

            }

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
         
        return Addresses;
    }

    @Override
    public Address update(Address dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Address create(Address dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
