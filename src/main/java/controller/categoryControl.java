package controller;

import dal.categoryDao;
import dal.filmDao;
import dal.userDao;
import dtos.filmDtos;
import model.Category;
import model.User;
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

        //Check cookies for remember me token
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

        //Paginator
        //page default = 1
        int page = 1;
        if (pageStr != null) {
            //If there is a new page number was sent from the client, parse it to integer and assign it to page
            page = Integer.parseInt(pageStr);
        }

        //6 films on each page
        int filmsPerPage = 6;
        List<filmDtos> films;
        int totalFilms;

        //If there is a search query, get films by search query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            films = fd.searchFilms(searchQuery, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsBySearchQuery(searchQuery);
        } else if (category != null && !category.isEmpty()) {
            //If there is a category, get films by category
            films = fd.getFilmsByCategory(category, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsByCategory(category);
        } else {
            //If there is no search query or category, get all films
            films = fd.getFilmsPerPage(page, filmsPerPage);
            totalFilms = fd.getTotalFilms();
        }

        //Calculate number of pages to show in paginator
        //*1.0 to convert totalFilms to double then divide by filmsPerPage to get the number of pages
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
