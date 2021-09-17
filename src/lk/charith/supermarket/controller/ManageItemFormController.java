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
import lk.charith.supermarket.model.Item;
import lk.charith.supermarket.model.Product;
import lk.charith.supermarket.view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ManageItemFormController {
    public Label lblDate;
    public TextField txtPropertyID;
    public TextField txtBatch;
    public TextField txtPrice;
    public TextField txtQty;
    public TextField txtDiscount;
    public ComboBox cmbProductID;
    public CheckBox setDiscount;
    public CheckBox setActive;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public TableView tblManageItem;
    public TableColumn colPropertyID;
    public TableColumn colBatch;
    public TableColumn colPrice;
    public TableColumn colDiscount;
    public TableColumn colQty;
    public TableColumn colProductID;
    public TableColumn colActive;
    public ImageView btnClose;

    public void initialize(){
        generateDate();
        getAllItems();
        getAllProductId();
        txtDiscount.setDisable ( true );
        generateId();

        colPropertyID.setCellValueFactory ( new PropertyValueFactory<>( "propertyId" ) );
        colBatch.setCellValueFactory ( new PropertyValueFactory<> ( "batch" ) );
        colPrice.setCellValueFactory ( new PropertyValueFactory<> ( "price" ) );
        colDiscount.setCellValueFactory ( new PropertyValueFactory<> ( "discount" ) );
        colActive.setCellValueFactory ( new PropertyValueFactory<> ( "activeState" ) );
        colQty.setCellValueFactory ( new PropertyValueFactory<> ( "qty" ) );
        colProductID.setCellValueFactory ( new PropertyValueFactory<> ( "productId" ) );

        tblManageItem.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( ( ItemTM ) newValue );
                });

    }

    public void generateDate(){
        lblDate.setText( LocalDate.now().toString());
    }

    private void setData ( ItemTM tm ) {
        try {
            txtPropertyID.setText( tm.getPropertyId() );
            txtBatch.setText( tm.getBatch() );
            txtPrice.setText( String.valueOf( tm.getPrice() ) );
            setDiscount.setSelected( tm.isDiscountState() );
            txtDiscount.setText( String.valueOf( tm.getDiscount() ) );
            setActive.setSelected( tm.isActiveState() );
            txtQty.setText( String.valueOf( tm.getQty() ) );
            cmbProductID.setValue( tm.getProductId() );
        }catch ( NullPointerException e ){

        }
    }

    public void getAllProductId(){
        try {
            List<Product> all = new ProductController ( ).getAllActiveState ( );
            ObservableList< String > productObservableList = FXCollections.observableArrayList ( );

            for ( Product product:all) {
                productObservableList.add ( product.getProductId () );
            }
            cmbProductID.setItems ( productObservableList );

        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void setDiscountOnAction(ActionEvent actionEvent) {
        if (setDiscount.isSelected ()){
            txtDiscount.setDisable ( false );
            txtDiscount.setText ( "0" );
        }else {
            txtDiscount.setDisable ( true );
        }
    }

    public void generateId(){
        try {
            ResultSet resultSet = new ItemController( ).autoGenerateID( );
            if (resultSet.next()){
                String oldId = resultSet.getString( 1 );
                String substring = oldId.substring( 5 , 7 );
                int intId = Integer.parseInt( substring );

                intId = intId + 1;
                if (intId<10){
                    txtPropertyID.setText( "P00-00"+intId );
                }else if (intId<100){
                    txtPropertyID.setText( "P00-0"+intId );
                }else if (intId<1000){
                    txtPropertyID.setText( "P00-"+intId );
                }
            }else {
                txtPropertyID.setText( "P00-001" );
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }

    }

    public void setActiveStateOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
        SimpleDateFormat formatter=new SimpleDateFormat ( "dd/MM/yyyy HH:mm" );
        Date date=new Date ( );

        Item item = new Item (
                txtPropertyID.getText ( ) ,
                txtBatch.getText ( ) ,
                BigDecimal.valueOf ( Long.parseLong ( txtPrice.getText ( ) ) ),
                setDiscount.isSelected (),
                BigDecimal.valueOf ( Long.parseLong ( txtDiscount.getText ( ) )) ,
                setActive.isSelected ( ) ,
                Integer.parseInt ( txtQty.getText ( ) ),
                formatter.format ( date ),
                String.valueOf ( cmbProductID.getValue ( ) )
        );

        try {
            if (new ItemController ().saveBatch ( item )){
                new Alert ( Alert.AlertType.CONFIRMATION, "Saved Success !").show();
                getAllItems ();
                generateId();
            }else {
                new Alert ( Alert.AlertType.CONFIRMATION, "Saved Fail !").show();
            }

        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void getAllItems(){
        try {
            List< Item > allItems = new ItemController ( ).getAllItems ( );

            ObservableList< ItemTM > tms = FXCollections.observableArrayList ( );

            for ( Item item:allItems ) {

                tms.add ( new ItemTM ( item.getPropertyId (), item.getBatch (), item.getPrice (),
                        item.isDiscountState (), item.getDiscount (), item.isActiveState (),
                        item.getQty (),item.getDateTime (), ( String ) item.getProductId ()));
            }
            tblManageItem.setItems ( tms );

        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        SimpleDateFormat formatter=new SimpleDateFormat ( "dd/MM/yyyy HH:mm" );
        Date date=new Date ( );

        Item item = new Item (
                txtPropertyID.getText ( ) ,
                txtBatch.getText ( ) ,
                BigDecimal.valueOf ( Long.parseLong ( txtPrice.getText ( ) ) ),
                setDiscount.isSelected (),
                BigDecimal.valueOf ( Long.parseLong ( txtDiscount.getText ( ) )) ,
                setActive.isSelected ( ) ,
                Integer.parseInt ( txtQty.getText ( ) ),
                formatter.format ( date ),
                String.valueOf ( cmbProductID.getValue ( ) )
        );
        try {
            if (new ItemController ().updateItem ( item )){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated Success !").show();
            }else {
                new Alert(Alert.AlertType.WARNING, "Try Again !").show();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
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
