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

											<div id="conteudo-projeto" class="container">

												<h1>🚧 Projeto com JDBC e Java Server Pages (JSP)</h1>

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
													<li>DTO - (Data Transfer Object)</li>
													<li>Factory</li>
													<li>Singleton</li>
												</ul>

												<h3>
													- <strong>Front-End:</strong>
												</h3>
												<ul>
													<li>JavaScript</li>
													<li>Jquery</li>
													<li>HTML5</li>
													<li>CSS3</li>
													<li>Bootstrap</li>
													<li>Chart.js</li>
												</ul>

												<h3>
													- <strong>🎲 Persistência de dados:</strong>
												</h3>
												<ul>
													<li>PostgreSQL</li>
												</ul>

												<h3>
													- <strong>🔨 Ferramentas:</strong>
												</h3>
												<ul>
													<li>JasperReport Studio</li>
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
      &lt;groupId&gt;jakarta.platform&lt;/groupId&gt;
      &lt;artifactId&gt;jakarta.jakartaee-web-api&lt;/artifactId&gt;
      &lt;version&gt;11.0.0-M5&lt;/version&gt;
      &lt;scope&gt;provided&lt;/scope&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;org.postgresql&lt;/groupId&gt;
      &lt;artifactId&gt;postgresql&lt;/artifactId&gt;
      &lt;version&gt;42.7.5&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;junit&lt;/groupId&gt;
      &lt;artifactId&gt;junit&lt;/artifactId&gt;
      &lt;version&gt;4.13.2&lt;/version&gt;
      &lt;scope&gt;test&lt;/scope&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;org.glassfish.web&lt;/groupId&gt;
      &lt;artifactId&gt;jakarta.servlet.jsp.jstl&lt;/artifactId&gt;
      &lt;version&gt;3.0.1&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;jakarta.servlet.jsp.jstl&lt;/groupId&gt;
      &lt;artifactId&gt;jakarta.servlet.jsp.jstl-api&lt;/artifactId&gt;
      &lt;version&gt;3.0.1&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
      &lt;artifactId&gt;jackson-databind&lt;/artifactId&gt;
      &lt;version&gt;2.18.3&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;com.fasterxml.jackson.datatype&lt;/groupId&gt;
      &lt;artifactId&gt;jackson-datatype-jsr310&lt;/artifactId&gt;
      &lt;version&gt;2.18.3&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;net.sf.jasperreports&lt;/groupId&gt;
      &lt;artifactId&gt;jasperreports&lt;/artifactId&gt;
      &lt;version&gt;7.0.2&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;net.sf.jasperreports&lt;/groupId&gt;
      &lt;artifactId&gt;jasperreports-pdf&lt;/artifactId&gt;
      &lt;version&gt;7.0.2&lt;/version&gt;
  &lt;/dependency&gt;

  &lt;dependency&gt;
      &lt;groupId&gt;org.mindrot&lt;/groupId&gt;
      &lt;artifactId&gt;jbcrypt&lt;/artifactId&gt;
      &lt;version&gt;0.4&lt;/version&gt;
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
