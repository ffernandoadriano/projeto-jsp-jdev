package filter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import dao.DaoException;
import dao.VersionadorDbDao;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Usuario;
import service.UsuarioSessionService;

@WebFilter("/*") /* intercepta todas as requisições do mapeamento */
public class AutenticacaoFilter implements Filter {

	/* Inicia os processos ou recursos quando o servidor sobe o projeto */
	// ex: inicia a conexão com o banco
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		VersionadorDbDao versionadorDbDao = new VersionadorDbDao();

		List<Path> arquivosSql = leitorArquivoSql();

		for (Path path : arquivosSql) {

			try {
				String nomeArquivo = path.getFileName().toString();

				if (!versionadorDbDao.existeArquivo(nomeArquivo)) {

					// Ler o conteúdo do arquivo
					String conteudoSql = Files.readString(path);

					// executa o sql do arquivo no banco
					versionadorDbDao.executeUpdate(conteudoSql);

					// salva o nome do arquivo lido no versionamento
					versionadorDbDao.insert(nomeArquivo);
				}
			} catch (DaoException | IOException e) {
				throw new ServletException(e);
			}
		}
	}

	/* Encerra os processos quando o servidor é parado */
	// mataria os processos de conexão com o banco
	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	/* Intercepta as requisições/respostas do sistema */
	// ex: validação de autenticação , validar e faze redirecionamento de páginas
	// etc...
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		

		HttpServletRequest req = (HttpServletRequest) request;
		String urlParaAutenticar = req.getServletPath(); // url que está sendo acessado

		UsuarioSessionService usuarioSS = new UsuarioSessionService(req);
		
		// session retorna um objeto
		Usuario usuario = usuarioSS.getUsuarioLogado();

		// Define quais URLs são públicas
		boolean urlPublica = urlParaAutenticar.startsWith("/index") || urlParaAutenticar.startsWith("/Login")
				|| urlParaAutenticar.endsWith(".css") || urlParaAutenticar.endsWith(".js")
				|| urlParaAutenticar.endsWith(".png") || urlParaAutenticar.endsWith(".jpg");
		/*
		 * .css, .js, .png, .jpg: servem para permitir o carregamento de arquivos
		 * estáticos (como estilos, scripts e imagens) mesmo que o usuário não esteja
		 * logado.
		 */
		;

		/* validar se está logado, senão, redireciona para tela de login */
		if (usuario == null && !urlPublica) {
			req.setAttribute("messageErro", "Por favor, realize o login!");
			req.getRequestDispatcher(String.format("/LoginFormServlet?url=%s", urlParaAutenticar)).forward(request,
					response);
		} else {

			// redireciona para Servlet desejada
			chain.doFilter(request, response); // continua a requisição
		}

	}

	private List<Path> leitorArquivoSql() throws ServletException {

		URL url = AutenticacaoFilter.class.getResource("/db/versionamento");

		if (url == null) {
			throw new ServletException("Diretório 'db/versionamento' não encontrado no classpath.");
		}

		try {

			Path caminhoVersionamento = Paths.get(url.toURI()); // Evita problemas com url.getFile() (ex: espaços, %20,
																// etc).

			try (Stream<Path> arquivos = Files.list(caminhoVersionamento)) {
				return arquivos.filter(Files::isRegularFile) // apenas arquivos, ignora pastas
						.filter(path -> path.toString().endsWith(".sql")) // só arquivos .sql
						.sorted() // ordena por nome
						.toList();
			}

		} catch (URISyntaxException | IOException e) {
			throw new ServletException("Erro ao tentar ler Arquivo: ", e);
		}
	}

}
