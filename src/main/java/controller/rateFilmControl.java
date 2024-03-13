package controller;

import dal.ratingDao;
import entity.Rating;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "rateFilmControl", urlPatterns = "/rateFilm")
public class rateFilmControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        int filmId = Integer.parseInt(req.getParameter("filmId"));
        String filmName = req.getParameter("filmName");
        String rating = req.getParameter("rate");

        if (rating == null || rating.trim().isEmpty()){
            req.setAttribute("mess", "Please choose stars to rate!");
            req.getRequestDispatcher("detail?filmName=" + filmName).forward(req, resp);
            return;
        }

        float ratingValue = Float.parseFloat(rating);
        ratingDao rd = new ratingDao();
        Rating existingRating = rd.checkRatingExisted(userId, filmId);

        if (existingRating != null) {
            // Update existing rating
            existingRating.setRatingValue(ratingValue);
            rd.updateRating(existingRating);
            req.setAttribute("mess", "Your rating has been submitted successfully!");
        } else {
            // Insert new rating
            Rating newRating = new Rating(0, filmId, userId, ratingValue);
            rd.rate(newRating);
            req.setAttribute("mess", "Your rating has been submitted successfully!");
        }

        resp.sendRedirect("detail?filmName=" + filmName);

    }
}
