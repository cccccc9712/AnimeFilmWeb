package controller.adminController;

import dal.categoryDao;
import dal.filmDao;
import dal.userDao;
import dtos.filmDtos;
import model.Category;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminDashboardControl", urlPatterns = "/adminDashboard")
public class adminDashboardControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmDao fd = new filmDao();
        categoryDao ctDao = new categoryDao();
        userDao userDao = new userDao();

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        if (user == null || !userDao.checkUserIsAdmin(user.getUserId())) {
            resp.sendRedirect("home");
            return;
        }

        String pageStr = req.getParameter("page");
        String searchQuery = req.getParameter("searchQuery");
        String sortField = req.getParameter("sort");
        String sortOrder = req.getParameter("order");
        String category = req.getParameter("categoryName");

        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }
        int filmsPerPage = 10;

        List<filmDtos> films;
        int totalFilms;
        List<Category> ct = ctDao.getCategories();

        if (sortField == null || sortField.isEmpty() && sortOrder == null || sortOrder.isEmpty()) {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                films = fd.searchFilms(searchQuery, page, filmsPerPage);
                totalFilms = fd.getTotalFilmsBySearchQuery(searchQuery);
            } else if (category != null && !category.isEmpty() && !category.equals("all")) {
                films = fd.getFilmsByCategory(category, page, filmsPerPage);
                totalFilms = fd.getTotalFilmsByCategory(category);
            } else {
                films = fd.getFilmsPerPage(page, filmsPerPage);
                totalFilms = fd.getTotalFilms();
            }
        } else {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                films = fd.searchFilmsBySort(searchQuery, page, filmsPerPage, sortField, sortOrder);
                totalFilms = fd.getTotalFilmsBySearchQuery(searchQuery);
            } else if (category != null && !category.isEmpty() && !category.equals("all")) {
                films = fd.getFilmsByCategoryBySort(category, page, filmsPerPage, sortField, sortOrder);
                totalFilms = fd.getTotalFilmsByCategory(category);
            } else {
                films = fd.getFilmsPerPageBySort(page, filmsPerPage, sortField, sortOrder);
                totalFilms = fd.getTotalFilms();
            }
        }

        int totalPages = (int) Math.ceil(totalFilms * 1.0 / filmsPerPage);

        req.setAttribute("films", films);
        req.setAttribute("categories", ct);
        req.setAttribute("totalFilms", totalFilms);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("currentCategory", category);
        req.setAttribute("currentSearch", searchQuery);
        req.setAttribute("currentSortField", sortField);
        req.setAttribute("currentSortOrder", sortOrder);
        req.getRequestDispatcher("/admin/adminDashboard.jsp").forward(req, resp);
    }
}
