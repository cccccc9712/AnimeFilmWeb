<%@ page import="entity.User" %>
<%@ page import="dal.userDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp"%>
<body class="body">
<!-- end header -->
<%@include file="decorator/header.jsp" %>
<!-- page title -->
<section class="section section--first section--bg" data-bg="img/section/section.jpg">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="section__wrap">
                    <!-- section title -->
                    <h2 class="section__title">Pricing</h2>
                    <!-- end section title -->

                    <!-- breadcrumb -->
                    <ul class="breadcrumb">
                        <li class="breadcrumb__item"><a href="home">Home</a></li>
                        <li class="breadcrumb__item breadcrumb__item--active">Pricing</li>
                    </ul>
                    <!-- end breadcrumb -->
                </div>
            </div>
        </div>
    </div>
</section>
<!-- end page title -->

<!-- pricing -->
<div class="section">
    <div class="container">
        <div class="row">
            <!-- plan features -->
            <div class="col-12">
                <ul class="row plan-features">
                    <li class="col-12 col-md-6 col-lg-4">1 month unlimited access!</li>
                    <li class="col-12 col-md-6 col-lg-4">Stream on your phone, laptop, tablet or TV.</li>
                    <li class="col-12 col-md-6 col-lg-4">You can watch a lot of movies.</li>
                    <li class="col-12 col-md-6 col-lg-4">Up-to-date new episodes immediately.</li>
                    <li class="col-12 col-md-6 col-lg-4">You can even Download & watch offline.</li>
                    <li class="col-12 col-md-6 col-lg-4">Thousands of TV shows, movies & more.</li>
                </ul>
            </div>
            <!-- end plan features -->

            <!-- price -->
            <div class="col-12 col-md-6">
                <div class="price">
                    <div class="price__item price__item--first"><span>Basic</span> <span>Free</span></div>
                    <div class="price__item"><span>7 days</span></div>
                    <div class="price__item"><span>480p Resolution</span></div>
                    <div class="price__item"><span>Limited Availability</span></div>
                    <div class="price__item"><span>Desktop Only</span></div>
                    <div class="price__item"><span>Limited Support</span></div>
                    <a style="color: whitesmoke" class="price__btn">Free</a>
                </div>
            </div>
            <!-- end price -->

            <%
                userDao ud = new userDao();
                if (user != null) {
                    Boolean isPremium = ud.checkUserPremiumStatus(user.getUserId());
                    request.setAttribute("isPremiumOrAdmin", isPremium || isAdmin);
                } %>

            <!-- price -->
            <div class="col-12 col-md-6">
                <div class="price price--premium">
                    <div class="price__item price__item--first"><span>Premium</span> <span>20.000 vnđ</span></div>
                    <div class="price__item"><span>1 Month</span></div>
                    <div class="price__item"><span>Full HD</span></div>
                    <div class="price__item"><span>Lifetime Availability</span></div>
                    <div class="price__item"><span>TV & Desktop</span></div>
                    <div class="price__item"><span>24/7 Support</span></div>
                    <c:choose>
                        <c:when test="${sessionScope.userSession != null}">
                            <c:choose>
                                <c:when test="${not isPremiumOrAdmin}">
                                    <a href="vnpay_pay.jsp" class="price__btn" id="registerButton">Register</a>
                                </c:when>
                                <c:otherwise>
                                    <a style="color: #FFD700" href="home" class="price__btn">You are already in premium <i style="margin: 5px;" class="fas fa-crown"></i></a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <a href="SignIn.jsp" class="price__btn">Register</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end pricing -->

<div style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
    <c:if test="${not empty message}">
        <div style="color: #fc4357;">${message}</div>
    </c:if>
</div>

<!-- features -->
<section class="section section--dark">
    <div class="container">
        <div class="row">
            <!-- section title -->
            <div class="col-12">
                <h2 class="section__title section__title--no-margin">Our Features</h2>
            </div>
            <!-- end section title -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-tv feature__icon"></i>
                    <h3 class="feature__title">Ultra HD</h3>
                    <p class="feature__text">If you are going to use a passage of Lorem Ipsum, you need to be sure there
                        isn't anything embarrassing hidden in the middle of text.</p>
                </div>
            </div>
            <!-- end feature -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-film feature__icon"></i>
                    <h3 class="feature__title">Film</h3>
                    <p class="feature__text">All the Lorem Ipsum generators on the Internet tend to repeat predefined
                        chunks as necessary, making this the first.</p>
                </div>
            </div>
            <!-- end feature -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-trophy feature__icon"></i>
                    <h3 class="feature__title">Awards</h3>
                    <p class="feature__text">It to make a type specimen book. It has survived not only five centuries,
                        but also the leap into electronic typesetting, remaining.</p>
                </div>
            </div>
            <!-- end feature -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-notifications feature__icon"></i>
                    <h3 class="feature__title">Notifications</h3>
                    <p class="feature__text">Various versions have evolved over the years, sometimes by accident,
                        sometimes on purpose.</p>
                </div>
            </div>
            <!-- end feature -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-rocket feature__icon"></i>
                    <h3 class="feature__title">Rocket</h3>
                    <p class="feature__text">It to make a type specimen book. It has survived not only five centuries,
                        but also the leap into electronic typesetting.</p>
                </div>
            </div>
            <!-- end feature -->

            <!-- feature -->
            <div class="col-12 col-md-6 col-lg-4">
                <div class="feature">
                    <i class="icon ion-ios-globe feature__icon"></i>
                    <h3 class="feature__title">Multi Language Subtitles </h3>
                    <p class="feature__text">Various versions have evolved over the years, sometimes by accident,
                        sometimes on purpose.</p>
                </div>
            </div>
            <!-- end feature -->
        </div>
    </div>
</section>
<!-- end features -->

<!-- partners -->

<!-- end partners -->

<!-- footer -->

<!-- end footer -->
<%@include file="decorator/footer.jsp" %>
<%@include file="decorator/script.jsp" %>
<!-- JS -->

</body>
<script>
    // document.addEventListener("DOMContentLoaded", function () {
    //     var registerButton = document.getElementById("registerButton");
    //
    //     registerButton.addEventListener("click", function (event) {
    //         event.preventDefault(); // Ngăn chặn hành động mặc định của nút "Register"
    //         // Tạo đối tượng XMLHttpRequest
    //         var xhr = new XMLHttpRequest();
    //
    //         // Xác định phương thức và URL của servlet ajaxServlet
    //         xhr.open("POST", "/AnimeFilmWeb/vnpayajax", true);
    //
    //         // Thiết lập tiêu đề của yêu cầu
    //         xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    //
    //         // Xử lý khi nhận được phản hồi từ servlet
    //         xhr.onreadystatechange = function () {
    //             if (xhr.readyState === 4 && xhr.status === 200) {
    //                 var response = JSON.parse(xhr.responseText);
    //                 if (response.code === "00") {
    //                     window.location.href = response.data;
    //                 } else {
    //                     alert("Error: " + response.message);
    //                 }
    //             }
    //         };
    //
    //         xhr.send();
    //     });
    // });
</script>

</html>
