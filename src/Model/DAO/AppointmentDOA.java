/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Appointment;
import Model.Customer;
import Utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cjd
 */
public class AppointmentDOA extends DataAccessObject<Appointment> {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE SET";
    private static final String GET_ALL = "SELECT * FROM appointment";
    private static final String DELETE = "DELETE FROM appointment";

    public AppointmentDOA(Connection connection) {
        super(connection);
    }
    
    @Override
    public Appointment findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Appointment> findAll() {
        ObservableList<Appointment> Appointment = FXCollections.observableArrayList();
        
        
        try(PreparedStatement statement = this.connection.prepareStatement(GET_ALL)){

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointmentId"));
                appointment.setCustomerId(resultSet.getInt("customerId"));
                appointment.setUserId(resultSet.getInt("userId"));
                appointment.setTitle(resultSet.getString("title"));
                appointment.setDescription(resultSet.getString("description"));
                appointment.setLocation(resultSet.getString("contact"));
                appointment.setContact(resultSet.getString("type"));
                appointment.setUrl(resultSet.getString("url"));
                appointment.setStartTime(resultSet.getTimestamp("start"));
                appointment.setEndTime(resultSet.getTimestamp("end"));
                appointment.setCreatedDate(resultSet.getTimestamp("createDate"));
                appointment.setCreatedBy(resultSet.getString("createdBy"));
                appointment.setLastUpdate(resultSet.getTimestamp("lastUpdate"));
                appointment.setLastUpdateBy(resultSet.getString("lastUpdateBy"));
                
                Appointment.add(appointment);

            }

        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
        return Appointment;
    }

    @Override
    public Appointment update(Appointment dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Appointment create(Appointment dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    


    
}
