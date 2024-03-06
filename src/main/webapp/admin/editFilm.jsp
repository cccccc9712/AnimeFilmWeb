<%@ page import="dtos.filmDtos" %>
<%@ page import="entity.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    filmDtos film = (filmDtos) request.getAttribute("film");
    List<Category> allCategories = (List<Category>) request.getAttribute("categories");
    List<Category> filmCategories = film.getCategories();
    List<Tag> allTags = (List<Tag>) request.getAttribute("tags");
    List<Tag> filmTags = film.getTags();
%>
<!DOCTYPE html>
<html lang="en">
<%@include file="adminDecorator/adminHead.jsp" %>
<style>
    #add-season {
        display: none;
    }
</style>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>
<div class="wrapper">
    <div class="container mt-5">
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                    ${errorMessage}
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                    ${successMessage}
            </div>
        </c:if>
        <ul style="margin-bottom: 20px" class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#tab-1"
                >Edit Film</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#tab-2"
                >Add Season</a>
            </li>
        </ul>
        <div class="tab-pane fade show active" id="tab-1">
            <h2>Edit Film</h2>
            <form action="editFilm" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" name="filmName" class="form-control" id="name" placeholder="${film.filmName}">
                </div>
                <div class="form-group">
                    <label for="trailerLink">Trailer Link:</label>
                    <input type="text" name="trailerLink" class="form-control" id="trailerLink"
                           placeholder="${film.trailerLink}">
                </div>
                <div class="form-group">
                    <label for="categories">Categories:</label><br>
                    <% for (Category category : allCategories) {
                        boolean isCateChecked = filmCategories.stream().anyMatch(fc -> fc.getCategoryName().equals(category.getCategoryName()));
                    %>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" name="categories" type="checkbox"
                               value="<%=category.getCategoryID()%>" <%=isCateChecked ? "checked" : ""%>>
                        <label class="form-check-label"
                        ><%=category.getCategoryName()%>
                        </label>
                    </div>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="categories">Tags:</label><br>
                    <% for (Tag tag : allTags) {
                        boolean isTagChecked = filmTags.stream().anyMatch(fc -> fc.getTagName().equals(tag.getTagName()));
                    %>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" name="tags" type="checkbox"
                               value="<%=tag.getTagID()%>" <%=isTagChecked ? "checked" : ""%>>
                        <label class="form-check-label"><%=tag.getTagName()%>
                        </label>
                    </div>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" name="description" id="description"
                              rows="3">${film.description}</textarea>
                </div>
                <div class="form-group">
                    <label for="thumbnail">Thumbnail:</label>
                    <input type="file" name="thumbnail" class="form-control-file" id="thumbnail">
                    <img style="margin: 5px 0px 0px 0px" src="${film.imageLink}" width="50vh"
                         alt="${film.filmName}">
                </div>
                <input name="filmId" type="hidden" value="${film.filmID}">
                <button type="submit" class="btn btn-primary">Submit</button>
                <button type="button" class="btn btn-secondary ml-2"><a
                        style="color: whitesmoke; text-decoration: none"
                        href="${pageContext.request.contextPath}/adminDashboard">Cancel</a>
                </button>
            </form>
        </div>
        <div class="tab-pane fade" id="tab-2">
            <h2>Add Season</h2>
            <form action="addSeason" method="get" id="addSeasonForm">
                <input type="hidden" name="filmId" value="${film.filmID}"/>
                <div class="form-group">
                    <label for="seasonName">Season Name:</label>
                    <input type="text" name="seasonName" class="form-control" id="seasonName"
                           placeholder="Enter season name">
                </div>
                <button type="submit" class="btn btn-primary" id="addSeasonBtn">Add Season</button>
                <button style="text-decoration: none" type="button" class="btn btn-secondary ml-2" id="cancelBtn">
                    <a style="color: whitesmoke; text-decoration: none"
                       href="${pageContext.request.contextPath}/adminDashboard">Cancel</a>
                </button>
            </form>
            <hr>
            <h3>Seasons Information</h3>
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Thumbnail</th>
                    <th>Name</th>
                    <th>Season name</th>
                    <th>Episodes</th>
                    <th>Add episodes</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="season" items="${seasons}">
                    <tr>
                        <td><img src="${film.imageLink}" width="50vh" alt="${film.filmName}" srcset=""></td>
                        <td>${film.filmName}</td>
                        <td>${season.seasonName}</td>
                        <td>${season.episodes.size()} episodes</td>
                        <td><a href="uploadEpisode.jsp" class="btn btn-primary">Add</a></td>
                        <td>
                            <form action="deleteSeason" method="POST">
                                <input type="hidden" name="filmId" value="${film.filmID}">
                                <input type="hidden" name="seasonId" value="${season.seasonID}">
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
                    <th>Season name</th>
                    <th>Episodes</th>
                    <th>Add episodes</th>
                    <th>Delete</th>
                </tr>
                </tfoot>
            </table>
        </div>
    </div>

</div>
<%@include file="adminDecorator/adminFooter.jsp" %>

<script>
    $(document).ready(function() {
        // Ẩn nội dung của tab-2 ngay khi trang được tải
        $("#tab-2").hide();

        // Khi một tab được click, hiển thị nội dung tương ứng và ẩn các tab khác
        $('.nav-tabs a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
            var tabId = $(this).attr('href');
            $(".tab-pane").not(tabId).css("display", "none");
            $(tabId).fadeIn();
        });

        // Tự động ẩn thông báo sau 2 giây
        setTimeout(function() {
            $(".alert").fadeOut("slow");
        }, 2000);
    });

</script>
</body>
</html>
