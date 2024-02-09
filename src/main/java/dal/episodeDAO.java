package dal;

import dtos.episodeDtos;
import dtos.filmDtos;
import entity.Episode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class episodeDAO extends DBContext{
    PreparedStatement ps = null;
    ResultSet rs = null;


    public episodeDtos getEpisodeById(int episodeId) {
        episodeDtos episode = null;
        // Giả sử có quan hệ: Episode -> Season -> Film
        String sql = "SELECT e.*, s.seasonID, s.filmID FROM Episode e " +
                "INNER JOIN Season s ON e.seasonID = s.seasonID " +
                "WHERE e.episodeID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, episodeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                episode = new episodeDtos();
                episode.setEpId(rs.getInt("episodeID"));
                episode.setEpTittle(rs.getString("title"));
                episode.setEpDate(rs.getDate("releaseDate"));
                episode.setEpLink(rs.getString("episodeLink"));
                episode.setFilmId(rs.getInt("filmID")); // Lấy filmID từ kết quả truy vấn
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối, ps, và rs
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return episode;
    }

    public void increaseViewCount(int filmId) {
        String sql = "UPDATE Film SET viewCount = viewCount + 1 WHERE filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            ps.executeUpdate(); // Sử dụng executeUpdate() để thực hiện truy vấn cập nhật
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
    }

    public static void main(String[] args) {
        episodeDAO d = new episodeDAO();
        d.increaseViewCount(1);
        filmDao fd = new filmDao();
        filmDtos fdt = fd.getFilmById(1);
        System.out.println(fdt.getViewCount());
    }
}
