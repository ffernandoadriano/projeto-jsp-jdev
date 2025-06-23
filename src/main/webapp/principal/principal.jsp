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
											<div id="conteudo-projeto">

												<h1>🚧 E-commerce Livraria</h1>
												<h2>Projeto Servlet, JSP e JPA</h2>

												<h2>📚 Conhecimentos aplicados</h2>

												<h3>
													- <strong>Tópicos POO:</strong>
												</h3>
												<ul>
													<li>Encapsulamento</li>
													<li>Construtores</li>
													<li>ToString (Object / overriding)</li>
													<li>Associações</li>
													<li>Modificadores de acesso</li>
													<li>Enumerador</li>
													<li>Downcasting</li>
													<li>Upcasting</li>
													<li>Membros estáticos</li>
													<li>Overriding</li>
													<li>Overloading</li>
													<li>Polimorfismo (ToString)</li>
													<li>Herença</li>
													<li>Exceções</li>
													<li>Padrão de camadas</li>
													<li>Reflection API</li>
													<li>Generics</li>
												</ul>

												<h3>
													- <strong>Tópicos de estruturas de dados:</strong>
												</h3>
												<ul>
													<li>Lista</li>
													<li>Vetor</li>
												</ul>

												<h3>
													- <strong>Arquitetura de software:</strong>
												</h3>
												<ul>
													<li>MVC - (Model-View-Controller)</li>
												</ul>

												<h3>
													- <strong>Padrão de projeto:</strong>
												</h3>
												<ul>
													<li>DAO - (Data Access Object)</li>
													<li>Factory</li>
													<li>Singleton</li>
												</ul>

												<h3>
													- <strong>Front-End:</strong>
												</h3>
												<ul>
													<li>JavaScript</li>
													<li>HTML5</li>
													<li>CSS3</li>
													<li>Bootstrap</li>
												</ul>

												<h3>
													- <strong>🎲 Persistência de dados:</strong>
												</h3>
												<ul>
													<li>MySQL</li>
												</ul>

												<h3>
													- <strong>🔨 Ferramentas:</strong>
												</h3>
												<ul>
													<li>Docker</li>
													<li>Apache Tomcat (Servlet Container)</li>
													<li>Eclipse</li>
												</ul>

												<h3>
													- <strong>Principais dependências usadas no
														projeto:</strong>
												</h3>
												<pre>
													<code>
&lt;dependencies&gt;
  &lt;dependency&gt;
      &lt;groupId&gt;org.hibernate.orm&lt;/groupId&gt;
      &lt;artifactId&gt;hibernate-core&lt;/artifactId&gt;
      &lt;version&gt;6.6.2.Final&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;mysql&lt;/groupId&gt;
      &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;
      &lt;version&gt;8.0.33&lt;/version&gt;
  &lt;/dependency&gt;	

  &lt;dependency&gt;
      &lt;groupId&gt;jakarta.servlet.jsp.jstl&lt;/groupId&gt;
      &lt;artifactId&gt;jakarta.servlet.jsp.jstl-api&lt;/artifactId&gt;
      &lt;version&gt;3.0.2&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;org.glassfish.web&lt;/groupId&gt;
      &lt;artifactId&gt;jakarta.servlet.jsp.jstl&lt;/artifactId&gt;
      &lt;version&gt;3.0.1&lt;/version&gt;
  &lt;/dependency&gt;
&lt;/dependencies&gt;
    </code>
												</pre>

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
