<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@taglib prefix="security" tagdir="/WEB-INF/tags/security" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>
<br/>
<section>
    <div class="left-padding">

        <%--Command message--%>
        <c:if test="${not empty param.result}">
            <div class="alert alert-info col-lg-5" role="alert">
                <c:out value="${param.result}"/>
            </div>
        </c:if>

        <%--Workload--%>
        <security:checkAutorization commandName="${AppCommandName.WORKLOAD_DISPLAY_ALL}">
            <fmt:message key="links.main.workload.list" var="button"/>
            <button:submitForm commandName="${AppCommandName.WORKLOAD_DISPLAY_ALL}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--Products--%>
        <security:checkAutorization commandName="${AppCommandName.PRODUCT_DISPLAY_ALL}">
            <fmt:message key="links.main.product.list" var="button"/>
            <button:submitForm commandName="${AppCommandName.PRODUCT_DISPLAY_ALL}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--New product--%>
        <security:checkAutorization commandName="${AppCommandName.PRODUCT_DISPLAY_CREATION}">
            <fmt:message key="links.main.product.creation" var="button"/>
            <button:submitForm commandName="${AppCommandName.PRODUCT_DISPLAY_CREATION}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--Materials--%>
        <security:checkAutorization commandName="${AppCommandName.MATERIAL_DISPLAY_ALL}">
            <fmt:message key="links.main.material.list" var="button"/>
            <button:submitForm commandName="${AppCommandName.MATERIAL_DISPLAY_ALL}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--New material--%>
        <security:checkAutorization commandName="${AppCommandName.MATERIAL_DISPLAY_CREATION}">
            <fmt:message key="links.main.material.create" var="button"/>
            <button:submitForm commandName="${AppCommandName.MATERIAL_DISPLAY_CREATION}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--Contracts--%>
        <security:checkAutorization commandName="${AppCommandName.CONTRACT_DISPLAY_ALL}">
            <fmt:message key="links.main.contract.list" var="button"/>
            <button:submitForm commandName="${AppCommandName.CONTRACT_DISPLAY_ALL}" buttonName="${button}"/>
        </security:checkAutorization>

        <%--New contract--%>
        <security:checkAutorization commandName="${AppCommandName.CONTRACT_DISPLAY_CREATION}">
            <fmt:message key="links.main.contract.create" var="button"/>
            <button:submitForm commandName="${AppCommandName.CONTRACT_DISPLAY_CREATION}" buttonName="${button}"/>
        </security:checkAutorization>
    </div>
</section>




