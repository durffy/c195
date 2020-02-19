/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    private static final String databaseName="U03Tlb";
    private static final String DB_URL="jdbc:mysql://3.227.166.251/" + databaseName;
    private static final String username="U03Tlb";
    private static final String password="53688076365";
    private static final String driver="com.mysql.jdbc.Driver";

    static Connection connection;
  
    public static Connection openConnection() throws ClassNotFoundException, SQLException, Exception{

        Class.forName(driver);
        connection = (Connection) DriverManager.getConnection(DB_URL, username, password);

        System.out.println("Connection Successful!");

        return connection;
    }
    
    public static Connection getConnection(){
        return connection;
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
