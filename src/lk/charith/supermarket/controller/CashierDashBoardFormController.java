package lk.charith.supermarket.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierDashBoardFormController {
    public ImageView btnCustomer;
    public ImageView btnOrders;
    public ImageView btnClose;

    public void customerOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnCustomer.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/ManageCustomerForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void orderOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnOrders.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/CashierForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnClose.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/DashBoardForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
}
