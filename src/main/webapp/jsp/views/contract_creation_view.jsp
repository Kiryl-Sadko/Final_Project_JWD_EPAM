<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/28/2020
  Time: 8:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying contract creation view--%>
<section>
    <div style="padding-left: 350px;">
        <b>
            <fmt:message key="string.contract.creation"/>
        </b>
    </div>
    <button:back/>

    <%--Creation form--%>
    <div class="left-padding">
        <ul>
            <c:choose>

                <%--Form without id input--%>
                <c:when test="${not empty param.product_id}">
                    <form method="post" action="${pageContext.request.contextPath}/">
                        <div class="row">

                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.enter.customer"/>
                            </span>

                            <label>
                                <input class="form-control"
                                       type="text"
                                       name="<c:out value="${AppConstants.PARAM_CUSTOMER_NAME}"/>"
                                       placeholder="<fmt:message key="string.enter.customer"/> " required>
                            </label>
                        </div>

                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.enter.quantity"/>
                            </span>
                            <label>
                                <input class="form-control"
                                       type="number" min="1"
                                       name="<c:out value="${AppConstants.PARAM_PRODUCT_QUANTITY}"/>"
                                       placeholder="<fmt:message key="string.enter.quantity"/>" required>
                            </label>
                        </div>

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>"
                               value="<c:out value="${param.product_id}"/>">

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                               value="<c:out value="${AppCommandName.CONTRACT_CREATE}"/>">

                        <input type="submit"
                               value="<fmt:message key="links.contract.create"/>"
                               class="btn row btn-outline-dark col-md-4"/>
                    </form>
                </c:when>

                <%--Form with id input--%>
                <c:otherwise>
                    <form method="post" action="${pageContext.request.contextPath}/">
                        <div class="row">

                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.enter.customer"/>
                            </span>

                            <label>
                                <input class="form-control"
                                       type="text"
                                       name="<c:out value="${AppConstants.PARAM_CUSTOMER_NAME}"/>"
                                       placeholder="<fmt:message key="string.enter.customer"/> " required>
                            </label>
                        </div>

                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.enter.quantity"/>
                            </span>
                            <label>
                                <input class="form-control"
                                       type="number" min="1"
                                       name="<c:out value="${AppConstants.PARAM_PRODUCT_QUANTITY}"/>"
                                       placeholder="<fmt:message key="string.enter.quantity"/>" required>
                            </label>
                        </div>

                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.enter.product"/>
                            </span>
                            <select class="form-control col-md-2"
                                    name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>">
                                <c:forEach items="${requestScope.product_list}" var="product">
                                    <option value="<c:out value="${product.id}"/>">
                                        <c:out value="${product.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                               value="<c:out value="${AppCommandName.CONTRACT_CREATE}"/>">

                        <input type="submit"
                               value="<fmt:message key="links.contract.create"/>"
                               class="btn row btn-outline-dark col-md-4"/>
                    </form>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</section>