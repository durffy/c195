/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author cjd
 */
public class CustomerViewController implements Initializable {
    
    @FXML private TextField TextFieldCustomerID;
    @FXML private TextField TextFieldCustomerName;
    
    @FXML private Button ButtonGoBack;
    @FXML private Button ButtonCancel;
    @FXML private Button ButtonRemove;
    @FXML private Button ButtonAddModify;
    
    @FXML private MenuButton MenuButtonAddress;
    
    @FXML private MenuButton MenuButtonActive;
    @FXML private MenuItem MenuActiveItemActive;
    @FXML private MenuItem MenuActiveItemInactive;
    
    @FXML private TableView TableViewCustomer;
    @FXML private TableColumn TableCustomerColumnCutomerID;
    @FXML private TableColumn TableCustomerColumnCustomerName;
    @FXML private TableColumn TableCustomerColumnAddress;
    @FXML private TableColumn TableCustomerColumnActive;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: load the Address Choices to the MenuButton
 
    }    
    
}
