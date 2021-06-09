<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>

<br/>
<%--Displaying Log In form--%>
<section>
    <div class="container">
        <%--Command Message--%>
        <c:if test="${not empty requestScope.message}">
            <div class="alert alert-info col-md-4" role="alert" style="font-weight: bolder; margin-left: 270px;">
                <c:out value="${requestScope.message}"/>
            </div>
        </c:if>

        <%--Log In form--%>
        <div class="left-padding">
            <form method="POST" action="${pageContext.request.contextPath}/">
                <span id="reauth-email" class="reauth-email"></span>
                <label><b><fmt:message key="user.email"/></b></label>
                <input class="form-control col-lg-5"
                       type="text"
                       placeholder="<fmt:message key="string.enter.email"/>"
                       name="<c:out value="${AppConstants.PARAM_EMAIL}"/>"
                       autocomplete="on"
                       required>

                <label><b><fmt:message key="user.password"/></b></label>

                <input class="form-control col-lg-5"
                       type="password"
                       placeholder="<fmt:message key="string.enter.password"/>"
                       name="<c:out value="${AppConstants.PARAM_PASSWORD}"/>"
                       autocomplete="off"
                       required>

                <input type="hidden"
                       name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                       value="<c:out value="${AppCommandName.LOGIN_SUBMIT}"/>">

                <button class="btn btn-outline-dark col-lg-5"
                        type="submit">
                    <fmt:message key="links.user.logIn"/></button>
                <div>
                    <a style="padding-left: 145px;"
                       href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.SIGN_UP_DISPLAY}"/>">
                        <span class="glyphicon glyphicon-user"></span>
                        <fmt:message key="links.user.signUp"/></a>
                </div>
            </form>
        </div>
    </div>
</section>
