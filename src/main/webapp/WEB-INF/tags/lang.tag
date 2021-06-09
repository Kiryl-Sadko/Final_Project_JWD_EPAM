<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Setting lacale--%>
<c:choose>
    <c:when test="${not empty requestScope.get('lang')}">
        <fmt:setLocale value="${requestScope.get('lang')}"/>
    </c:when>

    <c:otherwise>
        <fmt:setLocale value="${cookie['lang'].value}"/>
    </c:otherwise>
</c:choose>

<%--Setting lang parameter to url string--%>
<nav class="navbar">
    <div class="navbar-menu">
        <div class="navbar-end">
            <c:choose>
                <c:when test="${not empty pageContext.request.queryString}">
                    <c:choose>
                        <c:when test="${pageContext.request.queryString.contains('lang')}">
                            <c:set var="queryString" value="${pageContext.request.queryString}"/>
                            <c:set var="startIndex" value="${pageContext.request.queryString.indexOf('lang')}"/>
                            <c:set var="query" value="${queryString.substring(0, startIndex)}"/>
                            <c:set var="url"
                                   value="${pageContext.request.contextPath}?${query}lang="/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="url"
                                   value="${pageContext.request.contextPath}?${pageContext.request.queryString}&lang="/>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <c:otherwise>
                    <c:set var="url" value="${pageContext.request.contextPath}?lang="/>
                </c:otherwise>
            </c:choose>

            <c:forTokens items="en,ru" delims="," var="lang">
                <span class="navbar-item">
                    <a class="button is- is-inverted" href="${url}${lang}">
                        <span><fmt:message key="links.lang.${lang}"/></span>
                    </a>
                </span>
            </c:forTokens>
        </div>
    </div>
</nav>