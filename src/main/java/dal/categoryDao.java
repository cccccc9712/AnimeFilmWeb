package dal;

import entity.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class categoryDao extends DBContext{
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
                    category.setCategoryName(rs.getString("CategoryName"));
                    categories.add(category);
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }




    public static void main(String[] args) {
    categoryDao d = new categoryDao();
    List<Category> ct = d.getCategories();
    for (Category c : ct){
        System.out.println(c.getCategoryName());
    }
    }
}
