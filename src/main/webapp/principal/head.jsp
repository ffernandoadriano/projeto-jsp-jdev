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
<!-- DatePicker -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<style type="text/css">
#btn-form-cadastro, #btn-form-cadastro-novo, #btn-form-cadastro-telefone
	{
	margin-left: 10px;
	margin-bottom: 10px;
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

#radioSexo {
	margin-left: 3px;
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

#graficoContainer {
	display: none;
}

/* Page-estilo-home-inicio */
#conteudo-projeto {
	max-width: 960px;
	margin: 40px auto;
	background-color: #ffffff;
	padding: 40px 30px;
	border-radius: 12px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

h1, h2 {
	text-align: center;
	margin-bottom: 20px;
}

h3 {
	margin-top: 30px;
}

/* Estilo exclusivo para as listas dentro do #conteudo-projeto */
#conteudo-projeto ul {
	list-style-type: disc;
	padding-left: 40px;
}

pre {
	background-color: #212529;
	color: #f8f9fa;
	padding: 15px;
	border-radius: 10px;
	overflow-x: auto;
}

code {
	font-size: 0.9rem;
}

/* Page-estilo-home-fim */
</style>

<script type="text/javascript">

	function limparFormulario() {
		/*A ordem é essencial, pois, se os itens forem posicionados incorretamente, a funcionalidade pode não atingir sua eficácia total.*/
	
	    let elementos = document.getElementById("usuarioForm").elements; // retorna os elementos html dentro do formulário
		  for(let i = 0; i < elementos.length; i ++){
			  const el = elementos[i];

			    if (el.type === "radio") {
			        // pula os radios, para não mexer neles, mantendo o value = 'F' e 'M'
			        continue;
			    }

			    el.value = '';  // Limpa o campo existante no formulário
	      }
	      
		  // Seleciona o perfil para 0 "padrão"
		  document.getElementById("perfil").selectedIndex = 0;

		  // Limpar o campo de renda formatado com Cleave.js
		  valorBruto = "0";
		  if (typeof cleave !== 'undefined') {
		      cleave.setRawValue('0,00'); // zera o valor e aplica a formatação corretamente
		  }
		  
		  // esconde o botão telefone, se ele existir
		  const telefoneBtn = document.getElementById("btn-form-cadastro-telefone");
		  	if (telefoneBtn) {
			  telefoneBtn.style.display = 'none';
			}
	
		  // Oculta mensagens de sucesso e erro (definido no atributo 'class') 
		  const mensagens = document.querySelectorAll(".sucessoSalvo, .erro");
		  mensagens.forEach(msg => msg.style.display = "none");
	
		  // Limpa os parâmetros da URL
		  history.replaceState(null, '', window.location.pathname);
	
		  // Limpar o radio
		  const radios = Array.from(document.getElementsByName("sexo"));
		  radios.forEach(radio => radio.checked = false);	
	
		  // Limpar a imagem anterior para padrão
		  const fotoPerfil = document.getElementById("fotoBase64");
		  fotoPerfil.src = "<%=request.getContextPath()%>/assets/images/perfil.png";
	
		  // Limpar a url da foto download
		  const linkDownload = document.getElementById("linkDownloadFotoPerfil");
		 // Cria um objeto URL a partir do href atual, com base na origem do site
		  const url = new URL(linkDownload.href, window.location.origin);
		    // Seta o parâmetro "id" com valor vazio
		  url.searchParams.set("id", "");
		    // Atualiza o href do link
		  linkDownload.href = url.toString();
	
		}

	function limparFormTelefone() {
		/*A ordem é essencial, pois, se os itens forem posicionados incorretamente, a funcionalidade pode não atingir sua eficácia total.*/

		// Seleciona o tipo do contato para 0 "padrão"
		document.getElementById("tipo").selectedIndex = 0;

		// limpar contato
		document.getElementById("contato").value = '';

		// limpar informações adicionais
		document.getElementById("info").value = '';

		// remover campo oculto idTel, se existir
		var inputIdTel = document.getElementById("idTel");
		if (inputIdTel) {
			inputIdTel.remove();
		}

		// Limpa os parâmetros da URL
		 history.replaceState(null, '', window.location.pathname);
	}

	function isIdValido(id) {
	    return typeof id !== "undefined" && id !== null && id !== "";
	}


	function excluirCadastroUsuario(idUser = -1){

		let id;
		
		if(idUser === -1){
			// usado no formulario sem parametro
			id = document.forms["usuarioForm"].id.value; // captura o nome do campo do formulário
		}else{
			// Usado na lista com parametro
			id = idUser; 
		}
		
		
		// se diferente de vazio, significa que estou tentando excluir um usuario já cadastro no banco
		if(isIdValido(id)){
			
			let resposta = confirm("Deseja realmente excluir o registro?");

			if(resposta){
				// redireciona a página
				window.location = "<%=request.getContextPath()%>/ExcluirUsuarioServlet?id=" + id; 
			}
			
		}
	}

	function excluirTelefone(id){

		const usuarioId = document.forms["telefoneForm"].id.value; // captura o nome do campo do formulário
		
		// se diferente de vazio, significa que estou tentando excluir um usuario já cadastro no banco
		if(isIdValido(id)){
			
			let resposta = confirm("Deseja realmente excluir o registro?");

			if(resposta){
				// redireciona a página
				window.location = "${pageContext.request.contextPath}/ExcluirTelefoneServlet?idTel=" + id +"&idUser=" + usuarioId; 
			}
			
		}
	}

	// Jquery
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

	/* buscarUsuarioPorNome(pagina = 1): significa que o parâmetro pagina é opcional, e se nenhum valor for passado, ele assume o valor 1 por padrão.
		- buscarUsuarioPorNome();         // Vai usar pagina = 1
		- buscarUsuarioPorNome(3);        // Vai usar pagina = 3
	*/
	function buscarUsuarioPorNome(pagina = 1){
	
		const pesquisarNome = document.getElementById("pesquisarNome").value; // Validando para ter valor p/ buscar no banco de dados
	
	
		if(pesquisarNome != "" && pesquisarNome.trim() != ""){
	
			// URL para onde vai ser redirecionada
			let urlDirecionamento = "<%=request.getContextPath()%>/PesquisarUsuarioServlet";
			
			// ajax do Bootstrap
			$.ajax({
				
				method: "GET",
				url: urlDirecionamento,
				data: {
			        nome: pesquisarNome,  // uma forma de passar os parâmetros
			        pagina: pagina // 1 se for a primeira vez
			      },
				dataType: "json", // Especifica que a resposta esperada é JSON
				success: function(json, textStatus, jqXHR){
					/*
					1 - data: Os dados retornados pelo servidor.
					2 - textStatus: Uma string que descreve o status da requisição (por exemplo, "success").
					3 - jqXHR: O objeto jQuery XMLHttpRequest, que contém informações detalhadas sobre a requisição, incluindo os headers da resposta.
					*/
					
					//const json = JSON.parse(data); // convertendo uma String json para JSON em javaScript
	
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
	
					
					// criação da estrutura da paginação
					const totalPaginas = jqXHR.getResponseHeader("X-Total-Paginas"); //Este método retorna o valor do cabeçalho especificado.
					
					montarPaginacaoAjax(totalPaginas, pagina); // chama a função para montar a paginação
					
				} 
	
			}).fail(function(xhr, status, errorThrown){
					alert("Erro ao tentar pesquisar por nome: "+ xhr.responseText);
				} ); // xhr- detalhes do erro // status - status do erro // errorThrown - exceção de erro
	
		}
	
	}

		function montarPaginacaoAjax(totalPaginas, numPagina){
			
			// Remove linhas antigas
			$("#ulPaginacaoAjax > li").remove(); // jQuery, esse código remove todas as linhas <li> da tabela com ID ulPaginacaoAjax.
		
			// Array para acumular os trechos de HTML (faz o papel do StringBuilder do java)
			const sb = [];
		
			// Adiciona o botão de navegação para a página anterior
			sb.push('<li class="page-item"><a class="page-link" href="#" aria-label="Anterior"><span aria-hidden="true">&laquo;</span></a></li>');
		
			const paginaAtual = parseInt(numPagina, 10); // garante que seja número
			
			// Loop para criar os botões de número de página
			for (let i = 1; i <= totalPaginas; i++) {
			  sb.push('<li class="page-item' + (i === paginaAtual ? ' active' : '') + '" aria-current="page">');
			  sb.push('<a class="page-link" href="#">' + i + '</a>');
			  sb.push('</li>');
			}
		
			// Adiciona o botão de navegação para a próxima página
			sb.push('<li class="page-item"><a class="page-link" href="#" aria-label="Proximo"><span aria-hidden="true">&raquo;</span></a></li>');
		
			// Insere todo o HTML acumulado no elemento de paginação
			$("#ulPaginacaoAjax").html(sb.join(""));
		
			const paginationContainer = document.getElementById('ulPaginacaoAjax');
		
			// Verifica se o listener já foi adicionado
			if (!paginationContainer.dataset.listenerAdded) {
		
			  paginationContainer.addEventListener('click', function(event) {
			    let target = event.target;
		
			    // Garante que target seja sempre o <a> (pode ser um <span> que foi clicado)
			    if (!target.classList.contains('page-link')) {
			      target = target.closest('.page-link');
			    }
		
			    if (!target) return;
		
			    event.preventDefault();
		
			    const parentLi = target.closest('.page-item');
			    const activeItem = paginationContainer.querySelector('.page-item.active');
			    let currentPageNumber = activeItem ? activeItem.textContent.trim() : null;
		
			    // Se for número, converte para inteiro
			    if (currentPageNumber && /^\d+$/.test(currentPageNumber)) {
			      currentPageNumber = parseInt(currentPageNumber, 10);
			    } else {
			      return; // Não continuar se não houver página ativa válida
			    }
		
			    function setActive(index) {
			      const pageItems = paginationContainer.querySelectorAll('.page-item');
			      pageItems.forEach(item => item.classList.remove('active'));
		
			      const targetItem = Array.from(pageItems).find(item => item.textContent.trim() == index);
			      if (targetItem) {
			        targetItem.classList.add('active');
				    // chamar aqui ajax para a paginação clicada
			        buscarUsuarioPorNome(index); // passando o número da página clicada
			      }
			    }
		
			    const ariaLabel = target.getAttribute('aria-label');
		
			    if (ariaLabel === 'Anterior') {
			      if (currentPageNumber > 1) {
			        setActive(currentPageNumber - 1);
			      }
			    } else if (ariaLabel === 'Proximo') {
			      if (currentPageNumber < totalPaginas) {
			        setActive(currentPageNumber + 1);
			      }
			    } else {
			      const pageNumber = target.textContent.trim();
			      if (/^\d+$/.test(pageNumber)) {
			        setActive(pageNumber);
			      }
			    }
			  });
		
			  // Marca como já adicionado
			  paginationContainer.dataset.listenerAdded = 'true';
			}
	}
	
	function limparTabelaPesquisarUsuario(){
				// Remove linhas antigas
				$("#tbPesquisarUsuario > tbody > tr").remove(); // jQuery, esse código remove todas as linhas da <tbody> da tabela com ID tbPesquisarUsuario.

				// Remove linhas antigas
				$("#ulPaginacaoAjax > li").remove(); // jQuery, esse código remove todas as linhas <li> da tabela com ID ulPaginacaoAjax.
				
				document.getElementById("qtdRegistros").textContent = ""; // limpa a quantidade de registros
				document.getElementById("pesquisarNome").value = ""; // limpa o campo pesquisar nome
			}

			function editarFormularioUsuario(id){

				window.location.href = "<%=request.getContextPath()%>/CadastrarUsuarioServlet?userID="+ id +"&action=edit";
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
				// Inicia a leitura
			 	// reader.readAsDataURL(fileInput) vai ler o conteúdo da imagem como Data URL (Base64).
			    reader.readAsDataURL(file);  // Preview da imagem
			}

			function pesquisarCep(){
				const cep = document.getElementById("cep").value; 

				fetch("https://viacep.com.br/ws/" + cep + "/json/")
		        .then(resposta => {
		            if (!resposta.ok) {
		                throw new Error("Erro na requisição");
		            }
		            return resposta.json();
		        })
		        .then(dados => {
		            if (dados.erro) {
		                limpaFormularioCep();
		                alert("CEP não encontrado.");
		                return;
		            }

		            preencherCamposComDados(dados);
		        })
		        .catch(error => {
		            limpaFormularioCep();
		            console.error(error);
		        });
			}

			 function limpaFormularioCep() {
	            //Limpa valores do formulário de cep.
	            document.getElementById('rua').value=("");
	            document.getElementById('numero').value=("");
	            document.getElementById('bairro').value=("");
	            document.getElementById('cidade').value=("");
	            document.getElementById('estado').value=("");
	            document.getElementById('uf').value=("");
		    }

			function preencherCamposComDados(conteudo) {
			    document.getElementById('rua').value = conteudo.logradouro || "";
			    document.getElementById('bairro').value = conteudo.bairro || "";
			    document.getElementById('cidade').value = conteudo.localidade || "";
			    document.getElementById('estado').value = conteudo.estado || ""; 
			    document.getElementById('uf').value = conteudo.uf || "";
			}

			 function gerarRelatorioUsuario(tipoArquivo){

				const dataInicial = document.forms["relatorioUsuarioForm"].dataInicial.value; // Nome do formulário > nome do campo > valor campo. 
				const dataFinal = document.forms["relatorioUsuarioForm"].dataFinal.value; // Nome do formulário > nome do campo > valor campo. 

				if(dataInicial != null && dataFinal != null ){
					
					const dIni = parseDataBR(dataInicial);
				    const dFim = parseDataBR(dataFinal);
	
				    if (dIni > dFim) {
				        alert("A data inicial não pode ser maior que a data final.");
				        return;
				    }
				}

				let url;
				
				if(equalsIgnoreCase(tipoArquivo, "PDF")){
					
					url = "${pageContext.request.contextPath}/ImprimirRelatorioUsuarioPDFServlet?dataInicial=" + encodeURIComponent(dataInicial) + "&dataFinal=" + encodeURIComponent(dataFinal);
					
				}else if(equalsIgnoreCase(tipoArquivo, "EXCEL")){

					url = "${pageContext.request.contextPath}/ImprimirRelatorioUsuarioEXCELServlet?dataInicial=" + encodeURIComponent(dataInicial) + "&dataFinal=" + encodeURIComponent(dataFinal);

				}

				// Abre em uma nova aba
				window.open(url, '_blank');
			}

		 function parseDataBR(dataStr) {
		    const partes = dataStr.split('/');
		    if (partes.length !== 3) return null;

		    const [dia, mes, ano] = partes.map(Number);

		    const data = new Date(ano, mes - 1, dia); // mês começa do zero (0 = janeiro)

		    // Verifica se a data é válida (por exemplo, 31/02/2025 não é)
		    if (data.getFullYear() !== ano || data.getMonth() !== mes - 1 || data.getDate() !== dia) {
		        return null;
		    }

		    return data;
		}


		function equalsIgnoreCase(valueA, valueB) {
		    if (typeof valueA !== "string" || typeof valueB !== "string") return false;
		    return valueA.toLowerCase() === valueB.toLowerCase();
		}


		let graficoAtual = null; // variável global com a instância do gráfico.
		
		function gerarGraficoSalarial(){

			// URL para onde vai ser redirecionada
			let urlDirecionamento = document.getElementById("graficoMediaSalarialForm").action; // retorna o nome da 'action' do formulário

			const dataInicial = document.forms["graficoMediaSalarialForm"].dataInicial.value; // Nome do formulário > nome do campo > valor campo. 
			const dataFinal = document.forms["graficoMediaSalarialForm"].dataFinal.value; // Nome do formulário > nome do campo > valor campo. 

			if(dataInicial != null && dataFinal != null ){
				
				const dIni = parseDataBR(dataInicial);
			    const dFim = parseDataBR(dataFinal);

			    if (dIni > dFim) {
			        triggerNotification("top", "right", "fa fa-exclamation-triangle", "danger", "animated fadeInRight", "animated fadeOutRight", " A data inicial não pode ser maior que a data final.", '');
			        return;
			    }
			}
			
			// ajax do Bootstrap
			$.ajax({
				
				method: "POST",
				url: urlDirecionamento,
				data: {
			        dataInicial: encodeURIComponent(dataInicial),  // uma forma de passar os parâmetros
			        dataFinal: encodeURIComponent(dataFinal),
			        acao: "gerarGrafico"
			      },
				dataType: "json", // Especifica que a resposta esperada é JSON
				success: function(json, textStatus, jqXHR){
					/*
					1 - data: Os dados retornados pelo servidor.
					2 - textStatus: Uma string que descreve o status da requisição (por exemplo, "success").
					3 - jqXHR: O objeto jQuery XMLHttpRequest, que contém informações detalhadas sobre a requisição, incluindo os headers da resposta.
					*/

				 // Mostra o gráfico ao clicar no gerar gráfico
			    document.getElementById("graficoContainer").style.display = "block";

				// O ctx deve ser obtido como getContext('2d'), não apenas getElementById(...).
                // Essa abordagem funciona bem para cenários onde o gráfico é redesenhado com dados diferentes dinamicamente.
				const ctx = document.getElementById('myChart').getContext('2d');

				// Destroi o gráfico anterior, se existir
				if (graficoAtual) {
				    graficoAtual.destroy();
				}

				//  método .map() do JavaScript para transformar um array de objetos JSON em dois novos arrays:
				const labels = json.map(item => item.perfil);
				const valores = json.map(item => item.mediaSalarial);

				// Monta o gráfico na tela
				// Cria novo gráfico e salva na variável global
				graficoAtual = new Chart(ctx, {
					  type: 'bar',
					  data: {
					    labels: labels,
					    datasets: [{
					      label: 'Gráfico de média salarial por Perfil',
					      data: valores,
					      borderWidth: 1,
					      backgroundColor: 'rgba(54, 162, 235, 0.6)'
					    }]
					  },
					  options: {
					    scales: {
					      y: {
					        beginAtZero: true,
					        ticks: {
					          callback: v => v.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
					        }
					      }
					    },
					    plugins: {
					      tooltip: {
					        callbacks: {
					          label: ctx => ctx.parsed.y.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
					        }
					      }
					    }
					  }
					});

			} 
	
			}).fail(function(xhr, status, errorThrown){
					alert("Erro ao tentar gerar gráfico: "+ xhr.responseText);
				} ); // xhr- detalhes do erro // status - status do erro // errorThrown - exceção de erro
		}

</script>
</head>