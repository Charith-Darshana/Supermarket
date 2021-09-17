package lk.charith.supermarket.controller;

import lk.charith.supermarket.db.DBConnection;
import lk.charith.supermarket.model.Order;
import lk.charith.supermarket.model.OrderDetails;
import lk.charith.supermarket.utils.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {

    Connection connection = null;

    public boolean placeOrder(Order order) throws SQLException {

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm1 = connection.prepareStatement("INSERT INTO Orders VALUES(?,?,?,?,?)");
            stm1.setObject(1,order.getOrderID());
            stm1.setObject(2,order.getDateTime());
            stm1.setObject(3,order.getTotal());
            stm1.setObject(4,order.getCustomerID());
            stm1.setObject(5,order.getUserID());
            boolean isSaveOrder = stm1.executeUpdate()>0;
            if (isSaveOrder){
                if (saveOrderDetails(order.getDetails())){
                    connection.commit();
                }else {
                    connection.rollback();
                    return  false;
                }
            }else {
                connection.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;

    }

    private boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for ( OrderDetails detail : details) {
            PreparedStatement stm2 = connection.prepareStatement("INSERT INTO Orderdetail VALUES (?,?,?,?)");
            stm2.setObject(1,detail.getQty());
            stm2.setObject(2,detail.getUnitPrice());
            stm2.setObject(3,detail.getOrderID());
            stm2.setObject(4,detail.getPropertyID());
            boolean isSavedOrderDetails = stm2.executeUpdate()>0;
            if (isSavedOrderDetails){
                if (updateQty(detail.getPropertyID(),detail.getQty())){

                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String propertyID,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm3 = connection.prepareStatement("UPDATE Batch SET quantity=(quantity-?) WHERE propertyId=?");
        stm3.setObject(1,qty);
        stm3.setObject(2,propertyID);
        return stm3.executeUpdate()>0;
    }

}
