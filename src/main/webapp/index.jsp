<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>curso JSP</title>
<style>
.msgError {
	color: red;
}
</style>

</head>
<body>
	<h1>Seja bem-vindo ao projeto JSP!</h1>

	<!--  <input type="hidden" value="${param.url}" name="url" />  -->

	<form method="post" action="ServletLogin">
		<table aria-hidden="true">
			<tr>
				<td><label>Login:</label></td>
				<td><input type="text" name="login" required="required" /></td>
			</tr>
			<tr>
				<td><label>Senha:</label></td>
				<td><input type="password" name="senha" required="required" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit"
					value="Enviar" /></td>
			</tr>
		</table>
	</form>

	<!-- Expressão EL -->
	<h4 class="msgError">${messageErro}</h4>

</body>
</html>