<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/28/2020
  Time: 3:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<jsp:useBean id="balance" scope="request" type="java.math.BigDecimal"/>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Displaying wallet view--%>
<br/>
<section>
    <button:back/>
    <div style="padding-left: 350px">
        <p><fmt:message key="links.main.wallet.balance"/>: <c:out value="${balance}"/>, USD</p>

        <form method="POST" action="${pageContext.request.contextPath}/">

            <input class="form-control col-md-3"
                   type="number" step=".01" min="0"
                   name="<c:out value="${AppConstants.PARAM_AMOUNT_OF_MONEY}"/>"
                   required placeholder="<fmt:message key="string.money.enter"/> "/>

            <input type="hidden"
                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                   value="<c:out value="${AppCommandName.WALLET_REFILL_BALANCE}"/>"/>

            <input type="submit"
                   value="<fmt:message key="links.main.wallet.put.money"/>"
                   class="btn btn-outline-dark col-lg-3"/>
        </form>
    </div>
</section>



