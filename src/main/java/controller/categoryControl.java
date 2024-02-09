package controller;

import dal.filmDao;
import dtos.filmDtos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "categoryControl", urlPatterns = "/category")
public class categoryControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao fd = new filmDao();
        List<filmDtos> films = fd.getAllFilms();
        req.setAttribute("films", films);
        req.getRequestDispatcher("Catagories.jsp").forward(req, resp);
    }
}
