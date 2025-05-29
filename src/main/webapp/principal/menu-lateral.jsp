<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="pcoded-navbar">
	<div class="sidebar_toggle">
		<a href="#"><i class="icon-close icons"></i></a>
	</div>
	<div class="pcoded-inner-navbar main-menu">
		<div class="">
			<div class="main-menu-header">
				<c:if test="${not empty imagemPerfil}">
					<img class="img-80 img-radius"
						style="min-height: 61px; max-height: 100px;" src="${imagemPerfil}"
						alt="User-Profile">
				</c:if>
				<c:if test="${empty imagemPerfil}">
					<img class="img-80 img-radius" style="min-height: 61px"
						src="<%=request.getContextPath()%>/assets/images/avatar-blank2.jpg"
						alt="User-Profile">
				</c:if>
				<div class="user-details">
					<span id="more-details">${usuarioLogado.login}<i
						class="fa fa-caret-down"></i></span>
				</div>
			</div>

			<div class="main-menu-content">
				<ul>
					<li class="more-details"><a href="user-profile.html"><i
							class="ti-user"></i>Ver perfil</a> <!--  <a href="#!"><i class="ti-settings"></i>Settings</a>  -->
						<a href="<%=request.getContextPath()%>/LogoutServlet?acao=Logout"><i
							class="ti-layout-sidebar-left"></i>Sair</a></li>
				</ul>
			</div>
		</div>

		<!-- 
			      <div class="p-15 p-b-0">
			          <form class="form-material">
			              <div class="form-group form-primary">
			                  <input type="text" name="footer-email" class="form-control" required="required">
			                  <span class="form-bar"></span>
			                  <label class="float-label"><i class="fa fa-search m-r-10"></i>Search Friend</label>
			              </div>
			          </form>
			      </div>
			      
	       -->

		<div class="pcoded-navigation-label"
			data-i18n="nav.category.navigation">Menu</div>
		<ul class="pcoded-item pcoded-left-item">
			<li class="" id="home"><a
				href="<%=request.getContextPath()%>/HomeServlet"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-home"></i><b>D</b></span> <span class="pcoded-mtext"
					data-i18n="nav.dash.main">Página inicial</span> <span
					class="pcoded-mcaret"></span>
			</a></li>
			<li class="pcoded-hasmenu" id="cadastros"><a
				href="javascript:void(0)" class="waves-effect waves-dark"> <span
					class="pcoded-micon"><i class="ti-layout-grid2-alt"></i></span> <span
					class="pcoded-mtext" data-i18n="nav.basic-components.main">Cadastros</span>
					<span class="pcoded-mcaret"></span>
			</a>
				<ul class="pcoded-submenu">

					<!-- Inicio cadastro de usuário -->

					<!-- Verifica se o usuário logado é admin -->
					<c:if test="${usuarioLogado.perfil.id == 1}">
						<li class=""><a
							href="<%=request.getContextPath()%>/CadastrarUsuarioServlet"
							class="waves-effect waves-dark"> <span class="pcoded-micon"><i
									class="ti-angle-right"></i></span> <span class="pcoded-mtext"
								data-i18n="nav.basic-components.alert">Cadastrar Usuário</span>
								<span class="pcoded-mcaret"></span>
						</a></li>
					</c:if>
					<!-- Final cadastro de usuário -->
					<!--  
	                  <li class=" ">
	                      <a href="breadcrumb.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Breadcrumbs</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="button.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Button</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="tabs.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Tabs</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="color.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Color</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="label-badge.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Label Badge</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="tooltip.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Tooltip</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="typography.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Typography</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="notification.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Notification</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="icon-themify.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Themify</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	 				-->

				</ul></li>
		</ul>
		<div class="pcoded-navigation-label" data-i18n="nav.category.forms">Relatórios</div>
		<ul class="pcoded-item pcoded-left-item">
			<li class="" id="usuarios"><a
				href="<%=request.getContextPath()%>/RelatorioUsuarioServlet"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-pencil-alt"></i><b>FC</b></span> <span class="pcoded-mtext"
					data-i18n="nav.form-components.main">Usuários</span> <span
					class="pcoded-mcaret"></span>
			</a></li>
			<!-- 
		          <li>
		              <a href="bs-basic-table.html" class="waves-effect waves-dark">
		                  <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
		                  <span class="pcoded-mtext" data-i18n="nav.form-components.main">Basic Table</span>
		                  <span class="pcoded-mcaret"></span>
		              </a>
		          </li>
	           -->

		</ul>

		<div class="pcoded-navigation-label" data-i18n="nav.category.forms">Gráficos</div>
		<ul class="pcoded-item pcoded-left-item">
			<li class="" id="salarios"><a
				href="<%=request.getContextPath()%>/GraficoSalariosServlet"
				class="waves-effect waves-dark"> <span class="pcoded-micon"><i
						class="ti-bar-chart"></i><b>FC</b></span> <span class="pcoded-mtext"
					data-i18n="nav.form-components.main">Salários</span> <span
					class="pcoded-mcaret"></span>
			</a></li>
		</ul>



		<!-- 
	      <div class="pcoded-navigation-label" data-i18n="nav.category.forms">Chart &amp; Maps</div>
	      <ul class="pcoded-item pcoded-left-item">
	          <li>
	              <a href="chart.html" class="waves-effect waves-dark">
	                  <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
	                  <span class="pcoded-mtext" data-i18n="nav.form-components.main">Chart</span>
	                  <span class="pcoded-mcaret"></span>
	              </a>
	          </li>
	          <li>
	              <a href="map-google.html" class="waves-effect waves-dark">
	                  <span class="pcoded-micon"><i class="ti-layers"></i><b>FC</b></span>
	                  <span class="pcoded-mtext" data-i18n="nav.form-components.main">Maps</span>
	                  <span class="pcoded-mcaret"></span>
	              </a>
	          </li>
	          <li class="pcoded-hasmenu">
	              <a href="javascript:void(0)" class="waves-effect waves-dark">
	                  <span class="pcoded-micon"><i class="ti-layout-grid2-alt"></i></span>
	                  <span class="pcoded-mtext"  data-i18n="nav.basic-components.main">Pages</span>
	                  <span class="pcoded-mcaret"></span>
	              </a>
	              <ul class="pcoded-submenu">
	                  <li class=" ">
	                      <a href="auth-normal-sign-in.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.alert">Login</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="auth-sign-up.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Register</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	                  <li class=" ">
	                      <a href="sample-page.html" class="waves-effect waves-dark">
	                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
	                          <span class="pcoded-mtext" data-i18n="nav.basic-components.breadcrumbs">Sample Page</span>
	                          <span class="pcoded-mcaret"></span>
	                      </a>
	                  </li>
	              </ul>
	          </li>
	
	      </ul>
	  
	  -->
		<!-- 
		      <div class="pcoded-navigation-label" data-i18n="nav.category.other">Other</div>
		      <ul class="pcoded-item pcoded-left-item">
		          <li class="pcoded-hasmenu ">
		              <a href="javascript:void(0)" class="waves-effect waves-dark">
		                  <span class="pcoded-micon"><i class="ti-direction-alt"></i><b>M</b></span>
		                  <span class="pcoded-mtext" data-i18n="nav.menu-levels.main">Menu Levels</span>
		                  <span class="pcoded-mcaret"></span>
		              </a>
		              <ul class="pcoded-submenu">
		                  <li class="">
		                      <a href="javascript:void(0)" class="waves-effect waves-dark">
		                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
		                          <span class="pcoded-mtext" data-i18n="nav.menu-levels.menu-level-21">Menu Level 2.1</span>
		                          <span class="pcoded-mcaret"></span>
		                      </a>
		                  </li>
		                  <li class="pcoded-hasmenu ">
		                      <a href="javascript:void(0)" class="waves-effect waves-dark">
		                          <span class="pcoded-micon"><i class="ti-direction-alt"></i></span>
		                          <span class="pcoded-mtext" data-i18n="nav.menu-levels.menu-level-22.main">Menu Level 2.2</span>
		                          <span class="pcoded-mcaret"></span>
		                      </a>
		                      <ul class="pcoded-submenu">
		                          <li class="">
		                              <a href="javascript:void(0)" class="waves-effect waves-dark">
		                                  <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
		                                  <span class="pcoded-mtext" data-i18n="nav.menu-levels.menu-level-22.menu-level-31">Menu Level 3.1</span>
		                                  <span class="pcoded-mcaret"></span>
		                              </a>
		                          </li>
		                      </ul>
		                  </li>
		                  <li class="">
		                      <a href="javascript:void(0)" class="waves-effect waves-dark">
		                          <span class="pcoded-micon"><i class="ti-angle-right"></i></span>
		                          <span class="pcoded-mtext" data-i18n="nav.menu-levels.menu-level-23">Menu Level 2.3</span>
		                          <span class="pcoded-mcaret"></span>
		                      </a>
		                  </li>
		
		              </ul>
		          </li>
		      </ul>
	       -->
	</div>
</nav>