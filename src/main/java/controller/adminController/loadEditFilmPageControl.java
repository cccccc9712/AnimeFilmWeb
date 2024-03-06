package controller.adminController;

import dal.categoryDao;
import dal.episodeDao;
import dal.filmDao;
import dal.tagsDao;
import dtos.filmDtos;
import dtos.seasonDtos;
import entity.Category;
import entity.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "editFilmPageControl", urlPatterns = {"/editFilmPage"})
public class loadEditFilmPageControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmId = req.getParameter("filmId");
        filmDao dao = new filmDao();
        categoryDao d = new categoryDao();
        tagsDao t = new tagsDao();
        episodeDao ed = new episodeDao();

        List<Category> ct = d.getCategories();
        List<Tag> tg = t.getTags();
        filmDtos film = dao.getFilmById(Integer.parseInt(filmId));
        List<seasonDtos> seasonList = ed.getSeasonsForFilmById(Integer.parseInt(filmId));

        req.setAttribute("film", film);
        req.setAttribute("categories", ct);
        req.setAttribute("tags", tg);
        req.setAttribute("seasons", seasonList);
        req.getRequestDispatcher("/admin/editFilm.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
