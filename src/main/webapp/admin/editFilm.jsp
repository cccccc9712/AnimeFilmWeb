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
    <div id="edit-film">
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
            <h2>Edit Film</h2>
            <form>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" placeholder="${film.filmName}">
                </div>
                <div class="form-group">
                    <label for="trailerLink">Trailer Link:</label>
                    <input type="text" class="form-control" id="trailerLink" placeholder="${film.trailerLink}">
                </div>
                <div class="form-group">
                    <label for="categories">Categories:</label><br>
                    <% for (Category category : allCategories) {
                        boolean isChecked = filmCategories.stream().anyMatch(fc -> fc.getCategoryName().equals(category.getCategoryName()));
                    %>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="<%=category.getCategoryID()%>"
                               value="<%=category.getCategoryID()%>" <%=isChecked ? "checked" : ""%>>
                        <label class="form-check-label"
                               for="<%=category.getCategoryID()%>"><%=category.getCategoryName()%>
                        </label>
                    </div>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="categories">Tags:</label><br>
                    <% for (Tag tag : allTags) {
                        boolean isChecked = filmTags.stream().anyMatch(fc -> fc.getTagName().equals(tag.getTagName()));
                    %>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" id="<%=tag.getTagID()%>"
                               value="<%=tag.getTagID()%>" <%=isChecked ? "checked" : ""%>>
                        <label class="form-check-label" for="<%=tag.getTagID()%>"><%=tag.getTagName()%>
                        </label>
                    </div>
                    <% } %>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" rows="3">${film.description}</textarea>
                </div>
                <div class="form-group">
                    <label for="thumbnail">Thumbnail:</label>
                    <input type="file" class="form-control-file" id="thumbnail">
                    <img style="margin: 5px 0px 0px 0px" src="${film.imageLink}" width="50vh"
                         alt="${film.filmName}">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
                <button type="button" class="btn btn-secondary ml-2"><a
                        style="color: whitesmoke; text-decoration: none"
                        href="${pageContext.request.contextPath}/adminDashboard">Cancel</a>
                </button>
                <button style="float: right;" type="button" class="btn btn-primary" id="switchToAddSeason">Switch to
                    Add
                    Season
                </button>
            </form>
        </div>
    </div>

    <div id="add-season">
        <div class="container mt-5">
            <c:if test="${not empty errorMessage2}">
                <div class="alert alert-danger" role="alert">
                        ${errorMessage2}
                </div>
            </c:if>
            <c:if test="${not empty successMessage2}">
                <div class="alert alert-success" role="alert">
                        ${successMessage2}
                </div>
            </c:if>
            <h2>Add Season</h2>
            <form id="addSeasonForm">
                <div class="form-group">
                    <label for="seasonName">Season Name:</label>
                    <input type="text" class="form-control" id="seasonName" placeholder="Enter season name">
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary" id="addSeasonBtn">Add Season</button>
                <button style="text-decoration: none" type="button" class="btn btn-secondary ml-2" id="cancelBtn">
                    Cancel
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
                            <form action="#" method="POST">
                                <input type="hidden" name="thumbnail" value="${x.thumbnail}">
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
            <button type="button" class="btn btn-secondary" id="switchToEditFilm">Switch to Edit Film</button>
        </div>
    </div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>

<script>
    $(document).ready(function () {
        $("#switchToAddSeason").click(function () {
            $("#edit-film").hide();
            $("#add-season").show();
        });

        $("#switchToEditFilm").click(function () {
            $("#add-season").hide();
            $("#edit-film").show();
        });
    });
</script>
</body>

</html>
