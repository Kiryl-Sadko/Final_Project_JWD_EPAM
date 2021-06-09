<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:directive.attribute name="commandName" type="by.sadko.training.command.AppCommandName" required="true"
                         description="Command name to submit"/>

<jsp:useBean id="securityContext" type="by.sadko.training.SecurityContext" scope="application"/>

<c:if test="${securityContext.canExecute(commandName)}">
    <jsp:doBody/>
</c:if>


