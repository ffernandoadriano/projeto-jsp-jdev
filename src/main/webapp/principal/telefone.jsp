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


													<!-- Formulario inicio -->
													<div class="card-block">
														<h4 class="sub-title">Formulário</h4>
														<form
															action="${pageContext.request.contextPath}/SalvarTelefoneServlet"
															method="post" id="telefoneForm">

															<!-- id do telefone para edição -->
															<!-- essa verificação é necessário para retorna null em vez de "" (vazio) -->

															<c:if test="${not empty idTel}">
																<input type="hidden" name="idTel" id="idTel" value="${idTel}" />
															</c:if>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">ID:</label>
																<div class="col-sm-1">
																	<input type="text" name="id" id="id"
																		class="form-control" readonly="readonly" value="${id}">
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Nome:</label>
																<div class="col-sm-8">
																	<input type="text" name="nome" id="nome"
																		class="form-control" required="required"
																		readonly="readonly" value="${nome}" />
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Tipo:</label>
																<div class="col-sm-2">
																	<select name="tipo" class="form-control" id="tipo"
																		required="required">
																		<option value="0">Selecione o Contato</option>
																		<option value="1" ${tipo == 1 ? 'selected' : ''}>Celular</option>
																		<option value="2" ${tipo == 2 ?  'selected' : ''}>Telefone</option>
																	</select>
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Contato:</label>
																<div class="col-sm-3">
																	<input type="tel" name="contato" id="contato"
																		class="form-control" required="required"
																		value="${contato}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Informações
																	adicionais:</label>
																<div class="col-sm-8">
																	<input type="text" name="info" id="info"
																		class="form-control" value="${info}" />

																</div>
															</div>


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
																	onclick="limparFormTelefone();">Novo</button>
																<%
																/*
																Ao não definir nenhum 'type' para o botão. exemplo: <button>Salvar</button>
																O navegador assume automaticamente que é type="submit", ou seja: ele vai enviar o formulário.
																*/
																%>
																<button
																	class="btn btn-primary btn-round waves-effect hor-grd btn-grd-primary"
																	id="btn-form-cadastro">Salvar</button>
															</div>


														</form>
													</div>
													<!-- Formulario Fim -->
												</div>

												<!-- Basic Form Inputs card end -->

												<!-- linha de Contatos Inicio -->
												<div class="row">
													<div class="col-lg-12">
														<div class="card">
															<div class="card-block table-border-style">
																<h4 class="sub-title">Lista de Contato</h4>
																<div class="table-responsive">
																	<table class="table table-hover">
																		<thead>
																			<tr>
																				<th>ID</th>
																				<th>Tipo</th>
																				<th>Contato</th>
																				<th>Info. Adicional</th>
																				<th></th>
																				<th></th>
																			</tr>
																		</thead>
																		<tbody>
																			<c:forEach items="${telefones}" var="telefone">
																				<tr>
																					<td>${telefone.id}</td>
																					<td>${telefone.tipoContato.descricao}</td>
																					<td>${telefone.numero}</td>
																					<td>${telefone.infoAdicional}</td>
																					<td><a
																						class="btn btn-info btn-round waves-effect hor-grd btn-grd-info"
																						href="${pageContext.request.contextPath}/TelefoneServlet?foneID=${telefone.id}&action=edit">Editar</a></td>
																					<td><button type="button"
																							class="btn btn-danger btn-round waves-effect hor-grd btn-grd-danger"
																							onclick="excluirTelefone(${telefone.id});">Excluir</button></td>
																				</tr>

																			</c:forEach>

																		</tbody>
																	</table>
																</div>

															</div>

														</div>
													</div>
												</div>

												<!-- linha de Contatos Inicio -->
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

	<!-- Notificações -->
	<script>
	    $(document).ready(function () {
	        <c:if test="${erros != null}">
	            <c:forEach var="erro" items="${erros}">
	                triggerNotification("top", "right", "fa fa-exclamation-triangle", "danger", "animated fadeInRight", "animated fadeOutRight", " ${erro}", '');
	            </c:forEach>
	        </c:if>
	
	        <c:if test="${not empty sessionScope.Salvar}">
	            triggerNotification('top', 'center', 'fa fa-check', 'success', 'animated bounceIn', 'animated bounceOut', ' Registro salvo com sucesso!', '');
	        </c:if>
	
	        <c:if test="${not empty sessionScope.Atualizar}">
	            triggerNotification('top', 'center', 'fa fa-check', 'success', 'animated bounceIn', 'animated bounceOut', ' Registro atualizado com sucesso!', '');
	        </c:if>
	    });
	</script>

	<%-- Remoção da variável da sessão fora do <script> para evitar problemas --%>
	<!-- Remover Salvar da sessão após uso  com JSTL-->
	<c:if test="${not empty sessionScope.Salvar}">
		<c:remove var="Salvar" scope="session" />
	</c:if>

	<c:if test="${not empty sessionScope.Atualizar}">
		<c:remove var="Atualizar" scope="session" />
	</c:if>

</body>

</html>
