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


														<form
															action="<%=request.getContextPath()%>/SalvarUsuarioServlet"
															method="post" id="usuarioForm">
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">ID:</label>
																<div class="col-sm-1">
																	<input type="text" name="id" id="id"
																		class="form-control" readonly="readonly"
																		value="${empty id ? usuarioSalvo.id : id}">
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
                                                                    <div class="col-sm-8">
                                                                        <select name="perfil" class="form-control" required="required">
                                                                            <option value="0">Selecione o Perfil</option>
                                                                            <option value="1">Admin</option>
                                                                            <option value="2">Secretária</option>
                                                                            <option value="3">Auxiliar</option>
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
															/*removendo o usuarioSalvo da sessão com JSTL*/
															%>
															<c:remove var="usuarioSalvo" scope="session" />


															<div class="form-group row">
																<label class="col-sm-1 col-form-label"></label>
																<%
																/*Ao definir o 'type' como 'button' dentro do formulário, ele não envia.
																obs: não faz nada sozinho, só executa o que você manda via JavaScript
																*/
																%>
																<button type="button"
																	class="btn btn-success btn-round waves-effect hor-grd btn-grd-success"
																	id="btn-form-cadastro" onclick="limparFormulario();">Novo</button>
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
																	onclick="excluirCadastroComAjax2();">Excluir</button>
																<button type="button"
																	class="btn btn-inverse btn-round waves-effect hor-grd btn-grd-inverse"
																	id="btn-form-cadastro" data-toggle="modal"
																	data-target="#modalCadastro"
																	onclick="limparTabelaPesquisarUsuario();">Pesquisar</button>

															</div>


														</form>
													</div>
												</div>

												<c:choose>
													<c:when test="${erros != null}">
														<c:forEach var="erro" items="${erros}">
															<span class="erro">${erro}</span>
															<br>
														</c:forEach>
													</c:when>
													<%-- Este é um comentário JSP, que não causa erro --%>
													<c:when test="${param.acao eq 'salvar'}">
														<span class="sucessoSalvo">O registro foi salvo com
															sucesso!</span>
													</c:when>
													<c:when test="${param.acao eq 'atualizar'}">
														<span class="sucessoSalvo">O registro foi
															atualizado com sucesso!</span>
													</c:when>
												</c:choose>


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
																<c:forEach items="${listarUsuariosSession}"
																	var="usuario">
																	<tr>
																		<td>${usuario.id}</td>
																		<td>${usuario.nome}</td>
																		<td>${usuario.email}</td>
																		<td><a
																			class="btn btn-info btn-round waves-effect hor-grd btn-grd-info"
																			href="<%=request.getContextPath()%>/PesquisarUsuarioServlet?acao=editar&id=${usuario.id}">Editar</a></td>
																		<td><button type="button"
																				class="btn btn-danger btn-round waves-effect hor-grd btn-grd-danger"
																				onclick="excluirCadastro(${usuario.id})">Excluir</button></td>
																	</tr>

																</c:forEach>

															</tbody>
														</table>
													</div>
												</div>

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
</body>

</html>