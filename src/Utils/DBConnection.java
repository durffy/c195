/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    private static final String databaseName="";
    private static final String DB_URL="jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username="";
    private static final String password="";
    private static final String driver="com.mysql.jdbc.Driver";

    static Connection connection;
  
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{

      Class.forName(driver);
      //connection = DriverManager.getConnection(DB_URL, username, password);

      System.out.println("Connection Successful!");

    }

    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
      connection.close();
      System.out.println("Connection Closed!");
    }
  
}  
//    private ObservableList<Appointment> Appointments;
//    private ObservableList<Customer> Customers;
//    private ObservableList<Address> Addresses;
//    private ObservableList<City> Cities;
//    private ObservableList<Country> Countries;
//    
