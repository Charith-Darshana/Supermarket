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
import lk.charith.supermarket.model.User;
import lk.charith.supermarket.view.tm.UserTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageUserFormController {
    public Label lblDate;
    public TextField txtUserID;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtUserType;
    public CheckBox checkActive;
    public TableView tblUser;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public TableColumn colUserID;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colActive;
    public TableColumn colType;
    public ImageView btnClose;

    public void initialize() {

        colUserID.setCellValueFactory ( new PropertyValueFactory<>( "userId" ) );
        colUserName.setCellValueFactory ( new PropertyValueFactory<> ( "userName" ) );
        colPassword.setCellValueFactory ( new PropertyValueFactory<> ( "password" ) );
        colActive.setCellValueFactory ( new PropertyValueFactory<> ( "activeState" ) );
        colType.setCellValueFactory ( new PropertyValueFactory<> ( "userType" ) );

        generateDate();
        getAllUsers();

        tblUser.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    setData( ( UserTM ) newValue );
                });
    }

    public void generateDate(){
        lblDate.setText( LocalDate.now().toString());
    }

    private void setData (UserTM newValue ) {
        try {
            txtUserID.setText ( String.valueOf ( newValue.getUserId () ) );
            txtUserName.setText ( newValue.getUserName () );
            txtPassword.setText ( newValue.getPassword () );
            checkActive.setSelected ( newValue.getActiveState () );
            txtUserType.setText ( newValue.getUserType () );
        }catch ( NullPointerException e ){

        }
    }

    private void setUserId(){
        try {
            ResultSet rst = new UserController( ).generateUserId( );
            if (rst.next()){
                int tempId = rst.getInt( 1 );
                tempId = tempId+1;
                txtUserID.setText( String.valueOf( tempId ) );
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace( );
        }
    }

    public void getAllUsers(){
        try {
            List< User > allUsers = new UserController ( ).getAllUsers ( );
            ObservableList< UserTM > userTms = FXCollections.observableArrayList ( );

            for ( User user:allUsers) {
                userTms.add ( new UserTM (
                        user.getUserId (),user.getUserName (),
                        user.getPassword (),user.getActiveState (),
                        user.getUserType ()
                ) );
            }
            tblUser.setItems ( userTms );
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void checkActiveState(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {
        User user = new User (
                Integer.parseInt ( txtUserID.getText ( ) ) ,
                txtUserName.getText ( ) ,
                txtPassword.getText ( ) ,
                checkActive.isSelected ( ) ,
                txtUserType.getText ( )
        );
        try {
            if (new UserController ().saveUser ( user )){
                new Alert ( Alert.AlertType.CONFIRMATION,"User Saved!" ).show ();
                getAllUsers();
            }else {
                new Alert ( Alert.AlertType.ERROR,"Fail!" ).show ();
            }
        } catch ( SQLException throwables ) {
            throwables.printStackTrace ( );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace ( );
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        User user = new User (
                Integer.parseInt ( txtUserID.getText ( ) ) ,
                txtUserName.getText ( ) ,
                txtPassword.getText ( ) ,
                checkActive.isSelected ( ) ,
                txtUserType.getText ( )
        );
        try {
            if (new UserController ().updateUser ( user )){
                new Alert ( Alert.AlertType.CONFIRMATION,"User Update!" ).show ();
                getAllUsers();
            }else {
                new Alert ( Alert.AlertType.ERROR,"Update Fail!" ).show ();
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
