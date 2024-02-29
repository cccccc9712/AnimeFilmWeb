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
        <td>name</td>
        <td>uploadDate</td>
        <td>Episode Link</td>
        <td>Is Premium</td>
        <td>
            <form action="#" method="POST">
                <input type="hidden" name="thumbnail" value="${x.thumbnail}">
                <button type="submit" class="btn btn-danger">Delete</button>
            </form>
        </td>
        </tbody>
        <tfoot>
        <!-- Optional footer -->
        </tfoot>
    </table>
</div>

<%@include file="adminDecorator/adminFooter.jsp" %>
<script>
    // function uploadVideo() {
    //     var formData = new FormData();
    //     formData.append('file', document.getElementById('videoFile').files[0]);
    //     formData.append('title', document.getElementById('videoName').value);
    //     formData.append('private', document.getElementById('isPremium').checked ? 'true' : 'false');
    //
    //     var xhr = new XMLHttpRequest();
    //     xhr.open('POST', 'YOUR_API_ENDPOINT', true);
    //     xhr.upload.onprogress = function(e) {
    //         if (e.lengthComputable) {
    //             var percentComplete = (e.loaded / e.total) * 100;
    //             $('.progress-bar').width(percentComplete + '%').text(Math.round(percentComplete) + '%');
    //         }
    //     };
    //     xhr.onload = function() {
    //         if (this.status == 200) {
    //             // Handle the response content...
    //             console.log(this.response);
    //             // Ideally, add the new episode to the table
    //         } else {
    //             // Handle error case
    //         }
    //     };
    //     xhr.send(formData);
    // }
</script>
</body>
</html>
