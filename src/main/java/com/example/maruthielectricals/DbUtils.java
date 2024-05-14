package com.example.maruthielectricals;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.HashMap;

public class DbUtils {
    private Connection conn=null;
    private PreparedStatement psInsert=null;
    PreparedStatement psCheckUserExists=null;
    ResultSet resultSet=null;

    public DbUtils() throws SQLException {
    }

    public void saveProduct(String category, String pname, Integer qty, Float price, String des)  {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            psCheckUserExists=conn.prepareStatement("SELECT * FROM products WHERE pname= ? ");
            psCheckUserExists.setString(1, pname);
            resultSet=psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst())
            {
                System.out.println("Product Name Already exists");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this Product Name");
                alert.show();
            }
            else {
                System.out.println(category + " " + pname + " " + qty + " " + price + " " + des);
                psInsert = conn.prepareStatement("INSERT INTO products (category,pname,quantity,price,description) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1, category);
                psInsert.setString(2, pname);
                psInsert.setInt(3, qty);
                psInsert.setFloat(4, price);
                psInsert.setString(5, des);
                psInsert.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null)
            {
                try{
                    resultSet.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null)
            {
                try{
                    psCheckUserExists.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psInsert!=null)
            {
                try{
                    psInsert.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Product saved successfully");
        alert.show();
    }

    public void addCustomer(String name, String email, String num, String address) {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            psCheckUserExists=conn.prepareStatement("SELECT * FROM customer WHERE phone= ? ");
            psCheckUserExists.setString(1, num);
            resultSet=psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst())
            {
                System.out.println("Phone Number Already exists");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this Mobile Number");
                alert.show();
            }
            else {
                System.out.println(name + " " + email + " " + num + " " + address);
                psInsert = conn.prepareStatement("INSERT INTO customer (name,email,phone,address) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, name);
                psInsert.setString(2, email);
                psInsert.setString(3, num);
                psInsert.setString(4, address);
                psInsert.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null)
            {
                try{
                    resultSet.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null)
            {
                try{
                    psCheckUserExists.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psInsert!=null)
            {
                try{
                    psInsert.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public Customer getCustomer(int id){
        ResultSet r=null;
        Connection con=null;
        PreparedStatement ps=null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            ps=con.prepareStatement("SELECT * FROM customer WHERE cid= ?");
            ps.setInt(1,id);
            r=ps.executeQuery();
            if(r.next()){
                return new Customer(r.getInt("cid"),r.getString("name"),r.getString("email"),r.getString("phone"),r.getString("address"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(r!=null)
            {
                try{
                    r.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(ps!=null)
            {
                try{
                    ps.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(con!=null)
            {
                try{
                    con.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
    public ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> list= FXCollections.observableArrayList();
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            psCheckUserExists=conn.prepareStatement("SELECT * FROM customer");
            resultSet=psCheckUserExists.executeQuery();
            while(resultSet.next()){
                list.add(new Customer(resultSet.getInt("cid"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("phone"),resultSet.getString("address")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null)
            {
                try{
                    resultSet.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null)
            {
                try{
                    psCheckUserExists.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public ObservableList<Product> getAllProducts(){
        ObservableList<Product> list= FXCollections.observableArrayList();
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            psCheckUserExists=conn.prepareStatement("SELECT * FROM products");
            resultSet=psCheckUserExists.executeQuery();
            while(resultSet.next()){
                list.add(new Product(resultSet.getInt("pid"),resultSet.getString("category"),resultSet.getString("pname"),resultSet.getInt("quantity"),resultSet.getFloat("price"),resultSet.getString("description")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet!=null)
            {
                try{
                    resultSet.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null)
            {
                try{
                    psCheckUserExists.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
    public ObservableList<Invoice> getAllInvoices() throws SQLException {
        ObservableList<Invoice> list= FXCollections.observableArrayList();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
            psCheckUserExists=conn.prepareStatement("SELECT * FROM invoice");
            resultSet=psCheckUserExists.executeQuery();
            while(resultSet.next()){
                int a=resultSet.getInt("customer_id"),b=resultSet.getInt("invoice_id");
                Date d=resultSet.getDate("invoice_date");
                float total=resultSet.getFloat("total_amount");
                Customer c=getCustomer(a);
                Invoice i=new Invoice(a,d,total,c.getName());
                i.setInvoiceId(b);
                list.add(i);
            }
            if(resultSet!=null)
            {
                try{
                    resultSet.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null)
            {
                try{
                    psCheckUserExists.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        return list;
    }
    public void updateCustomer(Integer id, String name, String email, String number, String address) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("UPDATE customer SET name=?,email=?,phone=?,address=? WHERE cid=?");
        psInsert.setString(1, name);
        psInsert.setString(2, email);
        psInsert.setString(3, number);
        psInsert.setString(4, address);
        psInsert.setInt(5,id);
        psInsert.executeUpdate();
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void updateProduct(Integer id, String category, String name, Integer qty, Float price, String des) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("UPDATE products SET category=?,pname=?,quantity=?,price=?,description=? WHERE pid=?");
        psInsert.setString(1, category);
        psInsert.setString(2, name);
        psInsert.setInt(3, qty);
        psInsert.setFloat(4, price);
        psInsert.setString(5,des);
        psInsert.setInt(6,id);
        psInsert.executeUpdate();
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void deleteCustomer(Integer id) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("DELETE FROM customer WHERE cid=?");
        psInsert.setInt(1, id);
        psInsert.execute();
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void deleteProduct(Integer id) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("DELETE FROM products WHERE pid=?");
        psInsert.setInt(1, id);
        psInsert.execute();
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public int getInvoiceId() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("SELECT MAX(invoice_id) FROM invoice");
        ResultSet a=psInsert.executeQuery();
        int b;
        if(a.next())
            b=a.getInt(1);
        else b=0;
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(a!=null)
        {
            try{
                a.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return b;
    }
    public int nCustomers() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("SELECT COUNT(cid) FROM customer");
        ResultSet a=psInsert.executeQuery();
        int b;
        if(a.next())
            b=a.getInt(1);
        else b=0;
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(a!=null)
        {
            try{
                a.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return b;
    }
    public int nProducts() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("SELECT COUNT(pid) FROM products");
        ResultSet a=psInsert.executeQuery();
        int b;
        if(a.next())
            b=a.getInt(1);
        else b=0;
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(a!=null)
        {
            try{
                a.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return b;
    }
    public void insertInvoiceItem(ObservableList<InvoiceItem> ls) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert = conn.prepareStatement("INSERT INTO invoice_item (invoice_id,product_id,quantity,unit_price,total_price) VALUES (?, ?, ?, ?, ?)");

        for (InvoiceItem i : ls) {
            // Set parameter values for the current InvoiceItem
            psInsert.setInt(1, i.getInvoiceId());
            psInsert.setInt(2, i.getPid());
            psInsert.setInt(3, i.getQuantity());
            psInsert.setFloat(4, i.getUPrice());
            psInsert.setFloat(5, i.getTPrice());

            psInsert.addBatch();
        }
        psInsert.executeBatch();
        psInsert = conn.prepareStatement("UPDATE products SET quantity = quantity - ? WHERE pid = ?");
        for (InvoiceItem i : ls) {
            psInsert.setInt(1, i.getQuantity());
            psInsert.setInt(2, i.getPid());
            psInsert.addBatch();
        }
        psInsert.executeBatch();

            if(psInsert!=null)
            {
                try{
                    psInsert.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                try{
                    conn.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
    }

    public void saveInvoice(int id, java.util.Date date, float price) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert = conn.prepareStatement("INSERT INTO invoice (customer_id, invoice_date,total_amount) VALUES (?, ?, ?)");
        psInsert.setInt(1, id);
        psInsert.setDate(2, new Date(date.getTime()));
        psInsert.setFloat(3, price);
        psInsert.executeUpdate();

        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public HashMap<String, Integer> getInvoiceItems(int n) throws SQLException {
        HashMap<String,Integer> ls=new HashMap<>();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psCheckUserExists=conn.prepareStatement("SELECT p.pname,i.quantity FROM invoice_item AS i INNER JOIN products AS p ON p.pid=i.product_id WHERE invoice_id=?");
        psCheckUserExists.setInt(1,n);
        resultSet=psCheckUserExists.executeQuery();
        while(resultSet.next()){
            ls.put(resultSet.getString("pname"),resultSet.getInt("quantity"));
        }
        if(resultSet!=null)
        {
            try{
                resultSet.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(psCheckUserExists!=null)
        {
            try{
                psCheckUserExists.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return ls;
    }
    public Map<String,Float> getLastWeek() throws SQLException {
        Map<String,Float> ls=new TreeMap<>();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psCheckUserExists=conn.prepareStatement("SELECT invoice_date,sum(total_amount) AS total FROM invoice GROUP BY invoice_date ORDER BY invoice_date DESC LIMIT 7");
        resultSet=psCheckUserExists.executeQuery();
        while(resultSet.next()){
            ls.put(String.valueOf(resultSet.getDate("invoice_date")),resultSet.getFloat("total"));
        }
        if(resultSet!=null)
        {
            try{
                resultSet.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(psCheckUserExists!=null)
        {
            try{
                psCheckUserExists.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return ls;
    }
    public void deleteInvoice(int id) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psInsert=conn.prepareStatement("DELETE FROM invoice WHERE invoice_id=?");
        psInsert.setInt(1, id);
        psInsert.execute();
        if(psInsert!=null)
        {
            try{
                psInsert.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public ObservableList<String> getOutOfStock() throws SQLException {
        ObservableList<String> list= FXCollections.observableArrayList();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psCheckUserExists=conn.prepareStatement("SELECT pname FROM products WHERE quantity=0");
        resultSet=psCheckUserExists.executeQuery();
        while(resultSet.next()){
            list.add(resultSet.getString("pname"));
        }
        if(resultSet!=null)
        {
            try{
                resultSet.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(psCheckUserExists!=null)
        {
            try{
                psCheckUserExists.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return list;
    }
    public ObservableList<String> getDefficientStock() throws SQLException {
        ObservableList<String> list= FXCollections.observableArrayList();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
        psCheckUserExists=conn.prepareStatement("SELECT pname FROM products WHERE quantity BETWEEN 1 and 5");
        resultSet=psCheckUserExists.executeQuery();
        while(resultSet.next()){
            list.add(resultSet.getString("pname"));
        }
        if(resultSet!=null)
        {
            try{
                resultSet.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(psCheckUserExists!=null)
        {
            try{
                psCheckUserExists.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(conn!=null)
        {
            try{
                conn.close();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return list;
    }
//    public ObservableList<Invoice> getInvoices(java.util.Date x, java.util.Date y) throws SQLException {
//        ObservableList<Invoice> list= FXCollections.observableArrayList();
//        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/maruthi", "root", "Daya@1224");
//        psCheckUserExists=conn.prepareStatement("SELECT * FROM invoice WHERE invoice_date BETWEEN ? AND ?");
//        psCheckUserExists.setDate(1,(Date) x);
//        psCheckUserExists.setDate(2,(Date) y);
//        resultSet=psCheckUserExists.executeQuery();
//        while(resultSet.next()){
//            int a=resultSet.getInt("customer_id"),b=resultSet.getInt("invoice_id");
//            Date d=resultSet.getDate("invoice_date");
//            float total=resultSet.getFloat("total_amount");
//            Customer c=getCustomer(a);
//            Invoice i=new Invoice(a,d,total,c.getName());
//            i.setInvoiceId(b);
//            list.add(i);
//        }
//        if(resultSet!=null)
//        {
//            try{
//                resultSet.close();
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        if(psCheckUserExists!=null)
//        {
//            try{
//                psCheckUserExists.close();
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//        if(conn!=null)
//        {
//            try{
//                conn.close();
//            }catch(Exception e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        return list;
//    }

}
