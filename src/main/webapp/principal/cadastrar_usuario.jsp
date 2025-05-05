<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="pt-br">


<jsp:include page="head.jsp" />

<body>
	<!-- Carregamento inicial -->
	<jsp:include page="loaderstart.jsp"></jsp:include>

	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<!-- Menu topo -->
			<jsp:include page="menu-top.jsp"></jsp:include>

			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">

					<!-- Menu Lateral -->
					<jsp:include page="menu-lateral.jsp"></jsp:include>

					<div class="pcoded-content">

						<!-- Page-header abaixo do menu topo -->
						<jsp:include page="page-header.jsp"></jsp:include>

						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">

											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">

													<div class="card-block">
														<h4 class="sub-title">Formulário</h4>


														<%
														/* O atributo enctype especifica como os dados do formulário devem ser codificados ao serem enviados para o servidor.
														    Quando se trata de upload de arquivos, é necessário definir enctype="multipart/form-data" no elemento <form>

														Isso é crucial porque:
															# Permite que o navegador envie arquivos binários (como imagens ou documentos) juntamente com os dados do formulário.
															# Garante que os dados sejam divididos em múltiplas partes, facilitando a separação entre arquivos e outros campos do formulário
														*/
														%>

														<form
															action="<%=request.getContextPath()%>/SalvarUsuarioServlet"
															method="post" id="usuarioForm"
															enctype="multipart/form-data">

															<!-- Diz o local onde a imagem será inserida. -->
															<input type="hidden" value="perfil" name="tipoImagem" />

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">ID:</label>
																<div class="col-sm-1">
																	<input type="text" name="id" id="id"
																		class="form-control" readonly="readonly"
																		value="${empty id ? usuarioSalvo.id : id}">
																</div>
															</div>

															<!-- Atributo "SRC" será preenchido com a função visualizarImagemTela -->
															<div class="form-group row">
																<div class="col-sm-1">
																	<c:choose>

																		<c:when
																			test="${imagemBase64 != null || PerfilFoto != null}">

																			<a id="linkDownloadFotoPerfil"
																				href="<c:url value='/DownloadImagemServlet?id=${empty id ? usuarioSalvo.id : id}&tipoImagem=perfil' />">

																				<img alt="Foto Perfil"
																				src="${empty PerfilFoto ? imagemBase64.imageBase64 : PerfilFoto.imageBase64}"
																				id="fotoBase64" width="60px">

																			</a>

																		</c:when>


																		<c:otherwise>
																			<img alt="Foto Perfil"
																				src="<%=request.getContextPath()%>/assets/images/perfil.png"
																				id="fotoBase64" width="60px">
																		</c:otherwise>

																	</c:choose>



																</div>
																<div class="col-sm-8">
																	<input type="file" class="form-control"
																		accept="image/*" id="fileFoto" name="filePerfilFoto"
																		onchange="visualizarImagemTela('fotoBase64', 'fileFoto' )";>
																	<%
																	/*restringe para aceitar apenas imagem*/
																	%>
																</div>
															</div>


															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Nome:</label>
																<div class="col-sm-8">
																	<input type="text" name="nome" id="nome"
																		class="form-control" required="required"
																		value="${empty nome ? usuarioSalvo.nome : nome}" />
																</div>
															</div>




															<!-- Radio sexo inicio -->


															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Sexo:</label>


																<div class="col-sm-1 col-form-label" id="radioSexo">
																	<div class="col-sm-8" id="radioSexo">
																		<input class="form-check-input" type="radio"
																			name="sexo" value="M" required="required"
																			${sexo eq 'M' or usuarioSalvo.sexo.sigla eq 'M' ? 'checked' : ''}>
																		<label class="form-check-label" for="inlineRadio1">Masculino</label>
																	</div>

																</div>

																<div class="col-sm-1 col-form-label" id="radioSexo"
																	style="margin-left: 21px">
																	<input class="form-check-input" type="radio"
																		name="sexo" value="F" required="required"
																		${sexo eq 'F' or usuarioSalvo.sexo.sigla eq 'F' ? 'checked' : ''}>
																	<label class="form-check-label" for="inlineRadio2">Feminino</label>
																</div>
															</div>

															<!-- sexo fim -->

															<!-- Endereço inicio -->

															<input type="hidden" name="enderecoId"
																value="${not empty enderecoId ? enderecoId : usuarioSalvo.endereco.id}">

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Cep:</label>
																<div class="col-sm-2">
																	<input type="text" name="cep" id="cep"
																		onblur="pesquisarCep();" class="form-control"
																		required="required"
																		value="${not empty cep ? cep : usuarioSalvo.endereco.cep}"
																		maxlength="9" placeholder="00000-000" />
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Rua:</label>
																<div class="col-sm-6">
																	<input type="text" name="rua" id="rua"
																		class="form-control" required="required"
																		value="${not empty rua ? rua : usuarioSalvo.endereco.rua}" />
																</div>

																<label class="col-sm-1 col-form-label">Numero:</label>
																<div class="col-sm-1">
																	<input type="text" name="numero" id="numero"
																		class="form-control" required="required"
																		value="${not empty numero ? numero : usuarioSalvo.endereco.numero}" />
																</div>

															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Bairro:</label>
																<div class="col-sm-8">
																	<input type="text" name="bairro" id="bairro"
																		class="form-control" required="required"
																		value="${not empty bairro ? bairro : usuarioSalvo.endereco.bairro}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Cidade:</label>
																<div class="col-sm-3">
																	<input type="text" name="cidade" id="cidade"
																		class="form-control" required="required"
																		value="${not empty cidade ? cidade : usuarioSalvo.endereco.cidade}" />

																</div>

																<label class="col-sm-1 col-form-label">Estado:</label>
																<div class="col-sm-2">
																	<input type="text" name="estado" id="estado"
																		class="form-control" required="required"
																		value="${not empty estado ? estado : usuarioSalvo.endereco.estado}" />
																</div>

																<label class="col-sm-1 col-form-label">UF:</label>
																<div class="col-sm-1">
																	<input type="text" name="uf" id="uf"
																		class="form-control" required="required"
																		value="${not empty uf ? uf :usuarioSalvo.endereco.uf}" />
																</div>
															</div>

															<!-- Endereço Fim -->

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">E-mail:</label>
																<div class="col-sm-8">
																	<input type="email" name="email" id="email"
																		class="form-control" required="required"
																		value="${empty email ? usuarioSalvo.email : email}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Perfil:</label>
																<div class="col-sm-2">
																	<select name="perfil" class="form-control" id="perfil"
																		required="required">
																		<option value="0">Selecione o Perfil</option>
																		<option value="1"
																			${perfil == 1 or usuarioSalvo.perfil.id == 1 ? 'selected' : ''}>Admin</option>
																		<option value="2"
																			${perfil == 2 or usuarioSalvo.perfil.id == 2 ?  'selected' : ''}>Secretária</option>
																		<option value="3"
																			${perfil == 3 or usuarioSalvo.perfil.id == 3 ? 'selected' : ''}>Auxiliar</option>
																	</select>
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">login:</label>
																<div class="col-sm-8">
																	<input type="text" name="login" id="login"
																		class="form-control" required="required"
																		value="${empty login ? usuarioSalvo.login : login}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Senha:</label>
																<div class="col-sm-8">
																	<input type="password" name="senha" id="senha"
																		class="form-control" required="required"
																		value="${empty senha ? usuarioSalvo.senha : senha}" />

																</div>
															</div>

															<%
															/*removendo o usuarioSalvo e imagemBase64 da sessão com JSTL*/
															%>
															<c:remove var="usuarioSalvo" scope="session" />
															<c:remove var="imagemBase64" scope="session" />


															<div class="form-group row">
																<label class="col-sm-1 col-form-label"></label>
																<%
																/*Ao definir o 'type' como 'button' dentro do formulário, ele não envia.
																obs: não faz nada sozinho, só executa o que você manda via JavaScript
																*/
																%>
																<button type="button"
																	class="btn btn-success btn-round waves-effect hor-grd btn-grd-success"
																	id="btn-form-cadastro-novo"
																	onclick="limparFormulario();">Novo</button>
																<%
																/*
																Ao não definir nenhum 'type' para o botão. exemplo: <button>Salvar</button>
																O navegador assume automaticamente que é type="submit", ou seja: ele vai enviar o formulário.
																*/
																%>
																<button
																	class="btn btn-primary btn-round waves-effect hor-grd btn-grd-primary"
																	id="btn-form-cadastro">Salvar</button>
																<button type="button"
																	class="btn btn-danger btn-round waves-effect hor-grd btn-grd-danger"
																	id="btn-form-cadastro"
																	onclick="excluirCadastroUsuario();">Excluir</button>
																<c:if
																	test="${not empty id || not empty usuarioSalvo.id}">
																	<a
																		href="${pageContext.request.contextPath}/TelefoneServlet?id=${empty id ? usuarioSalvo.id : id}"
																		class="btn btn-info btn-round  waves-effect hor-grd btn-grd-info"
																		id="btn-form-cadastro-telefone">Telefone</a>
																</c:if>
																<button type="button"
																	class="btn btn-inverse btn-round waves-effect hor-grd btn-grd-inverse"
																	id="btn-form-cadastro" data-toggle="modal"
																	data-target="#modalCadastro"
																	onclick="limparTabelaPesquisarUsuario();">Pesquisar</button>

															</div>


														</form>
													</div>
												</div>

												<!-- Basic Form Inputs card end -->
											</div>

										</div>
									</div>

									<!-- linha de usuários -->
									<div class="row">
										<div class="col-lg-12">
											<div class="card">
												<div class="card-block table-border-style">
													<h4 class="sub-title">Lista de Usuários</h4>
													<div class="table-responsive">
														<table class="table table-hover">
															<thead>
																<tr>
																	<th>ID</th>
																	<th>Nome</th>
																	<th>Email</th>
																	<th></th>
																	<th></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${usuarios}" var="usuario">
																	<tr>
																		<td>${usuario.id}</td>
																		<td>${usuario.nome}</td>
																		<td>${usuario.email}</td>
																		<td><a
																			class="btn btn-info btn-round waves-effect hor-grd btn-grd-info"
																			href="<%=request.getContextPath()%>/CadastrarUsuarioServlet?userID=${usuario.id}&action=edit">Editar</a></td>
																		<td><button type="button"
																				class="btn btn-danger btn-round waves-effect hor-grd btn-grd-danger"
																				onclick="excluirCadastroUsuario(${usuario.id});">Excluir</button></td>
																	</tr>

																</c:forEach>

															</tbody>
														</table>
													</div>

													<c:if test="${totalPaginas > 0}">
														<!-- Menu Páginação- Inicio -->
														<nav aria-label="Page navigation example"
															class="pagination justify-content-center">
															<ul class="pagination">
																<li class="page-item"><a class="page-link" href="#"
																	aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
																</a></li>

																<c:forEach var="i" begin="1" end="${totalPaginas}"
																	step="1">

																	<li
																		class="page-item <c:if test="${paginacao == i}">active</c:if>"
																		aria-current="page"><span class="page-link">${i}</span>
																	</li>

																</c:forEach>

																<li class="page-item"><a class="page-link" href="#"
																	aria-label="Next"> <span aria-hidden="true">&raquo;</span>
																</a></li>
															</ul>
														</nav>
														<!-- Menu Páginação - fim -->
													</c:if>

												</div>

											</div>
										</div>


										<!-- Page-body end -->
									</div>
									<div id="styleSelector"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascript.jsp"></jsp:include>

	<!-- Large modal -->
	<div class="modal fade" id="modalCadastro" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl-custom">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
						Usuário</h5>
				</div>
				<!-- AQUI FOI ADICIONADO O SCROLL -->
				<div class="modal-body" style="max-height: 60vh; overflow-y: auto;">
					<!-- inicio do body -->

					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="Digite o nome" aria-label="nome"
							aria-describedby="basic-addon2" name="pesquisarNome"
							id="pesquisarNome" autocomplete="off"
							onkeydown="if (event.key === 'Enter') { event.preventDefault(); document.getElementById('btnPesquisar').click(); }">
						<div class="input-group-append">
							<button type="button"
								class="btn btn-success btn-round waves-effect hor-grd btn-grd-success"
								type="button" onclick="buscarUsuarioPorNome();"
								id="btnPesquisar" style="margin-left: 5px">Pesquisar</button>
						</div>
					</div>

					<div class="card-block table-border-style">
						<div class="table-responsive">
							<table class="table" id="tbPesquisarUsuario">
								<thead>
									<tr>
										<th>ID</th>
										<th>Nome</th>
										<th>Email</th>
										<th></th>
									</tr>
								</thead>
								<tbody>

								</tbody>
							</table>
						</div>
					</div>
					<!-- fim do body -->
				</div>

				<!-- Paginação Inicio -->
				<span style="margin-bottom: 0px" class="sub-title"></span>
				<nav sty aria-label="Page navigation example"
					class="pagination justify-content-center">
					<ul class="pagination" id="ulPaginacaoAjax">
						<!-- será preenchido pelo ajax -->
					</ul>
				</nav>
				<!-- Paginação Final -->

				<div class="modal-footer d-flex justify-content-between w-100">
					<span class="align-self-center" id="qtdRegistros"></span>
					<button type="button"
						class="btn btn-inverse btn-round waves-effect hor-grd btn-grd-inverse"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="jsWhatsAppIcone.jsp"></jsp:include>


	<!-- Notificações -->
	<script>
			 $(document).ready(function () {
				<c:choose>
					<c:when test="${erros != null}">
					
						<c:forEach var="erro" items="${erros}">
						 	triggerNotification("top", "right", "fa fa-exclamation-triangle", "danger", "animated fadeInRight", "animated fadeOutRight", " ${erro}", '');
						</c:forEach>
						
					</c:when>
					<%-- Este é um comentário JSP, que não causa erro --%>
					<c:when test="${param.acao eq 'salvar'}">
					
						 triggerNotification('top', 'center', 'fa fa-check', 'success', 'animated bounceIn', 'animated bounceOut', ' Registro salvo com sucesso!', '');
						 
					</c:when>
					<c:when test="${param.acao eq 'atualizar'}">
					
					 	triggerNotification('top', 'center', 'fa fa-check', 'success', 'animated bounceIn', 'animated bounceOut', ' Registro atualizado com sucesso!', '');
					 
					</c:when>
				</c:choose>
			 });
		</script>

	<!-- Mascara para Cep -->
	<script>
	  document.getElementById('cep').addEventListener('input', function (e) {
	    let cep = e.target.value;
	
	    // Remove tudo que não for número
	    cep = cep.replace(/\D/g, '');
	
	    // Adiciona o hífen depois do quinto dígito
	    if (cep.length > 5) {
	      cep = cep.substring(0, 5) + '-' + cep.substring(5, 8);
	    }
	
	    e.target.value = cep;
	  });
	</script>


	<!-- Paginação -->
	<script>
			 const pageItems = document.querySelectorAll('.pagination .page-item'); // item da lista
			 const pageLinks = document.querySelectorAll('.pagination .page-link'); // link da lista
			
			 function setActive(index) {
			   // Remove o active de todos
			   pageItems.forEach(item => item.classList.remove('active'));
			
			   // Adiciona active no índice atual
			   if (pageItems[index] && !pageItems[index].querySelector('span[aria-hidden]')) {
			    	 pageItems[index].classList.add('active');
			    	 
			     	// chamar o href para redirecionar aqui
			    	window.location.href ='<%=request.getContextPath()%>/CadastrarUsuarioServlet?pagina='+ index;
			   }
			 }

				// verifica na lista o index
			 function getCurrentIndex() {
			   let currentIndex = -1;
			   
			   pageItems.forEach((item, index) => {
			     if (item.classList.contains('active')) {
			       currentIndex = index;
			     }
			   });
			   
			   return currentIndex;
			 }
			
			 pageLinks.forEach(link => {
				 
			   link.addEventListener('click', function(event) {
				   
				 event.preventDefault(); // impede que a ação padrão associada a um evento específico seja executada.
			
			     const parentLi = this.parentElement; // acessa o elemento pai imediato "<li> é o pai"
			
			     if (this.getAttribute('aria-label') === 'Previous') {
			       // Clicou no Previous
			       const currentIndex = getCurrentIndex();
			       
			       if (currentIndex > 1) { // Índice 0 é o Previous, índice 1 é o 1
			         setActive(currentIndex - 1);
			       }
			       
			     } else if (this.getAttribute('aria-label') === 'Next') {
			       // Clicou no Next
			       const currentIndex = getCurrentIndex();
			       
			       if (currentIndex < pageItems.length - 2) { // penúltimo é 3, último é Next
			         setActive(currentIndex + 1);
			       }
			     } else {
			       // Clicou em um número direto
			       setActive([...pageLinks].indexOf(this)); // retorna a posição atual do clique
			     }
			   });
			 });
		</script>
</body>

</html>