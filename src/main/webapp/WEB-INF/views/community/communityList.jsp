<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
커뮤니티 리스트

	<c:forEach items="${requestScope.list}" var="list" >
	${list.commTitle}
	</c:forEach>
</body>
</html>