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
						</c:when>
						<c:when test="${caminho  eq '/principal/cadastrar_usuario.jsp' }">
							<h5 class="m-b-10">Cadastrar Usu�rio</h5>
							<p class="m-b-0">Formul�rio / Lista de Usu�rios</p>
						</c:when>
						<c:otherwise>
							<h5 class="m-b-10">Dashboard</h5>
							<p class="m-b-0">Welcome to Mega Able</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="col-md-4">
				<ul class="breadcrumb-title">
					<li class="breadcrumb-item"><a
						href="<%=request.getContextPath()%>/principal/principal.jsp">
							<i class="fa fa-home"></i>
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

						<c:otherwise>
							<li class="breadcrumb-item" aria-current="page">Dashboard</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>

		</div>
	</div>
</div>