package lk.charith.supermarket.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {
    public ImageView btnAdmin;
    public ImageView btnCashier;

    public void adminOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnAdmin.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/AdminLoginForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }

    public void cashierOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnCashier.getScene ().getWindow ();
        stage.close ();

        try {
            Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/CashierLoginForm.fxml" ) ) );
            Stage stage1 = new Stage ( );
            stage1.setScene ( scene );
            stage1.show ();
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
}
