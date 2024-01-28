package controller;

import dal.filmDao;
import dtos.filmDtos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "homeControl", urlPatterns = "/home")
public class homeControl  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao fd = new filmDao();
        List<filmDtos> films = fd.getAllFilms();
        List<filmDtos> newFilms = fd.getNewFilms();
        req.setAttribute("films", films);
        req.setAttribute("newFilms", newFilms);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
