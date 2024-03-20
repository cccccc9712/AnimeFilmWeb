package controller;

import dal.favouriteDao;
import dal.filmDao;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

@WebServlet(name = "favouriteControl", urlPatterns = "/favourite")
public class favouriteControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao fd = new filmDao();
        PrintWriter out = new PrintWriter(System.out);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");

        //If user has not logged in, redirect to the login page
        if (user == null) {
            resp.sendRedirect("SignIn.jsp");
            return;
        }

        int userId = user.getUserId();
        int filmId = Integer.parseInt(req.getParameter("filmId"));
        String filmName = req.getParameter("filmName");
        Timestamp addedDate = new Timestamp(System.currentTimeMillis());

        try {
            favouriteDao fad = new favouriteDao();
            if (!fd.isFavouriteExists(userId,filmId)) {
                //If the film is not in the favourite of that user in Database => add to favourite of that user
                fad.addToFavorites(userId, filmId, addedDate);
                resp.sendRedirect("detail?filmName=" + filmName);
            } else {
                //If the film is in the favourite of that user in Database => remove from favourite of that user
                fad.removeFavouriteFilm(userId, filmId);
                resp.sendRedirect("detail?filmName=" + filmName);
            }
        }catch (Exception e){
            e.printStackTrace();
            out.println("Favourite Control Error");
        }
    }
}
