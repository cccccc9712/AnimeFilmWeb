package dal;

import entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dao extends DBContext{
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User login(String mail, String pass) {
        String query = "SELECT * FROM [User] WHERE gmail = ? AND userPass = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, mail);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5));
            }
        }catch (Exception e) {

        }
        return null;
    }

//    // Phương thức để lấy danh sách filmDtos
//    public List<filmDtos> getAllFilms() {
//        List<filmDtos> films = new ArrayList<>();
//        String sql = "SELECT * FROM Film"; // Truy vấn cơ bản, có thể mở rộng để bao gồm thông tin từ các bảng khác
//        try {
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                filmDtos film = new filmDtos();
//                film.setFilmID(rs.getInt("filmID"));
//                film.setFilmName(rs.getString("filmName"));
//                film.setDescription(rs.getString("description"));
//                film.setImageLink(rs.getString("imageLink"));
//                // Đặt các thuộc tính khác của filmDtos
//
//                // Thêm logic để lấy thông tin từ các bảng Category, Tag, Season, và Episode
//                // Ví dụ: Lấy danh sách Category
//                List<Category> categories = getCategoriesForFilm(film.getFilmID());
//                film.setCategories(categories);
//
//                // Tương tự, thêm logic cho Tag, Season và Episode
//
//                films.add(film);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return films;
//    }
//
//    // Phương thức hỗ trợ để lấy danh sách Category cho một phim cụ thể
//    private List<Category> getCategoriesForFilm(int filmID) {
//        List<Category> categories = new ArrayList<>();
//        String sql = "SELECT c.CategoryName FROM Category c JOIN FilmCategory fc ON c.CategoryID = fc.CategoryID WHERE fc.filmID = ?";
//        try {
//            PreparedStatement ps = getConnection().prepareStatement(sql);
//            ps.setInt(1, filmID);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Category category = new Category();
//                category.setCategoryName(rs.getString("CategoryName"));
//                categories.add(category);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return categories;
//    }
//
//    // Tương tự, bạn có thể thêm các phương thức để lấy Tag, Season, và Episode

    public static void main(String[] args) {
        Dao dao = new Dao();
        User a = dao.login("thangtmhe180330","Thang123*");
        System.out.println(a);
    }

}
