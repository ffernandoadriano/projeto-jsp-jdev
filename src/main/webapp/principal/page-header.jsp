<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="caminho" value="${pageContext.request.servletPath}" />

<div class="page-header">
	<div class="page-block">
		<div class="row align-items-center">
			<div class="col-md-8">
				<div class="page-header-title">

					<c:choose>
						<c:when test="${caminho  eq '/principal/principal.jsp' }">
							<h5 class="m-b-10">P�gina inicial</h5>
							<p class="m-b-0">Bem vindo</p>
							<c:set var="id" value="home" />
							<!-- toda vez que acessar o home, reinicie a pagina��o -->
							<c:if test="${not empty sessionScope.paginacao}">
								<c:remove var="paginacao" scope="session" />
								<c:remove var="totalPaginas" scope="session" />
							</c:if>
						</c:when>
						<c:when test="${caminho  eq '/principal/cadastrar_usuario.jsp' }">
							<h5 class="m-b-10">Cadastrar Usu�rio</h5>
							<p class="m-b-0">Formul�rio / Lista de Usu�rios</p>
							<c:set var="id" value="cadastros" />
						</c:when>
						<c:when test="${caminho  eq '/principal/telefone.jsp' }">
							<h5 class="m-b-10">Telefone</h5>
							<p class="m-b-0">Formul�rio / Lista de Contato</p>
							<c:set var="id" value="cadastros" />
						</c:when>
						<c:when test="${caminho  eq '/principal/relatorio_usuario.jsp' }">
							<h5 class="m-b-10">Relat�rio de Usu�rio</h5>
							<p class="m-b-0">Filtrar</p>
							<c:set var="id" value="usuarios" />
						</c:when>
						<c:when
							test="${caminho  eq '/principal/grafico_mediaSalarial.jsp' }">
							<h5 class="m-b-10">Gr�fico M�dia Salarial</h5>
							<p class="m-b-0">Filtrar</p>
							<c:set var="id" value="salarios" />
						</c:when>
						<c:otherwise>
							<h5 class="m-b-10">Dashboard</h5>
							<p class="m-b-0">Welcome to Mega Able</p>
							<c:set var="id" value="home" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-4">
				<ul class="breadcrumb-title">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/HomeServlet"> <i
							class="fa fa-home"></i>
					</a></li>

					<c:choose>
						<c:when test="${caminho eq '/principal/principal.jsp'}">
							<li class="breadcrumb-item" aria-current="page">P�gina
								Inicial</li>
						</c:when>

						<c:when test="${caminho eq '/principal/cadastrar_usuario.jsp'}">
							<li class="breadcrumb-item" aria-current="page"><a href="#!">Cadastros</a></li>
							<li class="breadcrumb-item" aria-current="page">Cadastrar
								Usu�rio</li>
						</c:when>

						<c:when test="${caminho eq '/principal/telefone.jsp'}">
							<li class="breadcrumb-item" aria-current="page"><a href="#!">Cadastros</a></li>
						</c:when>

						<c:when test="${caminho eq '/principal/relatorio_usuario.jsp'}">
							<li class="breadcrumb-item" aria-current="page"><a href="#!">Usu�rios</a></li>
						</c:when>

						<c:when
							test="${caminho eq '/principal/grafico_mediaSalarial.jsp'}">
							<li class="breadcrumb-item" aria-current="page"><a href="#!">M�dia
									salarial</a></li>
						</c:when>

						<c:otherwise>
							<li class="breadcrumb-item" aria-current="page">Dashboard</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>

		</div>
	</div>
</div>

<!-- Marca o menu selecionado -->
<script>
	menuLateralAction("${id}");

	function menuLateralAction(id) {
		document.getElementById(id).classList.add('active');
	}
</script>