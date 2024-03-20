<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="dtos.filmDtos" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%
    filmDtos film = (filmDtos) request.getAttribute("film");
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp" %>
<style>
    textarea.form__textarea {
        overflow: auto;
    }

    textarea.form__textarea::-webkit-scrollbar {
        display: none;
    }

    textarea.form__textarea:focus {
        outline: none;
    }

    .show-more:hover, .show-less:hover {
        background: -webkit-linear-gradient(90deg, #ff55a5 0%, #ff5860 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        display: inline-block; /* Đảm bảo icon có thể áp dụng background */
    }

    .show-more, .show-less {
        font-size: 24px; /* Hoặc bất kỳ giá trị nào bạn muốn */
        color: honeydew; /* Màu cho icon show-less */
        margin: 0 0 0 55px;
    }

    /* Style The Dropdown Button */
    .dropbtnMORE {
        color: white;
        padding: 10px;
        font-size: 14px;
        border: none;
        cursor: pointer;
    }

    /* The container <div> - needed to position the dropdown content */
    .dropdownMORE {
        position: relative;
        display: inline-block;
    }

    /* Dropdown Content (Hidden by Default) */
    .dropdown-contentMORE {
        display: none;
        position: absolute;
        background-color: #2b2b31;
        min-width: 80px;
        box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
        z-index: 1;
        transition: opacity 0.5s ease;
    }

    /* Links inside the dropdown */
    .dropdown-contentMORE a, .dropdown-contentMORE button {
        color: whitesmoke;
        padding: 8px 12px;
        text-decoration: none;
        display: block;
    }

</style>
<body class="body">

<!-- header -->
<%@include file="decorator/header.jsp" %>
<!-- end header -->
<c:set var="maxPagesToShow" value="4"/> <!-- Giả sử bạn muốn hiển thị tối đa 5 trang -->
<c:set var="pageStart" value="${currentPage - (maxPagesToShow div 2)}"/>
<c:set var="pageEnd" value="${pageStart + maxPagesToShow - 1}"/>

<!-- Điều chỉnh nếu số lượng trang không đủ -->
<c:if test="${pageEnd > totalPages}">
    <c:set var="pageEnd" value="${totalPages}"/>
    <c:set var="pageStart" value="${pageEnd - maxPagesToShow + 1}"/>
</c:if>
<c:if test="${pageStart < 1}">
    <c:set var="pageStart" value="1"/>
    <c:set var="pageEnd" value="${maxPagesToShow < totalPages ? maxPagesToShow : totalPages}"/>
</c:if>
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

                                    <c:set var="isFavourite" value="${false}"/>
                                    <c:forEach items="${favouriteFilms}" var="favourite">
                                        <c:if test="${favourite.filmID == film.filmID}">
                                            <c:set var="isFavourite" value="${true}"/>
                                        </c:if>
                                    </c:forEach>

                                    <form action="${pageContext.request.contextPath}/favourite" method="post">
                                        <input type="hidden" name="filmId" value="${film.filmID}">
                                        <input type="hidden" name="filmName" value="${film.filmName}">
                                        <button type="submit" style="font-size:26px; margin-left: 20px">
                                            <i class="fa ${isFavourite ? 'fa-bookmark' : 'fa-bookmark-o'}"
                                               style="color: ${isFavourite ? 'yellow' : 'inherit'};"></i>
                                        </button>
                                    </form>

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
                                <button type="button" data-toggle="collapse"
                                        data-target="#collapse${seasonStatus.index}" aria-expanded="true"
                                        aria-controls="collapse${seasonStatus.index}">
                                    <span>${season.seasonName}</span>
                                </button>
                            </div>

                            <div id="collapse${seasonStatus.index}"
                                 class="collapse ${seasonStatus.index == 0 ? 'show' : ''}"
                                 aria-labelledby="heading${seasonStatus.index}" data-parent="#accordion">
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

                                <%--                                <li class="nav-item"><a class="nav-link" id="2-tab" data-toggle="tab" href="#tab-2"--%>
                                <%--                                                        role="tab" aria-controls="tab-2"--%>
                                <%--                                                        aria-selected="false">Reviews</a></li>--%>

                                <%--                                <li class="nav-item"><a class="nav-link" id="3-tab" data-toggle="tab" href="#tab-3"--%>
                                <%--                                                        role="tab" aria-controls="tab-3"--%>
                                <%--                                                        aria-selected="false">Photos</a></li>--%>
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
                                        <c:forEach items="${cmt}" var="comment">
                                            <li class="comments__item">
                                                <div class="comments__autor">
                                                    <img class="comments__avatar"
                                                         src="https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg"
                                                         alt="">
                                                    <span class="comments__name">${comment.userName}</span>
                                                    <span class="comments__time">${comment.commentDate}</span>
                                                </div>
                                                <p class="comments__text">${comment.commentText}</p>

                                                <div id="editForm${comment.commentID}" style="display:none;">
                                                    <form class="form"
                                                          action="updateComment"
                                                          method="post">
                                                        <textarea class="form__textarea" name="commentText"
                                                                  required>${comment.commentText}</textarea>
                                                        <input type="hidden" name="commentID"
                                                               value="${comment.commentID}"/>
                                                        <input type="hidden" name="filmName" value="${film.filmName}"/>
                                                        <input type="hidden" name="page" value="${currentPage}"/>
                                                        <button type="submit" style="color: whitesmoke">Update</button>
                                                        <button type="button"
                                                                style="color: whitesmoke; margin-left: 5px"
                                                                onclick="closeEditForm('${comment.commentID}')">Cancel
                                                        </button>
                                                    </form>
                                                </div>

                                                <div class="comments__actions">
                                                    <div class="comments__rate">
                                                        <div class="dropdownMORE">
                                                            <c:if test="${comment.userID == sessionScope.userSession.userId}">
                                                                <button class="dropbtnMORE"><i
                                                                        class="icon ion-ios-more"></i></button>
                                                                <div class="dropdown-contentMORE">
                                                                    <button type="button"
                                                                            onclick="showEditForm('${comment.commentID}')">
                                                                        Edit
                                                                    </button>
                                                                    <a href="deleteComment?commentID=${comment.commentID}&filmName=${film.filmName}&page=${currentPage}">Delete</a>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>

                                                    <c:if test="${comment.isEdited()}">
                                                        <button>(Edited)
                                                        </button>
                                                    </c:if>

                                                    <c:choose>
                                                        <c:when test="${not empty sessionScope.userSession}">
                                                            <button type="button"
                                                                    onclick="showReplyForm('${comment.commentID}');">
                                                                <i class="icon ion-ios-share-alt"></i>Reply
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button>Sign in to reply</button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>

                                                    <%--input reply for comment--%>
                                                <div id="replyForm${comment.commentID}" style="display:none;">
                                                    <form class="form" action="${pageContext.request.contextPath}/reply"
                                                          method="post">
                                                        <textarea style="height: 50px" class="form__textarea"
                                                                  name="replyText" required
                                                                  placeholder="Type your reply here..."></textarea>
                                                        <input type="hidden" name="parentCommentID"
                                                               value="${comment.commentID}"/>
                                                        <input type="hidden" name="filmID" value="${film.filmID}"/>
                                                        <input type="hidden" name="filmName" value="${film.filmName}"/>
                                                        <button style="height: 20px; width: 60px" type="submit"
                                                                class="form__btn">Reply
                                                        </button>
                                                    </form>
                                                </div>

                                                    <%--show reply--%>
                                                <ul class="comments__replies">
                                                    <c:forEach items="${comment.replies}" var="reply"
                                                               varStatus="status">
                                                        <li class="${status.index >= 2 ? 'hidden comments__item comments__item--answer' : 'comments__item comments__item--answer'}"
                                                            style="margin-top: 20px">
                                                            <div class="comments__autor">
                                                                <img class="comments__avatar"
                                                                     src="https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg"
                                                                     alt="">
                                                                <span class="comments__name">${reply.userName}</span>
                                                                <span class="comments__time">${reply.commentDate}</span>
                                                            </div>
                                                            <p class="comments__text">${reply.commentText}</p>

                                                                <%--edit reply--%>
                                                            <div id="editForm${reply.commentID}"
                                                                 style="display:none;">
                                                                <form class="form"
                                                                      action="updateComment"
                                                                      method="post">
                                                        <textarea class="form__textarea" name="commentText"
                                                                  required>${reply.commentText}</textarea>
                                                                    <input type="hidden" name="commentID"
                                                                           value="${reply.commentID}"/>
                                                                    <input type="hidden" name="filmName"
                                                                           value="${film.filmName}"/>
                                                                    <input type="hidden" name="page"
                                                                           value="${currentPage}"/>
                                                                    <button type="submit" style="color: whitesmoke">
                                                                        Update
                                                                    </button>
                                                                    <button type="button"
                                                                            style="color: whitesmoke; margin-left: 5px"
                                                                            onclick="closeEditForm('${reply.commentID}')">
                                                                        Cancel
                                                                    </button>
                                                                </form>
                                                            </div>

                                                                <%--reply action--%>
                                                            <div class="comments__actions">
                                                                <div class="comments__rate">
                                                                    <div class="dropdownMORE">
                                                                        <c:if test="${reply.userID == sessionScope.userSession.userId}">
                                                                            <button class="dropbtnMORE"><i
                                                                                    class="icon ion-ios-more"></i>
                                                                            </button>
                                                                            <div class="dropdown-contentMORE">
                                                                                <button type="button"
                                                                                        onclick="showEditForm('${reply.commentID}')">
                                                                                    Edit
                                                                                </button>
                                                                                <a href="deleteComment?commentID=${reply.commentID}&filmName=${film.filmName}&page=${currentPage}">Delete</a>
                                                                            </div>
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                                <c:if test="${reply.isEdited()}">
                                                                    <button>(Edited)
                                                                    </button>
                                                                </c:if>
                                                                <c:choose>
                                                                    <c:when test="${not empty sessionScope.userSession}">
                                                                        <button type="button"
                                                                                onclick="showReplyForm('${reply.commentID}');">
                                                                            <i class="icon ion-ios-share-alt"></i>Reply
                                                                        </button>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <button>Sign in to reply</button>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>

                                                                <%--input reply for reply--%>
                                                            <div id="replyForm${reply.commentID}" style="display:none;">
                                                                <form class="form"
                                                                      action="${pageContext.request.contextPath}/reply"
                                                                      method="post">
                                                        <textarea style="height: 50px" class="form__textarea"
                                                                  name="replyText" required
                                                                  placeholder="Type your reply here...">@${reply.userName} </textarea>
                                                                    <input type="hidden" name="parentCommentID"
                                                                           value="${comment.commentID}"/>
                                                                    <input type="hidden" name="filmID"
                                                                           value="${film.filmID}"/>
                                                                    <input type="hidden" name="filmName"
                                                                           value="${film.filmName}"/>
                                                                    <button style="height: 20px; width: 60px"
                                                                            type="submit"
                                                                            class="form__btn">Reply
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                    <c:if test="${fn:length(comment.replies) > 2}">
                                                        <button class="show-more" style="font-size: medium"
                                                                data-comment-id="${comment.commentID}"><a>Show more</a>
                                                        </button>
                                                        <button class="show-less"
                                                                style="display:none; font-size: medium"
                                                                data-comment-id="${comment.commentID}"><a>Show less</a>
                                                        </button>
                                                    </c:if>
                                                </ul>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>

                                <!-- Comments paginator -->
                                <c:if test="${totalComments > 0}">
                                    <div class="col-12">
                                        <ul class="paginator paginator--list">
                                            <c:if test="${currentPage > 1}">
                                                <li class="paginator__item paginator__item--prev">
                                                    <a href="detail?filmName=${film.filmName}&page=${1}"><i
                                                            class="icon ion-ios-arrow-back"></i><i
                                                            class="icon ion-ios-arrow-back"></i></a>
                                                </li>
                                            </c:if>
                                            <c:forEach begin="${pageStart}" end="${pageEnd}" var="i">
                                                <li class="paginator__item ${i == currentPage ? 'paginator__item--active' : ''}">
                                                    <a href="detail?filmName=${film.filmName}&page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${currentPage < totalPages}">
                                                <li class="paginator__item paginator__item--next">
                                                    <a href="detail?filmName=${film.filmName}&page=${totalPages}"><i
                                                            class="icon ion-ios-arrow-forward"></i><i
                                                            class="icon ion-ios-arrow-forward"></i></a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </div>
                                </c:if>
                                <!-- End paginator -->

                                <c:choose>
                                    <c:when test="${sessionScope.userSession != null}">
                                        <form action="${pageContext.request.contextPath}/comment" method="post"
                                              class="form">
                                        <textarea id="text" name="commentText" required
                                                  placeholder="Add your comment here..."
                                                  class="form__textarea"></textarea>
                                            <input type="hidden" name="filmId" value="${film.filmID}"/>
                                            <input type="hidden" name="filmName" value="${film.filmName}"/>
                                            <button type="submit" class="form__btn">Send</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="display: flex; justify-content: center; align-items: center;">
                                            <h1 style="color: white">Sign in to comment and rate!</h1>
                                            <a href="SignIn.jsp" class="header__sign-in">
                                                <i class="icon ion-ios-log-in"></i>
                                                <span>sign in</span>
                                            </a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <!-- End comments -->

                        </div>
                    </div>
                    <c:if test="${sessionScope.userSession != null}">
                        <div style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
                            <form style="padding: 20px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 10px;"
                                  action="${pageContext.request.contextPath}/rateFilm" method="post">
                                <div class="rate" data-user-rating="${userRating}">
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
                                <input type="hidden" name="filmId" value="${film.filmID}"/>
                                <input type="hidden" name="filmName" value="${film.filmName}"/>
                                <button style="cursor: pointer; height: 30px; width: 100px; margin-top: 10px;"
                                        type="submit"
                                        class="form__btn">Rate
                                </button>
                            </form>
                            <h1 style="color: #0cb457">${mess}</h1>
                        </div>
                    </c:if>
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
                    <c:forEach items="${rdFilms}" var="rdFilms">
                        <div class="col-6 col-sm-4 col-lg-6">
                            <div class="card">
                                <div class="card__cover">
                                    <img src="${rdFilms.imageLink}" alt="${rdFilms.filmName}">
                                    <a href="detail?filmName=${rdFilms.filmName}" class="card__play">
                                        <i class="icon ion-ios-play"></i>
                                    </a>
                                </div>
                                <div class="card__content">
                                    <h3 class="card__title"><a
                                            href="detail?filmName=${rdFilms.filmName}">${rdFilms.filmName}</a></h3>
                                    <span class="card__category">
										<c:forEach items="${rdFilms.categories}" var="category">
                                            <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                                        </c:forEach>
									</span>
                                    <span class="card__rate"><i
                                            class="icon ion-ios-star"></i>${rdFilms.ratingValue}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
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

    // Hàm highlight sao dựa trên người dùng hover
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

    // Reset màu sắc của các sao khi không hover hoặc sau khi đã chọn
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

    // Hàm này được gọi khi người dùng click vào một sao
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

    // Hàm highlight sao dựa trên đánh giá từ server
    function highlightUserRating(userRating) {
        if (userRating && userRating > 0) {
            const stars = document.querySelectorAll('.rate label');
            selectedStarIndex = Math.floor(userRating) - 1; // Cập nhật index dựa trên đánh giá từ server
            stars.forEach((star, index) => {
                if (index < userRating) {
                    star.style.color = '#ffc700'; // Highlight the star
                    star.previousElementSibling.checked = true; // Check the corresponding radio button
                } else {
                    star.style.color = '#ccc'; // Dim the rest of the stars
                    star.previousElementSibling.checked = false; // Uncheck the radio button
                }
            });
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        var rateDiv = document.querySelector('.rate');
        var userRating = rateDiv.getAttribute('data-user-rating');
        if (userRating) {
            highlightUserRating(parseInt(userRating));
        }
    });


    document.addEventListener("DOMContentLoaded", function () {
        var rows = document.querySelectorAll('tr[data-episode-id]');

        rows.forEach(function (row) {
            row.addEventListener('click', function () {
                var episodeId = this.getAttribute('data-episode-id');
                var filmId = this.closest('.accordion__card').getAttribute('data-film-id');
                var href = 'CheckPremium?episodeId=' + episodeId + '&filmId=' + filmId;

                window.location.href = href;
            });
        });
    });

    function showReplyForm(commentID) {
        // Lấy phần tử form trả lời dựa trên commentID
        var replyForm = document.getElementById('replyForm' + commentID);
        // Kiểm tra nếu form đang ẩn, thì hiển thị nó; nếu không thì ẩn nó
        if (replyForm.style.display === 'none' || replyForm.style.display === '') {
            replyForm.style.display = 'block';
        } else {
            replyForm.style.display = 'none';
        }
    }

    document.addEventListener('DOMContentLoaded', function () {
        // Xử lý Show More
        document.querySelectorAll('.show-more').forEach(button => {
            button.addEventListener('click', function () {
                var hiddenReplies = this.parentElement.querySelectorAll('.hidden');
                hiddenReplies.forEach((reply, index) => {
                    if (index < 2) {
                        reply.classList.remove('hidden');
                    }
                });
                this.nextElementSibling.style.display = ''; // Hiển thị nút Show Less

                if (this.parentElement.querySelectorAll('.hidden').length === 0) {
                    this.style.display = 'none'; // Ẩn nút Show More nếu không còn phản hồi bị ẩn
                }
            });
        });

        // Xử lý Show Less
        document.querySelectorAll('.show-less').forEach(button => {
            button.addEventListener('click', function () {
                var replies = this.parentElement.querySelectorAll('.comments__item.comments__item--answer:not(.hidden)');
                replies.forEach((reply, index) => {
                    if (index >= 2) { // Giả sử bạn muốn ẩn 2 phản hồi mỗi lần nhấn Show Less
                        reply.classList.add('hidden');
                    }
                });

                if (this.parentElement.querySelectorAll('.comments__item.comments__item--answer:not(.hidden)').length <= 2) {
                    this.style.display = 'none'; // Ẩn nút Show Less nếu số lượng phản hồi hiển thị như ban đầu
                }

                this.previousElementSibling.style.display = ''; // Hiển thị lại nút Show More
            });
        });
    });

    document.addEventListener('DOMContentLoaded', function () {
        // Gắn sự kiện click lên container chứa cả comment và reply
        var commentsContainer = document.querySelector('.comments'); // Thay đổi selector nếu cần
        if (commentsContainer) {
            commentsContainer.addEventListener('click', function (event) {
                var target = event.target;
                // Tìm button dropdown trong phần tử hiện tại hoặc phần tử cha
                var dropBtn = target.closest('.dropbtnMORE');

                if (dropBtn) {
                    // Ngăn chặn sự kiện lan truyền
                    event.stopPropagation();

                    // Logic mở/toggle dropdown
                    var dropdownContent = dropBtn.nextElementSibling;
                    var isOpen = dropdownContent.style.display === 'block';
                    closeAllDropdowns(); // Đóng tất cả dropdown trước khi toggle
                    dropdownContent.style.display = isOpen ? 'none' : 'block';
                } else {
                    // Đóng tất cả dropdown khi click ngoài
                    closeAllDropdowns();
                }
            });
        }

        // Hàm đóng tất cả dropdown
        function closeAllDropdowns() {
            document.querySelectorAll('.dropdown-contentMORE').forEach(function (content) {
                content.style.display = 'none';
            });
        }
    });

    function showEditForm(commentID) {
        var editForm = document.getElementById('editForm' + commentID);
        if (editForm.style.display === 'none' || replyForm.style.display === '') {
            editForm.style.display = 'block';
        }
    }

    function closeEditForm(commentID) {
        var editForm = document.getElementById('editForm' + commentID);
        if (editForm.style.display === 'block' || editForm.style.display === '') {
            editForm.style.display = 'none';
        }
    }
</script>
</html>