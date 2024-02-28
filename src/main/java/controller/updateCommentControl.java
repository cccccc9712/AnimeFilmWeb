package controller;

import dal.commentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet(urlPatterns = "/updateComment")
public class updateCommentControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commentText = req.getParameter("commentText");
        int commentId = Integer.parseInt(req.getParameter("commentID"));
        String filmName = req.getParameter("filmName");
        int currentPage = 1;
        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
        }
        commentDao cmd = new commentDao();

        boolean edited = cmd.editComment(commentId, commentText);
        if (edited) {
            resp.sendRedirect("detail?filmName=" + filmName + "&page=" + currentPage);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to update comment.");
        }
    }
}
