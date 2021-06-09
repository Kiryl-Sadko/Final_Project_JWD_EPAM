<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@ taglib prefix="security" tagdir="/WEB-INF/tags/security" %>

<%-- Navigation bar--%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
    <div class="container">

        <%-- Main label --%>
        <c:choose>
            <c:when test="${securityContext.loggedIn}">
                <a class="navbar-brand"
                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.INDEX}"/>">

                    <fmt:message key="string.title"/></a>
            </c:when>

            <c:otherwise>
                <a class="navbar-brand"
                   href="<c:out value="${pageContext.request.contextPath}/"/>">
                    <fmt:message key="string.title"/></a>
            </c:otherwise>
        </c:choose>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <%--Items--%>
        <div class="py-2 collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">

                <jsp:useBean id="securityContext" type="by.sadko.training.SecurityContext" scope="application"/>

                <%--About Us--%>
                <security:checkAutorization commandName="${AppCommandName.DISPLAY_ABOUT_US}">
                    <li class="nav-item active">
                        <a class="nav-link"
                           href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.DISPLAY_ABOUT_US}"/>">
                            <fmt:message key="links.about"/></a>
                    </li>
                </security:checkAutorization>

                <c:choose>
                    <c:when test="${securityContext.loggedIn}">

                        <%--Workload--%>
                        <security:checkAutorization commandName="${AppCommandName.WORKLOAD_DISPLAY_ALL}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.WORKLOAD_DISPLAY_ALL}"/>">
                                    <fmt:message key="links.main.workload.list"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--Products--%>
                        <security:checkAutorization commandName="${AppCommandName.PRODUCT_DISPLAY_ALL}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.PRODUCT_DISPLAY_ALL}"/>">
                                    <fmt:message key="links.main.product.list"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--New product--%>
                        <security:checkAutorization commandName="${AppCommandName.PRODUCT_DISPLAY_CREATION}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.PRODUCT_DISPLAY_CREATION}"/>">
                                    <fmt:message key="links.main.product.creation"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--Materials--%>
                        <security:checkAutorization commandName="${AppCommandName.MATERIAL_DISPLAY_ALL}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.MATERIAL_DISPLAY_ALL}"/>">
                                    <fmt:message key="links.main.material.list"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--New material--%>
                        <security:checkAutorization commandName="${AppCommandName.MATERIAL_DISPLAY_CREATION}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.MATERIAL_DISPLAY_CREATION}"/>">
                                    <fmt:message key="links.main.material.create"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--My contracts--%>
                        <security:checkAutorization commandName="${AppCommandName.CONTRACT_DISPLAY_ALL}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.CONTRACT_DISPLAY_ALL}"/>">
                                    <fmt:message key="links.main.contract.list"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--New contract--%>
                        <security:checkAutorization commandName="${AppCommandName.CONTRACT_DISPLAY_CREATION}">
                            <li class="nav-item active">
                                <a class="nav-link"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.CONTRACT_DISPLAY_CREATION}"/>">
                                    <fmt:message key="links.main.contract.create"/></a>
                            </li>
                        </security:checkAutorization>

                        <%--LogOut--%>
                        <li class="nav-item active">
                            <a class="nav-link"
                               href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.LOGOUT}"/>">
                                <fmt:message key="links.user.logOut"/></a>
                        </li>
                    </c:when>

                    <c:otherwise>

                        <%--Sign Up--%>
                        <li class="nav-item active">
                            <a class="nav-link"
                               href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.SIGN_UP_DISPLAY}"/>">
                                <span class="glyphicon glyphicon-user"></span>
                                <fmt:message key="links.user.signUp"/></a>
                        </li>

                        <%--Log In--%>
                        <li class="nav-item active">
                            <a class="nav-link"
                               href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.LOGIN_DISPLAY}"/>">
                                <span class="glyphicon glyphicon-log-in"></span>
                                <fmt:message key="links.user.logIn"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>


