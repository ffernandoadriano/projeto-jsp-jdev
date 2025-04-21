package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Imagem;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dao.DaoException;
import dao.ImagemDao;

@WebServlet("/DownloadImagemServlet")
public class DownloadImagemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ImagemDao imagemDao = new ImagemDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doIt(request, response);

	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idParam = request.getParameter("id");
		String tipo = request.getParameter("tipoImagem");

		if (idParam != null) {

			// null não pode ser passado diretamente para Long.valueOf()
			Long id = (idParam.isEmpty()) ? null : Long.valueOf(idParam.trim());

			try {
				Imagem imagem = imagemDao.encontrarPorId(id, tipo);

				if (imagem != null) {

					String nomeArquivo = "foto_" + tipo + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "."
							+ imagem.getExtensao();

					// comando para o navegador identificar o download
					response.setHeader("Content-Disposition", "attachment;filename=" + nomeArquivo);
					response.getOutputStream().write(imagem.getImage());
					return;
				}
			} catch (DaoException e) {
				throw new ServletException("Erro ao buscar imagem", e);
			}
		}

		request.getRequestDispatcher("/principal/cadastrar_usuario.jsp").forward(request, response);
	}

}
