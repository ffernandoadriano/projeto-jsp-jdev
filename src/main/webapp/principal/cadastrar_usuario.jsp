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
																	id="btn-form-cadastro" onclick="excluirCadastro();">Excluir</button>
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

</body>

</html>
