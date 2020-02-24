/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.DAO.UserDAO;
import Model.User;
import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CDuffy
 */
public class LoginViewController implements Initializable {

    @FXML private TextField TextFieldUsername;
    @FXML private PasswordField PasswordField;
    @FXML private Button ButtonLogin;
    @FXML private Button ButtonCancel;
    @FXML private Label LabelMainHeader;
    
    UserDAO userDAO = new UserDAO(DBConnection.getConnection());        
    ObservableList<User> users = userDAO.findAll();
    
    public static User CurrentUser;
    
    public User getCurrentUser(){
        return CurrentUser;
    }
    
    public void clickButtonLogin(ActionEvent event) throws IOException{
        
        boolean successfulLogin = false;
        
        for(User user : users){
            if(TextFieldUsername.getText().contains(TextFieldUsername.getText())){
                if(PasswordField.getText().contains(user.getPassword())){
                    
                    System.out.println(String.format("field: %s %s", TextFieldUsername.getText(), PasswordField.getText()));
                    System.out.println(String.format("class: %s %s", user.getUserName(), user.getPassword()));
                
                    CurrentUser = user;
                    successfulLogin = true;
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
                    Scene scene = new Scene(root);

                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                    
                }
            }
        }

        System.out.println(successfulLogin);
        
        if(!successfulLogin){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Credential Failure");
            alert.setContentText("Username and Password do not match");

            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
                alert.setTitle(alert.getTitle());
                alert.setContentText(alert.getContentText());
            }
            
            alert.showAndWait();
        }
    }
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closure Confirmation");
        alert.setContentText("Do you want to cancel the login?");

        if(!(Locale.getDefault()==Locale.US)){
            ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
            LoadLocales(rb); 
            alert.setTitle(alert.getTitle());
            alert.setContentText(alert.getContentText());
        }

        Optional<ButtonType> result = alert.showAndWait();
        
        // check for confirmation, check which tab is selected, check which item in the tab is selected
        if(result.get() == ButtonType.OK){
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }else {
            
        }
    }

    private void LoadLocales(ResourceBundle rb) {
        
        LabelMainHeader.setText(rb.getString(LabelMainHeader.getText()));
        TextFieldUsername.setPromptText(rb.getString(TextFieldUsername.getPromptText()));
        PasswordField.setPromptText(rb.getString(PasswordField.getPromptText()));
        ButtonLogin.setText(rb.getString(ButtonLogin.getText()));
        ButtonCancel.setText(rb.getString(ButtonCancel.getText()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(!(Locale.getDefault()==Locale.US)){
            rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
            LoadLocales(rb); 
        }

    }
    
}
