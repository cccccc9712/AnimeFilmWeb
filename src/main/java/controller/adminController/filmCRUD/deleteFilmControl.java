package controller.adminController.filmCRUD;

import dal.filmDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "deleteFilmControl", urlPatterns = {"/deleteFilm"})
public class deleteFilmControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao dao = new filmDao();
        int filmId = Integer.parseInt(req.getParameter("filmId"));
        dao.deleteFilm(filmId);
        resp.sendRedirect("adminDashboard");
    }
}
