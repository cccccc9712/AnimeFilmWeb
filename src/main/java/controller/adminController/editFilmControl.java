package controller.adminController;

import dal.categoryDao;
import dal.filmDao;
import dal.tagsDao;
import dtos.filmDtos;
import entity.Category;
import entity.Tag;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.awt.geom.Path2D.contains;

@WebServlet(name = "editFilmControl", value = "/editFilm")
@MultipartConfig(location = "C:\\Users\\tmtmt\\IdeaProjects\\AnimeFilmWeb\\src\\main\\webapp\\img\\thumbnailUpload")
public class editFilmControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter("filmId"));
        String filmName = req.getParameter("filmName");
        String filmDescription = req.getParameter("description");
        String trailerLink = req.getParameter("trailerLink");
        Part filePart = req.getPart("thumbnail");
        String fileName = filePart.getSubmittedFileName();
        String[] selectedTags = req.getParameterValues("tags");
        String[] selectedCategories = req.getParameterValues("categories");

        filmDao dao = new filmDao();
        categoryDao cateDao = new categoryDao();
        tagsDao tgDao = new tagsDao();

        String thumbnailPath = "img/thumbnailUpload/" + fileName;

        filmDtos film = dao.getFilmById(filmId);
        if (filmName != null && !filmName.isBlank()) {
            dao.updateFilmName(filmId, filmName);
        }
        if (filmDescription != null && !filmDescription.isBlank()) {
            dao.updateFilmDescription(filmId, filmDescription);
        }
        if (trailerLink != null && !trailerLink.isBlank()) {
            dao.updateFilmTrailerLink(filmId, trailerLink);
        }
        if (filePart != null && fileName != null && !fileName.isBlank() && filePart.getSize() > 0) {
            String savePath = getServletContext().getRealPath("/img/thumbnailUpload") + File.separator + fileName;
            File file = new File(savePath);

            if (!file.exists()) {
                filePart.write(savePath); // Ghi file mới
            } else {
                //Avoid duplicate files
                file.delete();
                filePart.write(savePath);
            }
            dao.updateFilmThumbnail(filmId, thumbnailPath);
        }
        List<Category> filmCategories = film.getCategories();
        List<Tag> filmTags = film.getTags();

        if (selectedCategories != null) {
            for (Category c : filmCategories) {
                if (!contains(selectedCategories, c.getCategoryID())) {
                    cateDao.removeCategoryFromFilm(filmId, c.getCategoryID());
                }
            }
            for (String c : selectedCategories) {
                if (!containsCategories(filmCategories, Integer.parseInt(c))) {
                    cateDao.insertCategory(filmId, Integer.parseInt(c));
                }
            }
        } else {
            cateDao.removeAllCategoryFromFilm(filmId);
        }

        if (selectedTags != null) {
            for (Tag t : filmTags) {
                if (!contains(selectedTags, t.getTagID())) {
                    tgDao.removeTagsFromFilm(filmId, t.getTagID());
                }
            }
            for (String t : selectedTags) {
                if (!containsTags(filmTags, Integer.parseInt(t))) {
                    tgDao.insertTags(filmId, Integer.parseInt(t));
                }
            }
        } else {
            tgDao.removeAllTagsFromFilm(filmId);
        }

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("success", true);
        responseObj.addProperty("message", "Film updated successfully");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(responseObj.toString());
    }

    private boolean contains(String[] array, int value) {
        for (String item : array) {
            if (Integer.parseInt(item) == value) {
                return true;
            }
        }
        return false;
    }

    private boolean containsCategories(List<Category> categories, int categoryId) {
        for (Category category : categories) {
            if (category.getCategoryID() == categoryId) {
                return true;
            }
        }
        return false;
    }

    private boolean containsTags(List<Tag> tags, int tagId) {
        for (Tag tag : tags) {
            if (tag.getTagID() == tagId) {
                return true;
            }
        }
        return false;
    }

}
