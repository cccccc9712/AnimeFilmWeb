package controller;

import dal.categoryDao;
import dal.tagsDao;
import entity.Category;
import entity.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "adminAddFilmControl", urlPatterns = {"/newFilmPage"})
public class adminAddFilmPageControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryDao d = new categoryDao();
        tagsDao t = new tagsDao();
        List<Category> ct = d.getCategories();
        List<Tag> tg = t.getTags();
        request.setAttribute("categories", ct);
        request.setAttribute("tags", tg);
        request.getRequestDispatcher("/admin/addNewFilms.jsp").forward(request, response);
    }
}
