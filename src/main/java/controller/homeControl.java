package controller;

import dal.filmDao;
import dtos.filmDtos;
import dtos.newestEpisodeDto;
import entity.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "homeControl", urlPatterns = "/home")
public class homeControl  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao fd = new filmDao();
        List<filmDtos> trendingFilms = fd.getFilmWithHighestViewCount();
        List<filmDtos> newFilms = fd.getNewFilms();
        List<newestEpisodeDto> latestEpisodes = fd.getLatestEpisodes();
        req.setAttribute("latestEpisodes", latestEpisodes);
        req.setAttribute("films", trendingFilms);
        req.setAttribute("newFilms", newFilms);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
