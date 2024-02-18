package controller;

import dal.commentDao;
import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "commentsControl", urlPatterns = "/comment")
public class commentsControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter("filmId"));
        String filmName = req.getParameter("filmName");
        String commentText = req.getParameter("commentText");
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        String parentCommentIDStr = req.getParameter("parentCommentID");
        Integer parentCommentID = null;
        if (parentCommentIDStr != null && !parentCommentIDStr.isEmpty()) {
            parentCommentID = Integer.parseInt(parentCommentIDStr);
        }

        commentDao cmd = new commentDao();
        cmd.insertComment(filmId, userId, commentText, parentCommentID);

        resp.sendRedirect("detail?filmName=" + filmName);
    }
}
