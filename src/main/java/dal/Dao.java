package dal;

import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao extends DBContext{
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User login(String mail, String pass) {
        String query = "SELECT * FROM [User] WHERE gmail = ? AND userPass = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, mail);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
            }
        }catch (Exception e) {

        }
        return null;
    }

    public static void main(String[] args) {
        Dao dao = new Dao();
        User a = dao.login("thangtmhe180330","Thang123*");
        System.out.println(a);
    }

}
