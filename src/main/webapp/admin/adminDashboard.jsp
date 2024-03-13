<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="maxPagesToShow" value="4"/>
<c:set var="pageStart"
       value="${(currentPage - (maxPagesToShow div 2)) > 0 ? (currentPage - (maxPagesToShow div 2)) : 1}"/>
<c:set var="pageEnd"
       value="${(pageStart + (maxPagesToShow - 1)) < totalPages ? (pageStart + (maxPagesToShow - 1)) : totalPages}"/>

<!-- Điều chỉnh nếu số lượng trang không đủ -->
<c:if test="${pageEnd - pageStart < maxPagesToShow - 1}">
    <c:set var="pageStart" value="${(pageEnd - (maxPagesToShow - 1)) > 0 ? (pageEnd - (maxPagesToShow - 1)) : 1}"/>
</c:if>
<!DOCTYPE html>
<html lang="en">
<%@include file="adminDecorator/adminHead.jsp" %>
<style>
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .card-header .input-group {
        margin-left: auto;
        max-width: 300px;
    }

    a {
        color: black;
    }
</style>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>
<div class="wrapper">
    <div class="container mt-5">
        <%@include file="adminDecorator/adminCard.jsp" %>
        <div class="card-header">
            <i class="fas fa-table me-1"></i>
            <a style="margin: 0 5px;">All Anime</a>
            <form action="newFilmPage" method="post">
                <button type="submit" class="btn btn-primary">Add new</button>
            </form>
            <div class="input-group">
                <form method="get" action="adminDashboard">
                    <input type="text" name="searchQuery" class="form-control" placeholder="Search...">
                </form>
            </div>
        </div>


        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>Thumbnail</th>
                <th>
                    <c:url var="sortUrlByFilmName" value="/adminDashboard">
                        <c:if test="${not empty currentSearch}">
                            <c:param name="searchQuery" value="${currentSearch}"/>
                        </c:if>
                        <c:if test="${not empty param.categoryName}">
                            <c:param name="categoryName" value="${param.categoryName}"/>
                        </c:if>
                        <c:param name="sort" value="filmName"/>
                        <c:param name="order"
                                 value="${currentSortField == 'filmName' && currentSortOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                    </c:url>
                    <a style="text-decoration: none"
                       href="${sortUrlByFilmName}">Name ${currentSortField == 'filmName' ? currentSortOrder == 'ASC' ? '&#9650;' : '&#9660;' : ''}</a>
                </th>
                <th>Seasons</th>
                <th>Episodes</th>
                <th>
                    <form action="adminDashboard" method="GET">
                        <select name="categoryName" onchange="this.form.submit()">
                            <option value="all">All Categories</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.categoryName}"
                                        <c:if test="${category.categoryName == param.categoryName}">selected</c:if>>${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </form>
                </th>
                <th>
                    <%--                    <form action="adminDashboard" method="GET">--%>
                    <%--                        <select name="tagName" onchange="this.form.submit()">--%>
                    <%--                            <option value="all">All Tags</option>--%>
                    <%--                            <c:forEach var="tag" items="${tags}">--%>
                    <%--                                <option value="${tag.tagName}"--%>
                    <%--                                        <c:if test="${tag.tagName == param.tagName}">selected</c:if>>${tag.tagName}</option>--%>
                    <%--                            </c:forEach>--%>
                    <%--                        </select>--%>
                    <%--                    </form>--%>
                    tags
                </th>
                <th>
                    <c:url var="sortUrlByViewCount" value="/adminDashboard">
                        <c:if test="${not empty currentSearch}">
                            <c:param name="searchQuery" value="${currentSearch}"/>
                        </c:if>
                        <c:if test="${not empty param.categoryName}">
                            <c:param name="categoryName" value="${param.categoryName}"/>
                        </c:if>
                        <c:param name="sort" value="viewCount"/>
                        <c:param name="order"
                                 value="${currentSortField == 'viewCount' && currentSortOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                    </c:url>
                    <a style="text-decoration: none"
                       href="${sortUrlByViewCount}">Views ${currentSortField == 'viewCount' ? currentSortOrder == 'ASC' ? '&#9650;' : '&#9660;' : ''}</a>
                </th>
                <th>
                    <c:url var="sortUrlByRating" value="/adminDashboard">
                        <c:if test="${not empty currentSearch}">
                            <c:param name="searchQuery" value="${currentSearch}"/>
                        </c:if>
                        <c:if test="${not empty param.categoryName}">
                            <c:param name="categoryName" value="${param.categoryName}"/>
                        </c:if>
                        <c:param name="sort" value="averageRating"/>
                        <c:param name="order"
                                 value="${currentSortField == 'averageRating' && currentSortOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                    </c:url>
                    <a style="text-decoration: none"
                       href="${sortUrlByRating}">Rate ${currentSortField == 'averageRating' ? currentSortOrder == 'ASC' ? '&#9650;' : '&#9660;' : ''}</a>
                </th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="film" items="${films}">
                <tr>
                    <td><img src="${film.imageLink}" width="50vh" alt="${film.filmName}"></td>
                    <td>${film.filmName}</td>
                    <td>${film.seasons.size()}</td>
                    <td>${film.episodes.size()}</td>
                    <td>
                        <c:forEach var="category" varStatus="categoryStatus" items="${film.categories}">
                            <a>${category.categoryName}</a><c:if test="${not categoryStatus.last}">, </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="tag" varStatus="tagStatus" items="${film.tags}">
                            ${tag.tagName}<c:if test="${not tagStatus.last}">, </c:if>
                        </c:forEach>
                    </td>
                    <td>${film.viewCount}</td>
                    <td>${film.ratingValue}</td>
                    <td><a href="editFilmPage?filmId=${film.filmID}" class="btn btn-primary">Edit</a></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/deleteFilm" method="POST">
                            <input type="hidden" name="filmId" value="${film.filmID}">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>Thumbnail</th>
                <th>Name</th>
                <th>Seasons</th>
                <th>Episodes</th>
                <th>Categories</th>
                <th>Tags</th>
                <th>Views</th>
                <th>Rate</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </tfoot>
        </table>

        <c:if test="${totalFilms > 0}">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <c:set var="previousPage" value="${currentPage - 1}"/>
                            <c:url value="adminDashboard" var="previousPageURL">
                                <c:param name="page" value="${previousPage}"/>
                                <c:if test="${not empty currentSearch}">
                                    <c:param name="searchQuery" value="${currentSearch}"/>
                                </c:if>
                                <c:if test="${not empty param.categoryName}">
                                    <c:param name="categoryName" value="${param.categoryName}"/>
                                </c:if>
                                <c:if test="${not empty currentSortField}">
                                    <c:param name="sort" value="${currentSortField}"/>
                                </c:if>
                                <c:if test="${not empty currentSortOrder}">
                                    <c:param name="order" value="${currentSortOrder}"/>
                                </c:if>
                            </c:url>
                            <a class="page-link" href="${previousPageURL}" tabindex="-1">Previous</a>
                        </li>
                    </c:if>
                    <c:forEach begin="${pageStart}" end="${pageEnd}" var="i">
                        <c:set var="pageNumberURL">
                            <c:url value="adminDashboard">
                                <c:param name="page" value="${i}"/>
                                <c:if test="${not empty currentSearch}">
                                    <c:param name="searchQuery" value="${currentSearch}"/>
                                </c:if>
                                <c:if test="${not empty param.categoryName}">
                                    <c:param name="categoryName" value="${param.categoryName}"/>
                                </c:if>
                                <c:if test="${not empty currentSortField}">
                                    <c:param name="sort" value="${currentSortField}"/>
                                </c:if>
                                <c:if test="${not empty currentSortOrder}">
                                    <c:param name="order" value="${currentSortOrder}"/>
                                </c:if>
                            </c:url>
                        </c:set>
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="${pageNumberURL}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <c:set var="nextPage" value="${currentPage + 1}"/>
                            <c:url value="adminDashboard" var="nextPageURL">
                                <c:param name="page" value="${nextPage}"/>
                                <c:if test="${not empty currentSearch}">
                                    <c:param name="searchQuery" value="${currentSearch}"/>
                                </c:if>
                                <c:if test="${not empty param.categoryName}">
                                    <c:param name="categoryName" value="${param.categoryName}"/>
                                </c:if>
                                <c:if test="${not empty currentSortField}">
                                    <c:param name="sort" value="${currentSortField}"/>
                                </c:if>
                                <c:if test="${not empty currentSortOrder}">
                                    <c:param name="order" value="${currentSortOrder}"/>
                                </c:if>
                            </c:url>
                            <a class="page-link" href="${nextPageURL}">Next</a>
                        </li>

                    </c:if>
                </ul>
            </nav>
        </c:if>

    </div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>
</body>
</html>