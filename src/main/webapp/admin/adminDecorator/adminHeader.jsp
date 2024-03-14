<style>
    .navbar-brand {
        padding-top: 0;
        padding-bottom: 0;
    }
    .navbar-custom {
        background-color: #2b2b31;
    }
    .navbar-custom .navbar-nav .nav-link {
        color: white;
    }
    .navbar-custom .navbar-nav .nav-link:hover {
        color: lightgray;
    }
    .navbar-brand img {
        height: 50px; /* Điều chỉnh chiều cao của logo */
        width: auto; /* Đảm bảo chiều rộng tự động điều chỉnh dựa trên chiều cao */
    }
</style>
<nav class="navbar navbar-expand-lg navbar-light navbar-custom">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">
            <img src="${pageContext.request.contextPath}/img/LOgo%20(1).png" height="391" alt="Logo">
        </a>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user"></i> ${sessionScope.userSession.getUserName()}
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="adminDashboard">Dashboard</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="logout">Logout</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

