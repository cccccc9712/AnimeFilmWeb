package controller;

import dal.filmDao;
import dtos.filmDtos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "filmDetailedControl", urlPatterns = "/detail")
public class filmDetailedControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmName = req.getParameter("filmName");
        filmDao fd = new filmDao();
        filmDtos film = fd.getFilmByName(filmName);
        req.setAttribute("film", film);
        req.getRequestDispatcher("SeriesDetailed.jsp").forward(req, resp);
    }

}
