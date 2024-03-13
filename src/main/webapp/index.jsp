<%@ page import="dtos.filmDtos" %>
<%@ page import="java.util.List" %>
<%@ page import="dal.filmDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp" %>
<style>
</style>
<body class="body">
<%--Header--%>
<%@include file="decorator/header.jsp" %>
<!-- home -->
<section class="home home--bg">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1 class="home__title"><b>NEW ANIME</b></h1>

                <button class="home__nav home__nav--prev" type="button">
                    <i class="icon ion-ios-arrow-round-back"></i>
                </button>
                <button class="home__nav home__nav--next" type="button">
                    <i class="icon ion-ios-arrow-round-forward"></i>
                </button>
            </div>

            <div class="col-12">
                <div class="owl-carousel home__carousel">
                    <c:forEach items="${newFilms}" var="newFilm">
                        <div class="item">
                            <!-- card -->
                            <div class="card card--big">
                                <div class="card__cover">
                                    <img id="newFilm" src="${newFilm.imageLink}" alt="${newFilm.filmName}">
                                    <a href="detail?filmName=${newFilm.filmName}" class="card__play">
                                        <i class="icon ion-ios-play"></i>
                                    </a>
                                </div>
                                <div class="card__content">
                                    <h3 class="card__title"><a
                                            href="detail?filmName=${newFilm.filmName}">${newFilm.filmName}</a></h3>
                                    <span class="card__category">
                                    <c:forEach items="${newFilm.categories}" var="category">
                                        <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                                    </c:forEach>
                                </span>
                                    <span class="card__rate">
                                                     <i class="icon ion-ios-star"></i>${newFilm.ratingValue}
                                        <i style="margin-left: 10px" class="icon ion-ios-eye"></i>${newFilm.viewCount}
                                                </span>
                                </div>
                            </div>
                            <!-- end card -->
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- end home -->

<!-- content -->
<section class="content">
    <div class="content__head">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <!-- content title -->
                    <h2 class="content__title">Item tabs</h2>
                    <!-- end content title -->

                    <!-- content tabs nav -->
                    <ul class="nav nav-tabs content__tabs" id="content__tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#tab-1" role="tab"
                               aria-controls="tab-1" aria-selected="true">New Episode</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tab-2" role="tab"
                               aria-controls="tab-1" aria-selected="true">Trending</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tab-3" role="tab" aria-controls="tab-2"
                               aria-selected="false">Favourite</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#tab-4" role="tab" aria-controls="tab-3"
                               aria-selected="false">Just Watched</a>
                        </li>

                    </ul>
                    <!-- end content tabs nav -->

                    <!-- content mobile tabs nav -->
                    <div class="content__mobile-tabs" id="content__mobile-tabs">
                        <div class="content__mobile-tabs-btn dropdown-toggle" role="navigation" id="mobile-tabs"
                             data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <input type="button" value="New items">
                            <span></span>
                        </div>

                        <div class="content__mobile-tabs-menu dropdown-menu" aria-labelledby="mobile-tabs">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item"><a class="nav-link active" id="1-tab" data-toggle="tab"
                                                        href="#tab-1" role="tab" aria-controls="tab-1"
                                                        aria-selected="true">New Episode</a></li>

                                <li class="nav-item"><a class="nav-link" id="2-tab" data-toggle="tab" href="#tab-2"
                                                        role="tab" aria-controls="tab-2"
                                                        aria-selected="false">Trending</a></li>

                                <li class="nav-item"><a class="nav-link" id="3-tab" data-toggle="tab" href="#tab-3"
                                                        role="tab" aria-controls="tab-3" aria-selected="false">Favourite</a></li>

                                <li class="nav-item"><a class="nav-link" id="4-tab" data-toggle="tab" href="#tab-4"
                                                        role="tab" aria-controls="tab-4"
                                                        aria-selected="false">Just Watched</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- end content mobile tabs nav -->
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <!-- content tabs -->
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="tab-1" role="tabpanel" aria-labelledby="1-tab">
                <div class="row">
                    <!-- card -->
                    <c:forEach items="${latestEpisodes}" var="latestFilm">
                        <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
                            <div class="card">
                                <div class="card__cover">
                                    <img src="${latestFilm.imageLink}" alt="${latestFilm.filmName}">
                                    <a href="CheckPremium?episodeId=${latestFilm.epId}&filmId=${latestFilm.filmId}"
                                       class="card__play">
                                        <i class="icon ion-ios-play"></i>
                                    </a>
                                </div>
                                <div class="card__content">
                                    <h3 class="card__title"><a
                                            href="CheckPremium?episodeId=${latestFilm.epId}&filmId=${latestFilm.filmId}">${latestFilm.filmName}</a>
                                    </h3>
                                    <span class="card__category">
                                    <a>${latestFilm.epTittle}</a>
                                    <a>${latestFilm.seasonName}</a>
                                </span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <!-- end card -->
                </div>
                <div class="col-12">
                    <a href="category" class="section__btn">Show more</a>
                </div>
            </div>

            <div class="tab-pane fade" id="tab-2" role="tabpanel" aria-labelledby="2-tab-tab">
                <div class="row">
                    <!-- card -->
                    <c:forEach items="${films}" var="film">
                        <div class="col-6 col-sm-12 col-lg-6">
                            <div class="card card--list">
                                <div class="row">
                                    <div class="col-12 col-sm-4">
                                        <div class="card__cover">
                                            <img style="" src="${film.imageLink}" alt="${film.filmName}">
                                            <a href="detail?filmName=${film.filmName}" class="card__play">
                                                <i class="icon ion-ios-play"></i>
                                            </a>
                                        </div>
                                    </div>

                                    <div class="col-12 col-sm-8">
                                        <div class="card__content">
                                            <h3 style="padding-top: 5px" class="card__title"><a
                                                    href="detail?filmName=${film.filmName}">${film.filmName}</a></h3>

                                            <span class="card__category">
                            <c:forEach items="${film.categories}" var="category">
                                <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                            </c:forEach>
                        </span>
                                            <div class="card__wrap">
                                                <span class="card__rate">
                                                     <i class="icon ion-ios-star"></i>${film.ratingValue}
                                                     <i style="margin-left: 10px"
                                                        class="icon ion-ios-eye"></i>${film.viewCount}
                                                </span>
                                                <ul class="card__list">
                                                    <c:forEach items="${film.tags}" var="tag">
                                                        <li>${tag.tagName}</li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                            <div class="card__description">
                                                <p>${film.description}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="col-12">
                    <a href="category" class="section__btn">Show more</a>
                </div>
            </div>


            <div class="tab-pane fade" id="tab-3" role="tabpanel" aria-labelledby="3-tab">
                <c:choose>
                    <c:when test="${sessionScope.userSession != null}">
                        <div class="row">
                            <!-- card -->
                            <c:choose>
                                <c:when test="${not empty favouriteFilms}">
                                    <c:forEach items="${favouriteFilms}" var="favouriteFilm">
                                        <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
                                            <div class="card">
                                                <div class="card__cover">
                                                    <img src="${favouriteFilm.imageLink}" alt="${favouriteFilm.filmName}">
                                                    <a href="detail?filmName=${favouriteFilm.filmName}" class="card__play">
                                                        <i class="icon ion-ios-play"></i>
                                                    </a>
                                                </div>
                                                <div class="card__content">
                                                    <h3 class="card__title"><a href="detail?filmName=${favouriteFilm.filmName}">${favouriteFilm.filmName}</a></h3>
                                                    <span class="card__category">
                                            <c:forEach items="${favouriteFilm.categories}" var="category">
                                                <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                                            </c:forEach>
                                        </span>
                                                    <span class="card__rate"><i class="icon ion-ios-star"></i>${favouriteFilm.ratingValue}</span>
                                                    <span style="margin-left: 5px" class="card__rate"><i class="icon ion-ios-eye"></i>${favouriteFilm.viewCount}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h1 style="color: white">No film watched.</h1>
                                </c:otherwise>
                            </c:choose>
                            <!-- end card -->
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="display: flex; justify-content: center; align-items: center;">
                            <h1 style="color: white">Log in to use this function!</h1>
                            <a href="SignIn.jsp" class="header__sign-in">
                                <i class="icon ion-ios-log-in"></i>
                                <span>sign in</span>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>


            <div class="tab-pane fade" id="tab-4" role="tabpanel" aria-labelledby="4-tab">
                <c:choose>
                    <c:when test="${sessionScope.userSession != null}">
                        <div class="row">
                            <!-- card -->
                            <c:choose>
                                <c:when test="${not empty watchedEpisodes}">
                                    <c:forEach items="${watchedEpisodes}" var="watchedEpisodes">
                                        <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
                                            <div class="card">
                                                <div class="card__cover">
                                                    <img src="${watchedEpisodes.imageLink}"
                                                         alt="${watchedEpisodes.filmName}">
                                                    <a href="watching?episodeId=${watchedEpisodes.epId}&filmId=${watchedEpisodes.filmId}"
                                                       class="card__play">
                                                        <i class="icon ion-ios-play"></i>
                                                    </a>
                                                </div>
                                                <div class="card__content">
                                                    <h3 class="card__title"><a
                                                            href="watching?episodeId=${watchedEpisodes.epId}&filmId=${watchedEpisodes.filmId}">${watchedEpisodes.filmName}</a>
                                                    </h3>
                                                    <span class="card__category">
                                                    <a>${watchedEpisodes.epTittle}</a>
                                                    <a>${watchedEpisodes.seasonName}</a>
                                                </span>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h1 style="color: white">No film watched.</h1>
                                </c:otherwise>
                            </c:choose>
                            <!-- end card -->
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="display: flex;
                                    justify-content: center;
                                    align-items: center;">
                            <h1 style="color: white">Log in to use this function!</h1>
                            <a href="SignIn.jsp" class="header__sign-in">
                                <i class="icon ion-ios-log-in"></i>
                                <span>sign in</span>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- end content tabs -->
    </div>

</section>
<!-- end content -->

<!-- expected premiere -->
<section class="section section--bg" data-bg="img/section/section.jpg">
    <div class="container">
        <div class="row">
            <!-- section title -->
            <div class="col-12">
                <h2 class="section__title">Expected premiere</h2>
            </div>
            <!-- end section title -->

            <!-- card -->
            <c:forEach items="${premiumEpisodes}" var="premiumEp">
                <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
                    <div class="card">
                        <div class="card__cover">
                            <img src="${premiumEp.imageLink}" alt="${premiumEp.filmName}">
                            <a href="CheckPremium?episodeId=${premiumEp.epId}&filmId=${premiumEp.filmId}" class="card__play">
                                <i class="icon ion-ios-play"></i>
                            </a>
                        </div>
                        <div class="card__content">
                            <h3 class="card__title"><a href="CheckPremium?episodeId=${premiumEp.epId}&filmId=${premiumEp.filmId}">${premiumEp.filmName}</a></h3>
                            <span class="card__category">
                                    <a>${premiumEp.epTittle}</a>
                                    <a>${premiumEp.seasonName}</a>
                                </span>
                            <span class="card__rate"><i class="fas fa-crown"></i></span>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <!-- end card -->
        </div>
    </div>
</section>
<!-- end expected premiere -->

<%-- Decorator --%>
<%@include file="decorator/footer.jsp" %>
<%@include file="decorator/script.jsp" %>
<%-- End Decorator --%>
</body>
</html>