package lk.charith.supermarket.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.charith.supermarket.model.Customer;
import lk.charith.supermarket.model.User;
import lk.charith.supermarket.view.tm.CustomerTM;
import lk.charith.supermarket.view.tm.ProductTM;
import lk.charith.supermarket.view.tm.UserTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageCustomerFormController {
    public TextField txtCustomerID;
    public TextField txtCustomerName;
    public TextField txtCustomerType;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtProvince;
    public TextField txtContact;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView tblCustomer;
    public Label lblDate;
    public TableColumn colCustomerID;
    public TableColumn colName;
    public TableColumn colCustomerType;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colContact;
    public ImageView btnClose;

    public void initialize(){

        colCustomerID.setCellValueFactory ( new PropertyValueFactory<>( "customerID" ) );
        colName.setCellValueFactory ( new PropertyValueFactory<> ( "customerName" ) );
        colCustomerType.setCellValueFactory ( new PropertyValueFactory<> ( "customerType" ) );
        colAddress.setCellValueFactory ( new PropertyValueFactory<> ( "customerAddress" ) );
        colCity.setCellValueFactory ( new PropertyValueFactory<> ( "customerCity" ) );
        colProvince.setCellValueFactory ( new PropertyValueFactory<> ( "customerProvince" ) );
        colContact.setCellValueFactory ( new PropertyValueFactory<> ( "customerContact" ) );

        generateDate();
        getAllCustomers();
        generateId();

        tblCustomer.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( (CustomerTM) newValue );
                });

    }

    private void setData (CustomerTM newValue ) {
        try {
            txtCustomerID.setText ( String.valueOf ( newValue.getCustomerID () ) );
            txtCustomerName.setText ( newValue.getCustomerName () );
            txtCustomerType.setText ( newValue.getCustomerType () );
            txtAddress.setText ( newValue.getCustomerAddress () );
            txtCity.setText ( newValue.getCustomerCity () );
            txtProvince.setText ( newValue.getCustomerProvince () );
            txtContact.setText (String.valueOf(newValue.getCustomerContact ()));
        }catch ( NullPointerException e ){

        }
    }

    public void getAllCustomers(){
        try {
            List<Customer> allCustomers = new CustomerController ( ).getAllCustomer ( );
            ObservableList< CustomerTM > customerTms = FXCollections.observableArrayList ( );

            for ( Customer customer:allCustomers) {
                customerTms.add ( new CustomerTM (
                        customer.getCustomerID(),
                        customer.getCustomerName(),
                        customer.getCustomerType(),
                        customer.getCustomerAddress(),
                        customer.getCustomerCity(),
                        customer.getCustomerProvince(),
                        customer.getCustomerContact()

                ) );
            }
            tblCustomer.setItems ( customerTms );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void generateDate(){
        lblDate.setText( LocalDate.now().toString());
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer (
                txtCustomerID.getText ( ) ,
                txtCustomerName.getText ( ) ,
                txtCustomerType.getText ( ) ,
                txtAddress.getText ( ),
                txtCity.getText ( ),
                txtProvince.getText ( ),
                Integer.parseInt ( txtContact.getText ( ) )
        );
        try {
            if (new CustomerController ().saveCustomer ( customer )){
                new Alert ( Alert.AlertType.CONFIRMATION,"Customer Saved!" ).show ();
                getAllCustomers();
            }else {
                new Alert ( Alert.AlertType.ERROR,"Fail!" ).show ();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void generateId(){
        try {
            ResultSet resultSet = new CustomerController( ).generateCustomerID( );
            if (resultSet.next()){
                String oldId = resultSet.getString( 1 );
                String substring = oldId.substring( 1 , 4 );
                int intId = Integer.parseInt( substring );

                intId = intId + 1;
                if (intId<10){
                    txtCustomerID.setText( "C00"+intId );
                }else if (intId<100){
                    txtCustomerID.setText( "C0"+intId );
                }else if (intId<1000){
                    txtCustomerID.setText( "C"+intId );
                }
            }else {
                txtCustomerID.setText( "C001" );
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }


    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnClose.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/CashierDashBoardForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
}
