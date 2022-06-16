package ro.ubb.posts.DB;

import ro.ubb.posts.Domain.Post;

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
            String url = "jdbc:mysql://localhost:3306/posts";
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

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        try {
            String query = "SELECT * FROM posts";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Post post = new Post(rs.getInt("id"), rs.getString("user"),
                        rs.getInt("topicId"), rs.getString("text"), rs.getInt("date"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Boolean updatePost(int postId, String user, int topicId, String text, int date) {
        boolean res = false;
        try {
            String query = "UPDATE posts SET user=?,topicId=?,text=?,date=? WHERE id=?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, user);
            stmt.setInt(2,topicId);
            stmt.setString(3, text);
            stmt.setInt(4, date);
            stmt.setInt(5, postId);
            System.out.println(user);
            int rs = stmt.executeUpdate();
            res = true;


            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public int getTopicId(String topicName) {
        int id = -1;
        try{
            String sql = "SELECT id FROM topics WHERE topicName=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, topicName);
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

    public boolean addTopic(String topicName) {
        boolean res = false;
        try{
            String sql = "INSERT INTO topics( topicName) VALUES (?)";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, topicName);
            stmt.executeUpdate();
            res = true;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean addPost(String user, String topicName, String text, int date) {
        boolean res = false;
        int topicId = this.getTopicId(topicName);
        if (topicId == -1) {
            addTopic(topicName);
            topicId = this.getTopicId(topicName);
        }
        try {
            String query = "INSERT INTO posts( user, topicId, text, date) VALUES (?,?,?,?)";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, user);
            stmt.setInt(2,topicId);
            stmt.setString(3, text);
            stmt.setInt(4, date);
            stmt.executeUpdate();
            res = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
