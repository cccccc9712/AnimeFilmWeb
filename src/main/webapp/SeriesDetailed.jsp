<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="dtos.filmDtos" %>
<%@ page import="entity.User" %>
<%
    filmDtos film = (filmDtos) request.getAttribute("film");
    User user = (User) session.getAttribute("userSession");
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp" %>
<body class="body">

<!-- header -->
<%@include file="decorator/header.jsp" %>
<!-- end header -->

<!-- details -->
<section class="section details">
    <!-- details background -->
    <div class="details__bg" data-bg="img/home/home__bg.jpg"></div>
    <!-- end details background -->

    <!-- details content -->
    <div class="container">

        <div class="row">
            <!-- title -->
            <div class="col-12">
                <h1 class="details__title">${film.filmName}</h1>
            </div>
            <!-- end title -->

            <!-- content -->
            <div class="col-10">
                <div class="card card--details card--series">
                    <div class="row">
                        <!-- card cover -->
                        <div class="col-12 col-sm-4 col-md-4 col-lg-3 col-xl-3">
                            <div class="card__cover">
                                <img src="${film.imageLink}" alt="${film.filmName}">
                            </div>
                        </div>
                        <!-- end card cover -->
                        <!-- card content -->
                        <div class="col-12 col-sm-8 col-md-8 col-lg-9 col-xl-9">
                            <div class="card__content">
                                <div class="card__wrap">
                                    <span class="card__rate"><i class="icon ion-ios-star"></i>${film.ratingValue}</span>

                                    <ul class="card__list">
                                            <c:forEach items="${film.tags}" var="tag">
                                                <li href="#">${tag.tagName}</li>
                                            </c:forEach>
                                    </ul>
                                    <button style="font-size:26px; margin-left: 20px"><i class="fa fa-bookmark-o"></i></button>
                                </div>

                                <ul class="card__meta">
                                    <li><span>Genre:</span>
                                        <c:forEach items="${film.categories}" var="category">
                                            <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                                        </c:forEach>
                                    </li>
                                </ul>

                                <div class="card__description card__description--details">
                                    ${film.description}
                                </div>
                            </div>
                        </div>
                        <!-- end card content -->
                    </div>
                </div>
            </div>
            <!-- end content -->

            <!-- player -->
            <div class="col-12 col-xl-6">
                <iframe width="560" height="315"
                        src="${film.trailerLink}"
                        frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                        allowfullscreen>
                </iframe>
            </div>
            <!-- End player -->

            <!-- accordion -->
            <div class="col-12 col-xl-6">
                <div class="accordion" id="accordion">
                    <c:forEach items="${season}" var="season" varStatus="seasonStatus">
                        <div data-film-id="${film.filmID}" class="accordion__card">
                            <div class="card-header" id="heading${seasonStatus.index}">
                                <button type="button" data-toggle="collapse" data-target="#collapse${seasonStatus.index}" aria-expanded="true" aria-controls="collapse${seasonStatus.index}">
                                    <span>${season.seasonName}</span>
                                </button>
                            </div>

                            <div id="collapse${seasonStatus.index}" class="collapse ${seasonStatus.index == 0 ? 'show' : ''}" aria-labelledby="heading${seasonStatus.index}" data-parent="#accordion">
                                <div class="card-body">
                                    <table class="accordion__list">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Title</th>
                                            <th>Release Date</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${season.episodes}" var="episode" varStatus="episodeStatus">
                                            <tr data-episode-id="${episode.epId}" style="cursor: pointer;">
                                                <td>${episodeStatus.index + 1}</td>
                                                <td>${episode.epTittle}</td>
                                                <td>${episode.epDate}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!-- end accordion -->

            <!-- availables -->
            <div class="col-12">
                <div class="details__wrap">
                    <!-- availables -->
                    <div class="details__devices">
                        <span class="details__devices-title">Available on devices:</span>
                        <ul class="details__devices-list">
                            <li><i class="icon ion-logo-apple"></i><span>IOS</span></li>
                            <li><i class="icon ion-logo-android"></i><span>Android</span></li>
                            <li><i class="icon ion-logo-windows"></i><span>Windows</span></li>
                            <li><i class="icon ion-md-tv"></i><span>Smart TV</span></li>
                        </ul>
                    </div>
                    <!-- end availables -->

                    <!-- share -->
                    <div class="details__share">
                        <span class="details__share-title">Share with friends:</span>

                        <ul class="details__share-list">
                            <li class="facebook"><a href="#"><i class="icon ion-logo-facebook"></i></a></li>
                            <li class="instagram"><a href="#"><i class="icon ion-logo-instagram"></i></a></li>
                            <li class="twitter"><a href="#"><i class="icon ion-logo-twitter"></i></a></li>
                            <li class="vk"><a href="#"><i class="icon ion-logo-vk"></i></a></li>
                        </ul>
                    </div>
                    <!-- end share -->
                </div>
            </div>
        </div>
    </div>
    <!-- end details content -->
</section>
<!-- end details -->

<!-- content -->
<section class="content">
    <div class="content__head">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <!-- content title -->
                    <h2 style="margin-bottom: 25px" class="content__title">Comments</h2>
                    <!-- end content title -->

                    <!-- content tabs nav -->
                    <%--                    <ul class="nav nav-tabs content__tabs" id="content__tabs" role="tablist">--%>
                    <%--                        <li class="nav-item">--%>
                    <%--                            <a class="nav-link active" data-toggle="tab" href="#tab-1" role="tab" aria-controls="tab-1" aria-selected="true">Comments</a>--%>
                    <%--                        </li>--%>

                    <%--                        <li class="nav-item">--%>
                    <%--                            <a class="nav-link" data-toggle="tab" href="#tab-2" role="tab" aria-controls="tab-2" aria-selected="false">Reviews</a>--%>
                    <%--                        </li>--%>

                    <%--                        <li class="nav-item">--%>
                    <%--                            <a class="nav-link" data-toggle="tab" href="#tab-3" role="tab" aria-controls="tab-3" aria-selected="false">Photos</a>--%>
                    <%--                        </li>--%>
                    <%--                    </ul>--%>
                    <!-- end content tabs nav -->

                    <!-- content mobile tabs nav -->
                    <div class="content__mobile-tabs" id="content__mobile-tabs">
                        <div class="content__mobile-tabs-btn dropdown-toggle" role="navigation" id="mobile-tabs"
                             data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <input type="button" value="Comments">
                            <span></span>
                        </div>

                        <div class="content__mobile-tabs-menu dropdown-menu" aria-labelledby="mobile-tabs">
                            <ul class="nav nav-tabs" role="tablist">
                                <li class="nav-item"><a class="nav-link active" id="1-tab" data-toggle="tab"
                                                        href="#tab-1" role="tab" aria-controls="tab-1"
                                                        aria-selected="true">Comments</a></li>

                                <li class="nav-item"><a class="nav-link" id="2-tab" data-toggle="tab" href="#tab-2"
                                                        role="tab" aria-controls="tab-2"
                                                        aria-selected="false">Reviews</a></li>

                                <li class="nav-item"><a class="nav-link" id="3-tab" data-toggle="tab" href="#tab-3"
                                                        role="tab" aria-controls="tab-3"
                                                        aria-selected="false">Photos</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- end content mobile tabs nav -->
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-12 col-lg-8 col-xl-8">
                <!-- content tabs -->
                <div class="tab-content" id="myTabContent">
                    <div class="tab-pane fade show active" id="tab-1" role="tabpanel" aria-labelledby="1-tab">
                        <div class="row">
                            <!-- comments -->
                            <div class="col-12">
                                <div class="comments">
                                    <ul class="comments__list">
                                        <li class="comments__item">
                                            <div class="comments__autor">
                                                <img class="comments__avatar" src="img/user.png" alt="">
                                                <span class="comments__name">John Doe</span>
                                                <span class="comments__time">30.08.2018, 17:53</span>
                                            </div>
                                            <p class="comments__text">There are many variations of passages of Lorem
                                                Ipsum available, but the majority have suffered alteration in some form,
                                                by injected humour, or randomised words which don't look even slightly
                                                believable. If you are going to use a passage of Lorem Ipsum, you need
                                                to be sure there isn't anything embarrassing hidden in the middle of
                                                text.</p>
                                            <div class="comments__actions">
<%--                                                <div class="comments__rate">--%>
<%--                                                    <button type="button"><i class="icon ion-md-thumbs-up"></i>12--%>
<%--                                                    </button>--%>

<%--                                                    <button type="button">7<i class="icon ion-md-thumbs-down"></i>--%>
<%--                                                    </button>--%>
<%--                                                </div>--%>

                                                <button type="button"><i class="icon ion-ios-share-alt"></i>Reply
                                                </button>
<%--                                                <button type="button"><i class="icon ion-ios-quote"></i>Quote</button>--%>
                                            </div>
                                        </li>

                                        <li class="comments__item comments__item--answer">
                                            <div class="comments__autor">
                                                <img class="comments__avatar" src="img/user.png" alt="">
                                                <span class="comments__name">John Doe</span>
                                                <span class="comments__time">24.08.2018, 16:41</span>
                                            </div>
                                            <p class="comments__text">Lorem Ipsum is simply dummy text of the printing
                                                and typesetting industry. Lorem Ipsum has been the industry's standard
                                                dummy text ever since the 1500s, when an unknown printer took a galley
                                                of type and scrambled it to make a type specimen book.</p>
                                            <div class="comments__actions">
<%--                                                <div class="comments__rate">--%>
<%--                                                    <button type="button"><i class="icon ion-md-thumbs-up"></i>8--%>
<%--                                                    </button>--%>

<%--                                                    <button type="button">3<i class="icon ion-md-thumbs-down"></i>--%>
<%--                                                    </button>--%>
<%--                                                </div>--%>

                                                <button type="button"><i class="icon ion-ios-share-alt"></i>Reply
                                                </button>
<%--                                                <button type="button"><i class="icon ion-ios-quote"></i>Quote</button>--%>
                                            </div>
                                        </li>

                                        <li class="comments__item comments__item--quote">
                                            <div class="comments__autor">
                                                <img class="comments__avatar" src="img/user.png" alt="">
                                                <span class="comments__name">John Doe</span>
                                                <span class="comments__time">11.08.2018, 11:11</span>
                                            </div>
                                            <p class="comments__text"><span>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable.</span>It
                                                has survived not only five centuries, but also the leap into electronic
                                                typesetting, remaining essentially unchanged. It was popularised in the
                                                1960s with the release of Letraset sheets containing Lorem Ipsum
                                                passages, and more recently with desktop publishing software like Aldus
                                                PageMaker including versions of Lorem Ipsum.</p>
                                            <div class="comments__actions">
<%--                                                <div class="comments__rate">--%>
<%--                                                    <button type="button"><i class="icon ion-md-thumbs-up"></i>11--%>
<%--                                                    </button>--%>

<%--                                                    <button type="button">1<i class="icon ion-md-thumbs-down"></i>--%>
<%--                                                    </button>--%>
<%--                                                </div>--%>

                                                <button type="button"><i class="icon ion-ios-share-alt"></i>Reply
                                                </button>
<%--                                                <button type="button"><i class="icon ion-ios-quote"></i>Quote</button>--%>
                                            </div>
                                        </li>

                                        <li class="comments__item">
                                            <div class="comments__autor">
                                                <img class="comments__avatar" src="img/user.png" alt="">
                                                <span class="comments__name">John Doe</span>
                                                <span class="comments__time">07.08.2018, 14:33</span>
                                            </div>
                                            <p class="comments__text">There are many variations of passages of Lorem
                                                Ipsum available, but the majority have suffered alteration in some form,
                                                by injected humour, or randomised words which don't look even slightly
                                                believable. If you are going to use a passage of Lorem Ipsum, you need
                                                to be sure there isn't anything embarrassing hidden in the middle of
                                                text.</p>
                                            <div class="comments__actions">
<%--                                                <div class="comments__rate">--%>
<%--                                                    <button type="button"><i class="icon ion-md-thumbs-up"></i>99--%>
<%--                                                    </button>--%>

<%--                                                    <button type="button">35<i class="icon ion-md-thumbs-down"></i>--%>
<%--                                                    </button>--%>
<%--                                                </div>--%>

                                                <button type="button"><i class="icon ion-ios-share-alt"></i>Reply
                                                </button>
<%--                                                <button type="button"><i class="icon ion-ios-quote"></i>Quote</button>--%>
                                            </div>
                                        </li>

                                        <li class="comments__item">
                                            <div class="comments__autor">
                                                <img class="comments__avatar" src="img/user.png" alt="">
                                                <span class="comments__name">John Doe</span>
                                                <span class="comments__time">02.08.2018, 15:24</span>
                                            </div>
                                            <p class="comments__text">Many desktop publishing packages and web page
                                                editors now use Lorem Ipsum as their default model text, and a search
                                                for 'lorem ipsum' will uncover many web sites still in their infancy.
                                                Various versions have evolved over the years, sometimes by accident,
                                                sometimes on purpose (injected humour and the like).</p>
                                            <div class="comments__actions">
<%--                                                <div class="comments__rate">--%>
<%--                                                    <button type="button"><i class="icon ion-md-thumbs-up"></i>74--%>
<%--                                                    </button>--%>

<%--                                                    <button type="button">13<i class="icon ion-md-thumbs-down"></i>--%>
<%--                                                    </button>--%>
<%--                                                </div>--%>

                                                <button type="button"><i class="icon ion-ios-share-alt"></i>Reply
                                                </button>
<%--                                                <button type="button"><i class="icon ion-ios-quote"></i>Quote</button>--%>
                                            </div>
                                        </li>
                                    </ul>

                                    <form style="padding-top: 0px" action="#" class="form">
                                        <div style="float: left; height: 46px; padding: 0 10px;" class="rate">
                                            <input type="radio" id="star1" name="rate" value="1"
                                                   style="display: none;"/>
                                            <label for="star1" title="text"
                                                   style="float:left; cursor:pointer; font-size:30px; color:#ccc;"
                                                   onmouseover="highlightStars(this)" onmouseout="resetStars()"
                                                   onclick="fixStars(this)">&#9733;</label>
                                            <input type="radio" id="star2" name="rate" value="2"
                                                   style="display: none;"/>
                                            <label for="star2" title="text"
                                                   style="float:left; cursor:pointer; font-size:30px; color:#ccc;"
                                                   onmouseover="highlightStars(this)" onmouseout="resetStars()"
                                                   onclick="fixStars(this)">&#9733;</label>
                                            <input type="radio" id="star3" name="rate" value="3"
                                                   style="display: none;"/>
                                            <label for="star3" title="text"
                                                   style="float:left; cursor:pointer; font-size:30px; color:#ccc;"
                                                   onmouseover="highlightStars(this)" onmouseout="resetStars()"
                                                   onclick="fixStars(this)">&#9733;</label>
                                            <input type="radio" id="star4" name="rate" value="4"
                                                   style="display: none;"/>
                                            <label for="star4" title="text"
                                                   style="float:left; cursor:pointer; font-size:30px; color:#ccc;"
                                                   onmouseover="highlightStars(this)" onmouseout="resetStars()"
                                                   onclick="fixStars(this)">&#9733;</label>
                                            <input type="radio" id="star5" name="rate" value="5"
                                                   style="display: none;"/>
                                            <label for="star5" title="text"
                                                   style="float:left; cursor:pointer; font-size:30px; color:#ccc;"
                                                   onmouseover="highlightStars(this)" onmouseout="resetStars()"
                                                   onclick="fixStars(this)">&#9733;</label>
                                        </div>
                                        <textarea id="text" name="text" class="form__textarea"
                                                  placeholder="Add comment"></textarea>
                                        <button type="button" class="form__btn">Send</button>
                                    </form>


                                </div>
                            </div>
                            <!-- end comments -->
                        </div>
                    </div>

                    <div class="tab-pane fade" id="tab-2" role="tabpanel" aria-labelledby="2-tab">
                        <div class="row">
                            <!-- reviews -->
                            <div class="col-12">
                                <div class="reviews">
                                    <ul class="reviews__list">
                                        <li class="reviews__item">
                                            <div class="reviews__autor">
                                                <img class="reviews__avatar" src="img/user.png" alt="">
                                                <span class="reviews__name">Best Marvel movie in my opinion</span>
                                                <span class="reviews__time">24.08.2018, 17:53 by John Doe</span>

                                                <span class="reviews__rating"><i
                                                        class="icon ion-ios-star"></i>8.4</span>
                                            </div>
                                            <p class="reviews__text">There are many variations of passages of Lorem
                                                Ipsum available, but the majority have suffered alteration in some form,
                                                by injected humour, or randomised words which don't look even slightly
                                                believable. If you are going to use a passage of Lorem Ipsum, you need
                                                to be sure there isn't anything embarrassing hidden in the middle of
                                                text.</p>
                                        </li>

                                        <li class="reviews__item">
                                            <div class="reviews__autor">
                                                <img class="reviews__avatar" src="img/user.png" alt="">
                                                <span class="reviews__name">Best Marvel movie in my opinion</span>
                                                <span class="reviews__time">24.08.2018, 17:53 by John Doe</span>

                                                <span class="reviews__rating"><i
                                                        class="icon ion-ios-star"></i>9.0</span>
                                            </div>
                                            <p class="reviews__text">There are many variations of passages of Lorem
                                                Ipsum available, but the majority have suffered alteration in some form,
                                                by injected humour, or randomised words which don't look even slightly
                                                believable. If you are going to use a passage of Lorem Ipsum, you need
                                                to be sure there isn't anything embarrassing hidden in the middle of
                                                text.</p>
                                        </li>

                                        <li class="reviews__item">
                                            <div class="reviews__autor">
                                                <img class="reviews__avatar" src="img/user.png" alt="">
                                                <span class="reviews__name">Best Marvel movie in my opinion</span>
                                                <span class="reviews__time">24.08.2018, 17:53 by John Doe</span>

                                                <span class="reviews__rating"><i
                                                        class="icon ion-ios-star"></i>7.5</span>
                                            </div>
                                            <p class="reviews__text">There are many variations of passages of Lorem
                                                Ipsum available, but the majority have suffered alteration in some form,
                                                by injected humour, or randomised words which don't look even slightly
                                                believable. If you are going to use a passage of Lorem Ipsum, you need
                                                to be sure there isn't anything embarrassing hidden in the middle of
                                                text.</p>
                                        </li>
                                    </ul>

                                    <form action="#" class="form">

                                        <input type="text" class="form__input" placeholder="Title">
                                        <textarea class="form__textarea" placeholder="Review"></textarea>
                                        <div class="form__slider">
                                            <div class="form__slider-rating" id="slider__rating"></div>
                                            <div class="form__slider-value" id="form__slider-value"></div>
                                        </div>

                                        <button type="button" class="form__btn">Send</button>
                                    </form>
                                </div>
                            </div>
                            <!-- end reviews -->
                        </div>
                    </div>

                    <div class="tab-pane fade" id="tab-3" role="tabpanel" aria-labelledby="3-tab">
                        <!-- project gallery -->
                        <%--                        <div class="gallery" itemscope>--%>
                        <%--                            <div class="row">--%>
                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-1.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-1.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 1</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>

                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-2.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-2.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 2</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>

                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-3.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-3.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 3</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>

                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-4.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-4.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 4</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>

                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-5.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-5.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 5</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>

                        <%--                                <!-- gallery item -->--%>
                        <%--                                <figure class="col-12 col-sm-6 col-xl-4" itemprop="associatedMedia" itemscope>--%>
                        <%--                                    <a href="img/gallery/project-6.jpg" itemprop="contentUrl" data-size="1920x1280">--%>
                        <%--                                        <img src="img/gallery/project-6.jpg" itemprop="thumbnail" alt="Image description" />--%>
                        <%--                                    </a>--%>
                        <%--                                    <figcaption itemprop="caption description">Some image caption 6</figcaption>--%>
                        <%--                                </figure>--%>
                        <%--                                <!-- end gallery item -->--%>
                        <%--                            </div>--%>
                        <%--                        </div>--%>
                        <!-- end project gallery -->
                    </div>
                </div>
                <!-- end content tabs -->
            </div>

            <!-- sidebar -->
            <div class="col-12 col-lg-4 col-xl-4">
                <div class="row">
                    <!-- section title -->
                    <div class="col-12">
                        <h2 class="section__title section__title--sidebar">You may also like...</h2>
                    </div>
                    <!-- end section title -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">I Dream in Another Language</a></h3>
                                <span class="card__category">
										<a href="#">Action</a>
										<a href="#">Triler</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>8.4</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover2.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">Benched</a></h3>
                                <span class="card__category">
										<a href="#">Comedy</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>7.1</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover3.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">Whitney</a></h3>
                                <span class="card__category">
										<a href="#">Romance</a>
										<a href="#">Drama</a>
										<a href="#">Music</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>6.3</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover4.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">Blindspotting</a></h3>
                                <span class="card__category">
										<a href="#">Comedy</a>
										<a href="#">Drama</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>7.9</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover5.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">I Dream in Another Language</a></h3>
                                <span class="card__category">
										<a href="#">Action</a>
										<a href="#">Triler</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>8.4</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->

                    <!-- card -->
                    <div class="col-6 col-sm-4 col-lg-6">
                        <div class="card">
                            <div class="card__cover">
                                <img src="img/covers/cover6.jpg" alt="">
                                <a href="#" class="card__play">
                                    <i class="icon ion-ios-play"></i>
                                </a>
                            </div>
                            <div class="card__content">
                                <h3 class="card__title"><a href="#">Benched</a></h3>
                                <span class="card__category">
										<a href="#">Comedy</a>
									</span>
                                <span class="card__rate"><i class="icon ion-ios-star"></i>7.1</span>
                            </div>
                        </div>
                    </div>
                    <!-- end card -->
                </div>
            </div>
            <!-- end sidebar -->
        </div>
    </div>
</section>
<!-- end content -->

<!-- footer -->
<%@include file="decorator/footer.jsp" %>
<!-- end footer -->

<!-- Root element of PhotoSwipe. Must have class pswp. -->
<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

    <!-- Background of PhotoSwipe.
    It's a separate element, as animating opacity is faster than rgba(). -->
    <div class="pswp__bg"></div>

    <!-- Slides wrapper with overflow:hidden. -->
    <div class="pswp__scroll-wrap">

        <!-- Container that holds slides. PhotoSwipe keeps only 3 slides in DOM to save memory. -->
        <!-- don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

            <div class="pswp__top-bar">

                <!--  Controls are self-explanatory. Order can be changed. -->

                <div class="pswp__counter"></div>

                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

                <!-- Preloader -->
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                        <div class="pswp__preloader__cut">
                            <div class="pswp__preloader__donut"></div>
                        </div>
                    </div>
                </div>
            </div>

            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button>

            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button>

            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>
        </div>
    </div>
</div>

<!-- JS -->
<%@include file="decorator/script.jsp" %>

</body>
<script>
    var selectedStarIndex = -1;

    function highlightStars(star) {
        resetStars();
        star.style.color = '#ffc700';
        let previousStar = star.previousElementSibling;
        while (previousStar) {
            if (previousStar.tagName === 'LABEL') {
                previousStar.style.color = '#ffc700';
            }
            previousStar = previousStar.previousElementSibling;
        }
    }

    function resetStars() {
        const stars = document.querySelectorAll('.rate label');
        stars.forEach((star, index) => {
            if (index <= selectedStarIndex) {
                star.style.color = '#ffc700'; // Keep the selected stars highlighted
            } else {
                star.style.color = '#ccc'; // Dim the rest of the stars
            }
        });
    }

    function fixStars(star) {
        const stars = document.querySelectorAll('.rate label');
        selectedStarIndex = Array.from(stars).indexOf(star); // Update the selected star index
        stars.forEach((s, index) => {
            if (index <= selectedStarIndex) {
                s.style.color = '#ffc700'; // Highlight the star and all stars to the left
                s.control.checked = true; // Check the star
            } else {
                s.style.color = '#ccc'; // Dim the rest of the stars
                s.control.checked = false; // Uncheck the star
            }
        });
    }


    document.addEventListener("DOMContentLoaded", function() {
        var rows = document.querySelectorAll('tr[data-episode-id]');

        rows.forEach(function(row) {
            row.addEventListener('click', function() {
                var episodeId = this.getAttribute('data-episode-id');
                var filmId = this.closest('.accordion__card').getAttribute('data-film-id');
                var href = 'watching?episodeId=' + episodeId + '&filmId=' + filmId;

                window.location.href = href;
            });
        });
    });
</script>
</html>