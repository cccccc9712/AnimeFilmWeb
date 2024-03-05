package controller;

import dal.episodeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "addSeasonsControl", urlPatterns = "/addSeason")
public class addSeasonsControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmId = req.getParameter("filmId");
        String seasonName = req.getParameter("seasonName");

        episodeDao dao = new episodeDao();
        boolean sucess = dao.addSeason(Integer.parseInt(filmId), seasonName);

        if (sucess) {
            req.setAttribute("successMessage", "Add season successfully");
        } else {
            req.setAttribute("errorMessage", "Add season failed");
        }
        req.getRequestDispatcher("editFilmPage?filmId=" + filmId).forward(req, resp);
    }
}
