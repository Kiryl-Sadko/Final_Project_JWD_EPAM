<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/7/2020
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying product edition view--%>
<section>
    <%--Command message--%>
    <div class="text-center">
        <b>
            <fmt:message key="string.product.edit"/>
        </b>
    </div>
    <button:back/>
    <div class="left-padding">
        <div class="row">

            <%--Current product--%>
            <div class="column col-md-4">
                <c:set var="product" value="${requestScope.product_dto}"/>
                <p><b><fmt:message key="string.name"/>:</b> <c:out value="${product.name}"/></p>
                <p><b><fmt:message key="string.product.weight"/>:</b> <c:out value="${product.weight}"/></p>
                <p><b><fmt:message key="string.product.material"/>:</b> <c:out value="${product.materialName}"/></p>
                <p><b><fmt:message key="string.product.operation"/>:</b>

                    <c:forEach items="${product.operationQueue}" var="operation">
                        <br/>
                        <c:out value="${operation.type.name()}"/>
                    </c:forEach></p>
                <p><b><fmt:message key="string.cost"/>:</b> <c:out value="${product.cost}"/></p>
            </div>

            <%--Form to changes product--%>
            <div class="column col-md-8">
                <ul class="row">
                    <form id="main-form" action="${pageContext.request.contextPath}/" method="post"></form>
                    <form id="select-form"></form>

                    <div class="main-component col-md-12">
                        <div class="row">
                    <span style="display: inline-block;width: 120px;">
                        <b><fmt:message key="string.name"/></b>
                    </span>

                            <label>
                                <input type="text" class="form-control col-md-12"
                                       value="<c:out value="${product.name}"/>"
                                       name="<c:out value="${AppConstants.PARAM_PRODUCT_NAME}"/>"
                                       form="main-form"
                                       placeholder="<fmt:message key="string.product.change.name"/>">
                            </label>

                        </div>

                        <div class="row">
                           <span style="display: inline-block;width: 120px;">
                                <b><fmt:message key="string.product.weight"/></b>
                           </span>
                            <label>
                                <input type="number" step=".001" class="form-control col-md-12"
                                       value="<c:out value="${product.weight}"/>"
                                       name="<c:out value="${AppConstants.PARAM_PRODUCT_WEIGHT}"/>"
                                       form="main-form"
                                       placeholder="<fmt:message key="string.product.change.weight"/>">
                            </label>
                        </div>

                        <div class="row">
                            <span style="display: inline-block;width: 120px;">
                                <b><fmt:message key="string.product.change.material"/></b>
                            </span>
                            <select class="form-control col-md-3"
                                    name="<c:out value="${AppConstants.PARAM_MATERIAL}"/>"
                                    form="main-form">
                                <option value="">
                                    <fmt:message key="string.product.change.material"/>
                                </option>
                                <c:forEach items="${requestScope.material_list}" var="material">
                                    <option value="<c:out value="${material.id}"/>">
                                        <c:out value="${material.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <br/>

                        <div class="select-component row">
                            <span style="display: inline-block;width: 120px;">
                                <b><fmt:message key="string.product.change.process"/></b>
                            </span>
                            <a href="#" class="btn btn-outline-dark add-operation"
                               style="float: left;margin-right: 10px;">
                                <fmt:message key="string.operation.add"/></a>

                            <select class="form-control col-md-2"
                                    name="${AppConstants.PARAM_OPERATION}"
                                    form="main-form">
                                <option value="">
                                    <fmt:message key="string.choose.operation"/>
                                </option>
                                <c:forEach items="${requestScope.operation_list}" var="operation">
                                    <option value="<c:out value="${operation.id}"/>">
                                        <c:out value="${operation.type.name()}"/>
                                    </option>
                                </c:forEach>
                            </select>

                            <input type="hidden"
                                   name="<c:out value="${AppConstants.PARAM_OPERATION_QUEUE}"/>"
                                   form="main-form"
                                   value="">
                        </div>

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_PRODUCT_ID}"/>"
                               value="<c:out value="${product.id}"/>"
                               form="main-form">

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                               value="<c:out value="${AppCommandName.PRODUCT_EDIT}"/>"
                               form="main-form">
                        <div style="clear: both;margin-top: 10px;"></div>

                        <input type="submit"
                               value="<fmt:message key="links.product.change"/>"
                               class="btn btn-outline-dark row col-md-6"
                               form="main-form"/>
                    </div>
                </ul>
            </div>
        </div>
    </div>
</section>

