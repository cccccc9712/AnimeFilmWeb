package controller;

import dal.categoryDao;
import dal.filmDao;
import dtos.filmDtos;
import entity.Category;
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
        categoryDao ctDao = new categoryDao();
        List<Category> ct = ctDao.getCategories();

        String pageStr = req.getParameter("page");
        String category = req.getParameter("categoryName");
        String searchQuery = req.getParameter("searchQuery");
        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        int filmsPerPage = 6;
        List<filmDtos> films;
        int totalFilms;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            films = fd.searchFilms(searchQuery, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsBySearchQuery(searchQuery);
        } else if (category != null && !category.isEmpty()) {
            films = fd.getFilmsByCategory(category, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsByCategory(category);
        } else {
            films = fd.getFilmsPerPage(page, filmsPerPage);
            totalFilms = fd.getTotalFilms();
        }


        int noOfPages = (int) Math.ceil(totalFilms * 1.0 / filmsPerPage);
        req.setAttribute("films", films);
        req.setAttribute("cate", ct);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("currentSearch", searchQuery);
        req.setAttribute("currentCategory", category);
        req.getRequestDispatcher("Categories.jsp").forward(req, resp);
    }

}
