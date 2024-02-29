package controller;

import dal.filmDao;
import dtos.filmDtos;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "adminDashboardControl", urlPatterns = "/adminDashboard")
public class adminDashboardControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao dao = new filmDao();
        List<filmDtos> films = dao.getAllFilms();
        req.setAttribute("films", films);
        req.getRequestDispatcher("/admin/adminDashboard.jsp").forward(req, resp);
    }
}
