package controller.adminController.seasonCRUD;

import com.google.gson.JsonObject;
import dal.episodeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "addSeasonsControl", urlPatterns = "/addSeason")
public class addSeasonsControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filmId = req.getParameter("filmId");
        String seasonName = req.getParameter("seasonName");

        episodeDao dao = new episodeDao();
        boolean sucess = dao.addSeason(Integer.parseInt(filmId), seasonName);

        JsonObject responseObj = new JsonObject();
        if (sucess) {
            responseObj.addProperty("success", true);
            responseObj.addProperty("message", "Season added successfully");
        } else {
            responseObj.addProperty("success", false);
            responseObj.addProperty("message", "Season added unsuccessfully");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(responseObj.toString());
    }
}
