/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import Controller.LoginViewController;
import Model.Appointment;
import Utils.DataAccessObject;
import Utils.DateTimeConverter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
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
            + "end=?, "
            + "lastUpdate=?,"
            + "lastUpdateBy=? "
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
                
                //date and time conversion UTC to LocalDateTime Timestamp
                Timestamp start = DateTimeConverter.toLocalDateTime(resultSet.getTimestamp("start"));
                Timestamp end =  DateTimeConverter.toLocalDateTime(resultSet.getTimestamp("end"));
                Timestamp createDate =  DateTimeConverter.toLocalDateTime(resultSet.getTimestamp("createDate"));
                Timestamp lastUpdate = DateTimeConverter.toLocalDateTime(resultSet.getTimestamp("lastUpdate"));
                
                appointment.setStartTime(start);
                appointment.setEndTime(end);
                appointment.setCreatedDate(createDate);
                appointment.setLastUpdate(lastUpdate);
                
                appointment.setCreatedBy(resultSet.getString("createdBy"));
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
            
            //date and time conversion LocalDateTime Timestamp to UTC Timestamp
            Timestamp start = DateTimeConverter.toUTC(dto.getStartTime());
            Timestamp end =  DateTimeConverter.toUTC(dto.getEndTime());
            dto.setLastUpdate(Timestamp.from(Instant.now()));
            Timestamp lastUpdate = DateTimeConverter.toUTC(dto.getLastUpdate());

            statement.setTimestamp(9, start);
            statement.setTimestamp(10, end);
            statement.setTimestamp(11, lastUpdate);
            statement.setString(12, dto.getLastUpdateBy());
            statement.setInt(13, dto.getAppointmentId());

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

            //date and time conversion UTC to LocalDateTime Timestamp
            Timestamp start = DateTimeConverter.toUTC(dto.getStartTime());
            Timestamp end =  DateTimeConverter.toUTC(dto.getEndTime());
            Timestamp now = DateTimeConverter.toUTC(Timestamp.from(Instant.now()));
            
            statement.setTimestamp(9, start);
            statement.setTimestamp(10,end);

            statement.setTimestamp(11, now);
            statement.setString(12, LoginViewController.CurrentUser.getUserName());
            statement.setTimestamp(13, now);
            statement.setString(14,LoginViewController.CurrentUser.getUserName());
            
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
