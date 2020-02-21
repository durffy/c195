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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
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

    
    @FXML private DatePicker DatePickerDate;
    @FXML private Tab tabMonthly;
    @FXML private Tab tabWeekly;
    
    //monthly table view
    @FXML private TableView<Appointment> TableViewMonth;
    @FXML private TableColumn<Appointment, Timestamp> TableMonthColumnStart;
    @FXML private TableColumn<Appointment, String> TableMonthColumnTitle;
    @FXML private TableColumn<Appointment, String> TableMonthColumnLocation;
    @FXML private TableColumn<Appointment, String> TableMonthColumnContact;
    
    //weekly table view
    @FXML private TableView<Appointment> TableViewWeek;
    @FXML private TableColumn<Appointment, Timestamp> TableWeekColumnStart;
    @FXML private TableColumn<Appointment, Timestamp>  TableWeekColumnEnd;
    @FXML private TableColumn<Appointment, String> TableWeekColumnTitle;
    @FXML private TableColumn<Appointment, String> TableWeekColumnDescription;
    @FXML private TableColumn<Appointment, String> TableWeekColumnContact;
    
    private AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
    private ObservableList<Appointment> Appointments = appointmentDOA.findAll();
    
    public void loadScene(Parent root, ActionEvent event){
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    
    public void loadWeeklySchedule(){
        
        ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();;

        for(int i=0; i<Appointments.size();i++){
            
            int appointmentDay = Appointments.get(i).getStartTime().toLocalDateTime().getDayOfYear();
            int appointmentYear = Appointments.get(i).getStartTime().toLocalDateTime().getYear();

            int selectedDay = DatePickerDate.getValue().getDayOfYear();
            int selectedYear = DatePickerDate.getValue().getYear();

            if(appointmentDay-selectedDay <= 3 && 
                    appointmentDay-selectedDay >= -3 && 
                    appointmentYear == selectedYear){
                
                weeklyAppointments.add(Appointments.get(i));
                
            }
        } 
        TableWeekColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableWeekColumnEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        TableWeekColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableWeekColumnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        TableWeekColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
           
        TableViewWeek.setItems(weeklyAppointments);
        
    }
    
    public void loadMonthlySchedule(){
        
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();;

        for(int i=0; i<Appointments.size();i++){
            
            int appointmentMonth = Appointments.get(i).getStartTime().toLocalDateTime().getMonthValue();
            int appointmentYear = Appointments.get(i).getStartTime().toLocalDateTime().getYear();
            
            int selectedMonth = DatePickerDate.getValue().getMonthValue();
            int selectedYear = DatePickerDate.getValue().getYear();
            
            
            if(appointmentMonth==selectedMonth && appointmentYear==selectedYear){
                monthlyAppointments.add(Appointments.get(i));
            }
        } 
         
        TableMonthColumnStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableMonthColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableMonthColumnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableMonthColumnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
             
        TableViewMonth.setItems(monthlyAppointments);

    }
    
    
    public void clickButtonCustomerView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        loadScene(root, event);

    }
    
    public void clickButtonReportsView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportsView.fxml"));
        loadScene(root, event);

    }
    
    public void clickButtonModifyAppointmentView(ActionEvent event)throws IOException{
        
        Appointment appointment=null;
        
        if(tabMonthly.isSelected()){
            if(!TableViewMonth.getSelectionModel().isEmpty()){

                appointment = TableViewMonth.getSelectionModel().getSelectedItem();


            }
        }else if(tabWeekly.isSelected()){
            if(!TableViewWeek.getSelectionModel().isEmpty()){

                appointment = TableViewWeek.getSelectionModel().getSelectedItem();

            }
        } 
        
        ModifyAppointmentViewController.appointment = appointment;
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentView.fxml"));
        loadScene(root, event);
        loadMonthlySchedule();
        loadWeeklySchedule();
    }
    
    public void clickButtonAddAppointmentView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointmentView.fxml"));
        loadScene(root, event);
        
    }
    
    public void clickButtonRemove(ActionEvent event) throws IOException{
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        alert.setTitle("Removal Confirmation");
        alert.setContentText("Do you want to delete this Record?");

        Optional<ButtonType> result = alert.showAndWait();
        
        // check for confirmation, check which tab is selected, check which item in the tab is selected
        if(result.get() == ButtonType.OK){
            
            Appointment appointment=null;
            
            if(tabMonthly.isSelected()){
                if(!TableViewMonth.getSelectionModel().isEmpty()){

                    appointment = TableViewMonth.getSelectionModel().getSelectedItem();

                 }
             }else if(tabWeekly.isSelected()){
                if(!TableViewWeek.getSelectionModel().isEmpty()){

                     appointment = TableViewWeek.getSelectionModel().getSelectedItem();

                 }
             } 
            
            
            appointmentDOA.delete(appointment.getAppointmentId());
            Appointments.remove(appointment);
            loadWeeklySchedule();
            loadMonthlySchedule();
            
        }else {
            
        }
    }
     
    public void clickButtonLogout(ActionEvent event) throws IOException{
        
        //todo: close the view
        
    }
    public void clickDatePicker (ActionEvent event) throws IOException{
        
        loadWeeklySchedule();
        loadMonthlySchedule();
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DatePickerDate.setValue(LocalDate.now());
        loadMonthlySchedule();
        loadWeeklySchedule();
    
    }    
    
}
