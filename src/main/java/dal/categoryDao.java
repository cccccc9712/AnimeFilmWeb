package dal;

import entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class categoryDao extends DBContext {
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "select * from Category";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setCategoryName(rs.getString("CategoryName"));
                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void insertCategory(int filmId, int categoryId) {
        String sql = "insert into FilmCategory values(?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCategoryFromFilm(int filmId, int categoryId) {
        String sql = "DELETE FROM FilmCategory WHERE filmID = ? AND categoryID = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            ps.setInt(2, categoryId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAllCategoryFromFilm(int filmId) {
        String sql = "DELETE FROM FilmCategory WHERE filmID = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        categoryDao d = new categoryDao();
        d.removeAllCategoryFromFilm(43);

    }
}
