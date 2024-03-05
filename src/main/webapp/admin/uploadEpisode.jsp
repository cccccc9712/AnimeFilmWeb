<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="adminDecorator/adminHead.jsp" %>
<style>
    .progress {
        margin-top: 20px;
    }
</style>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>

<div class="container mt-5">
    <form id="uploadForm">
        <div class="form-group">
            <label for="videoName">Video Name</label>
            <input type="text" class="form-control" id="videoName" name="name" required>
        </div>
        <div class="form-group">
            <label for="videoFile">Upload Video</label>
            <input type="file" class="form-control-file" id="videoFile" required>
            <div class="progress">
                <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
                     style="width: 0%">
                    0%
                </div>
            </div>
        </div>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="isPremium">
            <label class="form-check-label" for="isPremium">Premium</label>
        </div>
        <button style="margin: 20px 0px;" type="button" class="btn btn-primary" onclick="uploadVideo()">Upload Video
        </button>
    </form>

    <h3>Seasons Information</h3>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Title</th>
            <th>Upload Date</th>
            <th>Episode Link</th>
            <th>Is Premium</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody id="episodeList">
        <c:forEach var="episodes" items="${episodes}">
            <tr>
                <td>${episodes.epTittle}</td>
                <td>${episodes.epDate}</td>
                <td>${episodes.epLink}</td>
                <td>${episodes.epLink}</td>
                <td>
                    <form action="#" method="POST">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <!-- Optional footer -->
        </tfoot>
    </table>
</div>

<%@include file="adminDecorator/adminFooter.jsp" %>
</body>
</html>
