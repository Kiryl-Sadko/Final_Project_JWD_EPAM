<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@tag import="by.sadko.training.util.AppConstants" %>

<jsp:directive.attribute name="commandName" type="by.sadko.training.command.AppCommandName" required="true"
                         description="Command name to submit"/>
<jsp:directive.attribute name="buttonName" required="true" description="Button name to display"/>
<jsp:directive.attribute name="buttonSize" required="false" description="Size to display button"/>

<c:choose>
    <c:when test="${not empty commandName and not empty buttonName and not empty buttonSize}">

        <form method="get">
            <input type="hidden"
                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                   value="<c:out value="${commandName}"/>"/>

            <input type="submit"
                   value="<c:out value="${buttonName}"/>"
                   class="btn btn-outline-dark col-lg-${buttonSize}"/>
        </form>
    </c:when>

    <c:when test="${not empty commandName and not empty buttonName}">

        <form method="get">
            <input type="hidden"
                   name="<c:out value="${AppConstants.PARAM_COMMAND}"/>"
                   value="<c:out value="${commandName}"/>"/>

            <input type="submit"
                   value="<c:out value="${buttonName}"/>"
                   class="btn btn-outline-dark col-lg-5"/>
        </form>
    </c:when>
</c:choose>



