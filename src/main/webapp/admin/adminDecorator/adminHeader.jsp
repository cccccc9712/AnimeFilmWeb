<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .navbar-brand {
        padding-top: 0;
        padding-bottom: 0;
    }
    .navbar-custom {
        background-color: #303440;
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
        <a class="navbar-brand" href="../home">
            <img src="../img/LOgo.png" height="30" alt="Logo">
        </a>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user"></i> Username
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="#">Profile</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">Logout</a>
                </div>
            </li>
        </ul>
    </div>
</nav>

