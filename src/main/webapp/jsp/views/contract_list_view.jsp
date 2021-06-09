<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/28/2020
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.entity.ContractStatus" %>
<%@ page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying contract list--%>
<section>
    <div style="padding-left: 500px;">
        <b>
            <fmt:message key="string.contract.list"/>
        </b>
    </div>

    <button:back/>

    <%--Command message--%>
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-info" role="alert">
            <c:out value="${requestScope.message}"/>
        </div>
    </c:if>

    <%--Contract table--%>
    <div class="content">
        <c:choose>
            <c:when test="${not empty requestScope.user_contract_list}">
                <table class="table table-bordered table-striped table-active">
                    <thead>
                    <tr>
                        <th scope="row">#</th>
                        <th scope="row"><fmt:message key="string.contract.id"/></th>
                        <th scope="row"><fmt:message key="string.customer"/></th>
                        <th scope="row"><fmt:message key="string.product"/></th>
                        <th scope="row"><fmt:message key="string.quantity"/></th>
                        <th scope="row"><fmt:message key="string.payment_date"/></th>
                        <th scope="row"><fmt:message key="string.completion_date"/></th>
                        <th scope="row"><fmt:message key="string.price"/>, USD</th>
                        <th scope="row"><fmt:message key="string.status"/></th>
                        <th scope="row"><fmt:message key="string.operation"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:set var="page_number" value="${requestScope.page_number}"/>

                        <%--calculation index--%>
                    <c:choose>
                        <c:when test="${page_number > 1}">
                            <c:set var="i" value="${((page_number-1)*5)+1}"/>
                        </c:when>

                        <c:otherwise>
                            <c:set var="i" value="1"/>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach items="${requestScope.user_contract_list}" var="contract">
                        <tr>
                            <td><c:out value="${i}"/></td>
                            <td><c:out value="${contract.contractId}"/></td>
                            <td><c:out value="${contract.customerName}"/></td>
                            <td><c:out value="${contract.productName}"/></td>
                            <td><c:out value="${contract.productQuantity}"/></td>
                            <td><c:out value="${contract.paymentDate}"/></td>
                            <td><c:out value="${contract.completionDate}"/></td>
                            <td><c:out value="${contract.price}$"/></td>
                            <td><c:out value="${contract.status}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${contract.status.equals(ContractStatus.NOT_PAYED.status)}">

                                        <form method="post" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.CONTRACT_PAY}"/>"/>

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_CONTRACT_ID}"/>"
                                                   value="<c:out value="${contract.contractId}"/>">

                                            <input type="submit"
                                                   value="<fmt:message key="links.pay"/>"
                                                   class="btn btn-sm btn-outline-dark"/>
                                        </form>

                                        <form method="post" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.CONTRACT_DELETE}"/>"/>

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_CONTRACT_ID}"/>"
                                                   value="<c:out value="${contract.contractId}"/>">

                                            <input type="submit"
                                                   value="<fmt:message key="links.delete"/>"
                                                   class="btn btn-sm btn-outline-dark"/>
                                        </form>
                                    </c:when>

                                    <c:when test="${contract.status.equals((ContractStatus.DONE.status))}">

                                        <form method="post" action="${pageContext.request.contextPath}/">

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                                   value="<c:out value="${AppCommandName.CONTRACT_DELETE}"/>"/>

                                            <input type="hidden"
                                                   name="<c:out value="${AppConstants.PARAM_CONTRACT_ID}"/>"
                                                   value="<c:out value="${contract.contractId}"/>">

                                            <input type="submit"
                                                   value="<fmt:message key="links.delete"/>"
                                                   class="btn btn-sm btn-outline-dark"/>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <fmt:message key="string.expect"/>
                                    </c:otherwise>
                                </c:choose>
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
                                   href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.CONTRACT_DISPLAY_ALL}&${AppConstants.PAGE_NUMBER}=${number}"/>">

                                    <c:out value="${number}"/>
                                </a>
                            </li>

                        </c:forEach>
                    </ul>
                </div>
            </c:when>

            <%--Button contract creation--%>
            <c:otherwise>
                <div style="font-size: larger; padding-left: 350px;">
                    <b><fmt:message key="string.list.empty"/></b>
                    <br/>
                    <div style="padding-left: 100px">
                        <fmt:message key="links.main.contract.create" var="button"/>
                        <button:submitForm commandName="${AppCommandName.CONTRACT_DISPLAY_CREATION}"
                                           buttonName="${button}"
                                           buttonSize="2"/>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <br/>
</section>
