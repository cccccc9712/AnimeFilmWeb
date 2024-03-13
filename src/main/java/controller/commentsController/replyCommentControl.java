package controller.commentsController;

import dal.commentDao;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "replyCommentControl", urlPatterns = "/reply")
public class replyCommentControl extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String replyText = req.getParameter("replyText");
        int parentCommentID = Integer.parseInt(req.getParameter("parentCommentID"));
        int filmID = Integer.parseInt(req.getParameter("filmID"));
        String filmName = req.getParameter("filmName");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userSession");
        int userId = user.getUserId();
        PrintWriter out = new PrintWriter(System.out);

        commentDao cmd = new commentDao();
        cmd.saveReply(replyText, userId, filmID, parentCommentID);

        resp.sendRedirect("detail?filmName=" + filmName);
    }
}
