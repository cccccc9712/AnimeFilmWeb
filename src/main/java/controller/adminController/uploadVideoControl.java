package controller.adminController;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;

import java.io.IOException;
import java.io.InputStream;

import com.google.gson.JsonObject;
import dal.episodeDao;
import dal.filmDao;
import entity.Film;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;

@WebServlet(name = "uploadVideoControl", value = "/uploadVideo")
public class uploadVideoControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seasonId = req.getParameter("seasonId");
        String episodeName = req.getParameter("episodeName");
        Boolean isPremium = Boolean.parseBoolean(req.getParameter("isPremium"));
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        Timestamp uploadTime = new Timestamp(currentDate.getTime());
        if (episodeName == null || episodeName.isBlank()) {
            System.out.println("Episode name is null or empty");
            return;
        }
        String videoUrl = req.getParameter("videoUrl");

        boolean success = false;

        // Lưu URL vào DB tại đây
        episodeDao dao = new episodeDao();
        success = dao.addEpisode(Integer.parseInt(seasonId), episodeName, uploadTime, isPremium, videoUrl);

        JsonObject responseObj = new JsonObject();
        if (success) {
            responseObj.addProperty("success", true);
            responseObj.addProperty("message", "Episode uploaded successfully");
        } else {
            responseObj.addProperty("success", false);
            responseObj.addProperty("message", "Episode uploaded unsuccessfully");
        }

        // Setting up the response type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Sending the response back to the client
        resp.getWriter().write(responseObj.toString());
    }
}
