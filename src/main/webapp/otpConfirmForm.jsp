<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--head--%>
<%@include file="decorator/head.jsp"%>
<body>
<%--header--%>
<%@include file="decorator/header.jsp"%>

<%--forget password form--%>
<div class="sign section--bg" data-bg="img/section/section.jpg">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="sign__content">
                    <!-- authorization form -->
                    <form action="confirm" method="post" class="sign__form">
                        <a class="sign__logo">
                            <img style="width: auto;" src="img/LOgo%20(1).png" alt="">
                        </a>

                        <div class="sign__group">
                            <input type="text" name="code" class="sign__input" placeholder="OTP Code Verification">
                        </div>

                        <div style="color: #fc4357">${errorMessage}</div>

                        <button class="sign__btn" type="submit">Submit code</button>
                        <span class="sign__text">Go back to <a href="SignIn.jsp">sign in!</a>?</span>
                    </form>
                    <!-- end authorization form -->
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="decorator/footer.jsp"%>
<%@include file="decorator/script.jsp"%>
</body>
</html>
