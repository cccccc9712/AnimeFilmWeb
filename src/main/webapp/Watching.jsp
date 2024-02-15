<%--
  Created by IntelliJ IDEA.
  User: tmtmt
  Date: 1/24/2024
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="isYoutubeLink"
       value="${fn:contains(episode.epLink, 'youtube.com') or fn:contains(episode.epLink, 'youtu.be')}"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@include file="decorator/head.jsp" %>
<body>
<%--Header--%>
<%@include file="decorator/header.jsp" %>
<%--End Header--%>
<style>
    /* Container for the video player */
    .video-container {
        position: relative;
        width: 100%;
        padding-top: 56.25%; /* Tỉ lệ 16:9 */
        overflow: hidden;
    }

    .video-container iframe {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
</style>
<div class="container">

    <div class="row">
        <div class="col-12">
            <div class="video-container" style="text-align: center; padding-top: 100px">
                <c:choose>
                    <c:when test="${isYoutubeLink}">
                        <!-- Nếu link là YouTube, hiển thị iframe -->
                        <div class="video-container">
                            <iframe src="${episode.epLink}" frameborder="0"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                                    allowfullscreen></iframe>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <video controls crossorigin playsinline poster="" id="player">
                            <!-- Video files -->
                            <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-576p.mp4" type="video/mp4" size="576">
                            <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-720p.mp4" type="video/mp4" size="720">
                            <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-1080p.mp4" type="video/mp4" size="1080">
                            <source src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-1440p.mp4" type="video/mp4" size="1440">

                            <!-- Caption files -->
                            <track kind="captions" label="English" srclang="en" src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-HD.en.vtt"
                                   default>
                            <track kind="captions" label="Français" srclang="fr" src="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-HD.fr.vtt">

                            <!-- Fallback for browsers that don't support the <video> element -->
                            <a href="https://cdn.plyr.io/static/demo/View_From_A_Blue_Moon_Trailer-576p.mp4" download>Download</a>
                        </video>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="details__wrap" style="margin-bottom: 20px">
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
<%--Footer--%>
<%@include file="decorator/footer.jsp" %>
<%--End Footer--%>
<%--Script--%>
<%@include file="decorator/script.jsp" %>
<script>
    <%--window.onload = function() {--%>
    <%--    updateViewCount();--%>
    <%--};--%>

    <%--// document.addEventListener("DOMContentLoaded", function () {--%>
    <%--//     var video = document.getElementById('player'); // Thay 'yourVideoElementId' bằng ID thực tế của video player trong trang của bạn--%>
    <%--//     video.addEventListener('play', function () {--%>
    <%--//         var hasViewed = sessionStorage.getItem('hasViewed');--%>
    <%--//         if (!hasViewed) {--%>
    <%--//             setTimeout(function () {--%>
    <%--//                 if (!video.paused) {--%>
    <%--//                     updateViewCount(); // Gọi AJAX để tăng view trong database--%>
    <%--//                     sessionStorage.setItem('hasViewed', 'true'); // Đánh dấu video đã được xem--%>
    <%--//                 }--%>
    <%--//             }, 3000); // 3000 milliseconds = 3 giây--%>
    <%--//         }--%>
    <%--//     });--%>
    <%--// });--%>

    <%--var filmId = <c:out value="${episode.filmId}" />;--%>
    <%--function updateViewCount() {--%>
    <%--    fetch('AnimeFilmWeb/updateViewCount', {--%>
    <%--        method: 'GET',--%>
    <%--        headers: {'Content-Type': 'application/x-www-form-urlencoded'},--%>
    <%--        body: 'filmId=' + filmId--%>
    <%--    })--%>
    <%--        .then(response => {--%>
    <%--            if (response.ok) {--%>
    <%--                return response.text();--%>
    <%--            } else {--%>
    <%--                throw new Error('Server responded with a status: ' + response.status);--%>
    <%--            }--%>
    <%--        })--%>
    <%--        .then(data => console.log(data))--%>
    <%--        .catch(error => console.error('Error updating view count:', error));--%>
    <%--}--%>
</script>
<%--End Script--%>
</body>
</html>
