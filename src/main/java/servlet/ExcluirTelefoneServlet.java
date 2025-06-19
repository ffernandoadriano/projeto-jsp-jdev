package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ServiceException;
import service.TelefoneService;

@WebServlet("/ExcluirTelefoneServlet")
public class ExcluirTelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		String idTel = request.getParameter("idTel");
		String idUser = request.getParameter("idUser");

		try {
			TelefoneService telefoneService = new TelefoneService();
			telefoneService.deletarPorId(Long.parseLong(idTel));

			response.sendRedirect(request.getContextPath() + "/TelefoneServlet?id=" + idUser);
		} catch (ServiceException e) {
			throw new ServletException(e);
		}
	}
}
