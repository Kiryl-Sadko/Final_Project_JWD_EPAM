<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/24/2020
  Time: 12:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lang" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@page import="by.sadko.training.SecurityContext" %>
<%@page errorPage="error_page.jsp" %>

<head>

    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>

    <title><fmt:message key="string.title"/></title>

    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">


    <script src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
    <script src="js/dynamic-form.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    <%--Localization--%>
    <c:choose>
        <c:when test="${('en').equals(requestScope.lang)}">
            <fmt:setLocale value="en"/>
        </c:when>

        <c:otherwise>
            <fmt:setLocale value="ru"/>
        </c:otherwise>
    </c:choose>
    <fmt:setBundle basename="/i18n/ApplicationMessages" scope="application"/>

    <style>
        ul {
            padding: 0;
            margin: 0; /* Убираем отступы у списка */
        }

        div.left-padding {
            padding-left: 280px;
        }
    </style>
    <style>
        /* Make the image fully responsive */
        .carousel-inner img {
            width: 960px;
            height: 520px;
        }
    </style>
</head>

<body class="d-flex flex-column" style="min-height: 100%; position: relative;">

<!--Navigation bar-->
<header>

    <%--Links of the language--%>
    <div class="hero-head">
        <lang:lang/>
    </div>

    <jsp:include page="nav_bar.jsp"/>
</header>

<!--Page content-->
<section style="flex: 1;">

    <%--Backgorung page--%>
    <div style="background-image: url('http://localhost:8080/final_war/static/images/back.jpg');
background-repeat: no-repeat; background-size: cover; background-position: center center; height: 100px"></div>


    <div class="container-fluid">
        <div class="row">

            <%--Displaying wallet balance--%>
            <div class="col col-lg-2">
                <div>
                    <c:if test="${SecurityContext.getInstance().isLoggedIn()}">
                        <div class="col-lg-12 text">
                            <jsp:include page="views/balance_view.jsp"/>
                        </div>
                    </c:if>
                </div>
            </div>

            <%--Displaying command view--%>
            <div class="col">
                <div class="columns">
                    <c:choose>
                        <c:when test="${not empty viewName}">

                            <jsp:include page="views/${viewName}.jsp"/>
                        </c:when>

                        <c:otherwise>
                            <div style="height: 650px">
                                <div class="col-lg-12 left-padding">
                                    <h2 class="mt-5"><fmt:message key="string.welcome"/></h2>
                                    <p class="lead"><fmt:message key="string.welcome.message"/></p>
                                </div>

                                    <%--The slideshow--%>
                                <div class="carousel slide" data-ride="carousel"
                                     data-interval="3000">
                                    <div id="slide-show" class="carousel-inner">
                                        <ol class="carousel-indicators" style="padding-right: 240px;">
                                            <li data-target="#slide-show" data-slide-to="0" class="active"></li>
                                            <li data-target="#slide-show" data-slide-to="1"></li>
                                            <li data-target="#slide-show" data-slide-to="2"></li>
                                        </ol>

                                        <div class="carousel-item active">
                                            <img class="d-block"
                                                 src="<c:url value="/static/images/slide_1.png"/>"
                                                 width="960" height="520" alt="First slide">
                                        </div>
                                        <div class="carousel-item">
                                            <img class="d-block"
                                                 src="<c:url value="/static/images/slide_2.png"/>"
                                                 width="960" height="520" alt="Second slide">
                                        </div>
                                        <div class="carousel-item">
                                            <img class="d-block"
                                                 src="<c:url value="/static/images/slide_3.png"/>"
                                                 width="960" height="520" alt="Thread slide">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</section>

<!--Footer-->
<footer id="sticky-footer" class="py-3 bg-dark text-white-50">
    <div class="container text-center">
        <mediun>Copyright &copy; PMService</mediun>
    </div>
</footer>

</body>

<%--Script of the --%>
<script>
    $(document).ready(function () {
        var arr = [];

        var temp = $(this);

        $('select[name="operation"]').each(function (index) {
            arr.push($(this).val());
        });

        $('input[name="operation_queue"]').val(arr);

        $(".add-operation").click(function () {
                $(this).parent("div").append('<select class="form-control col-md-2" name="operation"><option value=""><fmt:message key="string.choose.operation"/> </option><c:forEach items="${requestScope.operation_list}" var="operation"><option value="${operation.id}"><c:out value="${operation.type.name()}"/></option></c:forEach></select>');

                var arr = [];

                var temp = $(this);

                $('select[name="operation"]').each(function (index) {
                    arr.push($(this).val());
                });
                $('input[name="operation_queue"]').val(arr);

                $('select[name="operation"]').change(function () {
                    var arr = [];

                    var temp = $(this);

                    $('select[name="operation"]').each(function (index) {
                        arr.push($(this).val());
                    });

                    $('input[name="operation_queue"]').val(arr);

                });
            }
        )
    });
</script>

<script>
    <fmt:message key="string.password.match" var="message"/>
    var check = function () {
        if (document.getElementById('password').value == document.getElementById('confirm_password').value) {
            document.getElementById('submit').disabled = false;

        } else {
            document.getElementById('submit').disabled = true;
        }
    }
</script>
