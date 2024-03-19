package dal;

import dtos.filmDtos;
import dtos.newestEpisodeDto;
import entity.*;

import java.sql.*;
import java.util.*;

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
                "ORDER BY f.filmID DESC;\n";
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
                film.setFilmID(rs.getInt("filmID"));
                film.setFilmName(rs.getString("filmName"));
                film.setDescription(rs.getString("description"));
                film.setImageLink(rs.getString("imageLink"));
                film.setTrailerLink(rs.getString("trailerLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                newFilms.add(film);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFilms;
    }


    private List<Category> getCategoriesForFilm(int filmID) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT c.CategoryID, c.CategoryName FROM Category c JOIN FilmCategory fc ON c.CategoryID = fc.CategoryID WHERE fc.filmID = ?";
        ResultSet rsLocal = null;
        try (PreparedStatement psLocal = getConnection().prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            rsLocal = psLocal.executeQuery();
            while (rsLocal.next()) {
                Category category = new Category();
                category.setCategoryID(rsLocal.getInt("CategoryID"));
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
        String sql = "SELECT t.tagID, t.tagName FROM Tag t JOIN FilmTag ft ON t.tagID = ft.tagID WHERE ft.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Tag tag = new Tag();
                    tag.setTagID(rsLocal.getInt("tagID"));
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
        String sql = "SELECT s.seasonID, s.seasonName FROM Season s WHERE s.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Season season = new Season();
                    season.setSeasonID(rsLocal.getInt("seasonID"));
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


    // Lấy thông tin phim theo ID
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
        return film;
    }

    // Lấy thông tin phim theo tên
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

    //Lấy danh sách phim có lượt xem cao nhất
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
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
                filmDtosList.add(film);
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

    // Lấy film mỗi trang (phân trang theo phương pháp bỏ qua các film trước đó)
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
        //"ONLY" có hay không không quan trọng
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            //Tính offset = số film được bỏ qua
            int offset = (currentPage - 1) * filmsPerPage;
            ps.setInt(1, offset);

            //fetch next = số film được lấy ra
            ps.setInt(2, filmsPerPage);
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
        }
        return films;
    }

    // Lấy film mỗi trang dựa theo Search (phân trang theo phương pháp đánh số)
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
            //Đánh số hàng bằng cho film bằng ROW_NUMBER()
            //Lấy film từ startRow đến row endRow
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
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
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

    //Lấy film mỗi trang dựa theo Category (phân trang theo phương pháp đánh số)
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
                film.setViewCount(rs.getLong("viewCount"));
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
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

    //Lấy các episodes mới được upload
    public List<newestEpisodeDto> getLatestEpisodes(int limit) {
        List<newestEpisodeDto> episodes = new ArrayList<>();
        String sql = "SELECT TOP (?) e.episodeID, e.title, e.episodeLink, e.releaseDate, f.filmID, f.filmName, f.imageLink, s.seasonName, e.isPremium "
                + "FROM Episode e "
                + "JOIN Season s ON e.seasonID = s.seasonID "
                + "JOIN Film f ON s.filmID = f.filmID "
                + "ORDER BY e.releaseDate DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                newestEpisodeDto episode = new newestEpisodeDto();
                episode.setEpId(rs.getInt("episodeID"));
                episode.setEpTittle(rs.getString("title"));
                episode.setEpLink(rs.getString("episodeLink"));
                episode.setFilmId(rs.getInt("filmID"));
                episode.setSeasonName(rs.getString("seasonName"));
                episode.setFilmName(rs.getString("filmName"));
                episode.setImageLink(rs.getString("imageLink"));
                episode.setPremium(rs.getBoolean("isPremium"));
                episodes.add(episode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }

    //Lấy số luượng episodes premium mới được upload
    public int countTotalEpisodes() {
        int totalEpisodes = 0;
        String sql = "SELECT COUNT(*) AS total FROM Episode";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalEpisodes = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalEpisodes;
    }


    // Lấy ngẫu nhiên 6 film
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
                film.setImageLink(rs.getString("imageLink"));
                film.setViewCount(rs.getLong("viewCount"));
                film.setRatingValue(getBetterFloat(rs.getFloat("averageRating")));
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
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
        boolean exists = false;
        String sql = "SELECT COUNT(*) AS count FROM Favourite WHERE userId = ? AND filmId = ?";

        try {
            conn = new DBContext().getConnection();
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

    public boolean addFilm(Film film) {
        String sql = "INSERT INTO Film (filmName, description, releaseDate, imageLink, trailerLink, viewCount) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getFilmName());
            ps.setString(2, film.getDescription());
            ps.setTimestamp(3, film.getFilmReleaseDate());
            ps.setString(4, film.getImageLink());
            ps.setString(5, film.getTrailerLink());
            ps.setLong(6, film.getViewCount());

            int result = ps.executeUpdate();
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    film.setFilmID(rs.getInt(1));
                }
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateFilmName(int filmId, String name) {
        String sql = "UPDATE Film SET filmName = ? WHERE filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, filmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFilmDescription(int filmId, String description) {
        String sql = "UPDATE Film SET description = ? WHERE filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, description);
            ps.setInt(2, filmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFilmThumbnail(int filmId, String thumbnailPath) {
        String sql = "UPDATE Film SET imageLink = ? WHERE filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, thumbnailPath);
            ps.setInt(2, filmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFilmTrailerLink(int filmId, String trailerLink) {
        String sql = "UPDATE Film SET trailerLink = ? WHERE filmID = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, trailerLink);
            ps.setInt(2, filmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFilm(int filmId) {
        String sql = "DECLARE @filmID INT;\n" +
                "SET @filmID = ?;\n" +
                "DELETE FROM Comments WHERE filmID = @filmID;\n" +
                "DELETE FROM WatchHistory WHERE filmID = @filmID;\n" +
                "DELETE FROM Favourite WHERE filmID = @filmID;\n" +
                "DELETE FROM Ratings WHERE filmID = @filmID;\n" +
                "DELETE FROM FilmTag WHERE filmID = @filmID;\n" +
                "DELETE FROM FilmCategory WHERE filmID = @filmID;\n" +
                "DELETE FROM Episode\n" +
                "WHERE seasonID IN (SELECT seasonID FROM Season WHERE filmID = @filmID);\n" +
                "DELETE FROM Season WHERE filmID = @filmID;\n" +
                "DELETE FROM Film WHERE filmID = @filmID;\n";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<filmDtos> searchFilmsBySort(String searchQuery, int page, int filmsPerPage, String sortField, String sortOrder) {
        List<filmDtos> films = new ArrayList<>();
        List<String> validSortFields = Arrays.asList("filmName", "description", "viewCount", "averageRating");
        sortField = validSortFields.contains(sortField) ? sortField : "filmID";
        sortOrder = "DESC".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

        String sql = "WITH FilteredFilms AS (\n" +
                "SELECT f.*, ra.averageRating, ROW_NUMBER() OVER (ORDER BY " + sortField + " " + sortOrder + ") AS RowNum\n" +
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
                film.setCategories(getCategoriesForFilm(film.getFilmID()));
                film.setTags(getTagsForFilm(film.getFilmID()));
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

    public List<filmDtos> getFilmsPerPageBySort(int currentPage, int filmsPerPage, String sortField, String sortOrder) {
        List<filmDtos> films = new ArrayList<>();

        List<String> validSortFields = Arrays.asList("filmName", "viewCount", "averageRating", "filmID");
        sortField = validSortFields.contains(sortField) ? sortField : "filmID";
        sortOrder = "DESC".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

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
                "ORDER BY " + sortField + " " + sortOrder + "\n" +
                "OFFSET ? ROWS\n" +
                "FETCH NEXT ? ROWS ONLY;";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            int offset = (currentPage - 1) * filmsPerPage;
            ps.setInt(1, offset);
            ps.setInt(2, filmsPerPage);
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return films;
    }

    public List<filmDtos> getFilmsByCategoryBySort(String categoryName, int page, int filmsPerPage, String sortField, String sortOrder) {
        List<filmDtos> films = new ArrayList<>();
        List<String> validSortFields = Arrays.asList("filmName", "viewCount", "averageRating", "filmID");
        sortField = validSortFields.contains(sortField) ? sortField : "filmID";
        sortOrder = "DESC".equalsIgnoreCase(sortOrder) ? "DESC" : "ASC";

        String query = "WITH RatingAverage AS (\n" +
                "    SELECT\n" +
                "        filmID,\n" +
                "        AVG(ratingValue) AS averageRating\n" +
                "    FROM\n" +
                "        Ratings\n" +
                "    GROUP BY\n" +
                "        filmID\n" +
                "), Film_CTE AS (\n" +
                "    SELECT ROW_NUMBER() OVER (ORDER BY " + sortField + " " + sortOrder + ") AS RowNum, f.*, ra.averageRating\n" +
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return films;
    }

    public List<newestEpisodeDto> getPremiumEpisodes() {
        List<newestEpisodeDto> episodes = new ArrayList<>();
        String sql = "SELECT e.episodeID, e.title, e.episodeLink, e.releaseDate, f.filmID, f.filmName, f.description, f.imageLink, f.trailerLink, f.viewCount, s.seasonName "
                + "FROM Episode e "
                + "JOIN Season s ON e.seasonID = s.seasonID "
                + "JOIN Film f ON s.filmID = f.filmID "
                + "WHERE e.isPremium = 1 "
                + "ORDER BY e.episodeID ASC ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                newestEpisodeDto episode = new newestEpisodeDto();
                episode.setEpId(rs.getInt("episodeID"));
                episode.setEpTittle(rs.getString("title"));
                episode.setEpLink(rs.getString("episodeLink"));
                episode.setFilmId(rs.getInt("filmID"));
                episode.setSeasonName(rs.getString("seasonName"));
                episode.setFilmName(rs.getString("filmName"));
                episode.setImageLink(rs.getString("imageLink"));
                episodes.add(episode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }


    public static void main(String[] args) {
        filmDao fd = new filmDao();
        System.out.println(fd.countTotalEpisodes());
    }

}