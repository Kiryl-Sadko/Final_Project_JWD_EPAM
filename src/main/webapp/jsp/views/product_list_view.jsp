<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/8/2020
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.entity.UserRole" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying product list--%>
<section>
    <div class="text-center">
        <b>
            <fmt:message key="string.product.list"/>
        </b>
    </div>

    <button:back/>

    <%--Command message--%>
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-info" role="alert" style="font-weight: bolder;">
            <c:out value="${requestScope.message}"/>
        </div>
    </c:if>

    <div class="content">
        <c:choose>
            <c:when test="${not empty requestScope.product_dto_list}">

                <!--Products table-->
                <table class="table table-bordered table-striped table-active">
                    <thead>
                    <tr>
                        <th scope="row">#</th>
                        <th scope="row"><fmt:message key="string.product.id"/></th>
                        <th scope="row"><fmt:message key="string.name"/></th>
                        <th scope="row"><fmt:message key="string.product.weight"/></th>
                        <th scope="row"><fmt:message key="string.product.material"/></th>
                        <th scope="row"><fmt:message key="string.product.process"/></th>
                        <c:choose>
                            <c:when test="${requestScope.account_role.equals(UserRole.CEO)}">
                                <th scope="row"><fmt:message key="string.cost"/>, USD</th>
                            </c:when>
                            <c:otherwise>
                                <th scope="row"><fmt:message key="string.price"/>, USD</th>
                            </c:otherwise>
                        </c:choose>
                        <th scope="row"><fmt:message key="string.operation"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:set var="page_number" value="${requestScope.page_number}"/>

                        <%--Index calculation--%>
                    <c:choose>
                        <c:when test="${page_number > 1}">
                            <c:set var="i" value="${((page_number-1)*5)+1}"/>
                        </c:when>

                        <c:otherwise>
                            <c:set var="i" value="1"/>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach items="${requestScope.product_dto_list}" var="product">
                        <tr>
                            <td><c:out value="${i}"/></td>
                            <td><c:out value="${product.id}"/></td>
                            <td><c:out value="${product.name}"/></td>
                            <td><c:out value="${product.weight}"/></td>
                            <td><c:out value="${product.materialName}"/></td>
                            <td>
                                <c:forEach items="${product.operationQueue}" var="operation">
                                    <c:out value="${operation.type.name()}"/>
                                    <br/>
                                </c:forEach>
                            </td>
                            <td><c:out value="${product.cost}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${requestScope.account_role.equals(UserRole.CEO)}">

                                        <form method="get" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>"
                                                   value="<c:out value="${product.id}"/>">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.PRODUCT_DISPLAY_EDITION}"/>"/>

                                            <input type="submit"
                                                   value="<fmt:message key="links.edit"/>"
                                                   class="btn btn-sm btn-outline-dark"/>

                                        </form>

                                        <form method="post" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.PRODUCT_DELETE}"/>"/>

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>"
                                                   value="<c:out value="${product.id}"/>">

                                            <input type="submit"
                                                   value="<fmt:message key="links.delete"/>"
                                                   class="btn btn-sm btn-outline-dark"/>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <form method="get" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.CONTRACT_DISPLAY_CREATION}"/>"/>

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>"
                                                   value="<c:out value="${product.id}"/>">

                                            <input type="submit"
                                                   value="<fmt:message key="links.product.order"/>"
                                                   class="btn btn-sm btn-outline-dark"/>

                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    </tbody>
                </table>

                <!--Pagination-->
                <div class="container" style="padding-left: 500px">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${requestScope.page_quantity}" var="number">

                            <li class="page-item">
                                <a class="btn btn-outline-dark"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.PRODUCT_DISPLAY_ALL}&${AppConstants.PAGE_NUMBER}=${number}"/>">
                                        ${number}
                                </a>
                            </li>

                        </c:forEach>
                    </ul>
                </div>
            </c:when>

            <%--Product creation button--%>
            <c:otherwise>
                <div style="font-size: larger; padding-left: 350px;">
                    <b><fmt:message key="string.list.empty"/></b>
                    <br/>
                    <div style="padding-left: 100px">
                        <fmt:message key="links.main.product.creation" var="button"/>
                        <button:submitForm commandName="${AppCommandName.PRODUCT_DISPLAY_CREATION}"
                                           buttonName="${button}"
                                           buttonSize="2"/>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</section>
