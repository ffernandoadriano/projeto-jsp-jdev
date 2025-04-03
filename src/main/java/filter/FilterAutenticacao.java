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
import jakarta.servlet.http.HttpSession;
import model.Usuario;

@WebFilter("/principal/*") /* intercepta todas as requisições do mapeamento */
public class FilterAutenticacao implements Filter {

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

		HttpSession session = req.getSession();

		String urlParaAutenticar = req.getServletPath(); // url que está sendo acessado

		// session retorna um objeto
		Usuario usuario = (Usuario) session.getAttribute("usuarioSession");

		/* validar se está logado, senão, redireciona para tela de login */
		if (usuario == null && !urlParaAutenticar.contains("LoginFormServlet") && !urlParaAutenticar.contains("LoginServlet")) {
			req.setAttribute("messageErro", "Por favor, realize o login!");
			req.getRequestDispatcher(String.format("/LoginFormServlet?url=%s", urlParaAutenticar)).forward(request, response);
		} else {

			// redireciona para Servlet desejada
			chain.doFilter(request, response);
		}

	}

}
