<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/23/2020
  Time: 9:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Displaying About US page--%>
<section>
    <br/>
    <button:back/>

    <%--The topic About Us--%>
    <div class="row col-lg-10" style="font-size: larger;">
        <b class="align-center" style="padding-left: 420px;"><fmt:message key="string.title"/></b>
        <br/>

        <fmt:message key="topic.1"/>
        <fmt:message key="topic.2"/>
        <fmt:message key="topic.3"/>
        <br/>
        <br/>
        <b><fmt:message key="topic.4"/></b>
    </div>
    <br/>

    <%--Slide-show--%>
    <div class="carousel slide" data-ride="carousel"
         data-interval="3000">
        <div id="slide-show" class="carousel-inner">
            <ol class="carousel-indicators" style="padding-right: 240px;">
                <li data-target="#slide-show" data-slide-to="0" class="active"></li>
                <li data-target="#slide-show" data-slide-to="1"></li>
                <li data-target="#slide-show" data-slide-to="2"></li>
                <li data-target="#slide-show" data-slide-to="3"></li>
            </ol>

            <div class="carousel-item">
                <img class="d-block"
                     src="<c:url value="/static/images/about_us/about_slide_4.jpg"/>"
                     width="960" height="520" alt="Fourth slide">
            </div>
            <div class="carousel-item active">
                <img class="d-block"
                     src="<c:url value="/static/images/about_us/about_slide_1.jpg"/>"
                     width="960" height="520" alt="First slide">
            </div>
            <div class="carousel-item">
                <img class="d-block"
                     src="<c:url value="/static/images/about_us/about_slide_2.jpg"/>"
                     width="960" height="520" alt="Second slide">
            </div>
            <div class="carousel-item">
                <img class="d-block"
                     src="<c:url value="/static/images/about_us/about_slide_3.jpg"/>"
                     width="960" height="520" alt="Thread slide">
            </div>
        </div>
    </div>
    <br/>
</section>
