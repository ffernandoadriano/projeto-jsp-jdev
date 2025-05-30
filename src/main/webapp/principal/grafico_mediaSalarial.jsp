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
														<h4 class="sub-title">Filtrar</h4>
														<form
															action="${pageContext.request.contextPath}/VisualizarRelatorioUsuarioServlet"
															method="post" id="relatorioUsuarioForm" novalidate>

															<div class="form-row align-items-end">
																<div class="form-group col-12 col-sm-4">
																	<label for="dataInicial">Data de Nascimento
																		Período:</label> <input type="text" class="form-control"
																		id="dataInicial" name="dataInicial"
																		value="${dataInicial}" placeholder="dd/MM/aaaa"
																		autocomplete="off" required>
																</div>

																<div class="form-group col-12 col-sm-4">
																	<label for="dataFinal">até</label> <input type="text"
																		class="form-control" id="dataFinal" name="dataFinal"
																		value="${dataFinal}" placeholder="dd/MM/aaaa"
																		autocomplete="off" required>
																</div>

																<div class="form-group col-12 col-sm-4">
																	<label class="d-none d-sm-block">&nbsp;</label>
																	<button type="button" class="btn btn-primary btn-block"
																		onclick="gerarGraficoSalarial();">Gerar
																		Gráfico</button>
																</div>
															</div>
														</form>
													</div>
													<!-- Formulario Fim -->
												</div>
												<!-- Basic Form Inputs card end -->
										
										
		
												<!-- Gráfico Inicio -->
												<div class="row" id="graficoContainer">
													<div class="col-lg-12">
														<div class="card">
															<div class="card-block table-border-style"></div>
															<div>
																<canvas id="myChart"></canvas>
															</div>
														</div>
													</div>
												</div>
												<!-- Gráfico Final -->


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

	<!-- DatePicker em Português (jQuery UI) -->
	<script>
		$(function() {
			const options = {
				dateFormat : 'dd/mm/yy',
				dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
						'Sexta', 'Sábado' ],
				dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
				dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
						'Sáb' ],
				monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril',
						'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro',
						'Outubro', 'Novembro', 'Dezembro' ],
				monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
						'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
				nextText : 'Próximo',
				prevText : 'Anterior',
				changeMonth : true,
				changeYear : true
			};

			$("#dataInicial").datepicker(options);
			$("#dataFinal").datepicker(options);
		});
	</script>

	<!-- Aplicando Mascara para dataInicial e dataInicial -->
	<script>
		function aplicarMascaraData(campo) {
			campo.addEventListener('input', function(e) {
				let valor = e.target.value.replace(/\D/g, '');

				if (valor.length > 2) {
					valor = valor.slice(0, 2) + '/' + valor.slice(2);
				}
				if (valor.length > 5) {
					valor = valor.slice(0, 5) + '/' + valor.slice(5);
				}
				if (valor.length > 10) {
					valor = valor.slice(0, 10);
				}

				e.target.value = valor;
			});
		}

		const campoDataInicial = document.getElementById('dataInicial');
		const campoDataFinal = document.getElementById('dataFinal');

		aplicarMascaraData(campoDataInicial);
		aplicarMascaraData(campoDataFinal);
	</script>

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
