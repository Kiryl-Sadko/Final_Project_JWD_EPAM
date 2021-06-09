<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="lang" tagdir="/WEB-INF/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/31/2020
  Time: 5:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isErrorPage="true" %>

<html>
<head>
    <title>ERROR</title>

    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">

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
</head>

<body class="d-flex flex-column" style="min-height: 100%; position: relative;">

<!--Navigation bar-->
<header>
    <div class="hero-head">
        <lang:lang/>
    </div>
    <jsp:include page="nav_bar.jsp"/>
</header>

<!--Page content-->
<section style="flex: 1;">

    <%--Background page--%>
    <div style="background-image: url('http://localhost:8080/final_war/static/images/back.jpg');
background-repeat: no-repeat; background-size: cover; background-position: center center; height: 100px"></div>

    <%--Error message--%>
    <div class="text-center">
        <br/>
        <h3>
            <fmt:message key="string.error"/>
        </h3>

        <br/>
        <c:if test="${not empty pageContext.exception.message}">
            <b>
                <fmt:message key="string.error.message"/>
            </b>
            <c:out value="${pageContext.exception.message}"/>
        </c:if>
    </div>
</section>

<!--Footer-->
<footer id="sticky-footer" class="py-3 bg-dark text-white-50">
    <div class="container text-center">
        <mediun>Copyright &copy; PMService</mediun>
    </div>
</footer>

</body>
</html>
