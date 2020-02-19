/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CDuffy
 */
public class ModifyAppointmentViewController implements Initializable {

    @FXML private TextField TextFieldAppointmentID;
    @FXML private TextField TextFieldUser;
    @FXML private TextField TextFieldTitle;
    @FXML private TextField TextFieldLocation;
    @FXML private TextField TextFieldContact;
    @FXML private TextField TextFieldType;
    @FXML private TextField TextFieldClient;
    @FXML private TextField TextFieldUrl;
    
    @FXML private DatePicker DatePickerStart;
    
    @FXML private TextArea TextAreaDescription;
    
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    
    public static Appointment appointment;
    
    public void loadCalendarView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    
    public void clickButtonSave(ActionEvent event) throws IOException{
        
        //check the input
        //add the record
        loadCalendarView(event);
        
    }
    
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        
        loadCalendarView(event);
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO, load the object to the fields for editing
        TextFieldAppointmentID.setText(Integer.toString(appointment.getAppointmentId()));
        TextFieldUser.setText(Integer.toString(appointment.getUserId()));
        TextFieldClient.setText(Integer.toString(appointment.getCustomerId()));
        TextFieldTitle.setText(appointment.getTitle());
        TextFieldLocation.setText(appointment.getLocation());
        TextFieldContact.setText(appointment.getContact()); 
        TextFieldType.setText(appointment.getType());
        TextFieldUrl.setText(appointment.getUrl());
        DatePickerStart.setValue(appointment.getStartTime().toLocalDateTime().toLocalDate());
        TextAreaDescription.setText(appointment.getDescription());

    }    
    
}
