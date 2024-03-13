package controller.adminController.episodeCRUD;

import dal.episodeDao;
import model.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "loadUploadEpisodePageControl", value = "/loadEpisodePage")
public class loadUploadEpisodePageControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seasonId = req.getParameter("seasonId");
        episodeDao episodeDao = new episodeDao();
        List<Episode> episodes = episodeDao.getEpisodesForSeason(Integer.parseInt(seasonId));
        req.setAttribute("episodes", episodes);
        req.setAttribute("seasonId", seasonId);
        req.getRequestDispatcher("/admin/uploadEpisode.jsp").forward(req, resp);
    }
}
