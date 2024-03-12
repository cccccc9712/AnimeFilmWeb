<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<%@include file="decorator/head.jsp" %>
<body class="body">

<!-- page 404 -->
<div class="page-404 section--bg" data-bg="img/section/section.jpg">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="page-404__wrap">
                    <div class="page-404__content">
                        <h1 class="page-404__title">404</h1>
                        <p class="page-404__text">The page you are looking for not available!</p>
                        <a href="home" class="page-404__btn">go back</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end page 404 -->

<%@include file="decorator/script.jsp" %>
</body>
</html>