<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Displaying wallet balance--%>
<section>
    <br/>
    <div>
        <b><fmt:message key="links.main.wallet.balance"/>: <c:out value="${requestScope.balance}"/> USD</b>
    </div>
    <div>
        <fmt:message key="links.wallet.refill" var="button"/>
        <button:submitForm commandName="${AppCommandName.WALLET_DISPLAY}" buttonName="${button}" buttonSize="7"/>
    </div>
</section>






