<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 6/29/2020
  Time: 9:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="button" tagdir="/WEB-INF/tags/button" %>

<%--Displaying permission page--%>
<br/>
<section>
    <button:back/>
    <div class="left-padding">
        <b><fmt:message key="security.not.enough.permission"/> </b>
    </div>
</section>


