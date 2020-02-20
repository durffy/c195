/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import static Controller.ModifyAppointmentViewController.appointment;
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
    
    private static final String UPDATE = "UPDATE appointment SET "
            + "userId=?, "
            + "customerId=?,"
            + "title=?, "
            + "location=?, "
            + "contact=?, "
            + "type=?, "
            + "url=?, "
            + "description=?, "
            + "start=?, "
            + "end=? "
            + "WHERE appointmentId=?";
    
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
                appointment.setLocation(resultSet.getString("location"));
                appointment.setContact(resultSet.getString("contact"));
                appointment.setType(resultSet.getString("type"));
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

/*
            1+ "userId=?, "
            2+ "customerId=?,"
            3+ "title=?, "
            4+ "location=?, "
            5+ "contact=?, "
            6+ "type=?, "
            7+ "url=?, "
            8+ "description=?, "
            9+ "start=?, "
            10+ "end=?";
            11+ "WHERE appointmentId=?";

*/
    @Override
    public Appointment update(Appointment dto) {
        Appointment appointment = null;
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
            
            System.out.println(dto.getCustomerId());
            
            statement.setInt(1, dto.getUserId());
            statement.setInt(2, dto.getCustomerId());
            statement.setString(3, dto.getTitle());
            statement.setString(4,dto.getLocation());
            statement.setString(5,dto.getContact());
            statement.setString(6, dto.getType());
            statement.setString(7,dto.getUrl());
            statement.setString(8, dto.getDescription());
            statement.setTimestamp(9, dto.getStartTime());
            statement.setTimestamp(10,dto.getEndTime());
            statement.setInt(11, dto.getAppointmentId());
            
            statement.execute();
            //customer = this.findById(dto.getCustomerId());
            
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return appointment;
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
