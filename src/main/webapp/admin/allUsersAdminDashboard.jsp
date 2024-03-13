<%--
  Created by IntelliJ IDEA.
  User: tmtmt
  Date: 3/13/2024
  Time: 6:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@include file="adminDecorator/adminHead.jsp" %>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>
<br><br>
<div class="wrapper">
    <div class="container">
        <%@include file="adminDecorator/adminCard.jsp" %>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">UserName</th>
                    <th scope="col">UserEmail</th>
                    <th scope="col">Is Admin</th>
                    <th scope="col">Is Premium</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${allUsers}" var="user">
                    <tr>
                        <td>${user.userName}</td>
                        <td>${user.userGmail}</td>
                        <td>${user.getAdmin()}</td>
                        <td>${user.isPremium()}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>
</body>
</html>
