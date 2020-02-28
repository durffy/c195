/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.Appointment;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import Model.DAO.AppointmentDOA;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author CDuffy
 */
public class Scheduler {
    

    // REQUIREMENT F
    public static Boolean checkForScheduleErrors(Timestamp startTime, Timestamp endTime){
        boolean passedChecks= false;
        
        if(startTime.before(Timestamp.from(Instant.now()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Schedule Error");
            alert.setContentText("Start Time cannot be in the past");

            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
                alert.setTitle(rb.getString(alert.getTitle())); 
                alert.setContentText(rb.getString(alert.getContentText()));

            }

            alert.show();
        }else if(startTime.after(endTime)){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Schedule Error");
            alert.setContentText("Start time occurs after the End Time");

            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
                alert.setTitle(rb.getString(alert.getTitle())); 
                alert.setContentText(rb.getString(alert.getContentText()));

            }

            alert.show();

        
        }else if(startTime.toLocalDateTime().getHour() < 6 ||//if the start time is before 06:00
                endTime.toLocalDateTime().getHour() < 6 ||//if the end time is before 06:00
                startTime.toLocalDateTime().getHour() > 20 ||//if the start time is after 20:00
                endTime.toLocalDateTime().getHour() > 20 ){//if the end time is after 20:00
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Schedule Error");
            alert.setContentText("Start or End Time is out of the range of 06:00 and 20:00 hours");

            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
                alert.setTitle(rb.getString(alert.getTitle())); 
                alert.setContentText(rb.getString(alert.getContentText()));

            }

            alert.show();
            
        }else{
            
            passedChecks = true;
        
        }
        
        return passedChecks;
        
    }
    
    
    //
    public static boolean CheckForScheduleOverLap(Timestamp start, Timestamp end){
        
        //True for No Overlap in Schedule found, False if there is an overlap found
        boolean NoOverlapFound = true;
        
        AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
        ObservableList<Appointment> AllAppointments = appointmentDOA.findAll();
        ObservableList<Appointment> AppointmentsWithOverlap = FXCollections.observableArrayList();
        
        for(Appointment appointment : AllAppointments){
                Timestamp s = appointment.getStartTime();
                Timestamp e = appointment.getEndTime();
                
                //check if the new datestart time falls between a startdate time and end time of another record
                if(appointment.getCustomerId() == Login.getUserId() &&//if the user ID matches AND (
                        (s.before(start) && e.after(start)|| // if the new time is between an appointment start and end time
                        s.before(end) && e.after(end)||// OR if the new end time is between an appointment start and end time
                        start.before(s) && end.after(s) ||// OR if the old start time is between the new appointment start and end time
                        start.before(e) && end.before(e) //OR if the old end time is between the new appointment start and end time
                        )){

                    AppointmentsWithOverlap.add(appointment);
                    NoOverlapFound = false;
                    
                }
        }
        
        if(!NoOverlapFound){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Schedule Error");
            alert.setContentText("The Schedule is overlaping with the following:" );

            
            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
                alert.setTitle(rb.getString(alert.getTitle())); 
                alert.setContentText(rb.getString(alert.getContentText()));

            }
            
            
            String AppointmentsString = "";
            for(Appointment apt: AppointmentsWithOverlap){
                AppointmentsString += apt.getTitle() + " at " + apt.getStartTime() + "\r\n";
            }
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            TextArea textArea = new TextArea(AppointmentsString);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.show();
        }
        
        
        return NoOverlapFound;

    }
}
