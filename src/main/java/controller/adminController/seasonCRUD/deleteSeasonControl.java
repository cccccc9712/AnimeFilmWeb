package controller.adminController.seasonCRUD;

import com.google.gson.JsonObject;
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
        JsonObject responseObj = new JsonObject();
        boolean sucess = dao.deleteSeason(Integer.parseInt(seasonId));
        if (sucess) {
            responseObj.addProperty("success", true);
            responseObj.addProperty("message", "Season deleted successfully");
        } else {
            responseObj.addProperty("success", false);
            responseObj.addProperty("message", "Season deleted unsuccessfully");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(responseObj.toString());
    }
}
