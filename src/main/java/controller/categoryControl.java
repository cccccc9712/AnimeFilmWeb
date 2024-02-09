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
        String category = req.getParameter("categoryName"); // Thêm dòng này để nhận category từ request
        int page = 1;
        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        int filmsPerPage = 6;
        List<filmDtos> films;
        int totalFilms;

        if (category != null && !category.isEmpty()) {
            // Lọc phim theo danh mục nếu có tham số category
            films = fd.getFilmsByCategory(category, page, filmsPerPage);
            totalFilms = fd.getTotalFilmsByCategory(category); // Bạn cần viết hàm này trong filmDao
        } else {
            // Lấy tất cả phim nếu không có tham số category
            films = fd.getFilmsPerPage(page, filmsPerPage);
            totalFilms = fd.getTotalFilms();
        }

        int noOfPages = (int) Math.ceil(totalFilms * 1.0 / filmsPerPage);
        req.setAttribute("films", films);
        req.setAttribute("cate", ct);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("currentCategory", category); // Gửi danh mục hiện tại trở lại JSP để hiển thị
        req.getRequestDispatcher("Categories.jsp").forward(req, resp);
    }

}
