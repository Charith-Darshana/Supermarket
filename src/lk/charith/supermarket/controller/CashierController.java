package lk.charith.supermarket.controller;

import lk.charith.supermarket.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierController {
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst= CrudUtil.execute( "SELECT id FROM Orders Order By id DESC LIMIT 1" );
        if (rst.next()){
            int tempId = Integer.parseInt( rst.getString( 1 ).split( "O" )[ 1 ] );
            tempId+=1;
            if (tempId < 10){
                return "O00"+tempId;
            }else if (tempId<100){
                return "O0"+tempId;
            }
        }
        return "O001";
    }


}
