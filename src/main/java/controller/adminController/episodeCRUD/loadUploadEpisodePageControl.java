package controller.adminController.episodeCRUD;

import dal.episodeDao;
import dal.userDao;
import entity.Episode;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "loadUploadEpisodePageControl", value = "/loadEpisodePage")
public class loadUploadEpisodePageControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seasonId = req.getParameter("seasonId");
        episodeDao episodeDao = new episodeDao();

        userDao userDao = new userDao();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        if (user == null || !userDao.checkUserIsAdmin(user.getUserId())) {
            resp.sendRedirect("home");
            return;
        }

        List<Episode> episodes = episodeDao.getEpisodesForSeason(Integer.parseInt(seasonId));
        req.setAttribute("episodes", episodes);
        req.setAttribute("seasonId", seasonId);
        req.getRequestDispatcher("/admin/uploadEpisode.jsp").forward(req, resp);
    }
}
