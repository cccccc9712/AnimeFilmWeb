package dal;

import dtos.filmDtos;
import dtos.newestEpisodeDto;
import entity.Category;
import entity.Tag;
import entity.Season;
import entity.Episode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class filmDao extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<filmDtos> getAllFilms() {
        List<filmDtos> films = new ArrayList<>();
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT\n" +
                "    f.*,\n" +
                "    ra.averageRating\n" +
                "FROM\n" +
                "    Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "ORDER BY f.filmID DESC;\n"; // Câu lệnh đã được chỉnh sửa để sắp xếp theo filmID giảm dần
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(rs.getFloat("averageRating"));

                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));

                films.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return films;
    }

    public List<filmDtos> getNewFilms() {
        List<filmDtos> newFilms = new ArrayList<>();
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT TOP (7) f.*, ra.averageRating\n" +
                "FROM Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "ORDER BY f.filmID DESC;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                // Thiết lập thông tin film
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));

                // Lấy thông tin liên quan
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));

                newFilms.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFilms;
    }


    private List<Category> getCategoriesForFilm(int filmID) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.CategoryName FROM Category c JOIN FilmCategory fc ON c.CategoryID = fc.CategoryID WHERE fc.filmID = ?";
        ResultSet rsLocal = null;
        try (PreparedStatement psLocal = getConnection().prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            rsLocal = psLocal.executeQuery();
            while (rsLocal.next()) {
                Category category = new Category();
                category.setCategoryName(rsLocal.getString("CategoryName"));
                categories.add(category);
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
        return categories;
    }

    private List<Tag> getTagsForFilm(int filmID) {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT t.tagName FROM Tag t JOIN FilmTag ft ON t.tagID = ft.tagID WHERE ft.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Tag tag = new Tag();
                    tag.setTagName(rsLocal.getString("tagName"));
                    tags.add(tag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tags;
    }

    private List<Season> getSeasonsForFilm(int filmID) {
        List<Season> seasons = new ArrayList<>();
        String sql = "SELECT s.seasonName FROM Season s WHERE s.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Season season = new Season();
                    season.setSeasonName(rsLocal.getString("seasonName"));
                    seasons.add(season);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seasons;
    }

    private List<Episode> getEpisodesForFilm(int filmID) {
        List<Episode> episodes = new ArrayList<>();
        String sql = "SELECT e.episodeID, e.title, e.episodeLink, e.releaseDate FROM Episode e JOIN Season s ON e.seasonID = s.seasonID JOIN Film f ON s.filmID = f.filmID WHERE f.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Episode episode = new Episode();
                    episode.setEpId(rsLocal.getInt("episodeID"));
                    episode.setEpTittle(rsLocal.getString("title"));
                    episode.setEpLink(rsLocal.getString("episodeLink"));
                    episode.setEpDate(rsLocal.getDate("releaseDate"));
                    episodes.add(episode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }


    public filmDtos getFilmById(int filmId) {
        filmDtos film = null;
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT f.*, ra.averageRating\n" +
                "FROM Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "WHERE f.filmID = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            rs = ps.executeQuery();
            if (rs.next()) {
                film = new filmDtos();
                // Set film details
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(rs.getFloat("averageRating"));

                // Get related data
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return film;
    }

    public filmDtos getFilmByName(String filmName) {
        filmDtos film = null;
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT f.*, ra.averageRating\n" +
                "FROM Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "WHERE f.filmName = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, filmName);
            rs = ps.executeQuery();
            if (rs.next()) {
                film = new filmDtos();
                // Set film details
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));

                // Get related data
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return film;
    }

    public List<filmDtos> getFilmWithHighestViewCount() {
        filmDtos film = null;
        List<filmDtos> filmDtosList = new ArrayList<>();
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                "),\n" +
                "HighestViewCount AS (\n" +
                "    SELECT TOP 8\n" +
                "        f.*,\n" +
                "        ra.averageRating\n" +
                "    FROM Film f\n" +
                "    LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "    ORDER BY f.viewCount DESC\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM HighestViewCount;\n";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));

                // Get related data
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
                filmDtosList.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return filmDtosList;
    }

    public int getTotalFilms() {
        String query = "select COUNT(*) from Film";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<filmDtos> getFilmsPerPage(int currentPage, int filmsPerPage) {
        List<filmDtos> films = new ArrayList<>();
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT f.*, ra.averageRating\n" +
                "FROM Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "ORDER BY f.filmID DESC\n" +
                "OFFSET ? ROWS\n" +
                "FETCH NEXT ? ROWS ONLY;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            int offset = (currentPage - 1) * filmsPerPage;
            ps.setInt(1, offset); // Đặt OFFSET
            ps.setInt(2, filmsPerPage); // Đặt số lượng ROWS để FETCH
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));
                // Get related data
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
                films.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return films;
    }


    public List<filmDtos> searchFilms(String searchQuery, int page, int filmsPerPage) {
        List<filmDtos> films = new ArrayList<>();
        String sql = "WITH FilteredFilms AS (\n" +
                "SELECT f.*, ra.averageRating, ROW_NUMBER() OVER (ORDER BY f.filmID DESC) AS RowNum\n" +
                "FROM Film f\n" +
                "LEFT JOIN (SELECT filmID, AVG(ratingValue) AS averageRating FROM Ratings GROUP BY filmID) ra ON f.filmID = ra.filmID\n" +
                "WHERE f.filmName LIKE ? OR f.description LIKE ?\n" +
                "),\n" +
                "PagedFilms AS (\n" +
                "SELECT * FROM FilteredFilms\n" +
                "WHERE RowNum BETWEEN ? AND ?\n" +
                ")\n" +
                "SELECT * FROM PagedFilms;";
        try {
            int startRow = (page - 1) * filmsPerPage + 1;
            int endRow = startRow + filmsPerPage - 1;
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ps.setInt(3, startRow);
            ps.setInt(4, endRow);
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));
                // Lấy thông tin liên quan
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
                films.add(film);
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
        return films;
    }

    public int getTotalFilmsBySearchQuery(String searchQuery) {
        int total = 0;
        String sql = "SELECT COUNT(*) as Total FROM Film WHERE filmName LIKE ? OR description LIKE ?\n";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }


    public int getTotalFilmsByCategory(String category) {
        String query = "SELECT COUNT(*) FROM Film f JOIN FilmCategory fc ON f.filmID = fc.filmID JOIN Category c ON fc.CategoryID = c.CategoryID WHERE c.CategoryName = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<filmDtos> getFilmsByCategory(String categoryName, int page, int filmsPerPage) {
        List<filmDtos> films = new ArrayList<>();
        String query = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                "), Film_CTE AS (\n" +
                "    SELECT ROW_NUMBER() OVER (ORDER BY f.filmID DESC) AS RowNum, f.*, ra.averageRating\n" +
                "    FROM Film f\n" +
                "    JOIN FilmCategory fc ON f.filmID = fc.filmID\n" +
                "    JOIN Category c ON fc.CategoryID = c.CategoryID\n" +
                "    LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "    WHERE c.CategoryName = ?\n" +
                ")\n" +
                "SELECT * FROM Film_CTE\n" +
                "WHERE RowNum BETWEEN ? AND ?;";
        try {
            int startRow = (page - 1) * filmsPerPage + 1;
            int endRow = startRow + filmsPerPage - 1;
            conn = getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, categoryName);
            ps.setInt(2, startRow);
            ps.setInt(3, endRow);
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));

                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));
                films.add(film);
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
        return films;
    }

    public List<newestEpisodeDto> getLatestEpisodes() {
        List<newestEpisodeDto> episodes = new ArrayList<>();
        String sql = "SELECT TOP 12 e.episodeID, e.title, e.episodeLink, e.releaseDate, f.filmID, f.filmName, f.description, f.imageLink, f.trailerLink, f.viewCount, s.seasonName "
                + "FROM Episode e "
                + "JOIN Season s ON e.seasonID = s.seasonID "
                + "JOIN Film f ON s.filmID = f.filmID "
                + "ORDER BY e.releaseDate DESC";
        try {
             conn = new DBContext().getConnection();
             ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newestEpisodeDto episode = new newestEpisodeDto();
                episode.setEpId(rs.getInt("episodeID"));
                episode.setEpTittle(rs.getString("title"));
                episode.setEpLink(rs.getString("episodeLink"));
                episode.setEpDate(rs.getDate("releaseDate"));
                episode.setFilmId(rs.getInt("filmID"));
                episode.setSeasonName(rs.getString("seasonName"));
                episode.setFilmName(rs.getString("filmName"));
                episode.setDescription(rs.getString("description"));
                episode.setImageLink(rs.getString("imageLink"));
                episode.setTrailerLink(rs.getString("trailerLink"));
                episode.setViewCount(rs.getLong("viewCount"));
                episode.setCategories(getCategoriesForFilm(episode.getFilmId()));
                episode.setTags(getTagsForFilm(episode.getFilmId()));
                episode.setSeasons(getSeasonsForFilm(episode.getFilmId()));
                episodes.add(episode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }

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

    public List<newestEpisodeDto> getWatchedEpisodesByUserId(int userId) {
        List<newestEpisodeDto> watchedEpisodes = new ArrayList<>();
        String sql = "SELECT Top 12 e.episodeID, e.title AS episodeTitle, e.episodeLink, s.seasonName, f.filmID, f.filmName, f.imageLink, f.viewCount, wh.watchDate, wh.watchTime " +
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
                newestEpisodeDto episode = new newestEpisodeDto();
                episode.setEpId(rs.getInt("episodeID"));
                episode.setEpTittle(rs.getString("episodeTitle"));
                episode.setEpLink(rs.getString("episodeLink"));
                episode.setSeasonName(rs.getString("seasonName"));
                episode.setFilmId(rs.getInt("filmID"));
                episode.setFilmName(rs.getString("filmName"));
                episode.setImageLink(rs.getString("imageLink"));
                episode.setViewCount(rs.getLong("viewCount"));
                watchedEpisodes.add(episode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return watchedEpisodes;
    }

    public List<filmDtos> getRandom6FilmsWithFullDetails() {
        List<filmDtos> films = new ArrayList<>();
        String sql = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                ")\n" +
                "SELECT TOP 6 f.*, ra.averageRating\n" +
                "FROM Film f\n" +
                "LEFT JOIN RatingAverage ra ON f.filmID = ra.filmID\n" +
                "ORDER BY NEWID();";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));

                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));

                films.add(film);
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
        return films;
    }

    public int getFilmIdByFilmName(String filmName) {
        int filmId = -1;
        String sql = "SELECT filmID FROM Film WHERE filmName = ?";

        try {
             conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, filmName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    filmId = rs.getInt("filmID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filmId;
    }

    public float getAverageRatingForFilm(int filmID) {
        String sql = "SELECT AVG(ratingValue) AS averageRating FROM Ratings WHERE filmID = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmID);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("averageRating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<filmDtos> getFavouriteFilmsByUserId(int userId) {
        List<filmDtos> favouriteFilms = new ArrayList<>();
        String sql = "SELECT f.*, ra.averageRating FROM Favourite fav "
                + "JOIN Film f ON fav.filmID = f.filmID "
                + "LEFT JOIN ("
                + "    SELECT filmID, AVG(ratingValue) AS averageRating "
                + "    FROM Ratings "
                + "    GROUP BY filmID"
                + ") ra ON f.filmID = ra.filmID "
                + "WHERE fav.userID = ? ORDER BY fav.addedDate DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                filmDtos film = new filmDtos();
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                film.setSeasons(getSeasonsForFilm(film.getFilmID()));
                film.setEpisodes(getEpisodesForFilm(film.getFilmID()));

                favouriteFilms.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favouriteFilms;
    }

    public float getBetterFloat(float num) {
        return Math.round(num * 10) / 10.0f;
    }


    public boolean isFavouriteExists(int userId, int filmId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;
        String sql = "SELECT COUNT(*) AS count FROM Favourite WHERE userId = ? AND filmId = ?";

        try {
            conn = getConnection(); // Giả định bạn đã có phương thức này để lấy kết nối DB
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, filmId);

            rs = ps.executeQuery();
            if (rs.next()) {
                exists = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }



    public static void main(String[] args) {
        filmDao fd = new filmDao();
        List<filmDtos> ned = fd.getFavouriteFilmsByUserId(3);
        for (filmDtos f : ned){
            System.out.println(f.toString());
        }
    }

}
