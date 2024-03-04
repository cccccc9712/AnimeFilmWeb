package dal;

import java.sql.*;

public class favouriteDao extends DBContext{
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void addToFavorites(int userId, int filmId, Timestamp addedDate) {
        String sql = "INSERT INTO Favourite (userID, filmID, addedDate) VALUES (?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, filmId);
            ps.setTimestamp(3, addedDate);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean removeFavouriteFilm(int userId, int filmId) {
        boolean isSuccess = false;
        String sql = "DELETE FROM Favourite WHERE userId = ? AND filmId = ?";
        try {
            conn = new DBContext().getConnection(); // Giả định bạn đã có phương thức này để kết nối với cơ sở dữ liệu
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, filmId);
            int affectedRows = ps.executeUpdate();
            isSuccess = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    public static void main(String[] args) {
        favouriteDao fd = new favouriteDao();
        fd.removeFavouriteFilm(3, 18);
    }
}
