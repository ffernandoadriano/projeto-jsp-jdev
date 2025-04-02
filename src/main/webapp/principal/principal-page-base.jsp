<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="head.jsp"/>

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
                                          
                                           <h1>Conteúdo da página do sistema</h1>
                                            
                                        </div>
                                    </div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
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
