<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.anyframejava.org/tags" prefix="anyframe" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="packageName" value="org.anyframe.plugin"/>
<c:set var="datePattern"><fmt:message key="date.format"></fmt:message></c:set>
