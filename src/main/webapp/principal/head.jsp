<head>
    <title>Projeto JSP</title>
      <!-- Meta -->
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
      <meta http-equiv="X-UA-Compatible" content="IE=edge" />
      <meta name="description" content="Projeto JSP." />
      <meta name="author" content="Fernando Adriano" />
      
      <!-- Favicon icon -->
      <link rel="icon" href="<%= request.getContextPath() %>/assets/images/favicon.ico" type="image/x-icon">
      
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
      
      <style type="text/css">
      		#btn-form-cadastro{
      			margin-left: 10px;
      			width: 100px;
      		}
      		
      		.erro {
				color: #F80000;
				font-weight: bold;
			}
			
			.sucessoSalvo{
				color: #28a745;
				font-weight: bold;
			}
      </style>
      
      <script type="text/javascript">
      
	      function limparFormulario() {

		      let elementos = document.getElementById("usuarioForm").elements; // retorna os elementos html dentro do formul�rio
	
	    	  for(let i = 0; i < elementos.length; i ++){
		    	  	elementos[i].value = ''; // Limpa o campo existante no formul�rio
		    	  }

	    	// Oculta mensagens de sucesso e erro (definido no atributo 'class') 
	    	  const mensagens = document.querySelectorAll(".sucessoSalvo, .erro");
	    	  mensagens.forEach(msg => msg.style.display = "none");

	    	  // Limpa os par�metros da URL
	    	  history.replaceState(null, '', window.location.pathname);
			}

			function excluirCadastro(){
					let id = document.forms["usuarioForm"].id.value; // captura o nome do campo do formul�rio

					// se diferente de vazio, significa que estou tentando excluir um usuario j� cadastro no banco
					if(id != ""){
						
						let resposta = confirm("Deseja realmente excluir o registro?");

						if(resposta){
							window.location = "<%=request.getContextPath()%>/ExcluirUsuarioServlet?id=" + id; 
							// redireciona a p�gina

						}
						
					}
				}

			function excluirCadastroComAjax(){
				let id = document.forms["usuarioForm"].id.value; // captura o nome do campo do formul�rio
				
				// se diferente de vazio, significa que estou tentando excluir um usuario j� cadastro no banco
				if(id != ""){
					
					let resposta = confirm("Deseja realmente excluir o registro?");

					if(resposta){

						// URL para onde vai ser redirecionada
						let urlDirecionamento = "<%=request.getContextPath()%>/ExcluirUsuarioServlet";

						$.ajax({
							
							method: "GET",
							url: urlDirecionamento,
							data: "id="+ id + "&acao=excluirComAjax",  // uma forma de passar os par�metros
							success: function(response){
									alert(response);
									limparFormulario(); // chamando fun��o
								}

						}).fail(function(xhr, status, errorThrown){
								alert("Erro ao tentar deletar por id: "+ xhr.responseText);
							} ); // xhr- detalhes do erro // status - status do erro // errorThrown - exce��o de erro
					}		
				}
			}
			
      </script>
  </head>