package controller;

import dal.episodeDAO;
import dal.filmDao;
import dtos.filmDtos;
import dtos.seasonDtos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "filmDetailedControl", urlPatterns = "/detail")
public class filmDetailedControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmName = req.getParameter("filmName");
        filmDao fd = new filmDao();
        episodeDAO ed = new episodeDAO();
        filmDtos film = fd.getFilmByName(filmName);
        List<seasonDtos> seasonList = ed.getSeasonsForFilm(filmName);
        req.setAttribute("film", film);
        req.setAttribute("season", seasonList);
        req.getRequestDispatcher("SeriesDetailed.jsp").forward(req, resp);
    }

}
