<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="adminDecorator/adminHead.jsp" %>
<body>

<%@include file="adminDecorator/adminHeader.jsp" %>
<div style="margin-bottom: 10px" class="wrapper">
<div class="container mt-5">
    <div class="alert alert-danger" role="alert">
        This is a danger alert—check it out!
    </div>
    <div class="alert alert-success" role="alert">
        This is a success alert—check it out!
    </div>
    <h2>Thêm Film</h2>
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
        <button type="button" class="btn btn-secondary ml-2"><a style="color: whitesmoke" href="adminDashboard.jsp">Cancel</a></button>
    </form>
</div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>

</body>
</html>

