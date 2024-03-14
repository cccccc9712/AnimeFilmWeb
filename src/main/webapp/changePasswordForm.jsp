<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <form action="changePassword" method="post" class="sign__form">
                        <a class="sign__logo">
                            <img style="width: auto;" src="img/LOgo%20(1).png" alt="">
                        </a>
                        <div class="sign__group">
                            <label>
                                <input type="password" name="password" class="sign__input" placeholder="New Password" />
                            </label>
                        </div>

                        <div class="sign__group">
                            <label>
                                <input type="password" name="confirmPassword" class="sign__input" placeholder="Confirm New Password">
                            </label>
                        </div>

                        <div style="color: #fc4357">${errorMessage}</div>

                        <button class="sign__btn" type="submit">Reset password</button>
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
