package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import model.Usuario;
import session.UsuarioLogadoSession;

@WebFilter("/*") /* intercepta todas as requisições do mapeamento */
public class AutenticacaoFilter implements Filter {

	/* Inicia os processos ou recursos quando o servidor sobe o projeto */
	// ex: inicia a conexão com o banco
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
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

		// session retorna um objeto
		Usuario usuario = UsuarioLogadoSession.getUsuarioLogado(req);

		// Define quais URLs são públicas
		boolean urlPublica = urlParaAutenticar.startsWith("/Login") ||
								 urlParaAutenticar.endsWith(".css") ||
				                  urlParaAutenticar.endsWith(".js") ||
				                 urlParaAutenticar.endsWith(".png") ||
				                 urlParaAutenticar.endsWith(".jpg");
		/*.css, .js, .png, .jpg: servem para permitir o carregamento de arquivos estáticos (como estilos, scripts e imagens) mesmo que o usuário não esteja logado.*/
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

}
