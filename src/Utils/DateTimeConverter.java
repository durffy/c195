/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
/**
 *
 * @author CDuffy
 */
public class DateTimeConverter {
    
   public static Timestamp toLocalDateTime(Timestamp utc){

        LocalDateTime ldt = utc.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime newLdt = newZdt.toLocalDateTime();
        
        return Timestamp.valueOf(newLdt);

    }

    public static Timestamp toUTC(Timestamp ts){

        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime newZdt = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime newLdt = newZdt.toLocalDateTime();

        return Timestamp.valueOf(newLdt);

    }
    
}
