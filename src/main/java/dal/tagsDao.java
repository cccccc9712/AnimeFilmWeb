package dal;

import entity.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class tagsDao extends DBContext{
    PreparedStatement ps = null;
    ResultSet rs = null;

//    public void insertTags(String filmID, String[] tags) {
//        String sql = "insert into FilmTag values(?, ?)";
//        try {
//            conn = new DBContext().getConnection();
//            ps = conn.prepareStatement(sql);
//            for (String tag : tags) {
//                ps.setString(1, filmID);
//                ps.setString(2, tag);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();

    public List<Tag> getTags() {
        List<Tag> categories = new ArrayList<>();
        String sql = "select * from Tag";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Tag category = new Tag();
                category.setTagID(rs.getString("tagID"));
                category.setTagName(rs.getString("tagName"));
                categories.add(category);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void insertTags(int filmId, int tagId) {
        String sql = "insert into FilmTag values(?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, filmId);
            ps.setInt(2, tagId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        tagsDao d = new tagsDao();
        List<Tag> ct = d.getTags();
        for (Tag c : ct){
            System.out.println(c.toString());
        }
    }
}
