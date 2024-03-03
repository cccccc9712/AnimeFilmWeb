<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="adminDecorator/adminHead.jsp" %>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>
<div style="margin-bottom: 10px" class="wrapper">
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
        <h2>Thêm Film</h2>
        <form action="addFilm" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" name="name" class="form-control" id="name" placeholder="Enter name">
            </div>
            <div class="form-group">
                <label for="trailerLink">Trailer Link:</label>
                <input type="text" name="trailerLink" class="form-control" id="trailerLink"
                       placeholder="Enter trailer link">
            </div>
            <div class="form-group">
                <label for="categories">Categories:</label><br>
                <c:forEach items="${categories}" var="category">
                    <div style="margin-left: 20px">
                        <input class="form-check-input" id="${category.categoryID}" type="checkbox" name="categories"
                               value="${category.categoryID}">
                        <label class="form-check-label"
                               for="${category.categoryID}">${category.categoryName}</label><br>
                    </div>
                </c:forEach>
            </div>
            <div class="form-group">
                <label for="categories">Tags:</label><br>
                <div class="form-check form-check-inline">
                    <c:forEach items="${tags}" var="tag">
                        <input class="form-check-input" name="tags" type="checkbox" id="${tag.tagID}"
                               value="${tag.tagID}">
                        <label class="form-check-label" for="${tag.tagID}">${tag.tagName}</label>
                    </c:forEach>
                </div>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea class="form-control" name="description" id="description" rows="3"></textarea>
            </div>
            <div class="form-group">
                <label for="thumbnail">Thumbnail:</label>
                <input type="file" name="thumbnail" class="form-control-file" id="thumbnail">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
            <button type="button" class="btn btn-secondary ml-2"><a style="color: whitesmoke; text-decoration: none"
                                                                    href="${pageContext.request.contextPath}/adminDashboard">Cancel</a>
            </button>
        </form>
    </div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>
</body>
</html>

