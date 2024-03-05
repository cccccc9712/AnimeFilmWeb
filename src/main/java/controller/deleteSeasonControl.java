package controller;

import dal.episodeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "deleteSeasonControl", urlPatterns = "/deleteSeason")
public class deleteSeasonControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seasonId = req.getParameter("seasonId");
        String filmId = req.getParameter("filmId");

        episodeDao dao = new episodeDao();
        boolean sucess = dao.deleteSeason(Integer.parseInt(seasonId));
         if (sucess) {
            req.setAttribute("successMessage", "Delete season successfully");
        } else {
            req.setAttribute("errorMessage", "Delete season failed");
         }

        req.getRequestDispatcher("editFilmPage?filmId=" + filmId).forward(req, resp);
    }
}
