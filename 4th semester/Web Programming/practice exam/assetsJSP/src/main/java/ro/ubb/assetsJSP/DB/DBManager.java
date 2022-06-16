package ro.ubb.assetsJSP.DB;

import ro.ubb.assetsJSP.Domain.Asset;

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
            String url = "jdbc:mysql://localhost:3306/assets";
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

    public boolean login(String user, String password){
        boolean res = false;
        try{
            String sql = "SELECT * FROM users WHERE username=? AND password=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                res = true;
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int getUserId(String user){
        int id = -1;
        try{
            String sql = "SELECT id FROM users WHERE username=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, user);
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

    public List<Asset> getUserAssets(String username) {
        ArrayList<Asset> assets = new ArrayList<Asset>();
        try {
            int userId = this.getUserId(username);

            String query = "SELECT * FROM `assets` where userid=?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Asset asset = new Asset(rs.getInt("id"), rs.getInt("userId"),
                        rs.getString("name"), rs.getString("description"),
                        rs.getInt("value"));
                assets.add(asset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }
}
