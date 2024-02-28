package dal;

import dtos.commentDto;
import entity.Comment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class commentDao extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public boolean insertComment(int filmID, int userID, String commentText, Integer parentCommentID) {
        String sql = "INSERT INTO Comments (filmID, userID, commentText, commentDate, parentCommentID) VALUES (?, ?, ?, GETDATE(), ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            ps.setInt(2, userID);
            ps.setString(3, commentText); // Không cần N? vì PreparedStatement xử lý
            if (parentCommentID != null) {
                ps.setInt(4, parentCommentID);
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<commentDto> getCommentsByFilmId(int filmID) {
        List<commentDto> comments = new ArrayList<>();
        String sql = "SELECT c.commentID, c.filmID, c.userID, c.commentText, c.commentDate, c.parentCommentID, u.userName " +
                "FROM Comments c " +
                "JOIN [User] u ON c.userID = u.userID " +
                "WHERE c.filmID = ? AND c.parentCommentID IS NULL " + // Chỉ lấy comments chính
                "ORDER BY c.commentDate DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commentDto comment = new commentDto(
                        rs.getInt("commentID"),
                        rs.getInt("filmID"),
                        rs.getInt("userID"),
                        rs.getString("commentText"),
                        rs.getTimestamp("commentDate"),
                        null, // parentCommentID luôn null vì đây là comment chính
                        rs.getString("userName")
                );
                comment.setEdited(rs.getBoolean("isEdited"));
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return comments;
    }


    public List<commentDto> getRepliesByCommentId(int commentID) {
        List<commentDto> replies = new ArrayList<>();
        String sql = "SELECT c.commentID, c.filmID, c.userID, c.commentText, c.commentDate, c.parentCommentID, c.isEdited ,u.userName " +
                "FROM Comments c " +
                "JOIN [User] u ON c.userID = u.userID " +
                "WHERE c.parentCommentID = ? " + // Lấy replies dựa trên parentCommentID
                "ORDER BY c.commentDate ASC"; // Sắp xếp replies theo thời gian
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, commentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                commentDto reply = new commentDto(
                        rs.getInt("commentID"),
                        rs.getInt("filmID"),
                        rs.getInt("userID"),
                        rs.getString("commentText"),
                        rs.getTimestamp("commentDate"),
                        rs.getInt("parentCommentID"), // Đây là reply, nên có parentCommentID
                        rs.getString("userName")
                );
                reply.setEdited(rs.getBoolean("isEdited"));
                replies.add(reply);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return replies;
    }

    public List<commentDto> getCommentsPerPage(int filmID, int currentPage, int commentsPerPage) {
        List<commentDto> comments = new ArrayList<>();
        String query = "SELECT c.*, u.userName FROM Comments c JOIN [User] u ON c.userID = u.userID WHERE c.filmID = ? AND c.parentCommentID IS NULL ORDER BY c.commentDate DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            int offset = (currentPage - 1) * commentsPerPage;
            ps.setInt(1, filmID);
            ps.setInt(2, offset); // Đặt OFFSET
            ps.setInt(3, commentsPerPage); // Đặt số lượng ROWS để FETCH
            rs = ps.executeQuery();
            while (rs.next()) {
                commentDto comment = new commentDto(
                        rs.getInt("commentID"),
                        rs.getInt("filmID"),
                        rs.getInt("userID"),
                        rs.getString("commentText"),
                        rs.getTimestamp("commentDate"),
                        null, // Giả sử bạn không lấy parentCommentID ở đây hoặc bạn có thể thêm nếu cần
                        rs.getString("userName")
                );
                comment.setEdited(rs.getBoolean("isEdited"));
                comments.add(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return comments;
    }


    public int getTotalCommentsForFilm(int filmID) {
        int totalComments = 0;
        // Thêm điều kiện parentCommentID IS NULL vào câu truy vấn
        String sql = "SELECT COUNT(*) AS total FROM Comments WHERE filmID = ? AND parentCommentID IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalComments = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return totalComments;
    }


    public void saveReply(String replyText, int userID, int filmID, int parentCommentID) {
        // SQL statement for inserting a new reply
        String sql = "INSERT INTO Comments (filmID, userID, commentText, commentDate, parentCommentID) " +
                "VALUES (?, ?, ?, GETDATE(), ?)";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            ps.setInt(2, userID);
            ps.setString(3, replyText);
            if (parentCommentID > 0) {
                ps.setInt(4, parentCommentID);
            } else {
                // If parentCommentID is not valid, set it to NULL
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteComment(int commentID) {
        String sql = "DELETE FROM Comments WHERE commentID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, commentID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false; // Mặc định trả về false nếu có lỗi xảy ra
    }

    public boolean canDeleteComment(int commentID, int userID) {
        String sql = "SELECT userID FROM Comments WHERE commentID = ?";
        try {
            conn = getConnection(); // Sử dụng getConnection() từ DBContext
            ps = conn.prepareStatement(sql);
            ps.setInt(1, commentID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int ownerUserID = rs.getInt("userID");
                return ownerUserID == userID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean editComment(int commentID, String newCommentText) {
        String sql = "UPDATE Comments " +
                "SET commentText = ?, isEdited = 1 " +
                "WHERE commentID = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newCommentText);
            ps.setInt(2, commentID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        commentDao cmd = new commentDao();
        cmd.deleteComment(2);
    }
}
