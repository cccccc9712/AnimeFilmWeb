package controller;

import dal.categoryDao;
import dal.filmDao;
import dal.userDao;
import dtos.filmDtos;
import entity.Category;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

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

        HttpSession session = req.getSession(false);
        Integer userId = null;

        if (session != null) {
            userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                Cookie[] cookies = req.getCookies();
                String rememberMeToken = null;
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("rememberMe".equals(cookie.getName())) {
                            rememberMeToken = cookie.getValue();
                            break;
                        }
                    }

                    if (rememberMeToken != null) {
                        userDao dao = new userDao();
                        User user = dao.getUserByRememberMeToken(rememberMeToken);
                        if (user != null) {
                            session = req.getSession(true);
                            session.setAttribute("userId", user.getUserId());
                            session.setAttribute("userSession", user);
                        }
                    }
                }
            }
        }

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
