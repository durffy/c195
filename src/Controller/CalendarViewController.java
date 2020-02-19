/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.DAO.AppointmentDOA;
import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;




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
    @FXML private TableView<Appointment> TableViewMonth;
    @FXML private TableColumn<Appointment, Timestamp> TableMonthColumnStart;
    @FXML private TableColumn<Appointment, String> TableMonthColumnTitle;
    @FXML private TableColumn<Appointment, String> TableMonthColumnLocation;
    @FXML private TableColumn<Appointment, String> TableMonthColumnContact;
    
    //weekly table view
    @FXML private TableView TableViewWeek;
    @FXML private TableColumn TableWeekColumnStart;
    @FXML private TableColumn TableWeekColumnEnd;
    @FXML private TableColumn TableWeekColumnTitle;
    @FXML private TableColumn TableWeekColumnDescription;
    @FXML private TableColumn TableWeekColumnContact;
    
    private AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
    private ObservableList<Appointment> Appointments = appointmentDOA.findAll();

    
    public void loadScene(Parent root, ActionEvent event){
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    public void clickButtonCustomerView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        loadScene(root, event);

    }
    
    public void clickButtonReportsView(ActionEvent event)throws IOException{
        
        //todo load the record
        

        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportsView.fxml"));
        loadScene(root, event);

    }
    
    public void clickButtonModifyAppointmentView(ActionEvent event)throws IOException{
        
        //todo: load the record
        //get selected appointment
        //setSelected appointment in the Modify view
        Appointment appointment = TableViewMonth.getSelectionModel().getSelectedItem();
        ModifyAppointmentViewController.appointment = appointment;
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentView.fxml"));
        loadScene(root, event);

    }
    
    public void clickButtonAddAppointmentView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentView.fxml"));
        loadScene(root, event);


    }
    
    public void clickButtonRemove(ActionEvent event) throws IOException{
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        alert.setTitle("Removal Confirmation");
        alert.setContentText("Do you want to delete this Record?");
        alert.show();
        
        //todo: delete record
    }
        
    public void clickButtonClose(ActionEvent event) throws IOException{
        
        //todo: close the view
        
    }

    public void loadWeeklySchedule(){
        //TODO: get the date
        //TODO: load monday through friday based on the date
        TableWeekColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableWeekColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableWeekColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableWeekColumnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        TableWeekColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
           
        TableViewWeek.setItems(Appointments);
        
    }
    
    public void loadMonthlySchedule(){
        //TODO: only load the month
        TableMonthColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableMonthColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableMonthColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableMonthColumnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
             
        TableViewMonth.setItems(Appointments);

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadMonthlySchedule();
        loadWeeklySchedule();
    
    }    
    
}
