<head>
<title>Projeto JSP</title>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="description" content="Projeto JSP." />
<meta name="author" content="Fernando Adriano" />

<!-- Favicon icon -->
<link rel="icon"
	href="<%=request.getContextPath()%>/assets/images/favicon.ico"
	type="image/x-icon">

<!-- Google font-->
<link href="https://fonts.googleapis.com/css?family=Roboto:400,500"
	rel="stylesheet">
<!-- waves.css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/pages/waves/css/waves.min.css"
	type="text/css" media="all">
<!-- Required Fremwork -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/css/bootstrap/css/bootstrap.min.css">
<!-- waves.css -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/pages/waves/css/waves.min.css"
	type="text/css" media="all">
<!-- themify icon -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/icon/themify-icons/themify-icons.css">
<!-- Font Awesome -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/icon/font-awesome/css/font-awesome.min.css">
<!-- scrollbar.css -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/css/jquery.mCustomScrollbar.css">
<!-- am chart export.css -->
<link rel="stylesheet"
	href="https://www.amcharts.com/lib/3/plugins/export/export.css"
	type="text/css" media="all" />
<!-- Style.css -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/css/style.css">
<!-- icone whatsApp -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<!-- ico font -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/icon/icofont/css/icofont.css">
<!-- Notification.css -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/pages/notification/notification.css">
<!-- Animate.css -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/css/animate.css/css/animate.css">


<style type="text/css">
#btn-form-cadastro {
	margin-left: 10px;
	width: 110px;
}

.erro {
	color: #F80000;
	font-weight: bold;
}

.sucessoSalvo {
	color: #28a745;
	font-weight: bold;
}

.modal-xl-custom {
	max-width: 50%;
	<%/* Estilopersonalizado para aumentar	o tamanho do modal */%>
}

/*botão de propagando*/
.fixed-button {
	display: none !important;
}

.footerwhatsapp {
	display: none;
	position: fixed;
	bottom: 25px;
	right: 15px;
	z-index: 99999999;
	transition: opacity 0.3s ease;
}

.footerwhatsapp img {
	height: 60px;
	width: 50px;
}
</style>

<script type="text/javascript">

	      function limparFormulario() {

		      let elementos = document.getElementById("usuarioForm").elements; // retorna os elementos html dentro do formulário
	
	    	  for(let i = 0; i < elementos.length; i ++){
		    	  	elementos[i].value = ''; // Limpa o campo existante no formulário
		    	  }

	    	// Oculta mensagens de sucesso e erro (definido no atributo 'class') 
	    	  const mensagens = document.querySelectorAll(".sucessoSalvo, .erro");
	    	  mensagens.forEach(msg => msg.style.display = "none");

	    	  // Limpa os parâmetros da URL
	    	  history.replaceState(null, '', window.location.pathname);

	    	  // Limpar o radio
	    	  const radios = document.getElementsByName("sexo");
	    	  radios.forEach(radio => radio.checked = false);

	    	  // Limpar a imagem anterior para padrão
	    	  const fotoPerfil = document.getElementById("fotoBase64");
	    	  fotoPerfil.src = "<%=request.getContextPath()%>/assets/images/perfil.png";
			}

			function excluirCadastro(){
					let id = document.forms["usuarioForm"].id.value; // captura o nome do campo do formulário

					// se diferente de vazio, significa que estou tentando excluir um usuario já cadastro no banco
					if(id != ""){
						
						let resposta = confirm("Deseja realmente excluir o registro?");

						if(resposta){
							window.location = "<%=request.getContextPath()%>/ExcluirUsuarioServlet?id=" + id; 
							// redireciona a página

						}
						
					}
				}

			function excluirCadastroComAjax(){
				let id = document.forms["usuarioForm"].id.value; // captura o nome do campo do formulário
				
				// se diferente de vazio, significa que estou tentando excluir um usuario já cadastro no banco
				if(id != ""){
					
					let resposta = confirm("Deseja realmente excluir o registro?");

					if(resposta){

						// URL para onde vai ser redirecionada
						let urlDirecionamento = "<%=request.getContextPath()%>/ExcluirUsuarioServlet";

						$.ajax({
							
							method: "GET",
							url: urlDirecionamento,
							data: "id="+ id + "&acao=excluirComAjax",  // uma forma de passar os parâmetros
							success: function(response){
				
									limparFormulario(); // chamando função
									
									// document.getElementById("msg").textContent = response; // Um exemplo caso precise enviar a resposta do servidor para alguma id especifico
								} 

						}).fail(function(xhr, status, errorThrown){
								alert("Erro ao tentar deletar por id: "+ xhr.responseText);
							} ); // xhr- detalhes do erro // status - status do erro // errorThrown - exceção de erro
					}		
				}
			}

			function excluirCadastroComAjax2() {
			    let id = document.forms["usuarioForm"].id.value;

			    if (id !== "") {
			        let resposta = confirm("Deseja realmente excluir o registro?");
			        
			        if (resposta) {
			            let url = "<%=request.getContextPath()%>/ExcluirUsuarioServlet?id=" + id + "&acao=excluirComAjax";
			            let request;

			            if (window.XMLHttpRequest) {
			                request = new XMLHttpRequest();
			            } else {
			                request = new ActiveXObject("Microsoft.XMLHTTP");
			            }
			            request.onreadystatechange = () => ajaxProcessarRecebimento(request); // (lambda) => Função de tratamento de retorno
			            request.open("GET", url, true); // true = assíncrono
			            request.send();
			        }
			    }
			}


			function ajaxProcessarRecebimento(request) {
				if (request.readyState === 4) {
                    if (request.status === 200) {
                    	limparFormulario(); // chamando função
                        // document.getElementById("msg").textContent = request.responseText;
                    } else {
                        alert("Erro ao tentar deletar por id: " + request.status + " - " + request.responseText);
                    }
                }
			}

			function buscarUsuarioPorNome(){

				const pesquisarNome = document.getElementById("pesquisarNome").value; // Validando para ter valor p/ buscar no banco de dados

				if(pesquisarNome != "" && pesquisarNome.trim() != ""){

					// URL para onde vai ser redirecionada
					let urlDirecionamento = "<%=request.getContextPath()%>/PesquisarUsuarioServlet";
					
					// ajax do Bootstrap
					$.ajax({
						
						method: "GET",
						url: urlDirecionamento,
						data: "nome="+ pesquisarNome,   // uma forma de passar os parâmetros
						success: function(response){

							const json = JSON.parse(response); // convertendo uma String json para JSON em javaScript

							// Remove linhas antigas
							$("#tbPesquisarUsuario > tbody > tr").remove(); // jQuery, esse código remove todas as linhas da <tbody> da tabela com ID tbPesquisarUsuario.

							
							for(let i = 0; i < json.length; i++){

								//  A melhor forma de simular um StringBuilder é usar um array de strings + join() em JS.
								const sb = [];

								sb.push("<tr>");
								sb.push("<td>" + json[i].id + "</td>");
								sb.push("<td>" + json[i].nome + "</td>");
								sb.push("<td>" + json[i].email + "</td>");
								sb.push("<td>");
								sb.push("<button type=\"button\" class=\"btn btn-info btn-round waves-effect hor-grd btn-grd-info\" " +
								         "onclick=\"editarFormularioUsuario(" + json[i].id + ");\">Editar</button>");
								sb.push("</td>");
								sb.push("</tr>");

								const linha = sb.join("");
								
								$("#tbPesquisarUsuario > tbody").append(linha); // adicionando no body da tabela
								document.getElementById("qtdRegistros").textContent = "Total de Registros: "+ json.length; // Apresentará a quantidade de registros encontrados.
							}

							// Apresentará 0 quando não encontrar nenhum registro.
							if(json.length == 0){
								document.getElementById("qtdRegistros").textContent = "Total de Registros: "+ json.length;
							}
							
						} 

					}).fail(function(xhr, status, errorThrown){
							alert("Erro ao tentar pesquisar por nome: "+ xhr.responseText);
						} ); // xhr- detalhes do erro // status - status do erro // errorThrown - exceção de erro

				}

			}

			function limparTabelaPesquisarUsuario(){
				// Remove linhas antigas
				$("#tbPesquisarUsuario > tbody > tr").remove(); // jQuery, esse código remove todas as linhas da <tbody> da tabela com ID tbPesquisarUsuario.
				
				document.getElementById("qtdRegistros").textContent = ""; // limpa a quantidade de registros
				document.getElementById("pesquisarNome").value = ""; // limpa o campo pesquisar nome
			}

			function editarFormularioUsuario(id){

				window.location.href = "<%=request.getContextPath()%>/EditarUsuarioServlet?id="+ id;
			}

			
			function excluirCadastro(id) {

			    if (id !== "") {
			        let resposta = confirm("Deseja realmente excluir o registro?");
			        
			        if (resposta) {
			        	window.location.href = "<%=request.getContextPath()%>/ExcluirUsuarioServlet?id=" + id +"&acao=excluir";
			        }
			    }
			}

			
			function visualizarImagemTela(idImagem, idInputFile) {
				
			    const preview = document.getElementById(idImagem); // Campo id da imagem
			    const inputFile = document.getElementById(idInputFile); // Campo id do upload file  "obs: esse campo pode retornar mais de uma imagem"
			    const hiddenBase64 = document.getElementById("fotoBase64"); // campo hidden para guardar a imagem em base64

			    if (!preview || !inputFile || !inputFile.files.length) {
			    	 // entra aqui se NÃO tiver arquivo selecionado
			        preview.src = ""; // limpar a imagem
			        if (hiddenBase64) hiddenBase64.value = ""; // limpa o campo hidden também
			        return;
			    }

			    const file = inputFile.files[0]; // pega o primeiro arquivo (imagem)
			    
			 	// Cria o leitor de arquivos
			    const reader = new FileReader();

			    // Quando terminar de ler o arquivo
			    reader.onload = () => {
			    	// reader.result: contém o conteúdo do arquivo lido como uma string Base64 (que pode ser usada direto no src da imagem
			        preview.src = reader.result;  // mostra a imagem na tela
			        if (hiddenBase64) {
			            hiddenBase64.value = reader.result;    // salva a imagem em base64 no campo hidden
			        }
			    };
				// Incia a leitura
			 	// reader.readAsDataURL(fileInput) vai ler o conteúdo da imagem como Data URL (Base64).
			    reader.readAsDataURL(file);  // Preview da imagem
			}
			
      </script>
</head>