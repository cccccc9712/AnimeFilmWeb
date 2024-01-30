//package filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//@WebFilter(urlPatterns = {"/index.jsp", "/header.jsp", "/footer.jsp"})
//class homePageFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String path = request.getServletPath();
//
//        if (path.equals("/index.jsp") || path.equals("/header.jsp") || path.equals("/footer.jsp")) {
//            response.sendRedirect("/home");
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
//}
