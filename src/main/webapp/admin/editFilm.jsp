<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="alert alert-danger" role="alert">
            This is a danger alert—check it out!
        </div>
        <div class="alert alert-success" role="alert">
            This is a success alert—check it out!
        </div>
        <h2>Edit Film</h2>
        <form>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" class="form-control" id="name" placeholder="Enter name">
            </div>
            <div class="form-group">
                <label for="trailerLink">Trailer Link:</label>
                <input type="text" class="form-control" id="trailerLink" placeholder="Enter trailer link">
            </div>
            <div class="form-group">
                <label for="categories">Categories:</label><br>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="actionAdventure" value="Action/Adventure">
                    <label class="form-check-label" for="actionAdventure">Action/Adventure</label>
                </div>
                <!-- Add other checkboxes here -->
            </div>
            <div class="form-group">
                <label for="categories">Tags:</label><br>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" id="13" value="13">
                    <label class="form-check-label" for="13">13+</label>
                    <input class="form-check-input" type="checkbox" id="16" value="16">
                    <label class="form-check-label" for="16">16+</label>
                    <input class="form-check-input" type="checkbox" id="18" value="18">
                    <label class="form-check-label" for="18">18+</label>
                </div>
                <!-- Add other checkboxes here -->
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" id="description" rows="3"></textarea>
            </div>
            <div class="form-group">
                <label for="thumbnail">Thumbnail:</label>
                <input type="file" class="form-control-file" id="thumbnail">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <button type="button" class="btn btn-secondary ml-2"><a style="color: whitesmoke; text-decoration: none" href="adminDashboard.jsp">Cancel</a></button>
            <button style="float: right;" type="button" class="btn btn-primary" id="switchToAddSeason">Switch to Add
                Season
            </button>
        </form>
    </div>
</div>

<div id="add-season">
    <div class="container mt-5">
        <div class="alert alert-danger" role="alert">
            This is a danger alert—check it out!
        </div>
        <div class="alert alert-success" role="alert">
            This is a success alert—check it out!
        </div>
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
            <button style="text-decoration: none" type="button" class="btn btn-secondary ml-2" id="cancelBtn">Cancel</button>
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
                <th>Upload date</th>
                <th>Add episodes</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><img src="" width="50vh" alt="" srcset=""></td>
                <td>name</td>
                <td>Season name</td>
                <td>episodes</td>
                <td>uploadDate</td>
                <td><a href="uploadEpisode.jsp" class="btn btn-primary">Add</a></td>
                <td>
                    <form action="#" method="POST">
                        <input type="hidden" name="thumbnail" value="${x.thumbnail}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th>Thumbnail</th>
                <th>Name</th>
                <th>Season name</th>
                <th>Episodes</th>
                <th>Upload date</th>
                <th>Add episodes</th>
                <th>Delete</th>
            </tr>
            </tfoot>
        </table>
        <button type="button" class="btn btn-secondary" id="switchToEditFilm">Switch to Edit Film</button>
    </div>
</div>
</div>>
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
