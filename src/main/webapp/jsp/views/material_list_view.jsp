<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/25/2020
  Time: 8:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@ page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying material list--%>
<section>
    <div style="padding-left: 500px">
        <b>
            <fmt:message key="string.material.list"/>
        </b>
    </div>

    <button:back/>

    <%--Command message--%>
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-info" role="alert" style="font-weight: bolder;">
            <c:out value="${requestScope.message}"/>
        </div>
    </c:if>

    <%--Material teble--%>
    <div class="content">
        <c:choose>
            <c:when test="${not empty requestScope.material_list}">
                <table class="table table-bordered table-striped table-active">
                    <thead>
                    <tr>
                        <th scope="row">#</th>
                        <th scope="row"><fmt:message key="string.material.id"/></th>
                        <th scope="row"><fmt:message key="string.name"/></th>
                        <th scope="row"><fmt:message key="string.material.delivery"/></th>
                        <th scope="row"><fmt:message key="string.material.cost"/></th>
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

                    <c:forEach items="${requestScope.material_list}" var="material">
                        <tr>
                            <td><c:out value="${i}"/></td>
                            <td><c:out value="${material.id}"/></td>
                            <td><c:out value="${material.name}"/></td>
                            <td><c:out value="${material.deliveryTime}"/></td>
                            <td><c:out value="${material.cost}"/></td>
                            <td>

                                    <%--Edit command--%>
                                <form method="get" action="${pageContext.request.contextPath}/">

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_MATERIAL_ID}"/>"
                                           value="<c:out value="${material.id}"/>">

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                           value="<c:out value="${AppCommandName.MATERIAL_DISPLAY_EDITION}"/>"/>

                                    <input type="submit"
                                           value="<fmt:message key="links.edit"/>"
                                           class="btn btn-sm btn-outline-dark"/>
                                </form>

                                    <%--Delete command --%>
                                <form method="post" action="${pageContext.request.contextPath}/">

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                           value="<c:out value="${AppCommandName.MATERIAL_DELETE}"/>"/>

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_MATERIAL_ID}"/>"
                                           value="<c:out value="${material.id}"/>">

                                    <input type="submit"
                                           value="<fmt:message key="links.delete"/>"
                                           class="btn btn-sm btn-outline-dark"/>
                                </form>
                            </td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    </tbody>
                </table>

                <%--Pagination--%>
                <div class="container" style="padding-left: 500px">
                    <ul class="pagination">
                        <c:forEach begin="1" end="${requestScope.page_quantity}" var="number">

                            <li class="page-item">
                                <a class="btn btn-outline-dark"
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.MATERIAL_DISPLAY_ALL}&${AppConstants.PAGE_NUMBER}=${number}"/>">
                                        ${number}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:when>

            <%--Material creation button--%>
            <c:otherwise>
                <div style="font-size: larger; padding-left: 350px;">
                    <b><fmt:message key="string.list.empty"/></b>
                    <br/>
                    <div style="padding-left: 100px">
                        <fmt:message key="links.main.contract.create" var="button"/>
                        <button:submitForm commandName="${AppCommandName.MATERIAL_DISPLAY_CREATION}"
                                           buttonName="${button}"
                                           buttonSize="2"/>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <br/>
</section>
