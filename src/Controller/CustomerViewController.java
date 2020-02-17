/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Address;
import Model.Customer;
import Model.DAO.AddressDAO;
import Model.DAO.CustomerDAO;
import Model.DBConnection;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author cjd
 */
public class CustomerViewController implements Initializable {
    
    @FXML private TextField TextFieldCustomerName;
    
    @FXML private Button ButtonGoBack;
    @FXML private Button ButtonAdd;
    @FXML private Button ButtonCancel;
    
    @FXML private MenuButton MenuButtonAddress;
    
    @FXML private MenuButton MenuButtonActive;
    @FXML private MenuItem MenuActiveItemActive;
    @FXML private MenuItem MenuActiveItemInactive;
    
    @FXML private TableView<Customer> TableViewCustomer;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnCustomerId;
    @FXML private TableColumn<Customer, String> TableCustomerColumnCustomerName;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnAddress;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnActive;

    private int Active = 1;
    
    private AddressDAO addressDAO = new AddressDAO(DBConnection.getConnection());
    private ObservableList<Address> Addresses = addressDAO.findAll();
    
    CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());        
    ObservableList<Customer> Customers = customerDAO.findAll();
     
    public void clickButtonGoBack(ActionEvent event) throws IOException{
        
            Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
            Scene scene = new Scene(root);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            
    }
    
    public void clickButtonAdd(ActionEvent event) throws IOException, SQLException, Exception{
        
        CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());
        Customer customer = new Customer();
        LocalDateTime lastUpdate = LocalDateTime.now();
        LocalDateTime createDate = LocalDateTime.now();

        customer.setCustomerName(TextFieldCustomerName.getText().toString());
        customer.setAddressId(Integer.parseInt(MenuButtonAddress.getId()));
        customer.setActive(Active);
        customer.setCreateDate(createDate);
        customer.setCreatedBy(User.getUserName());
        customer.setLastUpdate(lastUpdate);
        customer.setLastUpdateBy(User.getUserName());

        customerDAO.create(customer);
        
        loadCustomerTable();
        resetInputs();
        
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    public void onCustomerNameEdit(TableColumn.CellEditEvent<Customer, String> event) throws IOException{
            
        CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());
        Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
        TableViewCustomer.getSelectionModel().clearSelection();

        customer.setCustomerName(event.getNewValue());
        customer.setLastUpdate(LocalDateTime.now());


        customerDAO.update(customer);
        loadCustomerTable();
        resetInputs();

    }
    
    public void onAddressEditStart(TableColumn.CellEditEvent<Customer, Integer> event)throws IOException{
        ContextMenu cm = new ContextMenu();
         
        MenuItem mi1 = new MenuItem("Menu 1");
        cm.getItems().add(mi1);
        MenuItem mi2 = new MenuItem("Menu 2");
        cm.getItems().add(mi2);

    }
    
    public void onAddressEditCommit(TableColumn.CellEditEvent<Customer, Integer> event) throws IOException{

        CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());
        Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
        TableViewCustomer.getSelectionModel().clearSelection();
        
        int addressId = event.getNewValue();

        customer.setAddressId(addressId);
        customer.setLastUpdate(LocalDateTime.now());


        customerDAO.update(customer);
        loadCustomerTable();
        resetInputs();

    }
    
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        //todo: deselect if customer record is selected
        resetInputs();
    }
    
    public void resetInputs(){
        TableViewCustomer.getSelectionModel().clearSelection();
        
        TextFieldCustomerName.clear();
        MenuButtonAddress.setText("Address");
    }
    
    //todo: load the customer from the selected row
    public void loadCustomerTable(){
        
        TableCustomerColumnCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        TableCustomerColumnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableCustomerColumnAddress.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        TableCustomerColumnActive.setCellValueFactory(new PropertyValueFactory<>("active"));
        
        
        TableViewCustomer.setEditable(true);
        TableCustomerColumnCustomerName.setCellFactory(TextFieldTableCell.forTableColumn());
        TableCustomerColumnAddress.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        TableViewCustomer.setItems(Customers);
        TableViewCustomer.getColumns().get(0).setVisible(false);
        TableViewCustomer.getColumns().get(0).setVisible(true);
    }


    private void loadAddresses() {
        
        
        for(int i=0; i<  Addresses.size(); i++){
            MenuItem AddressMenuItem = new MenuItem(Addresses.get(i).getAddress() + ", " + Addresses.get(i).getPostalCode());
            AddressMenuItem.setId(Integer.toString(Addresses.get(i).getAddressId()));
            
            AddressMenuItem.setOnAction((event)->{
                MenuButtonAddress.setText(AddressMenuItem.getText());
                MenuButtonAddress.setId(AddressMenuItem.getId());
            });
                        
            MenuButtonAddress.getItems().addAll(AddressMenuItem);
            
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadCustomerTable();
        loadAddresses();

    }    
}
