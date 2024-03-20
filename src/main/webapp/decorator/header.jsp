<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- header -->
<style>

    @media (max-width: 768px) {
        .header__logo img {
            max-height: 50px;
        }
        .header__content a{
            padding: 0px;
        }
    }

</style>
<header class="header">
    <div class="header__wrap">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="header__content">
                        <!-- header logo -->
                        <a href="home" class="header__logo">
                            <img style="width: auto; height: 90px; border-radius: 10px" src="img/LOgo%20(1).png" alt="">
                        </a>
                        <!-- end header logo -->

                        <!-- header nav -->
                        <ul class="header__nav">
                            <!-- dropdown -->
                            <li class="header__nav-item">
                                <a class="dropdown-toggle header__nav-link" href="category" role="button"
                                    >All films</a>
<%--                                <ul class="dropdown-menu header__dropdown-menu"--%>
<%--                                    aria-labelledby="dropdownMenuCatalog">--%>
<%--                                    <li><a href="Categories.jsp">Catalog List</a></li>--%>
<%--                                </ul>--%>
                            </li>
                            <!-- end dropdown -->

                            <li class="header__nav-item">
                                <a href="pricing.jsp" class="header__nav-link">Pricing Plan</a>
                            </li>

                            <li class="header__nav-item">
                                <a href="about.jsp" class="header__nav-link">About us</a>
                            </li>

                            <!-- dropdown -->
<%--                            <li class="dropdown header__nav-item">--%>
<%--                                <a class="dropdown-toggle header__nav-link header__nav-link--more" href="#"--%>
<%--                                    role="button" id="dropdownMenuMore" data-toggle="dropdown" aria-haspopup="true"--%>
<%--                                    aria-expanded="false"><i class="icon ion-ios-more"></i></a>--%>

<%--                                <ul class="dropdown-menu header__dropdown-menu" aria-labelledby="dropdownMenuMore">--%>
<%--                                    <li><a href="SignIn.jsp">Sign In</a></li>--%>
<%--                                    <li><a href="SignUp.jsp">Sign Up</a></li>--%>
<%--                                </ul>--%>
<%--                            </li>--%>
                            <!-- end dropdown -->

                        </ul>
                        <!-- end header nav -->

                        <%
                            User user = (User) session.getAttribute("userSession");
                            Boolean isAdmin = false;
                            if(user != null){
                                isAdmin = user.getAdmin();
                                request.setAttribute("isAdmin", isAdmin);
                            }
                        %>

                        <!-- header auth -->
                        <div class="header__auth">
                            <button class="header__search-btn" type="button">
                                <i class="icon ion-ios-search"></i>
                            </button>

                            <c:choose>
                                <c:when test="${sessionScope.userSession != null}">
                                    <li class="dropdown header__nav-item">
                                        <a class="dropdown-toggle header__nav-link" style="margin-left: 40px" href="#" role="button" id="dropdownUser" data-toggle="dropdown"><i class="fas fa-user" style="color: #ffffff;margin-right: 10px"></i>${sessionScope.userSession.getUserName()}</a>
                                        <ul class="dropdown-menu header__dropdown-menu" aria-labelledby="dropdownUser">
                                            <c:choose>
                                                <c:when test="${not isAdmin}">
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="adminDashboard">Dashboard</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                                        </ul>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <a href="SignIn.jsp" class="header__sign-in">
                                        <i class="icon ion-ios-log-in"></i>
                                        <span>sign in</span>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- end header auth -->

                        <!-- header menu btn -->
                        <button class="header__btn" type="button">
                            <span></span>
                            <span></span>
                            <span></span>
                        </button>
                        <!-- end header menu btn -->
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- header search -->
    <form action="category" method="GET" class="header__search">
    <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="header__search-content">
                        <input type="text" placeholder="Search for an anime, movies that you are looking for" name="searchQuery">

                        <button type="submit">search</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <!-- end header search -->
</header>
<!-- end header -->