/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Model.Appointment;
import Utils.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cjd
 */
public class AppointmentDOA extends DataAccessObject<Appointment> {

    private static final String INSERT = "INSERT INTO appointment "
            + "(userId, "
            + "customerId, "
            + "title, "
            + "location, "
            + "contact, "
            + "type, "
            + "url, "
            + "description, "
            + "start,"
            + "end, "
            + "createDate, "
            + "createdBy, "
            + "lastUpdate, "
            + "lastUpdateBy) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
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
    private static final String DELETE = "DELETE FROM appointment WHERE appointmentId=?;";

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

    @Override
    public Appointment update(Appointment dto) {
        Appointment appointment = null;
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);){
            
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

        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return appointment;
    }
 

    @Override
    public Appointment create(Appointment dto) {
        Appointment appointment = null;
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);){
            
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

            statement.setTimestamp(11, Timestamp.from(Instant.now()));
            //statement.setString(12,User.getUserName());
            statement.setString(12,"hardCodedTest");
            statement.setTimestamp(13, Timestamp.from(Instant.now()));
            //statement.setString(14,User.getUserName());
            statement.setString(14,"hardCodedTest");
            
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {

                dto.setCustomerId(resultSet.getInt(1));
                
            }   
            return dto;
            
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
