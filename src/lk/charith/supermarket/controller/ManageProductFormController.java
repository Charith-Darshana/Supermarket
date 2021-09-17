package lk.charith.supermarket.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.charith.supermarket.model.Product;
import lk.charith.supermarket.view.tm.ProductTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageProductFormController {
    public TextField txtProductID;
    public TextField txtProductName;
    public TextField txtDescription;
    public TextField txtSpecification;
    public TextField txtDisplay;
    public TextField txtAvailableBrand;
    public CheckBox checkAvailability;
    public CheckBox checkActive;
    public TableView tblProduct;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public Label lblDate;
    public TableColumn colProductID;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colSpecifi;
    public TableColumn colDisplay;
    public TableColumn colBrand;
    public TableColumn colAvailability;
    public TableColumn colActive;
    public ImageView btnClose;

    public void initialize() {

        colProductID.setCellValueFactory ( new PropertyValueFactory<>( "productId" ) );
        colName.setCellValueFactory ( new PropertyValueFactory<> ( "name" ) );
        colDescription.setCellValueFactory ( new PropertyValueFactory<> ( "description" ) );
        colSpecifi.setCellValueFactory ( new PropertyValueFactory<> ( "spec" ) );
        colDisplay.setCellValueFactory ( new PropertyValueFactory<> ( "displayName" ) );
        colAvailability.setCellValueFactory ( new PropertyValueFactory<> ( "availability" ) );
        colActive.setCellValueFactory ( new PropertyValueFactory<> ( "activeState" ) );
        colBrand.setCellValueFactory ( new PropertyValueFactory<> ( "brands" ) );

        generateDate();
        generateId();
        getAllProduct();

        tblProduct.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( ( ProductTM ) newValue );
                });

    }

    public void generateDate(){
        lblDate.setText( LocalDate.now().toString());
    }

    public void setData (ProductTM tm){
        try {
            txtProductID.setText(tm.getProductId ());
            txtProductName.setText(tm.getName());
            txtDescription.setText(tm.getDescription ());
            txtSpecification.setText(tm.getSpec ());
            txtAvailableBrand.setText(tm.getDisplayName ());
            checkAvailability.setSelected (tm.isAvailability ());
            checkActive.setSelected (tm.isActiveState ());
            txtAvailableBrand.setText ( tm.getBrands () );
        } catch (NullPointerException ex) {

        }
    }

    public void generateId(){
        try {
            ResultSet resultSet = new ProductController( ).autoGenerateID( );
            if (resultSet.next()){
                String oldId = resultSet.getString( 1 );
                String substring = oldId.substring( 1 , 4 );
                int intId = Integer.parseInt( substring );

                intId = intId + 1;
                if (intId<10){
                    txtProductID.setText( "P00"+intId );
                }else if (intId<100){
                    txtProductID.setText( "P0"+intId );
                }else if (intId<1000){
                    txtProductID.setText( "P"+intId );
                }
            }else {
                txtProductID.setText( "P001" );
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }

    public void getAllProduct(){
        try {
            List<Product> all = new ProductController ( ).getAll ( );

            ObservableList< ProductTM > productTMS = FXCollections.observableArrayList ( );


            for ( Product product:all) {
                productTMS.add ( new ProductTM (
                        product.getProductId (),product.getName (),
                        product.getDescription (),product.getSpec (),
                        product.getDisplayName (),product.isAvailability (),
                        product.isActiveState (),product.getBrands ()
                ) );

            }
            tblProduct.setItems ( productTMS );

        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void checkAvailabilityOnAction(ActionEvent actionEvent) {
    }

    public void checkActiveOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
        Product product = new Product (
                txtProductID.getText ( ) ,
                txtProductName.getText ( ) ,
                txtDescription.getText ( ) ,
                txtSpecification.getText ( ) ,
                txtDisplay.getText ( ) ,
                checkAvailability.isSelected ( ) ,
                checkActive.isSelected ( ) ,
                txtAvailableBrand.getText ( )
        );
        try {
            if (new ProductController ().saveProduct ( product )){
                new Alert ( Alert.AlertType.CONFIRMATION,"Saved @!" ).show ();
                getAllProduct ();
                generateId();
            }else {
                new Alert ( Alert.AlertType.ERROR,"Failed @!" ).show ();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
        Product product = new Product (
                txtProductID.getText ( ) ,
                txtProductName.getText ( ) ,
                txtDescription.getText ( ) ,
                txtSpecification.getText ( ) ,
                txtDisplay.getText ( ) ,
                checkAvailability.isSelected ( ) ,
                checkActive.isSelected ( ) ,
                txtAvailableBrand.getText ( )
        );
        try {
            if (new ProductController ().updateProduct ( product )){
                new Alert ( Alert.AlertType.CONFIRMATION,"Update Success !" ).show ();
                getAllProduct ();
            }else {
                new Alert ( Alert.AlertType.WARNING,"Update Fail !" ).show ();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnClose.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/AdminDashBoardForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
}
