/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.User;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CDuffy
 */
public class Login {
    private static User user;

    public Login(User user) throws IOException{
        setUser(user);
    }
    
    public static int getUserId(){
        return user.getUserId();
    }
    
    public static void setUser(User user) throws IOException{
        
        if(user != null){
            
            writeToLogFile("LOGIN", user.getUserName());
          
        }
        Login.user = user;
    }
    
    public static String getUserName(){
        return user.getUserName();
    }
    
    public static void Logout() throws IOException{
        
        writeToLogFile("LOGOUT", Login.user.getUserName());
        setUser(null);
    }
    
    private static void writeToLogFile(String status, String user) throws IOException{
        
        FileWriter fw = new FileWriter("login.log", true);
        PrintWriter pw = new PrintWriter(fw);
        Timestamp now = Timestamp.from(Instant.now());
        //Date now = Date.from(Instant.now());

        //pw.print("["+ now +"] "+ status + " user: " + user + "\r\n");
        
        pw.print(String.format("[%s] %s user: %s\r\n", now.toString(), status, user));
        pw.close();
    }
}
