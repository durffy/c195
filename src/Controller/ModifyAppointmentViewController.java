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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    @FXML private DatePicker DatePickerEnd;
    
    @FXML private MenuButton MenuButtonStart;
    @FXML private MenuButton MenuButtonEnd;
    
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
        appointment.getStartTime().toInstant();
        DatePickerEnd.setValue(appointment.getEndTime().toLocalDateTime().toLocalDate());
        appointment.getEndTime().toInstant();
        TextAreaDescription.setText(appointment.getDescription());
        
        
        String[] interval = new String[]{":00",
            ":15",
            ":30",
            ":45"};
        
        for (int i=0; i < 24; i++){  
            for (String interval1 : interval) {
                MenuItem StartMenuItem = new MenuItem();
                
                StartMenuItem.setText(String.format("%d%s", i, interval1));
                StartMenuItem.setOnAction((event)->{
                    
                    MenuButtonStart.setText(StartMenuItem.getText());
                    
                });
                MenuItem EndMenuItem = new MenuItem();
                EndMenuItem.setText(String.format("%d%s", i, interval1));
                EndMenuItem.setOnAction((event)->{
                    
                    MenuButtonEnd.setText(EndMenuItem.getText());
                    
                    
                });
                
                MenuButtonStart.getItems().addAll(StartMenuItem);
                MenuButtonEnd.getItems().addAll(EndMenuItem);
            }
        }
    }    

}
