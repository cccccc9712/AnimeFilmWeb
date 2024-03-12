package controller.adminController.seasonCRUD;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.episodeDao;

import java.io.IOException;

@WebServlet(name = "editSeasonNameControl", value = "/editSeasonName")
public class editSeasonNameControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seasonIdStr = req.getParameter("seasonId");
        String filmId = req.getParameter("filmId");
        String seasonName = req.getParameter("seasonName");
        if (seasonIdStr == null || seasonName == null) {
            return;
        }

        System.out.println("seasonId: " + seasonIdStr);
        System.out.println("seasonName: " + seasonName);
        System.out.println("filmId: " + filmId);

        episodeDao ed = new episodeDao();
        int seasonId = Integer.parseInt(seasonIdStr);
        boolean success = ed.updateSeasonName(seasonId, seasonName);

        JsonObject responseObj = new JsonObject();
        if (success) {
            responseObj.addProperty("success", true);
            responseObj.addProperty("message", "Season name updated successfully");
        } else {
            responseObj.addProperty("success", false);
            responseObj.addProperty("message", "Season name updated unsuccessfully");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(responseObj.toString());
    }
}
