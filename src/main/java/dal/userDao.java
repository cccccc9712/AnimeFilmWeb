package dal;

import dtos.userDto;
import entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    user = new User();
                    user.setUserId(rs.getInt("userID"));
                    user.setUserName(rs.getString("userName"));
                    user.setUserPass(storedHash);
                    user.setUserGmail(rs.getString("gmail"));
                    user.setAdmin(rs.getBoolean("isAdmin"));
                }
            }
        } catch (Exception e) {

        }
        return user;
    }

    public boolean registerUser(User user) {
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
            rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userID");
                String userName = rs.getString("userName");
                String userPass = rs.getString("userPass");
                String gmail = rs.getString("gmail");
                boolean isAdmin = rs.getBoolean("isAdmin");
                user = new User(userName, userPass, gmail, isAdmin);
                user.setUserId(userId);
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

    public boolean checkUserPremiumStatus(int userID) {
        boolean isPremium = false;
        Connection localConn = null;
        PreparedStatement localPs = null;
        ResultSet localRs = null;
        try {
            localConn = new DBContext().getConnection();
            String sql = "SELECT * FROM PremiumStatus WHERE userID = ? AND outOfDate > GETDATE() AND [status] = 'active'";
            localPs = localConn.prepareStatement(sql);
            localPs.setInt(1, userID);
            localRs = localPs.executeQuery();
            if (localRs.next()) {
                isPremium = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (localRs != null) localRs.close();
                if (localPs != null) localPs.close();
                if (localConn != null) localConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isPremium;
    }


    public boolean registerPremium(int userId, String premiumName, Date registeredDate, Date outOfDate) {
        boolean registeredSuccessfully = false;

        try {
            conn = new DBContext().getConnection();
            String sql = "INSERT INTO PremiumStatus (userID, premiumName, registeredDate, outOfDate) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, premiumName);
            ps.setDate(3, registeredDate);
            ps.setDate(4, outOfDate);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                registeredSuccessfully = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return registeredSuccessfully;
    }

    public boolean checkUserIsAdmin(int userID) {
        boolean isAdmin = false;
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT * FROM [User] WHERE userID = ? AND isAdmin = 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                isAdmin = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAdmin;
    }

    public List<userDto> getAllUsersWithPremiumStatus() {
        String query = "select * from [User]";
        List<userDto> listUser = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                userDto user = new userDto();
                user.setUserId(rs.getInt("userID"));
                user.setUserName(rs.getString("userName"));
                user.setUserGmail(rs.getString("gmail"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                user.setPremium(checkUserPremiumStatus(rs.getInt("userID")));
                listUser.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listUser;
    }


    public static void main(String[] args) {
        userDao dao = new userDao();
        List<userDto> listUser = dao.getAllUsersWithPremiumStatus();
        for (userDto user : listUser) {
            System.out.println(user.toString());
        }
    }

}