package dal;

import dtos.filmDtos;
import entity.Category;
import entity.Tag;
import entity.Season;
import entity.Episode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class filmDao extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    // Phương thức chính để lấy danh sách filmDtos
    public List<filmDtos> getAllFilms() {
        List<filmDtos> films = new ArrayList<>();
        String sql = "SELECT * FROM Film";
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
                film.setViewCount(rs.getLong("viewCount"));

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
        String sql = "SELECT e.title, e.episodeLink FROM Episode e JOIN Season s ON e.seasonID = s.seasonID JOIN Film f ON s.filmID = f.filmID WHERE f.filmID = ?";
        try (PreparedStatement psLocal = conn.prepareStatement(sql)) {
            psLocal.setInt(1, filmID);
            try (ResultSet rsLocal = psLocal.executeQuery()) {
                while (rsLocal.next()) {
                    Episode episode = new Episode();
                    episode.setEpTittle(rsLocal.getString("title"));
                    episode.setEpLink(rsLocal.getString("episodeLink"));
                    episodes.add(episode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodes;
    }

    public static void main(String[] args) {
        filmDao d = new filmDao();
        List<filmDtos> films = d.getAllFilms();
        for (filmDtos f : films){
            System.out.println(f);
        }
    }
}
