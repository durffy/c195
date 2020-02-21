/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import Model.DAO.AppointmentDOA;
import Model.DAO.CustomerDAO;
import Model.DAO.UserDAO;
import Model.User;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author cjd
 */
public class ReportsViewController implements Initializable {

    @FXML private Button ButtonGoBack;
    
    @FXML private MenuButton MenuButtonReports;
    @FXML private MenuItem MenuReportsItemNumberOfAppointmentsByType;
    @FXML private MenuItem MenuReportsItemSchedulePerConsultant;
    @FXML private MenuItem MenuReportsItemActiveCustomers;
    
    @FXML private TextArea TextAreaOutput;
    
    public void clickButtonGoBack(ActionEvent event) throws IOException{
        
            Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
            Scene scene = new Scene(root);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            
    }
    
    public void clickMenuReportsItemNumberOfAppointmentsByType(ActionEvent event) throws IOException{

        TextAreaOutput.clear();
        
        //load query for Count of Appointments by type
        AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
        ObservableList<Appointment> appointments = appointmentDOA.findAll();
        
        Map<String, Long> appointmentsMap = appointments.stream()
            .collect(Collectors.groupingBy(Appointment::getType, Collectors.counting()));
        
        appointmentsMap.forEach((key, value)-> TextAreaOutput.appendText(String.format("%s: %d\r\n",key, value)));
        
    }
    
    public void clickMenuReportsItemSchedulePerConsultant(ActionEvent event) throws IOException{

        TextAreaOutput.clear();
        
        //load query for items per consultant
        AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
        ObservableList<Appointment> appointments = appointmentDOA.findAll();
        
        UserDAO userDAO = new UserDAO(DBConnection.getConnection());
        ObservableList<User> users = userDAO.findAll();
        
        Map<Integer, Long> appointmentsMap = appointments.stream()
            .collect(Collectors.groupingBy(Appointment::getUserId, Collectors.counting()));
        
        appointmentsMap.forEach((key, value)-> TextAreaOutput.appendText(String.format("%s: %d\r\n",users.get(key-1).getUserName(), value)));
        
    }
    
    public void clickMenuReportsItemActiveCustomers(ActionEvent event) throws IOException{
        //clear output on textarea
        TextAreaOutput.clear();
        
        //load query for the Number of Active Customers
        CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());
        ObservableList<Customer> Customers = customerDAO.findAll();
        
        int count = 0;
        
        for(int i =0; i < Customers.size(); i ++){
            if(Customers.get(i).getActive() == 1){
                count++;
            }
        }
        
        TextAreaOutput.setText(String.format("Active: %s", count));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
