<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 7/25/2020
  Time: 9:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="by.sadko.training.command.AppCommandName" %>
<%@page import="by.sadko.training.util.AppConstants" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying material creation view--%>
<section>
    <div style="padding-left: 350px;">
        <b>
            <fmt:message key="string.material.creation"/>
        </b>
    </div>

    <button:back/>

    <div class="left-padding">
        <ul>
            <%--Material creation form --%>
            <form method="post" action="${pageContext.request.contextPath}/">

                <%--Material name input --%>
                <div class="row">
                    <span style="display: inline-block;width: 100px;">
                        <fmt:message key="string.enter.name"/>
                    </span>

                    <label>
                        <input class="form-control"
                               type="text"
                               name="<c:out value="${AppConstants.PARAM_MATERIAL_NAME}"/>"
                               placeholder="<fmt:message key="string.enter.material.name"/> "
                               required>
                    </label>
                </div>

                <%--Delivery time input--%>
                <div class="row">
                            <span style="display: inline-block;width: 100px;">
                            <fmt:message key="string.material.delivery"/>
                            </span>
                    <label>
                        <input class="form-control"
                               type="number" min="0" step=".1"
                               name="<c:out value="${AppConstants.PARAM_MATERIAL_DELIVERY}"/>"
                               placeholder="<fmt:message key="string.material.delivery"/>" required>
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
                               placeholder="<fmt:message key="string.material.cost"/>" required>
                    </label>
                </div>

                <input type="hidden"
                       name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                       value="<c:out value="${AppCommandName.MATERIAL_CREATE}"/>">

                <input type="submit"
                       value="<fmt:message key="links.material.create"/>"
                       class="btn row btn-outline-dark col-md-4"/>
            </form>
        </ul>
    </div>
</section>