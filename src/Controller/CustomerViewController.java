/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.DAO.AddressDAO;
import Model.DAO.AppointmentDOA;
import Model.DAO.CustomerDAO;
import Utils.DBConnection;
import Utils.Login;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
    @FXML private Button ButtonGoBack, 
            ButtonAdd, 
            ButtonCancel,
            ButtonRemove;
    
    @FXML private MenuButton MenuButtonAddress;
    
    @FXML private TableView<Customer> TableViewCustomer;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnCustomerId;
    @FXML private TableColumn<Customer, String> TableCustomerColumnCustomerName;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnAddress;
    @FXML private TableColumn<Customer, Integer> TableCustomerColumnActive;

    private int Active = 1;
    
    private AddressDAO addressDAO = new AddressDAO(DBConnection.getConnection());
    private ObservableList<Address> Addresses = addressDAO.findAll();
    
    private CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());        
    private ObservableList<Customer> Customers = customerDAO.findAll(); 
     
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

        customer.setCustomerName(TextFieldCustomerName.getText());
        customer.setAddressId(Integer.parseInt(MenuButtonAddress.getId()));
        customer.setActive(Active);
        customer.setCreateDate(createDate);
        //customer.setCreatedBy(User.getUserName())
        customer.setCreatedBy(Login.getUserName());
        customer.setLastUpdate(lastUpdate);
        //customer.setLastUpdateBy(User.getUserName());
        customer.setLastUpdateBy(Login.getUserName());

        customerDAO.create(customer);
        
        Customers.add(customer);
        
        loadCustomerTable();
        resetInputs();
        
    }
    
    public void clickButtonRemove(ActionEvent event) throws IOException{
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle("Removal Confirmation");
        alert.setContentText("This will delete all realated appointments. Do you want to delete this Record?");
        
        if(!(Locale.getDefault()==Locale.US)){
            ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());

            alert.setTitle(rb.getString(alert.getTitle())); 
            alert.setContentText(rb.getString(alert.getContentText()));

        }
        
        Optional<ButtonType> result = alert.showAndWait();
        
        // check for confirmation, check which item in the tab is selected
        if(result.get() == ButtonType.OK){
            
            AppointmentDOA appointmentDOA= new AppointmentDOA(DBConnection.getConnection());   
            
            Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
            
            appointmentDOA.deleteWithCustomer(customer.getCustomerId());
            customerDAO.delete(customer.getCustomerId());
            Customers.remove(customer);
            
                 
            
        }
    }
    
    /**
     *
     * @param event
     * @throws IOException
     */
    public void onCustomerNameEdit(TableColumn.CellEditEvent<Customer, String> event) throws IOException{
            
        Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
        TableViewCustomer.getSelectionModel().clearSelection();

        customer.setCustomerName(event.getNewValue());
        customer.setLastUpdate(LocalDateTime.now());
        customer.setLastUpdateBy(Login.getUserName());

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

        Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
        TableViewCustomer.getSelectionModel().clearSelection();
        
        int addressId = event.getNewValue();

        customer.setAddressId(addressId);
        customer.setLastUpdate(LocalDateTime.now());

        customerDAO.update(customer);
        loadCustomerTable();
        resetInputs();

    }
    
    public void onActiveEditCommit(TableColumn.CellEditEvent<Customer, Integer> event) throws IOException{

        
        int columnValue = event.getNewValue();
                
        if(columnValue == 0 || columnValue == 1){

            Customer customer = TableViewCustomer.getSelectionModel().getSelectedItem();
            TableViewCustomer.getSelectionModel().clearSelection();

            int active = event.getNewValue();

            customer.setActive(active);
            customer.setLastUpdate(LocalDateTime.now());


            customerDAO.update(customer);
            loadCustomerTable();
            resetInputs();
            
        }else{
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Active Values");
            alert.setContentText("Please set either 0, for inactive or 1 for active");
            
            if(!(Locale.getDefault()==Locale.US)){
                ResourceBundle rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());

                alert.setTitle(rb.getString(alert.getTitle())); 
                alert.setContentText(rb.getString(alert.getContentText()));

            }
            
            alert.show();
            resetInputs();
            
        }
        


    }
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        resetInputs();
    }
    
    public void resetInputs(){
        TableViewCustomer.getSelectionModel().clearSelection();
        TextFieldCustomerName.clear();

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
        TableCustomerColumnActive.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
               
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
    
    public void LoadLocales(ResourceBundle rb){
        
        rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());

        
        TextFieldCustomerName.setPromptText(rb.getString(TextFieldCustomerName.getPromptText()));

        ButtonGoBack.setText(rb.getString(ButtonGoBack.getText()));
        ButtonAdd.setText(rb.getString(ButtonAdd.getText()));
        ButtonCancel.setText(rb.getString(ButtonCancel.getText()));
        ButtonRemove.setText(rb.getString(ButtonRemove.getText()));
        
        MenuButtonAddress.setText(rb.getString(MenuButtonAddress.getText()));

        TableCustomerColumnCustomerId.setText(rb.getString(TableCustomerColumnCustomerId.getText()));
        TableCustomerColumnCustomerName.setText(rb.getString(TableCustomerColumnCustomerName.getText()));
        TableCustomerColumnAddress.setText(rb.getString(TableCustomerColumnAddress.getText()));
        TableCustomerColumnActive.setText(rb.getString(TableCustomerColumnActive.getText()));
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
        loadCustomerTable();
        loadAddresses();

    }    
}
