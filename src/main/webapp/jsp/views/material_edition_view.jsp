<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/26/2020
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying material edition view--%>
<section>
    <div class="text-center">
        <b>
            <fmt:message key="string.material.edit"/>
        </b>
    </div>
    <button:back/>
    <div class="left-padding">
        <div class="row">

            <%--Current material--%>
            <div class="column col-md-4">
                <c:set var="material" value="${requestScope.material}"/>
                <p><b><fmt:message key="string.enter.material.name"/>:</b> <c:out value="${material.name}"/></p>
                <p><b><fmt:message key="string.material.delivery"/>:</b> <c:out value="${material.deliveryTime}"/></p>
                <p><b><fmt:message key="string.material.cost"/>:</b> <c:out value="${material.cost}"/></p>
            </div>

            <%--Form to changes material--%>
            <div class="column col-md-8">
                <ul>
                    <form method="post" action="${pageContext.request.contextPath}/">

                        <%--Material name input--%>
                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                                <fmt:message key="string.enter.name"/>
                            </span>

                            <label>
                                <input class="form-control"
                                       type="text"
                                       name="<c:out value="${AppConstants.PARAM_MATERIAL_NAME}"/>"
                                       placeholder="<fmt:message key="string.material.change.name"/> ">
                            </label>
                        </div>

                        <%-- Delivery time input --%>
                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.material.delivery"/>
                            </span>

                            <label>
                                <input class="form-control"
                                       type="number" min="0" step=".1"
                                       name="<c:out value="${AppConstants.PARAM_MATERIAL_DELIVERY}"/>"
                                       placeholder="<fmt:message key="string.material.change.time"/>">
                            </label>
                        </div>

                        <%--Cost input--%>
                        <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.material.cost"/>
                            </span>
                            <label>
                                <input class="form-control"
                                       type="number" min="0" step=".01"
                                       name="<c:out value="${AppConstants.PARAM_MATERIAL_COST}"/>"
                                       placeholder="<fmt:message key="string.material.change.cost"/>">
                            </label>
                        </div>

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_MATERIAL_ID}"/>"
                               value="<c:out value="${material.id}"/>">

                        <input type="hidden"
                               name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                               value="<c:out value="${AppCommandName.MATERIAL_EDIT}"/>">

                        <input type="submit"
                               value="<fmt:message key="links.edit"/>"
                               class="btn row btn-outline-dark col-md-6"/>
                    </form>
                </ul>
            </div>
        </div>
    </div>
</section>

