package controller;

import model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class filterServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute("userSession");
        if (path.matches(".*(vnpay_pay).*")) {
            if (user == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
            }
        }

        if (!path.endsWith(".jsp")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.matches(".*(footer|head|script|header|index)\\.jsp")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
        } else{
                String newPath = path.replaceAll("\\.jsp$", "");
                httpResponse.sendRedirect(httpRequest.getContextPath() + newPath);
            }
        }

        @Override
        public void destroy () {
            Filter.super.destroy();
        }
    }
