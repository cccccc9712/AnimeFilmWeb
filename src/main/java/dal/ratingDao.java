package dal;

import entity.Rating;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ratingDao extends DBContext {
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Rating checkRatingExisted(int userId, int filmId) {
        String sql = "SELECT * FROM Ratings WHERE userID = ? AND filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, filmId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Rating(
                            rs.getInt("ratingID"),
                            rs.getInt("filmID"),
                            rs.getInt("userID"),
                            rs.getFloat("ratingValue")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRating(Rating rating) {
        String sql = "UPDATE Ratings SET ratingValue = ?, ratingDate = GETDATE() WHERE ratingID = ?";
        try  {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setFloat(1, rating.getRatingValue());
            ps.setInt(2, rating.getRatingId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rate(Rating rating) {
        String sql = "INSERT INTO Ratings (filmID, userID, ratingValue, ratingDate) VALUES (?, ?, ?, ?)";
        try {
            conn = this.getConnection();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, rating.getFilmId());
            ps.setInt(2, rating.getUserId());
            ps.setFloat(3, rating.getRatingValue());
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rating.setRatingId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Float getUserRatingForFilm(int userId, int filmId) {
        String sql = "SELECT ratingValue FROM Ratings WHERE userId = ? AND filmId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, filmId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getFloat("ratingValue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }
        return null;
    }

    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // Log the exception or handle it
                e.printStackTrace();
            }
        }
    }

}
