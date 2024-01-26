<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- header -->
<header class="header">
    <div class="header__wrap">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="header__content">
                        <!-- header logo -->
                        <a href="index.jsp" class="">
                            <img style="width: auto; height: 90px; border-radius: 10px" src="img/LOgo%20(1).png" alt="">
                        </a>
                        <!-- end header logo -->

                        <!-- header nav -->
                        <ul class="header__nav">
                            <!-- dropdown -->

                            <!-- end dropdown -->

                            <!-- dropdown -->
                            <li class="header__nav-item">
                                <a class="dropdown-toggle header__nav-link" href="Catagories.jsp" role="button"
                                    >Categories</a>

<%--                                <ul class="dropdown-menu header__dropdown-menu"--%>
<%--                                    aria-labelledby="dropdownMenuCatalog">--%>
<%--                                    <li><a href="Catagories.jsp">Catalog List</a></li>--%>
<%--                                </ul>--%>
                            </li>
                            <!-- end dropdown -->

                            <li class="header__nav-item">
                                <a href="pricing.jsp" class="header__nav-link">Pricing Plan</a>
                            </li>

                            <li class="header__nav-item">
                                <a href="About.jsp" class="header__nav-link">About us</a>
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

                        <!-- header auth -->
                        <div class="header__auth">
                            <button class="header__search-btn" type="button">
                                <i class="icon ion-ios-search"></i>
                            </button>

                            <a href="SignIn.jsp" class="header__sign-in">
                                <i class="icon ion-ios-log-in"></i>
                                <span>sign in</span>
                            </a>
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
    <form action="#" class="header__search">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="header__search-content">
                        <input type="text" placeholder="Search for a movie, TV Series that you are looking for">

                        <button type="button">search</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <!-- end header search -->
</header>
<!-- end header -->