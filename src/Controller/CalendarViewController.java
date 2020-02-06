/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cjd
 */
public class CalendarViewController implements Initializable {

    @FXML private Button ButtonCustomerView;
    @FXML private Button ButtonReportsView;
    @FXML private Button ButtonAddAppointmentView;
    @FXML private Button ButtonModifyAppointmentView;
    
    //monthly table view
    @FXML private TableView TableViewMonthly;
    @FXML private TableColumn TableMonthlyColumnSunday;
    @FXML private TableColumn TableMonthlyColumnMonday;
    @FXML private TableColumn TableMonthlyColumnTuesday;
    @FXML private TableColumn TableMonthlyColumnWednesday;
    @FXML private TableColumn TableMonthlyColumnThursday;
    @FXML private TableColumn TableMonthlyColumnFriday;
    @FXML private TableColumn TableMonthlyColumnSaturday;
    
    //weekly table view
    @FXML private TableView TableViewWeekly;
    @FXML private TableColumn TableWeeklyColumnWeek;
    @FXML private TableColumn TableWeeklyColumnDateTime;
    @FXML private TableColumn TableWeeklyColumnTitle;
    @FXML private TableColumn TableWeeklyColumnDescription;
    @FXML private TableColumn TableWeeklyColumnLocation;
    @FXML private TableColumn TableWeeklyColumnContact;
    
    
    
    public void clickButtonCustomerView(ActionEvent event)throws IOException{
        

        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    
    public void clickButtonReportsView(ActionEvent event)throws IOException{
        
        //todo load the record

        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportsView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    
    public void clickButtonModifyAppointmentView(ActionEvent event)throws IOException{
        
        //todo: load the record
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    
    public void clickButtonAddAppointmentView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    
    public void clickButtonRemove(ActionEvent event) throws IOException{
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        alert.setTitle("Removal Confirmation");
        alert.setContentText("Do you want to delete this Record?");
        alert.show();
        
        //todo: delete record
    }
        
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
