<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Receber nome</title>
	</head>
	<body>
		<% String nome = request.getParameter("nome");
		   out.println("nome: "+ nome);
		%>
		<br>
		<% int idade = Integer.valueOf(request.getParameter("idade"));
		   out.println("idade: "+ idade);
		%>
	</body>
</html>