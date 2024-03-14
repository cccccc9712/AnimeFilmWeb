package controller.adminController.filmCRUD;

import dal.*;
import dtos.filmDtos;
import dtos.seasonDtos;
import entity.Category;
import entity.Tag;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        userDao userDao = new userDao();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        if (user == null || !userDao.checkUserIsAdmin(user.getUserId())) {
            resp.sendRedirect("home");
            return;
        }

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
