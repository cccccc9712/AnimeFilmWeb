package controller.adminController;

import dal.categoryDao;
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
        filmDao fd = new filmDao();

        String pageStr = req.getParameter("page");
        String searchQuery = req.getParameter("searchQuery");

        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int filmsPerPage = 10;

        List<filmDtos> films;
        int totalFilms;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            films = fd.searchFilms(searchQuery, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsBySearchQuery(searchQuery);
        } else {
            films = fd.getFilmsPerPage(page, filmsPerPage);
            totalFilms = fd.getTotalFilms();
        }

        int totalPages = (int) Math.ceil(totalFilms * 1.0 / filmsPerPage);

        req.setAttribute("films", films);
        req.setAttribute("totalFilms", totalFilms);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("currentSearch", searchQuery);
        req.getRequestDispatcher("/admin/adminDashboard.jsp").forward(req, resp);
    }
}
