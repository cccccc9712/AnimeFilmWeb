<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="adminDecorator/adminHead.jsp" %>
<style>
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .card-header .input-group {
        margin-left: auto;
        max-width: 300px;
    }
</style>
<body>
<%@include file="adminDecorator/adminHeader.jsp" %>
<div style="margin-bottom: 10px" class="wrapper">
<div class="container mt-5">
    <div class="row mb-3">
        <div class="col-md-3">
            <a href="#" class="btn btn-info btn-block">All User</a>
        </div>
        <div class="col-md-3">
            <a href="#" class="btn btn-success btn-block">Top Category</a>
        </div>
        <div class="col-md-3">
            <a href="#" class="btn btn-danger btn-block">Top Anime</a>
        </div>
        <div class="col-md-3">
            <a href="#" class="btn btn-warning btn-block">Recycle Bin</a>
        </div>
    </div>

    <div class="card-header">
        <i class="fas fa-table me-1"></i>
        <a style="margin: 0 5px;">All Anime</a>
        <a class="btn btn-primary align-content-lg-start" href="addNewFilms.jsp">Add new</a>
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search...">
        </div>
    </div>


    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>Thumbnail</th>
            <th>Name</th>
            <th>Seasons</th>
            <th>Episodes</th>
            <th>Categories</th>
            <th>Tags</th>
            <th>Views</th>
            <th>Rate</th>
            <th>Upload date</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><img src="" width="50vh" alt="" srcset=""></td>
            <td>name</td>
            <td>seasons</td>
            <td>episodes</td>
            <td>category</td>
            <td>tag</td>
            <td>Views</td>
            <td>Rate</td>
            <td>uploadDate</td>
            <td><a href="editFilm.jsp" class="btn btn-primary">Edit</a></td>
            <td>
                <form action="#" method="POST">
                    <input type="hidden" name="thumbnail" value="${x.thumbnail}">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </td>
        </tr>
        <!-- Add more rows here -->
        </tbody>
        <tfoot>
        <tr>
            <th>Thumbnail</th>
            <th>Name</th>
            <th>Seasons</th>
            <th>Episodes</th>
            <th>Categories</th>
            <th>Tags</th>
            <th>Views</th>
            <th>Rate</th>
            <th>Upload date</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </tfoot>
    </table>

    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
</div>
</div>
<%@include file="adminDecorator/adminFooter.jsp" %>

</body>

</html>