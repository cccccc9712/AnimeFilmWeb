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
        <ul id="editform" style="margin-bottom: 20px" class="nav nav-tabs">
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
            <form id="editForm" method="post" action="editFilm" enctype="multipart/form-data">
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
            <form action="addSeason" method="POST" id="addSeasonForm">
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
                        <td>
                                ${season.seasonName}
                            <i class="fa fa-edit"
                               onclick="event.stopPropagation(); editSeasonName('${film.filmID}','${season.seasonID}', '${season.seasonName}');"></i>
                        </td>

                        <td>${season.episodes.size()} episodes</td>
                        <td><a href="loadEpisodePage?seasonId=${season.seasonID}" class="btn btn-primary">Add</a></td>
                        <td>
                            <form action="deleteSeason" method="POST" class="deleteSeasonForm">
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
    $(document).ready(function () {
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
    });

    //Xử ly tạo form edit season name và gửi ve server
    function editSeasonName(filmId, seasonId, currentName) {
        var inputField = document.createElement('input');
        inputField.type = 'text';
        inputField.value = currentName;
        inputField.className = 'form-control';
        var td = event.target.parentNode;
        td.innerHTML = '';
        td.appendChild(inputField);
        inputField.focus();
        inputField.onblur = function () {
            td.removeChild(inputField);
            td.innerHTML = currentName + '<i class="fa fa-edit" onclick="event.stopPropagation(); editSeasonName(\'' + filmId + '\', \'' + seasonId + '\', \'' + currentName + '\');"></i>';
        };
        inputField.onkeypress = function (e) {
            if (e.key === 'Enter') {
                var newName = inputField.value;
                console.log("newName: " + newName + " seasonId: " + seasonId + " filmId: " + filmId);
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/AnimeFilmWeb/editSeasonName", true);
                xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            var response = JSON.parse(xhr.responseText);
                            if (response.success) {
                                td.textContent = newName;
                                td.insertAdjacentHTML('beforeend', '<i class="fa fa-edit" onclick="event.stopPropagation(); editSeasonName(\'' + filmId + '\', \'' + seasonId + '\', \'' + newName + '\');"></i>');
                                localStorage.setItem('uploadOccurred', 'true');
                                localStorage.setItem('uploadMessage', response.message);
                                localStorage.setItem('uploadSuccess', response.success);

                                window.location.reload();
                            }
                        } else {
                            console.error('Đã xảy ra lỗi khi gửi yêu cầu AJAX');
                        }
                    }
                };
                console.log("filmId=" + filmId + "&seasonId=" + seasonId + "&seasonName=" + newName);
                xhr.send("filmId=" + filmId + "&seasonId=" + seasonId + "&seasonName=" + newName);

                e.preventDefault();
            }
        };
    }

    //Xu ly thông báo va reload trang
    document.addEventListener('DOMContentLoaded', (event) => {
            const uploadOccurred = localStorage.getItem('uploadOccurred') === 'true';
            const message = localStorage.getItem('uploadMessage');
            const isSuccess = localStorage.getItem('uploadSuccess') === 'true';

            if (uploadOccurred) {
                if (isSuccess && message) {
                    $('#editform').before('<div class="alert alert-success" role="alert">' + message + '</div>');
                } else if (message) {
                    $('#editform').before('<div class="alert alert-danger" role="alert">' + message + '</div>');
                }

                setTimeout(function () {
                    $(".alert").fadeOut("slow");
                }, 2000);

                localStorage.removeItem('uploadMessage');
                localStorage.removeItem('uploadSuccess');
                localStorage.removeItem('uploadOccurred');
            }
        }
    );

    //Update thông tin của film
    $(document).ready(function () {
        $('#editForm').submit(function (event) {
            event.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                type: "POST",
                url: $(this).attr('action'),
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    if (response.success) {
                        localStorage.setItem('uploadOccurred', 'true');
                        localStorage.setItem('uploadMessage', response.message);
                        localStorage.setItem('uploadSuccess', response.success);

                        window.location.reload();
                    } else {
                        alert('Có lỗi xảy ra');
                    }
                },
                error: function () {
                    alert('Lỗi AJAX');
                }
            });
        });
    });

    //Xu ly add season moi
    $('#addSeasonForm').submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: $(this).attr('action'),
            data: $(this).serialize(),
            success: function (response) {
                if (response.success) {
                    localStorage.setItem('uploadOccurred', 'true');
                    localStorage.setItem('uploadMessage', response.message);
                    localStorage.setItem('uploadSuccess', response.success);

                    window.location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function () {
                alert('Có lỗi xảy ra');
            }
        });
    });

    //Xu ly xoa season
    $('.deleteSeasonForm').submit(function (event) {
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: $(this).attr('action'),
            data: $(this).serialize(),
            success: function (response) {
                if (response.success) {
                    localStorage.setItem('uploadOccurred', 'true');
                    localStorage.setItem('uploadMessage', response.message);
                    localStorage.setItem('uploadSuccess', response.success);

                    window.location.reload();
                } else {
                    alert(response.message);
                }
            },
            error: function () {
                alert('Có lỗi xảy ra');
            }
        });
    });
</script>
</body>
</html>
