package dal;

import dtos.userDto;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDao extends DBContext {
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User login(String mail, String pass) {
        User user = null;
        String query = "SELECT * FROM [User] WHERE gmail = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, mail);
            rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("userPass");

                if (BCrypt.checkpw(pass, storedHash)) {
                    user = new User(rs.getInt("userID"),
                            rs.getString("userName"),
                            storedHash,
                            rs.getString("gmail"),
                            rs.getBoolean("isAdmin"));
                }
            }
        } catch (Exception e) {

        }
        return user;
    }

    public boolean registerUser(userDto user) {
        String hashedPassword = BCrypt.hashpw(user.getUserPass(), BCrypt.gensalt());
        String sql = "INSERT INTO [User] (userName, userPass, gmail, isAdmin) VALUES (?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, hashedPassword);
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

    public boolean saveUserRememberToken(int userId, String rememberToken) {
        String sql = "UPDATE [User] SET remember_token = ? WHERE userID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, rememberToken);
            ps.setInt(2, userId);
            int result = ps.executeUpdate();
            return result > 0; // Nếu có ít nhất một bản ghi được cập nhật, trả về true
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        } finally {
            // Đóng các resource để tránh rò rỉ bộ nhớ
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User getUserByRememberMeToken(String rememberMeToken) {
        User user = null;
        String sql = "SELECT userID, userName, userPass, gmail, isAdmin FROM [User] WHERE remember_token = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, rememberMeToken);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userID");
                    String userName = rs.getString("userName");
                    String userPass = rs.getString("userPass");
                    String gmail = rs.getString("gmail");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    user = new User(userId, userName, userPass, gmail, isAdmin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean deleteRememberToken(int userId) {
        String sql = "UPDATE [User] SET remember_token = NULL WHERE userID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu có ít nhất một hàng được cập nhật.
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra.
        }
    }

    public boolean changePassword(String email, String newPassword) {
        String sql = "UPDATE [User] SET userPass = ? WHERE gmail = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword); // Thay đổi mật khẩu mới
            ps.setString(2, email); // Định dạng email của người dùng cần thay đổi mật khẩu

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        userDao dao = new userDao();
        User user = dao.login("user4@gmail.com", "Pass4");
        System.out.println(user);
    }

}
