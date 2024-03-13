package controller.adminController;

import dal.userDao;
import dtos.userDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "allUserPageControl", value = "/allUserPageControl")
public class allUserPageControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userDao userDao = new userDao();
        List<userDto> allUsers = userDao.getAllUsersWithPremiumStatus();
        req.setAttribute("allUsers", allUsers);
        req.getRequestDispatcher("admin/allUsersAdminDashboard.jsp").forward(req, resp);
    }
}
