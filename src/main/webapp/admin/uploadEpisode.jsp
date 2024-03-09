<%@ page import="java.util.List" %>
<%@ page import="entity.Episode" %>
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
    <form id="uploadForm" enctype="multipart/form-data" onsubmit="event.preventDefault();">
        <div class="form-group">
            <label for="videoName">Video Name</label>
            <input type="text" class="form-control" id="videoName" name="videoName" required>
        </div>
        <div class="form-group">
            <label for="videoFile">Upload Video</label>
            <input type="file" class="form-control-file" name="videoFile" id="videoFile" required>
            <div class="progress">
                <div id="uploadProgress" class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0"
                     aria-valuemax="100"
                     style="width: 0%">
                    0%
                </div>
            </div>
        </div>
        <div class="form-check">
            <input type="checkbox" name="isPremium" class="form-check-input" id="isPremium">
            <label class="form-check-label" for="isPremium">Premium</label>
        </div>
        <button style="margin: 20px 0px;" type="submit" class="btn btn-primary">Upload Video</button>
    </form>

    <h3>Episodes Information</h3>
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
        <c:forEach var="episode" items="${episodes}">
            <tr>
                <td>${episode.epTittle}</td>
                <td>${episode.epDate}</td>
                <td>${episode.epLink}</td>
                <td>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" name="premium" value="${episode.epId}"
                            ${episode.getPremium() ? 'checked' : ''}
                               onchange="updatePremiumStatus(${episode.epId}, this.checked)"/>
                        <label class="form-check-label" for="premiumCheckbox">
                            Premium
                        </label>
                    </div>
                </td>
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

<script type="module">
    // Import the functions you need from the SDKs you need
    import {initializeApp} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-app.js";
    import {getAnalytics} from "https://www.gstatic.com/firebasejs/10.8.1/firebase-analytics.js";
    // TODO: Add SDKs for Firebase products that you want to use
    // https://firebase.google.com/docs/web/setup#available-libraries

    // Your web app's Firebase configuration
    // For Firebase JS SDK v7.20.0 and later, measurementId is optional
    const firebaseConfig = {
        apiKey: "AIzaSyD5plxfNZMk7ISxOCMLKD8DwlbUmQXwE4I",
        authDomain: "animefilmweb.firebaseapp.com",
        projectId: "animefilmweb",
        storageBucket: "animefilmweb.appspot.com",
        messagingSenderId: "309915701188",
        appId: "1:309915701188:web:1f5d64e9ccce666f529e91",
        measurementId: "G-S3T9C6V40R"
    };

    // Initialize Firebase
    const app = initializeApp(firebaseConfig);
    const analytics = getAnalytics(app);
    import {
        getStorage,
        ref,
        uploadBytesResumable,
        getDownloadURL
    } from "https://www.gstatic.com/firebasejs/10.8.1/firebase-storage.js";

    const storage = getStorage(app);

    document.getElementById('uploadForm').addEventListener('submit', function (event) {
        event.preventDefault();
        console.log('Form submitted');
        console.log(document.getElementById('videoName')); // Check if it's null
        console.log(document.getElementById('isPremium')); // Check if it's null
        const file = document.getElementById('videoFile').files[0];
        const storageRef = ref(storage, 'videos/' + file.name);

        // Tạo một tác vụ upload có thể tiếp tục và theo dõi tiến trình của nó
        const uploadTask = uploadBytesResumable(storageRef, file);

        // Lắng nghe sự kiện tiến trình để cập nhật UI
        uploadTask.on('state_changed',
            (snapshot) => {
                // Tính toán phần trăm hoàn thành
                const progress = Math.round((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
                console.log('Upload is ' + progress + '% done');
                var progressBar = document.getElementById('uploadProgress');
                progressBar.style.width = progress + '%';
                progressBar.textContent = progress + '%';
            },
            (error) => {
                // Xử lý bất kỳ lỗi nào xảy ra trong quá trình upload
                console.error("Upload failed:", error);
            },
            () => {
                // Hoàn thành upload: lấy URL tải xuống
                getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
                    console.log('File available at', downloadURL);
                    // Tại đây bạn có thể gửi URL đến server của mình hoặc thực hiện hành động tiếp theo
                    $.ajax({
                        url: '/AnimeFilmWeb/uploadVideo',
                        type: 'POST',
                        data: {
                            videoUrl: downloadURL,
                            seasonId: ${seasonId},
                            episodeName: document.getElementById('videoName').value,
                            isPremium: document.getElementById('isPremium').checked ? 'true' : 'false'
                        },
                        success: function (response) {
                            // Check the response from the server
                            localStorage.setItem('uploadOccurred', 'true'); // Set this when an upload occurs
                            localStorage.setItem('uploadMessage', response.message);
                            localStorage.setItem('uploadSuccess', response.success);

                            // Reload the page
                            window.location.reload();
                        }
                    });
                });
            }
        );
    });
    document.addEventListener('DOMContentLoaded', (event) => {
            // Check if there is an upload message stored
            const uploadOccurred = localStorage.getItem('uploadOccurred') === 'true';
            const message = localStorage.getItem('uploadMessage');
            const isSuccess = localStorage.getItem('uploadSuccess') === 'true';

            if (uploadOccurred) {
                if (isSuccess && message) {
                    // Display success message
                    $('#uploadForm').before('<div class="alert alert-success" role="alert">' + message + '</div>');
                } else if (message) {
                    // Display error message
                    $('#uploadForm').before('<div class="alert alert-danger" role="alert">' + message + '</div>');
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
</script>
<script>
    function updatePremiumStatus(episodeId, isChecked) {
        $.ajax({
            url: '/AnimeFilmWeb/updatePremium',
            type: 'POST',
            data: {
                episodeId: episodeId,
                isPremium: isChecked ? 'true' : 'false'
            },
            success: function (response) {
                localStorage.setItem('uploadOccurred', 'true');
                localStorage.setItem('uploadMessage', response.message);
                localStorage.setItem('uploadSuccess', response.success);

                window.location.reload();
            },
        });
    }
</script>
</body>
</html>
