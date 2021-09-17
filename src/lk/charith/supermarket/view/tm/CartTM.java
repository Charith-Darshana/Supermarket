package lk.charith.supermarket.view.tm;

import com.jfoenix.controls.JFXButton;

import java.awt.*;

public class CartTM {
    private String propertyID;
    private String productName;
    private double unitPrice;
    private int qty;
    private double discount;
    private double total;
    private JFXButton deleteBtn;

    public CartTM() {
    }

    public CartTM(String propertyID, String productName, double unitPrice, int qty, double discount, double total, JFXButton deleteBtn) {
        this.propertyID = propertyID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.discount = discount;
        this.total = total;
        this.deleteBtn = deleteBtn;
    }

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public JFXButton getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(JFXButton deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "propertyID='" + propertyID + '\'' +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", discount=" + discount +
                ", total=" + total +
                ", deleteBtn=" + deleteBtn +
                '}';
    }
}
