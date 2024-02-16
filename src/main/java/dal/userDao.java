package dal;

import dtos.userDto;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDao extends DBContext{
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
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
            }
        }catch (Exception e) {

        }
        return null;
    }

    public boolean registerUser(userDto user) {
        String sql = "INSERT INTO [User] (userName, userPass, gmail, isAdmin) VALUES (?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserPass()); // Giả sử bạn đã hash mật khẩu
            ps.setString(3, user.getUserGmail());
            ps.setBoolean(4, user.getAdmin());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE gmail = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUserNameExists(String userName) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE userName = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        userDao dao = new userDao();
        System.out.println(dao.checkEmailExists("thangtmhe180330@gmail.com"));
    }

}
