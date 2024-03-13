package dal;

import dtos.episodeDtos;
import dtos.newestEpisodeDto;
import dtos.seasonDtos;
import model.Episode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class episodeDao extends DBContext {
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
                season.setSeasonID(rsLocal.getInt("seasonID"));
                season.setSeasonName(rsLocal.getString("seasonName"));
                season.setFilmId(rsLocal.getInt("filmID"));
                season.setEpisodes(getEpisodesForSeason(season.getSeasonID()));
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
                season.setSeasonID(rsLocal.getInt("seasonID"));
                season.setSeasonName(rsLocal.getString("seasonName"));
                season.setFilmId(rsLocal.getInt("filmID"));
                season.setEpisodes(getEpisodesForSeason(season.getSeasonID()));
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

    public boolean addSeason(int filmID, String seasonName) {
        String sql = "INSERT INTO Season (filmID, seasonName) VALUES (?, ?)";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            ps.setString(2, seasonName);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSeason(int seasonID) {
        String deleteEpisodesSQL = "DELETE FROM Episode WHERE seasonID = ?";
        String deleteSeasonSQL = "DELETE FROM Season WHERE seasonID = ?";
        String deleteWatchHistorySQL = "DELETE FROM WatchHistory WHERE episodeID IN (SELECT episodeID FROM Episode WHERE seasonID = ?)";

        try {
            conn = new DBContext().getConnection();

            // Xóa các bản ghi liên quan trong bảng WatchHistory
            ps = conn.prepareStatement(deleteWatchHistorySQL);
            ps.setInt(1, seasonID);
            ps.executeUpdate();

            // Tiếp theo, xóa các tài liệu trong bảng Episode của mùa đó
            ps = conn.prepareStatement(deleteEpisodesSQL);
            ps.setInt(1, seasonID);
            ps.executeUpdate();

            // Cuối cùng, xóa mùa từ bảng Season
            ps = conn.prepareStatement(deleteSeasonSQL);
            ps.setInt(1, seasonID);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Đảm bảo đóng kết nối sau khi sử dụng
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //Lưu các Episodes mà User đã xem
    public boolean saveWatchedHistory(int userID, int filmID, int episodeID, java.sql.Date watchDate, java.sql.Time watchTime) {
        String sql = "INSERT INTO WatchHistory (userID, filmID, episodeID, watchDate, watchTime) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, filmID);
            ps.setInt(3, episodeID);
            ps.setDate(4, watchDate);
            ps.setTime(5, watchTime);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Lấy ra các Episodes mà User đã xem
    public List<newestEpisodeDto> getWatchedEpisodesByUserId(int userId) {
        List<newestEpisodeDto> watchedEpisodes = new ArrayList<>();
        Map<Integer, newestEpisodeDto> episodeMap = new LinkedHashMap<>(); // Sử dụng LinkedHashMap để duy trì thứ tự
        String sql = "SELECT Top 12 e.episodeID, e.title, e.episodeLink, s.seasonName, f.filmID, f.filmName, f.imageLink, wh.watchDate, wh.watchTime " +
                "FROM WatchHistory wh " +
                "JOIN Episode e ON wh.episodeID = e.episodeID " +
                "JOIN Season s ON e.seasonID = s.seasonID " +
                "JOIN Film f ON s.filmID = f.filmID " +
                "WHERE wh.userID = ? " +
                "ORDER BY wh.watchDate DESC, wh.watchTime DESC;";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int episodeId = rs.getInt("episodeID");
                if (!episodeMap.containsKey(episodeId)) {
                    newestEpisodeDto episode = new newestEpisodeDto();
                    episode.setEpId(episodeId);
                    episode.setEpTittle(rs.getString("title"));
                    episode.setEpLink(rs.getString("episodeLink"));
                    episode.setSeasonName(rs.getString("seasonName"));
                    episode.setFilmId(rs.getInt("filmID"));
                    episode.setFilmName(rs.getString("filmName"));
                    episode.setImageLink(rs.getString("imageLink"));
                    watchedEpisodes.add(episode);
                    episodeMap.put(episodeId, episode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return watchedEpisodes;
    }

    public boolean addEpisode(int seasonID, String title, Timestamp releaseDate, boolean isPremium, String episodeLink) {
        String sql = "INSERT INTO Episode (seasonID, title, releaseDate, isPremium, episodeLink) VALUES (?, ?, ?, ?, ?)";
        boolean rowUpdated;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, seasonID);
            ps.setString(2, title);
            ps.setTimestamp(3, releaseDate);
            ps.setBoolean(4, isPremium);
            ps.setString(5, episodeLink);

            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            rowUpdated = false;
        }
        return rowUpdated;
    }

    public boolean updatePremium(int episodeId, boolean isPremium) {
        String sql = "UPDATE Episode SET isPremium = ? WHERE episodeID = ?";
        boolean rowUpdated;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isPremium);
            ps.setInt(2, episodeId);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            rowUpdated = false;
        }
        return rowUpdated;
    }

    public boolean updateSeasonName(int seasonId, String seasonName) {
        String sql = "UPDATE Season SET seasonName = ? WHERE seasonID = ?";
        boolean rowUpdated;
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, seasonName);
            ps.setInt(2, seasonId);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            rowUpdated = false;
        }
        return rowUpdated;
    }

    public static void main(String[] args) {
        episodeDao d = new episodeDao();
        d.updateSeasonName(46, "Season 5");
    }
}
