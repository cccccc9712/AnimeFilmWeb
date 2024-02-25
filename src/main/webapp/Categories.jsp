<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="maxPagesToShow" value="5"/>
<c:set var="pageStart" value="${(currentPage - (maxPagesToShow div 2)) > 0 ? (currentPage - (maxPagesToShow div 2)) : 1}"/>
<c:set var="pageEnd" value="${(pageStart + (maxPagesToShow - 1)) < noOfPages ? (pageStart + (maxPagesToShow - 1)) : noOfPages}"/>

<!-- Điều chỉnh nếu số lượng trang không đủ -->
<c:if test="${pageEnd - pageStart < maxPagesToShow - 1}">
    <c:set var="pageStart" value="${(pageEnd - (maxPagesToShow - 1)) > 0 ? (pageEnd - (maxPagesToShow - 1)) : 1}"/>
</c:if>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<%@include file="decorator/head.jsp"%>
<body class="body">
<%@include file="decorator/header.jsp"%>
<!-- page title -->
<section class="section section--first section--bg" data-bg="img/section/section.jpg">
  <div class="container">
    <div class="row">
      <div class="col-12">
        <div class="section__wrap">
          <!-- section title -->
          <h2 class="section__title">All films</h2>
          <!-- end section title -->

          <!-- breadcrumb -->
          <ul class="breadcrumb">
            <li class="breadcrumb__item"><a href="home">Home</a></li>
            <li class="breadcrumb__item breadcrumb__item--active">Catalog list</li>
          </ul>
          <!-- end breadcrumb -->
        </div>
      </div>
    </div>
  </div>
</section>
<!-- end page title -->

<!-- filter -->
<div class="filter">
  <div class="container">
    <div class="row">
      <div class="col-12">
        <form action="category" method="get">
          <div class="filter__content">
            <div class="filter__items">
              <!-- filter item -->
              <div class="filter__item" id="filter__genre">
                <span class="filter__item-label">GENRE:</span>

                <!-- Sửa đổi ở đây: Thêm input ẩn -->
                <input type="hidden" name="categoryName" id="categoryInput">

                <div class="filter__item-btn dropdown-toggle" role="navigation" id="filter-genre" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <input type="button" value="${empty currentCategory ? 'All films' : currentCategory}" />
                  <span></span>
                </div>

                <ul class="filter__item-menu dropdown-menu scrollbar-dropdown" aria-labelledby="filter-genre">
                  <c:forEach items="${cate}" var="category">
                    <!-- Sử dụng data-attribute để lưu categoryName -->
                    <li onclick="selectCategory('${category.categoryName}');">${category.categoryName}</li>
                  </c:forEach>
                </ul>
              </div>
              <!-- end filter item -->
            </div>
            <!-- filter btn -->
            <button class="filter__btn" type="submit">apply filter</button>
            <!-- end filter btn -->
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<!-- end filter -->

<!-- catalog -->
<div class="catalog">
  <div class="container">
    <div class="row">
      <!-- card -->
      <c:forEach items="${films}" var="film">
      <div class="col-6 col-sm-12 col-lg-6">
        <div class="card card--list">
          <div class="row">
            <div class="col-12 col-sm-4">
              <div class="card__cover">
                <img src="${film.imageLink}" alt="${film.filmName}">
                <a href="detail?filmName=${film.filmName}" class="card__play">
                  <i class="icon ion-ios-play"></i>
                </a>
              </div>
            </div>

            <div class="col-12 col-sm-8">
              <div class="card__content">
                <h3 class="card__title"><a href="detail?filmName=${film.filmName}">${film.filmName}</a></h3>
                <span class="card__category">
										<c:forEach items="${film.categories}" var="category">
                                          <a href="category?categoryName=${category.categoryName}">${category.categoryName}</a>
                                        </c:forEach>
									</span>

                <div class="card__wrap">
                  <span class="card__rate">
                    <i class="icon ion-ios-star"></i>${film.ratingValue}
                     <i  style="margin-left: 10px" class="icon ion-ios-eye"></i>${film.viewCount}
                  </span>

                  <ul class="card__list">
                    <c:forEach items="${film.tags}" var="tag">
                      <li>${tag.tagName}</li>
                    </c:forEach>
                  </ul>
                </div>

                <div class="card__description">
                  <p>${film.description}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      </c:forEach>
      <!-- end card -->

      <!-- paginator -->
      <c:if test="${not empty films and films.size() > 0}">
      <div class="col-12">
        <ul class="paginator paginator--list">
            <c:if test="${currentPage > 1}">
                <li class="paginator__item">
                    <c:choose>
                        <c:when test="${not empty currentSearch}">
                            <a href="category?page=${currentPage - 1}&searchQuery=${currentSearch}"><i class="icon ion-ios-arrow-back"></i></a>
                        </c:when>
                        <c:when test="${not empty currentCategory}">
                            <a href="category?page=${currentPage - 1}&categoryName=${currentCategory}"><i class="icon ion-ios-arrow-back"></i></a>
                        </c:when>
                        <c:otherwise>
                            <a href="category?page=${currentPage - 1}"><i class="icon ion-ios-arrow-back"></i></a>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:if>
          <c:forEach begin="1" end="${noOfPages}" var="i">
            <li class="paginator__item ${currentPage == i ? 'paginator__item--active' : ''}">
              <c:choose>
                <c:when test="${not empty currentSearch}">
                  <a href="category?page=${i}&searchQuery=${currentSearch}">${i}</a>
                </c:when>
                <c:when test="${not empty currentCategory}">
                  <a href="category?page=${i}&categoryName=${currentCategory}">${i}</a>
                </c:when>
                <c:otherwise>
                  <a href="category?page=${i}">${i}</a>
                </c:otherwise>
              </c:choose>
            </li>
          </c:forEach>
          <c:if test="${currentPage < noOfPages}">
            <li class="paginator__item">
              <c:choose>
                <c:when test="${not empty currentSearch}">
                  <a href="category?page=${currentPage + 1}&searchQuery=${currentSearch}"><i class="icon ion-ios-arrow-forward"></i></a>
                </c:when>
                <c:when test="${not empty currentCategory}">
                  <a href="category?page=${currentPage + 1}&categoryName=${currentCategory}"><i class="icon ion-ios-arrow-forward"></i></a>
                </c:when>
                <c:otherwise>
                  <a href="category?page=${currentPage + 1}"><i class="icon ion-ios-arrow-forward"></i></a>
                </c:otherwise>
              </c:choose>
            </li>
          </c:if>
        </ul>
      </div>
      </c:if>
      <c:if test="${empty films or films.size() == 0}">
        <div class="col-12">
          <h2 class="section__title">No result based on your search.</h2>
        </div>
      </c:if>
      <!-- end paginator -->
    </div>
  </div>
</div>
<!-- end catalog -->

<!-- expected premiere -->
<section class="section section--bg" data-bg="img/section/section.jpg">
  <div class="container">
    <div class="row">
      <!-- section title -->
      <div class="col-12">
        <h2 class="section__title">Expected premiere</h2>
      </div>
      <!-- end section title -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">I Dream in Another Language</a></h3>
            <span class="card__category">
								<a href="#">Action</a>
								<a href="#">Triler</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>8.4</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover3.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">Benched</a></h3>
            <span class="card__category">
								<a href="#">Comedy</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>7.1</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover2.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">Whitney</a></h3>
            <span class="card__category">
								<a href="#">Romance</a>
								<a href="#">Drama</a>
								<a href="#">Music</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>6.3</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover6.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">Blindspotting</a></h3>
            <span class="card__category">
								<a href="#">Comedy</a>
								<a href="#">Drama</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>7.9</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover4.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">I Dream in Another Language</a></h3>
            <span class="card__category">
								<a href="#">Action</a>
								<a href="#">Triler</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>8.4</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- card -->
      <div class="col-6 col-sm-4 col-lg-3 col-xl-2">
        <div class="card">
          <div class="card__cover">
            <img src="img/covers/cover5.jpg" alt="">
            <a href="#" class="card__play">
              <i class="icon ion-ios-play"></i>
            </a>
          </div>
          <div class="card__content">
            <h3 class="card__title"><a href="#">Benched</a></h3>
            <span class="card__category">
								<a href="#">Comedy</a>
							</span>
            <span class="card__rate"><i class="icon ion-ios-star"></i>7.1</span>
          </div>
        </div>
      </div>
      <!-- end card -->

      <!-- section btn -->
      <div class="col-12">
        <a href="#" class="section__btn">Show more</a>
      </div>
      <!-- end section btn -->
    </div>
  </div>
</section>
<!-- end expected premiere -->

<!-- footer -->
<%@include file="decorator/footer.jsp"%>
<!-- end footer -->

<!-- JS -->
<%@include file="decorator/script.jsp"%>

<script>
  // Hàm để cập nhật giá trị input ẩn khi một category được chọn
  function selectCategory(categoryName) {
    document.getElementById('categoryInput').value = categoryName;
    // Cập nhật text cho nút, nếu bạn muốn hiển thị tên category đã chọn
    document.getElementById('filter-genre').querySelector('input[type="button"]').value = categoryName;
  }
</script>
</body>

</html>