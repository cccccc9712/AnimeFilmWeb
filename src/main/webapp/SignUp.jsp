<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp"%>
<body class="body">
<%@include file="decorator/header.jsp"%>
<div class="sign section--bg" data-bg="img/section/section.jpg">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="sign__content">
                    <!-- registration form -->
                    <form action="signup" method="post" class="sign__form">
                        <a href="home" class="sign__logo">
                            <img src="img/LOgo%20(1).png" alt="">
                        </a>


                        <div class="sign__group">
                            <input type="text" name="email" class="sign__input" placeholder="Email">
                        </div>

                        <div class="sign__group">
                            <input type="password" name="password" class="sign__input" placeholder="Password">
                        </div>

                        <div class="sign__group">
                            <input type="password" name="confirmPassword" class="sign__input" placeholder="Confirm Password">
                        </div>

                        <div class="sign__group sign__group--checkbox">
                            <input id="agree" name="agree" type="checkbox">
                            <label for="agree">I agree to the <a href="#">Privacy Policy</a></label>
                        </div>

                        <c:if test="${not empty errorMessage}">
                            <div style="color: red;">${errorMessage}</div>
                        </c:if>

                        <button class="sign__btn" type="submit">Sign up</button>

                        <span class="sign__text">Already have an account? <a href="SignIn.jsp">Sign in!</a></span>
                    </form>
                    <!-- registration form -->
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JS -->
<%@include file="decorator/footer.jsp"%>
<%@include file="decorator/script.jsp"%>
</body>

</html>