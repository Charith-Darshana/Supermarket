package lk.charith.supermarket.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.charith.supermarket.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class CashierLoginFormController {
    public TextField txtUserID;
    public PasswordField txtPassword;
    public Button btnSignin;
    public static String userId;

    public void signOnAction(ActionEvent actionEvent) {
        try {
            User allActiveStateUsers = new UserController ( ).getActiveUsers ( txtUserID.getText ( ) , txtPassword.getText ( ) );
            userId=txtUserID.getText();
            System.out.println(userId );
            if (allActiveStateUsers!=null){

                Scene scene = new Scene( FXMLLoader.load( getClass( ).getResource( "../view/CashierDashBoardForm.fxml" ) ) );
                Stage ps = new Stage( );
                ps.setScene( scene );
                ps.show();

            }else {
                new Alert( Alert.AlertType.WARNING,"Something went Wrong.Please Contact Admin !  " ).show();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        } catch ( IOException e ) {
            e.printStackTrace( );
        }
    }
}
