package controller.adminController.episodeCRUD;

import com.google.gson.JsonObject;
import dal.episodeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "updatePremiumControl", value = "/updatePremium")
public class updatePremiumControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String episodeIdStr =req.getParameter("episodeId");
        String isPremiumStr = req.getParameter("isPremium");
        if (isPremiumStr == null || episodeIdStr == null) {
            System.out.println(episodeIdStr);
            System.out.println(isPremiumStr);
            return;
        }

        int episodeId = Integer.parseInt(episodeIdStr);
        boolean isPremium = Boolean.parseBoolean(isPremiumStr);

        episodeDao dao = new episodeDao();

        boolean success = dao.updatePremium(episodeId, isPremium);

        JsonObject responseObj = new JsonObject();
        if (success) {
            responseObj.addProperty("success", true);
            if (isPremium)
                responseObj.addProperty("message", "Video is now for premium");
            else
                responseObj.addProperty("message", "Video is now for free");
        } else {
            responseObj.addProperty("success", false);
            responseObj.addProperty("message", "Episode uploaded unsuccessfully");
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write(responseObj.toString());

    }
}
