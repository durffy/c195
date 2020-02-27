/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

/**
 *
 * @author CDuffy
 */
public class Scheduler {
    
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
}
