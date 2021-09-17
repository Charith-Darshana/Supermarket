package lk.charith.supermarket.controller;

import lk.charith.supermarket.model.Customer;
import lk.charith.supermarket.model.User;
import lk.charith.supermarket.utils.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",customer.getCustomerID(),customer.getCustomerName(),customer.getCustomerType(),customer.getCustomerAddress(),customer.getCustomerCity(),customer.getCustomerProvince(),customer.getCustomerContact());
    }

    public List<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next ()){
            customers.add(
                    new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getInt(7)
                    )
            );
        }
        return customers;
    }

    public ResultSet generateCustomerID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1");
        return rst;
    }

    public ArrayList<String> getAllCustomerIDs() throws SQLException, ClassNotFoundException {
        ArrayList<String> ids = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT id FROM Customer");
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return ids;
    }

    public Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute ( "SELECT * FROM Customer WHERE id=?" , id );
        if (rst.next ()){
            return new Customer(
                    rst.getString ( 1 ),
                    rst.getString ( 2 ),
                    rst.getString ( 3 ),
                    rst.getString ( 4 ),
                    rst.getString ( 5 ),
                    rst.getString ( 6 ),
                    rst.getInt(7)
            );
        }
        return null;
    }
}
