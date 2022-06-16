package ro.ubb.subiect927JSP.DB;

import ro.ubb.subiect927JSP.Domain.Document;

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
            String url = "jdbc:mysql://localhost:3306/subiect927";
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

    public boolean addKeyword(String key, String value) {
        boolean res = false;
        try{
            String sql = "INSERT INTO keyword(keyword, value) VALUES (?,?)";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, value);
            stmt.executeUpdate();
            res = true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<Document> displayDocuments(String text) {
        List<Document> documents = new ArrayList<>();
        try {
            String query = "SELECT * FROM document where title LIKE ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, "%" + text + "%");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Document document = new Document(rs.getInt("id"), rs.getString("title"), rs.getString("listOfTemplates"));
                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }
}
