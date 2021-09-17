package lk.charith.supermarket.model;

public class OrderDetails {
    private int qty;
    private double unitPrice;
    private String orderID;
    private String propertyID;

    public OrderDetails() {
    }

    public OrderDetails(int qty, double unitPrice, String orderID, String propertyID) {
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.orderID = orderID;
        this.propertyID = propertyID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", orderID='" + orderID + '\'' +
                ", propertyID='" + propertyID + '\'' +
                '}';
    }
}
