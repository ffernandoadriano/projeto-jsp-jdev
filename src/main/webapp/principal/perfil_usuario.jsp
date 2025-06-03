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


														<%
														/* O atributo enctype especifica como os dados do formulário devem ser codificados ao serem enviados para o servidor.
														    Quando se trata de upload de arquivos, é necessário definir enctype="multipart/form-data" no elemento <form>

														Isso é crucial porque:
															# Permite que o navegador envie arquivos binários (como imagens ou documentos) juntamente com os dados do formulário.
															# Garante que os dados sejam divididos em múltiplas partes, facilitando a separação entre arquivos e outros campos do formulário
														*/
														%>

														<form
															action="<%=request.getContextPath()%>/SalvarUsuarioServlet"
															method="post" id="perfilUsuarioForm"
															enctype="multipart/form-data">

															<!-- Diz o local onde a imagem será inserida. -->
															<input type="hidden" value="perfil" name="tipoImagem" />

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">ID:</label>
																<div class="col-sm-1">
																	<input type="text" name="id" id="id"
																		class="form-control" readonly="readonly" value="${id}">
																</div>
															</div>

															<!-- Atributo "SRC" será preenchido com a função visualizarImagemTela -->
															<div class="form-group row">
																<div class="col-sm-1">
																	<c:choose>

																		<c:when
																			test="${imagemBase64 != null || PerfilFoto != null}">

																			<a id="linkDownloadFotoPerfil"
																				href="<c:url value='/DownloadImagemServlet?id=${id}&tipoImagem=perfil' />">

																				<img alt="Foto Perfil"
																				src="${empty PerfilFoto ? imagemBase64.imageBase64 : PerfilFoto.imageBase64}"
																				id="fotoBase64" width="60px">

																			</a>

																		</c:when>


																		<c:otherwise>
																			<img alt="Foto Perfil"
																				src="<%=request.getContextPath()%>/assets/images/perfil.png"
																				id="fotoBase64" width="60px">
																		</c:otherwise>

																	</c:choose>



																</div>
																<div class="col-sm-8">
																	<input type="file" class="form-control"
																		accept="image/*" id="fileFoto" name="filePerfilFoto"
																		onchange="visualizarImagemTela('fotoBase64', 'fileFoto' )";>
																	<%
																	/*restringe para aceitar apenas imagem*/
																	%>
																</div>
															</div>


															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Nome:</label>
																<div class="col-sm-8">
																	<input type="text" name="nome" id="nome"
																		class="form-control" required="required"
																		value="${nome}" />
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Data
																	Nascimento:</label>
																<div class="col-sm-2">
																	<input type="text" name="dataNascimento"
																		placeholder="dd/mm/aaaa" maxlength="10"
																		id="dataNascimento" class="form-control"
																		style="width: 140px;" required="required"
																		value="${dataNascimento}" />
																</div>
															</div>


															<!-- Radio sexo inicio -->

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Sexo:</label>


																<div class="col-sm-1 col-form-label" id="sexoM">
																	<div class="col-sm-8" id="radioSexo">
																		<input class="form-check-input" type="radio"
																			name="sexo" value="M" required="required"
																			${sexo eq 'M' ? 'checked' : ''}> <label
																			class="form-check-label" for="inlineRadio1">Masculino</label>
																	</div>

																</div>

																<div class="col-sm-1 col-form-label" id="sexoF"
																	style="margin-left: 18px">
																	<input class="form-check-input" type="radio"
																		name="sexo" value="F" required="required"
																		${sexo eq 'F' ? 'checked' : ''}> <label
																		class="form-check-label" for="inlineRadio2">Feminino</label>
																</div>
															</div>

															<!-- sexo fim -->

															<!-- Endereço inicio -->

															<input type="hidden" name="enderecoId"
																value="${enderecoId}">

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Cep:</label>
																<div class="col-sm-2">
																	<input type="text" name="cep" id="cep"
																		onblur="pesquisarCep();" class="form-control"
																		required="required" value="${cep}" maxlength="9"
																		placeholder="00000-000" />
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Rua:</label>
																<div class="col-sm-6">
																	<input type="text" name="rua" id="rua"
																		class="form-control" required="required"
																		value="${rua}" />
																</div>

																<label class="col-sm-1 col-form-label">Numero:</label>
																<div class="col-sm-1">
																	<input type="text" name="numero" id="numero"
																		class="form-control" required="required"
																		value="${numero}" />
																</div>

															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Bairro:</label>
																<div class="col-sm-8">
																	<input type="text" name="bairro" id="bairro"
																		class="form-control" required="required"
																		value="${bairro}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Cidade:</label>
																<div class="col-sm-3">
																	<input type="text" name="cidade" id="cidade"
																		class="form-control" required="required"
																		value="${cidade}" />

																</div>

																<label class="col-sm-1 col-form-label">Estado:</label>
																<div class="col-sm-2">
																	<input type="text" name="estado" id="estado"
																		class="form-control" required="required"
																		value="${estado}" />
																</div>

																<label class="col-sm-1 col-form-label">UF:</label>
																<div class="col-sm-1">
																	<input type="text" name="uf" id="uf"
																		class="form-control" required="required" value="${uf}" />
																</div>
															</div>

															<!-- Endereço Fim -->

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">E-mail:</label>
																<div class="col-sm-8">
																	<input type="email" name="email" id="email"
																		class="form-control" required="required"
																		value="${email}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Perfil:</label>
																<div class="col-sm-2">
																	<select name="perfil" class="form-control"
																		style="width: 153px;" id="perfil" required="required">
																		<option value="0">Selecione o Perfil</option>
																		<option value="1" ${perfil == 1 ? 'selected' : ''}>Admin</option>
																		<option value="2" ${perfil == 2 ?  'selected' : ''}>Secretária</option>
																		<option value="3" ${perfil == 3 ? 'selected' : ''}>Auxiliar</option>
																	</select>
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Renda
																	Mensal:</label>
																<div class="col-sm-2">
																	<input type="text" name="rendaMensal" id="rendaMensal"
																		placeholder="R$ 0,00" maxlength="20"
																		class="form-control" required="required"
																		value="${rendaMensal}" />
																</div>
															</div>

															<div class="form-group row">
																<label class="col-sm-1 col-form-label">login:</label>
																<div class="col-sm-8">
																	<input type="text" name="login" id="login"
																		class="form-control" required="required"
																		value="${login}" />
																</div>
															</div>
															<div class="form-group row">
																<label class="col-sm-1 col-form-label">Senha:</label>
																<div class="col-sm-8">
																	<input type="password" name="senha" id="senha"
																		class="form-control" required="required"
																		value="${senha}" />

																</div>
															</div>

															<%
															/*removendo a imagemBase64 da sessão com JSTL*/
															%>
															<c:if test="${not empty sessionScope.imagemBase64}">
																<c:remove var="imagemBase64" scope="session" />
															</c:if>


															<div class="form-group row">
																<label class="col-sm-1 col-form-label"></label>
																<%
																/*
																Ao não definir nenhum 'type' para o botão. exemplo: <button>Salvar</button>
																O navegador assume automaticamente que é type="submit", ou seja: ele vai enviar o formulário.
																*/
																%>
																<button
																	class="btn btn-primary btn-round waves-effect hor-grd btn-grd-primary"
																	id="btn-form-cadastro">Salvar</button>
																<c:if test="${not empty id}">
																	<a
																		href="${pageContext.request.contextPath}/TelefoneServlet?id=${id}"
																		class="btn btn-info btn-round  waves-effect hor-grd btn-grd-info"
																		id="btn-form-cadastro-telefone">Telefone</a>
																</c:if>

															</div>


														</form>
													</div>
												</div>

												<!-- Basic Form Inputs card end -->
											</div>

										</div>
									</div>
									<div id="styleSelector"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascript.jsp"></jsp:include>
	
	<jsp:include page="jsWhatsAppIcone.jsp"></jsp:include>


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

	<!-- Mascara para Cep -->
	<script>
	  document.getElementById('cep').addEventListener('input', function (e) {
	    let cep = e.target.value;
	
	    // Remove tudo que não for número
	    cep = cep.replace(/\D/g, '');
	
	    // Adiciona o hífen depois do quinto dígito
	    if (cep.length > 5) {
	      cep = cep.substring(0, 5) + '-' + cep.substring(5, 8);
	    }
	
	    e.target.value = cep;
	  });
	</script>

	<!-- DatePicker em Português (Jquery) -->
	<script>
	  $(function() {
	    $("#dataNascimento").datepicker({
	      dateFormat: 'dd/mm/yy',
	      dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
	      dayNamesMin: ['D','S','T','Q','Q','S','S'],
	      dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
	      monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	      monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
	      nextText: 'Próximo',
	      prevText: 'Anterior',
	      changeMonth: true,
	      changeYear: true
	    });
	  });
	</script>

	<!-- Mascara para Data de Nascimento -->
	<script>
	  const campoData = document.getElementById('dataNascimento');
	
	  campoData.addEventListener('input', function(e) {
	    let valor = e.target.value.replace(/\D/g, ''); // Remove tudo que não for número
	
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
	</script>

	<!-- Máscara Monetária (Cleave.js) -->
	<!-- Cleave.js é uma biblioteca que formata campos de texto em tempo real (números, datas, moeda etc.). -->
	<script
		src="https://cdn.jsdelivr.net/npm/cleave.js@1.6.0/dist/cleave.min.js"></script>

	<script>
		// Inicia com 0
	    let valorBruto = "0"; // Armazena somente os dígitos que o usuário digitou
	
	    const input = document.getElementById('rendaMensal');
	    const cleave = new Cleave(input, {
	      numeral: true, //  ativa formatação numérica
	      numeralThousandsGroupStyle: 'thousand', // garante que os milhares sejam agrupados
	      delimiter: '.', //  separador de milhar brasileiro
	      numeralDecimalMark: ',', // vírgula como separador decimal (pt-BR)
	      numeralDecimalScale: 2, // Casas decimais
	      prefix: 'R$ ', // adiciona "R$ " antes do número
	      rawValueTrimPrefix: true // permite pegar o valor sem o R$ depois, se quiser
	    });

		 // Define o valor inicial como R$ 0,00
		<c:if test="${empty rendaMensal}">
	    	cleave.setRawValue('0,00');
	    </c:if>
	
	    input.addEventListener('input', function (e) {
	      const digitado = e.data; // é o último caractere digitado

	  	 // Se for um backspace → apaga o último caractere de valorBruto
	      if (e.inputType === 'deleteContentBackward') {
	        valorBruto = valorBruto.slice(0, -1);

	        // Se for um número → adiciona ao valorBruto
	      } else if (/\d/.test(digitado)) {
	        valorBruto += digitado;
	      }

	  	  // divide valorBruto por 100 para transformar em reais
	  	  // Ex: "123" → 1.23 → R$ 1,23
	      const valorFormatado = (parseInt(valorBruto || "0", 10) / 100).toFixed(2);
	      // atualiza o valor exibido com a formatação correta
	      cleave.setRawValue(valorFormatado.replace('.', ','));
	    });

		 // Garante que o usuário só possa digitar números e comandos de navegação (como setas e backspace).
	    input.addEventListener('keydown', function (e) {
	      const permitidos = ['Backspace', 'Tab', 'ArrowLeft', 'ArrowRight'];
	      if (!/\d/.test(e.key) && !permitidos.includes(e.key)) {
	        e.preventDefault();
	      }
	    });
	  </script>
</body>

</html>