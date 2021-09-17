package lk.charith.supermarket.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class AdminLoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnLogin;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        try {
            String name = txtUserName.getText ( ).trim ( );
            String password = txtPassword.getText ( ).trim ( );
            if ( name.length ( ) > 0 && password.length ( ) > 0 ) {

                if ( name.equalsIgnoreCase ( "Supermarket" )
                        && password.equalsIgnoreCase ( "1234" ) ) {


                    Scene scene = new Scene ( FXMLLoader.load ( getClass ( ).getResource ( "../view/AdminDashBoardForm.fxml" ) ) );
                    Stage primaryStage = new Stage ( );
                    primaryStage.setScene ( scene );
                    primaryStage.show ( );

                }
                else {
                    new Alert ( Alert.AlertType.WARNING , "Try Again !!!!" ,
                            ButtonType.OK , ButtonType.NO ).show ( );
                }
            }
            else {
                new Alert ( Alert.AlertType.WARNING , "Empty !!!!" ,
                        ButtonType.OK , ButtonType.NO ).show ( );
            }
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }
    }
}
