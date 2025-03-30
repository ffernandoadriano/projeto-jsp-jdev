<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>curso JSP</title>
</head>
<body>
	<h1>Meu projeto JSP</h1>

	<form method="post" action="ServletLogin">
		<table>
			<tr>
				<td><label>Nome:</label></td>
				<td><input type="text" name="nome" /></td>
			</tr>
			<tr>
				<td><label>Idade:</label></td>
				<td><input type="number" name="idade" min="1" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Enviar" /></td>
			</tr>
		</table>
	</form>
</body>
</html>