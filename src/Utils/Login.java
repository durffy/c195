/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.User;
import java.util.logging.Logger;

/**
 *
 * @author CDuffy
 */
public class Login {
    private static User user;
    private Logger logger = Logger.getLogger("login");
    
    
    public Login(User user){
        setUser(user);
    }
    
    public static int getUserId(){
        return user.getUserId();
    }
    
    public static void setUser(User user){
        
        if(user != null){
            
        }
        Login.user = user;
    }
    
    public static String getUserName(){
        return user.getUserName();
    }
    
    
    
}
