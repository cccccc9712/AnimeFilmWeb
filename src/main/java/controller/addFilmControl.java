package controller;

import dal.categoryDao;
import dal.filmDao;
import dal.tagsDao;
import entity.Category;
import entity.Film;
import entity.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@WebServlet(name = "addFilmControl", urlPatterns = {"/addFilm"})
@MultipartConfig(location = "C:\\Users\\tmtmt\\IdeaProjects\\AnimeFilmWeb\\src\\main\\webapp\\img\\thumbnailUpload")
public class addFilmControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("thumbnail");
        String name = req.getParameter("name");
        String trailerLink = req.getParameter("trailerLink");
        String[] categories = req.getParameterValues("categories");
        String[] tags = req.getParameterValues("tags");
        String description = req.getParameter("description");
        String fileName = filePart.getSubmittedFileName();
        Timestamp releaseDate = new Timestamp(new Date().getTime());

        String savePath = getServletContext().getRealPath("/img/thumbnailUpload") + File.separator + fileName;
        File fileSaveDir = new File(savePath);
        filePart.write(savePath + File.separator);

        String thumbnailPath = "img/thumbnailUpload/" + fileName;

        filmDao dao = new filmDao();
        categoryDao categoryDao = new categoryDao();
        tagsDao tagsDao = new tagsDao();
        Film film = new Film();
        film.setFilmName(name);
        film.setFilmTrailerLink(trailerLink);
        film.setFilmDescription(description);
        film.setFilmReleaseDate(releaseDate);
        film.setFilmImgLink(thumbnailPath);
        film.setFilmViewCount(0L);

        categoryDao d = new categoryDao();
        tagsDao t = new tagsDao();
        List<Category> ct = d.getCategories();
        List<Tag> tg = t.getTags();


        // Add film to database
        boolean sucess = dao.addFilm(film);
        int filmId = film.getFilmID();
        if (sucess) {
            if (categories != null) {
                for (String categoryId : categories) {
                    categoryDao.insertCategory(filmId, Integer.parseInt(categoryId));
                }
            }
            if (tags != null) {
                for (String tagId : tags) {
                    tagsDao.insertTags(filmId, Integer.parseInt(tagId));
                }
            }
            req.setAttribute("categories", ct);
            req.setAttribute("tags", tg);
            req.setAttribute("successMessage", "Film added successfully");
            req.getRequestDispatcher("/admin/addNewFilms.jsp").forward(req, resp);
        } else {
            System.out.println("Film added failed");
            req.setAttribute("errorMessage", "Film added failed");
            req.getRequestDispatcher("/admin/addNewFilms.jsp").forward(req, resp);
        }
    }
}
