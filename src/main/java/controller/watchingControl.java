package controller;

import dal.episodeDAO;
import dal.filmDao;
import dtos.episodeDtos;
import dtos.filmDtos;
import entity.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "watchingControl", urlPatterns = "/watching")
public class watchingControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String episodeIdStr = req.getParameter("episodeId");
        int filmId = Integer.parseInt(req.getParameter("filmId"));
        if (episodeIdStr != null && !episodeIdStr.isEmpty()) {
            int episodeId = Integer.parseInt(episodeIdStr);
            episodeDAO fd = new episodeDAO();
            episodeDtos episode = fd.getEpisodeById(episodeId);
            fd.increaseViewCount(filmId);
            req.setAttribute("episode", episode);
            req.getRequestDispatcher("Watching.jsp").forward(req, resp);
        }
    }
}
