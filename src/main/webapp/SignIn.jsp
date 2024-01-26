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
                    <!-- authorization form -->
                    <form action="login" method="post" class="sign__form">
                        <a href="index.jsp" class="sign__logo">
                            <img style="width: auto;" src="img/LOgo%20(1).png" alt="">
                        </a>

                        <div class="sign__group">
                            <input type="email" name="mail" class="sign__input" placeholder="Email">
                        </div>

                        <div class="sign__group">
                            <input type="password" name="pass" class="sign__input" placeholder="Password">
                        </div>

                        <div class="sign__group sign__group--checkbox">
                            <input id="remember" name="remember" type="checkbox" checked="checked">
                            <label for="remember">Remember Me</label>
                        </div>

                        <div style="color: red">${failedLoginMessage}</div>

                        <button class="sign__btn" type="submit">Sign in</button>

                        <span class="sign__text">Don't have an account? <a href="SignUp.jsp">Sign up!</a></span>

                        <span class="sign__text"><a href="#">Forgot password?</a></span>
                    </form>

                    <!-- end authorization form -->
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