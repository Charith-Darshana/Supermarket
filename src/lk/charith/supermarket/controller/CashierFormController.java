package lk.charith.supermarket.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import lk.charith.supermarket.model.*;
import lk.charith.supermarket.view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CashierFormController {
    public ComboBox<String> cmbCustomerID;
    public ComboBox<String> cmdPropertyID;
    public TextField txtName;
    public TextField txtType;
    public TextField txtContact;
    public TextField txtProductName;
    public TextField txtPrice;
    public TextField txtDiscount;
    public TextField txtQty;
    public Button btnAdd;
    public TableView<CartTM> tblCart;
    public TableColumn colPropertyID;
    public TableColumn colProductName;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    public TableColumn colOption;
    public TextField txtOrderQty;
    public Label txtTotalCost;
    public Button btnPlaceOrder;
    public Label lblOrderID;
    public Label lblDate;
    public Label lblTime;
    public Label lblCashierID;
    public ImageView btnClose;

    public void initialize() throws SQLException, ClassNotFoundException {

        colPropertyID.setCellValueFactory(new PropertyValueFactory<>("propertyID"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));


        loadCustomerIDs();
        loadPropertyIDs();
        setDate();
        setTime();
        setOrderId();
        lblCashierID.setText(  CashierLoginFormController.userId );

        cmbCustomerID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData(newValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } );

        cmdPropertyID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    try {
                        setBatchData(newValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } );

    }

    private void setTime() {
        Timeline timeline = new Timeline( new KeyFrame( Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "hh:mm:ss a");
            lblTime.setText( LocalDateTime.now().format( formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount( Animation.INDEFINITE);
        timeline.play();
    }

    private void setDate() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    private void setBatchData(String propertyId) throws SQLException, ClassNotFoundException {
        Item temp = new ItemController().searchItem(propertyId);
        txtProductName.setText(temp.getDateTime());
        txtPrice.setText(String.valueOf(temp.getPrice()));
        txtQty.setText(String.valueOf(temp.getQty()));
        txtDiscount.setText(String.valueOf(temp.getDiscount()));

    }

    private void setCustomerData(String id) throws SQLException, ClassNotFoundException {
        Customer temp = new CustomerController().searchCustomer(id);
        txtName.setText(temp.getCustomerName());
        txtType.setText(temp.getCustomerType());
        txtContact.setText(String.valueOf(temp.getCustomerContact()));
    }


    private void loadPropertyIDs() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(new ItemController().getAllPropertyIDs());
        cmdPropertyID.setItems(obList);
    }

    private void loadCustomerIDs() throws SQLException, ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList(new CustomerController().getAllCustomerIDs());
        cmbCustomerID.setItems(obList);
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void addOnAction(ActionEvent actionEvent) {

        int qty = Integer.parseInt( txtOrderQty.getText( ) );
        double uniPrice = Double.parseDouble( txtPrice.getText( ) );
        double dic = Double.parseDouble( txtDiscount.getText( ) );

        double dicTot = (dic * qty);
        double subTot = (uniPrice * qty) - (dic * qty);

        JFXButton btn = new JFXButton("DELETE");

        CartTM tm = new CartTM(
                cmdPropertyID.getValue(),
                txtProductName.getText(),
                uniPrice,
                qty,
                dicTot,
                subTot,
                btn
        );

        btn.setOnAction((e)->{
            obList.remove(tm);
            tblCart.refresh();
            calculateTotal();
        });

        int rowNumber = isExists(tm);

        if (rowNumber==-1){

            if(Integer.parseInt(txtQty.getText())>=qty){
                obList.add(tm);
                tblCart.setItems(obList);
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            }

        }else {

            if (Integer.parseInt(txtQty.getText())>=(obList.get(rowNumber).getQty()+qty)){
                obList.get(rowNumber).setQty(obList.get(rowNumber).getQty()+qty);
                obList.get(rowNumber).setTotal(obList.get(rowNumber).getTotal()+subTot);
                tblCart.refresh();
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            }

        }
        calculateTotal();
    }

    private int isExists(CartTM tm) {
        for (int i=0; i<obList.size(); i++){
            if (obList.get(i).getPropertyID().equals(tm.getPropertyID())){
                return i;
            }
        }
      return -1;
    }

    double totalCost = 0.0;
    private void calculateTotal(){
        totalCost=0;
        for (CartTM temp:obList
             ) {
            totalCost+= temp.getTotal();
        }
        txtTotalCost.setText(String.valueOf(totalCost));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {

        ArrayList<OrderDetails> details = new ArrayList<>();
        for (CartTM tm:obList) {
            details.add(
                    new OrderDetails(
                            tm.getQty(),
                            tm.getUnitPrice(),
                            lblOrderID.getText(),
                            tm.getPropertyID()
                    )
            );
        }

        Order order = new Order(
                lblOrderID.getText(),
                lblDate.getText(),
                Double.parseDouble(txtTotalCost.getText()),
                cmbCustomerID.getValue(),
                Integer.parseInt(lblCashierID.getText()),
                details
        );


        try {
            if(new OrderController().placeOrder(order)){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved Success !").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Saved Failed ! Try Again !");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setOrderId(){
        try {
            String s = new CashierController( ).generateOrderId( );
            lblOrderID.setText( s );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }

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
