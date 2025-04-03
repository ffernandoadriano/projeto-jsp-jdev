<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
	
	<style type="text/css">
		.container-center {
		    display: flex;               /* Transforma em um contêiner flexível */
		    flex-direction: column;      /* Organiza os elementos na vertical */
		    align-items: center;         /* Centraliza horizontalmente */
		    justify-content: center;     /* Centraliza verticalmente */
		    height: 100vh;               /* Ocupa toda a altura da tela */
		    text-align: center;          /* Centraliza o texto */
		}
		
		form {
		    width: 30%;
		    min-width: 300px; /* Garante que o formulário não fique pequeno demais */
		    text-align: left;
		}
		
		.tituloLogin {
    		margin-bottom: 40px; /* Ajuste o valor conforme necessário */
		}

		.msgError {
		    color: red;
		    margin-top: 20px;
		    font-size: 17px
		}
		
		#inputPassword4{
			margin-bottom: 20px;
		}
		
		#loginCampo{
			margin-bottom: 15px;
		}
		
	</style>
	
	<title>curso JSP</title>

	<!-- Google font-->
      <link href="https://fonts.googleapis.com/css?family=Roboto:400,500" rel="stylesheet">
      <!-- waves.css -->
      <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/pages/waves/css/waves.min.css" type="text/css" media="all">
      <!-- Required Fremwork -->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/bootstrap/css/bootstrap.min.css">
      <!-- waves.css -->
      <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/pages/waves/css/waves.min.css" type="text/css" media="all">
      <!-- themify icon -->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/icon/themify-icons/themify-icons.css">
      <!-- Font Awesome -->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/icon/font-awesome/css/font-awesome.min.css">
      <!-- scrollbar.css -->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/jquery.mCustomScrollbar.css">
        <!-- am chart export.css -->
      <link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
      <!-- Style.css -->
      <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/assets/css/style.css">
      
</head>
<body>

	<!-- Carregamento inicial -->
 	<jsp:include page="loaderstart.jsp"></jsp:include>

	 <div class="container-center">
	 
		<h1 class="tituloLogin">Seja bem-vindo ao projeto JSP!</h1>
	
		<!--  <input type="hidden" value="${param.url}" name="url" />  -->
	
		<form method="post" action="LoginServlet">
	
			<div class="col-md-12">
				<label class="form-label">Login</label>
				<input type="text" name="login"  class="form-control"  required="required" id="loginCampo"/>
			</div>
		
			<div class="col-md-12">
				<label class="form-label">Senha</label>
				<input type="password" name="senha"	class="form-control" required="required" id="inputPassword4" />
			</div>
			
			<div class="d-grid gap-2 col-6 mx-auto">
				<input type="submit" value="Acessar" class="btn btn-primary btn-lg" />
			</div>
	
		</form>
	
		<!-- Expressão EL -->
		<h4 class="msgError">${messageErro}</h4>
	
	 </div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
		
		<jsp:include page="javascript.jsp"></jsp:include>
</body>
</html>