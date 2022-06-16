package ro.ubb.ordersJSP.DB;

import ro.ubb.ordersJSP.Domain.Order;
import ro.ubb.ordersJSP.Domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Statement stmt;
    private static Connection connection;

    public DBManager() {
        connect();
    }

    public static void connect() {
        if(connection == null) {
            String url = "jdbc:mysql://localhost:3306/orders";
            try{
                Class.forName( "com.mysql.jdbc.Driver" );
                connection = DriverManager.getConnection( url, "root", "diana" );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        if(connection == null)
            connect();
        return connection;
    }

    public boolean addProduct(String name, String description) {
        boolean res = false;
        try{
            String sql = "INSERT INTO products(name, description) VALUES (?,?)";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
            res = true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Product> getProductsStartingWith(String text) {
        List<Product> products = new ArrayList<Product>();
        try {
            String query = "SELECT * FROM products where name LIKE ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, text + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getProductId(String name) {
        int id = -1;
        try{
            String sql = "SELECT id FROM products WHERE name=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean addOrder(List<Order> orders) {
        boolean res = false;
        try {
            for (Order order: orders) {
                String user = order.getUser();
                int productId = this.getProductId(order.getProduct());
                int quantity = order.getQuantity();

                String query = "INSERT INTO orders(user, productId, quantity) VALUES (?,?,?)";
                PreparedStatement stmt = getConnection().prepareStatement(query);
                stmt.setString(1, user);
                stmt.setInt(2, productId);
                stmt.setInt(3, quantity);
                stmt.executeUpdate();
            }
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
