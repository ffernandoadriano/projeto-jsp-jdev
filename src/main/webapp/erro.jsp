<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Página de Erro</title>
</head>
<body>
	<h1>Erro, entre em contato com a equipe de suporte do sistema.</h1>
	<%=exception.getMessage()%>
</body>
</html>