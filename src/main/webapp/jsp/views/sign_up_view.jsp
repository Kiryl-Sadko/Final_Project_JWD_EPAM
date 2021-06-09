<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>

<%--Displaying Sign Up--%>
<section>
    <div class="container">
        <br/>

        <%--Command message--%>
        <c:if test="${not empty requestScope.message}">
            <div class="alert alert-danger col-md-4" role="alert" style="font-weight: bolder; margin-left: 270px;">
                <c:out value="${requestScope.message}"/>
            </div>
        </c:if>

        <div class="alert alert-info col-md-4" role="alert" style="font-weight: bolder; margin-left: 270px;">
            <fmt:message key="string.signUp"/>
        </div>

        <%--Sign Up form--%>
        <div class="left-padding">
            <form method="POST" action="${pageContext.request.contextPath}/">
                <label><b><fmt:message key="user.name"/></b></label>
                <input class="form-control col-lg-5"
                       type="text"
                       placeholder="<fmt:message key="string.enter.name"/>"
                       name="name" autocomplete="off" required/>

                <label><b><fmt:message key="user.email"/></b></label>
                <input class="form-control col-lg-5"
                       type="text"
                       placeholder="<fmt:message key="string.enter.email"/> "
                       name="email" autocomplete="off" required/>

                <label><b><fmt:message key="user.password"/></b></label>
                <input class="form-control col-lg-5"
                       type="password" placeholder="<fmt:message key="string.enter.password"/>" name="password"
                       autocomplete="off" id="password" onkeyup='check();' required/>

                <label><b><fmt:message key="string.enter.password.confirm"/></b></label>
                <input class="form-control col-lg-5"
                       type="password" placeholder="<fmt:message key="string.enter.password.confirm"/>"
                       autocomplete="off" id="confirm_password" onkeyup='check();' required>
                <br/>
                <span id='message'></span>
                <input type="hidden"
                       name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                       value="<c:out value="${AppCommandName.SIGN_UP_SUBMIT}"/>">

                <button class="btn btn-outline-dark col-lg-5" type="submit" id="submit"><fmt:message
                        key="links.user.signUp"/></button>

                <div>
                    <a style="padding-left: 145px;"
                       href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.LOGIN_DISPLAY}"/>">
                        <span class="glyphicon glyphicon-user"></span>
                        <fmt:message key="links.user.logIn"/></a>
                </div>
            </form>
        </div>
    </div>
</section>

