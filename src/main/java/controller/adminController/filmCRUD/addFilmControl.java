package controller.adminController.filmCRUD;

import dal.categoryDao;
import dal.filmDao;
import dal.tagsDao;
import model.Film;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

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

        filmDao dao = new filmDao();
        categoryDao categoryDao = new categoryDao();
        tagsDao tagsDao = new tagsDao();
        Film film = new Film();

        if (name.isBlank() || trailerLink.isBlank() || description.isBlank()) {
            req.setAttribute("errorMessage", "Please input to continue!");
            req.getRequestDispatcher("newFilmPage").forward(req, resp);
            return;
        }

        if (fileName != null && !fileName.isBlank() && filePart.getSize() > 0) {
            String savePath = getServletContext().getRealPath("/img/thumbnailUpload") + File.separator + fileName;
            filePart.write(savePath + File.separator);
            String thumbnailPath = "img/thumbnailUpload/" + fileName;
            film.setImageLink(thumbnailPath);
        }else{
            req.setAttribute("errorMessage", "Please input to continue!");
            req.getRequestDispatcher("newFilmPage").forward(req, resp);
            return;
        }

        film.setFilmName(name);
        film.setTrailerLink(trailerLink);
        film.setDescription(description);
        film.setFilmReleaseDate(releaseDate);
        film.setViewCount(0L);

        boolean success = dao.addFilm(film);
        int filmId = film.getFilmID();
        if (success) {
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
            req.setAttribute("successMessage", "Film added successfully");
            req.getRequestDispatcher("newFilmPage").forward(req, resp);
        } else {
            System.out.println("Film added failed");
            req.setAttribute("errorMessage", "Film added failed");
            req.getRequestDispatcher("newFilmPage").forward(req, resp);
        }
    }
}