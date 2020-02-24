/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
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
    @FXML private ToggleGroup ToggleGroupLanguage;
    @FXML private RadioButton RadioButtonEnglish;
    @FXML private RadioButton RadioButtonSpanish;
    @FXML private Label LabelMainHeader;
    
    
    
    public void clickButtonLogin(ActionEvent event) throws IOException{
        
        //check username and password against the db
        //if true, load the Calendarview
        if(true){
            Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
            Scene scene = new Scene(root);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    
    public void clickButtonCancel(){
        
        //prompt are you sure
        //close program
        
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
