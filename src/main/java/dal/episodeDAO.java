package dal;

import dtos.episodeDtos;
import dtos.filmDtos;
import dtos.seasonDtos;
import entity.Episode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<seasonDtos> getSeasonsForFilm(String filmName) {
        List<seasonDtos> seasons = new ArrayList<>();
        String sql = "SELECT s.seasonID, s.seasonName, f.filmID, f.filmName \n" +
                "FROM Season s\n" +
                "JOIN Film f ON s.filmID = f.filmID\n" +
                "WHERE f.filmName = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setString(1, filmName);
            ResultSet rsLocal = psLocal.executeQuery();
            while (rsLocal.next()) {
                seasonDtos season = new seasonDtos();
                season.setSeasonId(rsLocal.getInt("seasonID"));
                season.setSeasonName(rsLocal.getString("seasonName"));
                season.setFilmId(rsLocal.getInt("filmID"));
                season.setEpisodes(getEpisodesForSeason(season.getSeasonId()));
                seasons.add(season);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seasons;
    }

    public List<episodeDtos> getEpisodesForSeason(int seasonId) {
        List<episodeDtos> episodes = new ArrayList<>();
        String sql = "SELECT episodeID, title, episodeLink, releaseDate FROM Episode WHERE seasonID = ?";
        ResultSet rsLocal = null;
        try (PreparedStatement psLocal = getConnection().prepareStatement(sql)) {
            psLocal.setInt(1, seasonId);
            rsLocal = psLocal.executeQuery();
            while (rsLocal.next()) {
                episodeDtos episode = new episodeDtos();
                episode.setEpId(rsLocal.getInt("episodeID"));
                episode.setEpTittle(rsLocal.getString("title"));
                episode.setEpLink(rsLocal.getString("episodeLink"));
                episode.setEpDate(rsLocal.getDate("releaseDate"));
                // Giả sử bạn cũng muốn lưu trữ seasonID trong EpisodeDtos, bạn cần thêm thuộc tính và setter tương ứng trong class EpisodeDtos
                // episode.setSeasonId(seasonId); // Chỉ thêm nếu có thuộc tính seasonId trong EpisodeDtos
                episodes.add(episode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rsLocal != null) {
                try {
                    rsLocal.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return episodes;
    }


    public static void main(String[] args) {
        episodeDAO d = new episodeDAO();
        List<seasonDtos> sd = d.getSeasonsForFilm("Youkoso Jitsuryoku Shijou Shugi no Kyoushitsu");
        for (seasonDtos a : sd){
            System.out.println(a.toString());
        }
    }
}
