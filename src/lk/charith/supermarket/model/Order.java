package lk.charith.supermarket.model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String dateTime;
    private Double total;
    private String customerID;
    private int userID;
    private ArrayList<OrderDetails> details;

    public Order() {
    }

    public Order(String orderID, String dateTime, Double total, String customerID, int userID, ArrayList<OrderDetails> details) {
        this.orderID = orderID;
        this.dateTime = dateTime;
        this.total = total;
        this.customerID = customerID;
        this.userID = userID;
        this.details = details;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public ArrayList<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", total=" + total +
                ", customerID='" + customerID + '\'' +
                ", userID=" + userID +
                ", details=" + details +
                '}';
    }
}
