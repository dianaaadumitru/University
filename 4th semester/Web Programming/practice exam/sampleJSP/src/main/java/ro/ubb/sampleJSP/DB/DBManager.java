package ro.ubb.sampleJSP.DB;

import ro.ubb.sampleJSP.Domain.Project;
import ro.ubb.sampleJSP.Domain.UserToAdd;

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
            String url = "jdbc:mysql://localhost:3306/sample";
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

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();

        try {
            String query = "SELECT * FROM project ";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Project project = new Project(rs.getInt("id"), rs.getInt("projectManagerId"), rs.getString("name"),
                        rs.getString("description"), rs.getString("members"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public List<Project> getUsersProjects(String user) {
        List<Project> projects = new ArrayList<>();
        try {
            String query = "SELECT * FROM project ";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String members = rs.getString("members");
                if (members.contains(user)) {
                    Project project = new Project(rs.getInt("id"), rs.getInt("projectManagerId"), rs.getString("name"),
                            rs.getString("description"), members);
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public boolean userExists(String name) {
        boolean res = false;
        try{
            String sql = "SELECT id FROM softwaredeveloper where name=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, name);
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

    public boolean projectExists(String name) {
        boolean res = false;
        try{
            String sql = "SELECT id FROM project where name=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, name);
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

    public void addProject(String name) {
        try {
            String query = "INSERT INTO project(projectManagerId, name, description, members) VALUES (0,?,'','')";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProjectsMembers(String name) {
        String members = "";
        try {
            String sql = "SELECT members FROM project where name=?";

            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                members = rs.getString("members");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    //-1 - error, 1 - success, 0 - user doesn't exist
    public int addUserToProjects(List<UserToAdd> userToProjects) {
        int res = -1;
        for(UserToAdd userToAdd: userToProjects) {
            if (!this.userExists(userToAdd.getUser())) {
                return 0;
            }
            if (!this.projectExists(userToAdd.getProject())) {
                this.addProject(userToAdd.getProject());
            }

            String members = this.getProjectsMembers(userToAdd.getProject());
            if (!members.contains(userToAdd.getUser())) {
                if (members.length() != 0){
                    members += ",";
                }
                members += userToAdd.getUser();
                try {
                    String query = "UPDATE project SET members=? WHERE name=?";
                    PreparedStatement stmt = getConnection().prepareStatement(query);
                    stmt.setString(1, members);
                    stmt.setString(2, userToAdd.getProject());
                    stmt.executeUpdate();
                    res = 1;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return res;
    }
}
