package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import dao.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Endereco;
import model.Imagem;
import model.Usuario;
import model.enums.Perfil;
import model.enums.Sexo;
import service.ImagemService;
import service.ImagemUploadSessionService;
import service.ServiceException;
import service.UsuarioService;
import service.UsuarioSessionService;
import session.NotificationSession;
import session.PaginationSession;
import util.PaginacaoUtil;
import util.PasswordUtil;
import util.StringUtils;

/**
 * No lado do servidor, o servlet responsável por processar o upload deve ser
 * anotado com @MultipartConfig. Essa anotação informa ao contêiner que o
 * servlet está preparado para lidar com requisições do tipo
 * multipart/form-data.
 **/
@MultipartConfig
@WebServlet("/SalvarUsuarioServlet")
public class SalvarUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final transient UsuarioService usuarioService = new UsuarioService();

	private final transient ImagemService imagemService = new ImagemService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UsuarioSessionService usuarioSS = new UsuarioSessionService(request);
		ImagemUploadSessionService imagemUSS = new ImagemUploadSessionService(request);

		// ação 'editProfile' editar perfil usuario
		String action = request.getParameter("action");

		// Extrai os dados digitados no formulário
		String idParam = request.getParameter("id");
		// null não pode ser passado diretamente para Long.valueOf()
		Long id = (StringUtils.isEmpty(idParam)) ? null : Long.valueOf(idParam);
		String nome = request.getParameter("nome").trim();
		String dataNascimento = request.getParameter("dataNascimento").trim();
		String sexo = request.getParameter("sexo").trim();
		String email = request.getParameter("email").trim();
		String perfil = request.getParameter("perfil").trim();
		String rendaMensal = request.getParameter("rendaMensal").trim();
		String login = request.getParameter("login").trim();
		String senha = request.getParameter("senha").trim();

		// Endereco
		String idParamEnd = request.getParameter("enderecoId");
		// null não pode ser passado diretamente para Long.valueOf()
		Long enderecoId = (StringUtils.isEmpty(idParamEnd)) ? null : Long.valueOf(idParamEnd);
		String cep = request.getParameter("cep").trim();
		String rua = request.getParameter("rua").trim();
		String numero = request.getParameter("numero").trim();
		String bairro = request.getParameter("bairro").trim();
		String cidade = request.getParameter("cidade").trim();
		String estado = request.getParameter("estado").trim();
		String uf = request.getParameter("uf").trim();

		// Foto Perfil
		Imagem imagemPerfil = carregarImagemPerfil(request, id, imagemUSS);

		// Faz a validação dos dados digitados
		validarNome(nome, request);
		validarSexo(sexo, request);
		validarEmail(action, email, id, request, usuarioSS);
		validarPerfil(perfil, request);
		validarLogin(action, login, id, request, usuarioSS);
		validarSenha(senha, request);

		// Caso tenha ocorrido algum erro de validação, coloca as informações novamente
		// na request (para permitir que o formulário
		// exiba os campos preenchidos) e volta para a mesma tela.
		if (existemErros(request)) {
			definirValores(idParam, nome, dataNascimento, sexo, email, perfil, rendaMensal, login, senha, request);
			definirValoresEndereco(cep, rua, numero, bairro, cidade, estado, uf, request);

			if (imagemPerfil != null) {
				definirValoresFotoPerfil(imagemPerfil, imagemUSS);
			}

			// Mostra a lista de usuários
			request.setAttribute("usuarios", getUsuarios(usuarioSS, request));

			request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);
			return;
		}

		Endereco endereco = new Endereco();
		endereco.setId(enderecoId);
		endereco.setCep(cep);
		endereco.setRua(rua);
		endereco.setNumero(numero);
		endereco.setBairro(bairro);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setUf(uf);

		/*
		 * Remove "R$" e espaços, substitui ponto por vazio (milhar) e vírgula por ponto
		 * (decimal)
		 */
		rendaMensal = rendaMensal.replace("R$", "").replace(" ", "").replace(".", "").replace(",", ".");

		/*
		 * Verifica se a senha digitada é diferente dos caracteres
		 */
		boolean deveAtualizarSenha = !senha.equals("********");

		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setDataNascimento(LocalDate.parse(dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		usuario.setSexo(Sexo.fromSigla(sexo));
		usuario.setEmail(email);
		usuario.setRendaMensal(Double.parseDouble(rendaMensal));
		usuario.setLogin(login);
		usuario.setSenha(PasswordUtil.hashPassword(senha));
		usuario.setPerfil(Perfil.fromId(Integer.valueOf(perfil)));
		usuario.setEndereco(endereco);

		try {

			String acao = usuarioService.salvarOuAtualizarUsuario(usuario, usuarioSS, deveAtualizarSenha);

			if (imagemPerfil != null) {
				salvarFotoPerfil(imagemPerfil, usuario, imagemUSS);
			}

			NotificationSession.set(request, acao, "ok");

			String redirecionamento;

			if (!StringUtils.isEmpty(action)) {
				redirecionamento = "/PerfilUsuarioServlet";
				atualizarDadosUsuarioLogado(usuario, imagemPerfil, usuarioSS);

			} else {
				redirecionamento = "/CadastrarUsuarioServlet";
			}

			response.sendRedirect(
					request.getContextPath() + redirecionamento + "?userID=" + usuario.getId() + "&action=save");
		} catch (ServiceException | DaoException e) {
			throw new ServletException(e);
		}

	}

	private void atualizarDadosUsuarioLogado(Usuario usuario, Imagem imagemPerfil,
			UsuarioSessionService usuarioSessionService) {

		Usuario usuarioLogado = usuarioSessionService.getUsuarioLogado();
		usuarioLogado.setNome(usuario.getNome());
		usuarioLogado.setDataNascimento(usuario.getDataNascimento());
		usuarioLogado.setSexo(usuario.getSexo());
		usuarioLogado.setEmail(usuario.getEmail());
		usuarioLogado.setLogin(usuario.getLogin());
		usuarioLogado.setPerfil(usuario.getPerfil());
		usuarioLogado.setRendaMensal(usuario.getRendaMensal());
		usuarioLogado.setSenha(usuario.getSenha());
		usuarioLogado.setAdmin(usuario.isAdmin());
		usuarioLogado.setEndereco(usuario.getEndereco());
		usuarioLogado.setTelefones(usuario.getTelefones());

		// Foto Perfil
		usuarioSessionService.createFotoPerfil(imagemPerfil);
	}

	/**
	 * Coloca os valores submetidos pelo formulário novamente na request, para que
	 * possam ser exibidos novamente após o carregamento da página
	 * 
	 * @throws IOException
	 */
	private void definirValores(String id, String nome, String dataNascimento, String sexo, String email, String perfil,
			String rendaMensal, String login, String senha, HttpServletRequest request) {
		request.setAttribute("id", id);
		request.setAttribute("nome", nome);
		request.setAttribute("dataNascimento", dataNascimento);
		request.setAttribute("sexo", sexo);
		request.setAttribute("email", email);
		request.setAttribute("perfil", perfil);
		request.setAttribute("rendaMensal", rendaMensal);
		request.setAttribute("login", login);
		request.setAttribute("senha", senha);
	}

	private void definirValoresFotoPerfil(Imagem imagem, ImagemUploadSessionService imagemUSS) {

		imagemUSS.create(imagem);

		// armazena os dados na sessão para reuso
		imagemUSS.createTemp(imagem);
	}

	private void definirValoresEndereco(String cep, String rua, String numero, String bairro, String cidade,
			String estado, String uf, HttpServletRequest request) {
		request.setAttribute("cep", cep);
		request.setAttribute("rua", rua);
		request.setAttribute("numero", numero);
		request.setAttribute("bairro", bairro);
		request.setAttribute("cidade", cidade);
		request.setAttribute("estado", estado);
		request.setAttribute("uf", uf);
	}

	/**
	 * Valida o nome
	 * 
	 * @param nome
	 */
	private void validarNome(String nome, HttpServletRequest request) {
		if (StringUtils.isEmpty(nome)) {
			adicionarErro("O nome é obrigatório", request);
		}

		if (nome.length() > 255) {
			adicionarErro("O nome digitado é muito grande", request);
		}
	}

	/**
	 * Valida o sexo
	 * 
	 * @param sexo
	 */
	private void validarSexo(String sexo, HttpServletRequest request) {
		if (StringUtils.isEmpty(sexo)) {
			adicionarErro("O sexo é obrigatório", request);
		}
	}

	/**
	 * Valida o e-mail
	 * 
	 * @param email
	 * @throws ServletException
	 */
	private void validarEmail(String acao, String email, Long id, HttpServletRequest request,
			UsuarioSessionService usuarioSS) throws ServletException {

		if (StringUtils.isEmpty(email)) {
			adicionarErro("O e-mail é obrigatório", request);
		}

		if (email.length() > 320) {
			adicionarErro("O e-mail digitado é muito grande", request);
		}

		// Valida com base em uma expressão regular
		if (!email.matches("[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}")) {
			adicionarErro("O e-mail digitado não tem formato válido", request);
		}

		try {

			boolean emailEmUso = usuarioService.isEmailEmUso(id, email, acao, usuarioSS);

			if (emailEmUso) {
				adicionarErro("O e-mail informado já está cadastrado. Por favor, utilize outro.", request);
			}

		} catch (ServiceException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Valida o perfil
	 * 
	 * @param perfil
	 */
	private void validarPerfil(String perfil, HttpServletRequest request) {
		if (perfil == null || perfil.equals("0")) {
			adicionarErro("Selecione um perfil válido.", request);
		}
	}

	/**
	 * Valida o login
	 * 
	 * @param login
	 * @throws ServletException
	 * 
	 */
	private void validarLogin(String acao, String login, Long id, HttpServletRequest request,
			UsuarioSessionService usuarioSS) throws ServletException {
		if (StringUtils.isEmpty(login)) {
			adicionarErro("O login é obrigatório", request);
		}

		if (login.length() > 50) {
			adicionarErro("O login digitado é muito grande", request);
		}

		try {
			boolean loginEmUso = usuarioService.isLoginEmUso(id, login, acao, usuarioSS);

			if (loginEmUso) {
				adicionarErro("O login informado já está em uso. Por favor, escolha outro.", request);
			}

		} catch (ServiceException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Valida a senha
	 * 
	 * @param senha
	 */
	private void validarSenha(String senha, HttpServletRequest request) {
		if (StringUtils.isEmpty(senha)) {
			adicionarErro("O senha é obrigatório", request);
		}

		if (senha.length() > 50) {
			adicionarErro("O senha digitado é muito grande", request);
		}
	}

	/**
	 * Adiciona um erro de validação na request. Isto é feito através da colocação
	 * de uma list de erros na request
	 * 
	 * @param erro
	 */
	@SuppressWarnings("unchecked")
	private void adicionarErro(String erro, HttpServletRequest request) {
		List<String> erros = (List<String>) request.getAttribute("erros");
		if (erros == null) {
			erros = new ArrayList<>();
			request.setAttribute("erros", erros);
		}

		erros.add(erro);
	}

	/**
	 * Verifica se existem erros de validação
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean existemErros(HttpServletRequest request) {
		List<String> erros = (List<String>) request.getAttribute("erros");
		if (erros == null || erros.isEmpty()) {
			return false;
		}
		return true;
	}

	private Imagem carregarImagemPerfil(HttpServletRequest request, Long usuairoId,
			ImagemUploadSessionService imagemUSS) throws IOException, ServletException {

		// Imagem Perfil
		Part filePart = request.getPart("filePerfilFoto"); // Pega a foto da tela do InputFile
		Imagem tempImagem = imagemUSS.getTemp();

		// Local do tipo da imagem
		String tipo = request.getParameter("tipoImagem");

		// Faz a verificação se foi adicionado alguma imagem
		if (filePart.getSize() >= 1 || tempImagem != null) {
			byte[] imagemBytes = tempImagem == null ? filePart.getInputStream().readAllBytes() : tempImagem.getImage();
			String contentType = tempImagem == null ? filePart.getContentType() : tempImagem.getContentType();
			String extensao = tempImagem == null ? contentType.split("/")[1] : tempImagem.getExtensao();
			String imagemBase64 = tempImagem == null ? convertImageByteToBase64(imagemBytes, contentType)
					: tempImagem.getImageBase64();

			return construirImagem(tipo, imagemBytes, extensao, imagemBase64, null);
		} else {
			// carrega o preview da imagem no formulário caso seja um usuário já cadastrado
			if (usuairoId != null) {
				try {
					Imagem imagem = imagemService.buscarPorUsuarioIdETipo(usuairoId, tipo);
					imagemUSS.create(imagem);
				} catch (ServiceException e) {
					throw new ServletException(e);
				}
			}

		}

		return null;

	}

	private void salvarFotoPerfil(Imagem imagem, Usuario usuario, ImagemUploadSessionService imagemUSS)
			throws DaoException {
		try {

			// adiciona o usuário na imagem
			imagem.setUsuario(usuario);

			boolean existeFotoPerfil = imagemService.existeFotoPerfil(usuario.getId());

			imagemService.salvarOuAtualizarImagem(existeFotoPerfil, imagem);

			// cria a sessão da imagem
			imagemUSS.create(imagem);
			imagemUSS.removerTemp();

		} catch (ServiceException e) {
			throw new DaoException(e);
		}
	}

	private Imagem construirImagem(String tipo, byte[] imagemBytes, String extensao, String imagemBase64,
			Usuario usuario) {
		Imagem imagem = new Imagem();
		imagem.setUsuario(usuario);
		imagem.setImage(imagemBytes);
		imagem.setExtensao(extensao);
		imagem.setTipo(tipo);
		imagem.setDataUpload(LocalDateTime.now());
		imagem.setImageBase64(imagemBase64);
		return imagem;
	}

	private String convertImageByteToBase64(byte[] image, String contentType) {
		/* Formato para visulizar a imagem no jsp */
		return String.format("data:%s;base64,%s", contentType, Base64.getEncoder().encodeToString(image));
	}

	private List<Usuario> getUsuarios(UsuarioSessionService usuarioSS, HttpServletRequest request)
			throws ServletException {
		try {
			return usuarioService.listarPorUsuarioLogado(usuarioSS.getUsuarioLogado().getId(), offset(request));
		} catch (ServiceException e) {
			throw new ServletException(e);
		}
	}

	private int offset(HttpServletRequest request) {
		return PaginacaoUtil.calcularOffset(paginaAtual(request), usuarioService.getLimitePagina());
	}

	private int paginaAtual(HttpServletRequest request) {
		return Integer.parseInt(PaginationSession.get(request, "paginacao"));
	}
}
