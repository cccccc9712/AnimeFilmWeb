package dal;

import dtos.episodeDtos;
import dtos.seasonDtos;
import entity.Episode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class episodeDao extends DBContext{
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
            ps.executeUpdate();
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
    public List<seasonDtos> getSeasonsForFilmById(int filmId) {
        List<seasonDtos> seasons = new ArrayList<>();
        String sql = "SELECT s.seasonID, s.seasonName, f.filmID, f.filmName \n" +
                "FROM Season s\n" +
                "JOIN Film f ON s.filmID = f.filmID\n" +
                "WHERE f.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmId);
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

    public List<Episode> getEpisodesForSeason(int seasonId) {
        List<Episode> episodes = new ArrayList<>();
        String sql = "SELECT episodeID, title, episodeLink, releaseDate, isPremium FROM Episode WHERE seasonID = ?";
        ResultSet rsLocal = null;
        try (PreparedStatement psLocal = getConnection().prepareStatement(sql)) {
            psLocal.setInt(1, seasonId);
            rsLocal = psLocal.executeQuery();
            while (rsLocal.next()) {
                Episode episode = new Episode();
                episode.setEpId(rsLocal.getInt("episodeID"));
                episode.setEpTittle(rsLocal.getString("title"));
                episode.setEpLink(rsLocal.getString("episodeLink"));
                episode.setEpDate(rsLocal.getDate("releaseDate"));
                episode.setPremium(rsLocal.getBoolean("isPremium"));
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

    public boolean checkEpisodeIsPremium(int episodeId) {
        boolean isPremium = false;
        try {
            conn = new DBContext().getConnection();
            String sql = "SELECT isPremium FROM Episode WHERE episodeID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, episodeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                isPremium = rs.getBoolean("isPremium");
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
        return isPremium;
    }


    public static void main(String[] args) {
        episodeDao d = new episodeDao();
    }
}
