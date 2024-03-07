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
                            <source src="${episode.epLink}" type="video/mp4">
                            <!-- Fallback for browsers that don't support the <video> element -->
                            <a href="${episode.epLink}" download>Download</a>
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
</script>
<%--End Script--%>
</body>
</html>
