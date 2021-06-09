<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/4/2020
  Time: 12:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.entity.ContractStatus" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying workload--%>
<section>
    <div class="text-center">
        <b>
            <fmt:message key="links.main.workload.list"/>
        </b>
    </div>

    <button:back/>

    <%--Command message--%>
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-info" role="alert" style="font-weight: bolder;">
            <c:out value="${requestScope.message}"/>
        </div>
    </c:if>

    <%--Workload table--%>
    <div class="content">
        <c:if test="${not empty requestScope.workload_list}">
            <table class="table table-bordered table-striped table-active">
                <thead>
                <tr>
                    <th scope="row">#</th>
                    <th scope="row"><fmt:message key="string.contract.id"/></th>
                    <th scope="row"><fmt:message key="string.user.name"/></th>
                    <th scope="row"><fmt:message key="string.customer"/></th>
                    <th scope="row"><fmt:message key="string.product"/></th>
                    <th scope="row"><fmt:message key="string.quantity"/></th>
                    <th scope="row"><fmt:message key="string.payment_date"/></th>
                    <th scope="row"><fmt:message key="string.completion_date"/></th>
                    <th scope="row"><fmt:message key="string.cost"/>, USD</th>
                    <th scope="row"><fmt:message key="string.price"/>, USD</th>
                    <th scope="row"><fmt:message key="string.status"/></th>
                    <th scope="row"><fmt:message key="string.contract.progress"/></th>
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

                <c:forEach items="${requestScope.workload_list}" var="workload">
                    <tr>
                        <td><c:out value="${i}"/></td>
                        <td><c:out value="${workload.contractId}"/></td>
                        <td><c:out value="${workload.userName}"/></td>
                        <td><c:out value="${workload.customerName}"/></td>
                        <td><c:out value="${workload.productName}"/></td>
                        <td><c:out value="${workload.productQuantity}"/></td>
                        <td><c:out value="${workload.paymentDate}"/></td>
                        <td><c:out value="${workload.completionDate}"/></td>
                        <td><c:out value="${workload.cost}"/></td>
                        <td><c:out value="${workload.price}"/></td>
                        <td><c:out value="${workload.status}"/></td>
                        <td><c:out value="${workload.progress}"/></td>
                        <td>
                            <c:if test="${workload.status.equals(ContractStatus.NOT_PAYED.status)}">

                                <form method="get" action="${pageContext.request.contextPath}/">

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                                           value="<c:out value="${AppCommandName.CONTRACT_MAKE_DISCOUNT}"/>"/>

                                    <input type="hidden"
                                           name="<c:out value="${AppConstants.PARAM_CONTRACT_ID}"/>"
                                           value="<c:out value="${workload.contractId}"/>">

                                    <label for="${AppConstants.PARAM_DISCOUNT}"><fmt:message
                                            key="string.discount"/></label>

                                    <select class="form-control col" id="${AppConstants.PARAM_DISCOUNT}"
                                            name="<c:out value="${AppConstants.PARAM_DISCOUNT}"/>">
                                        <option>3</option>
                                        <option>5</option>
                                        <option>7</option>
                                        <option>10</option>
                                    </select>

                                    <input type="submit"
                                           value="<fmt:message key="links.contract.make.discount"/>"
                                           class="btn btn-sm btn-outline-dark"/>
                                </form>
                            </c:if>
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
                               href="<c:out value="?${AppConstants.PARAM_COMMAND}=${AppCommandName.WORKLOAD_DISPLAY_ALL}&${AppConstants.PAGE_NUMBER}=${number}"/>">
                                    ${number}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </c:if>
    </div>
</section>