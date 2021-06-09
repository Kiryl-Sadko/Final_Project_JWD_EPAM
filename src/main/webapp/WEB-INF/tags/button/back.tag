<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div>
    <form>
        <input type="button" class="btn btn-outline-dark"
               value="<fmt:message key="links.return"/> "
               onclick="history.back()">
    </form>
</div>