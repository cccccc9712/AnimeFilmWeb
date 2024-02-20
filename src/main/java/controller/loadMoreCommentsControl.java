package controller;

import dal.commentDao;
import dtos.commentDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "loadMoreCommentsControl", urlPatterns = "/loadMoreReplies")
public class loadMoreCommentsControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commentId = req.getParameter("commentId");
        // Giả sử bạn đã có phương thức để lấy các replies mới dựa trên commentId
        commentDao cmd = new commentDao();
        List<commentDto> replies = cmd.getRepliesByCommentId(Integer.parseInt(commentId));

        // Tạo HTML cho các replies mới
        StringBuilder replyHtml = new StringBuilder();
        for (commentDto reply : replies) {
            replyHtml.append("<li class=\"comments__item comments__item--answer\">")
                    .append("<div class=\"comments__autor\">")
                    .append("<img class=\"comments__avatar\" src=\"img/user.png\" alt=\"\">")
                    .append("<span class=\"comments__name\">").append(reply.getUserName()).append("</span>")
                    .append("<span class=\"comments__time\">").append(reply.getCommentDate()).append("</span>")
                    .append("</div>")
                    .append("<p class=\"comments__text\">").append(reply.getCommentText()).append("</p>")
                    .append("</li>");
        }

        // Gửi HTML về cho client
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(replyHtml.toString());
    }
}
