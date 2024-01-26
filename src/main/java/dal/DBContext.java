package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    protected Connection conn;

    public DBContext() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=MovieDatabase;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error: Unable to close the database connection.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();

            if (conn != null) {
                System.out.println("Connected Successfully!");
            } else {
                System.out.println("Connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}