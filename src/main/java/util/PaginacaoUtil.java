package util;

public class PaginacaoUtil {

	private PaginacaoUtil() {
		// Construtor privado para não permitir instanciar essa classe
	}

	public static int calcularTotalPaginas(int totalRegistros, int limite) {
		if (limite <= 0) {
			throw new IllegalArgumentException("O limite deve ser maior que zero.");
		}
		return (int) Math.ceil((double) totalRegistros / limite); //Math.ceil() sempre arredonda para cima.
	}

	public static int calcularOffset(int paginaAtual, int limite) {
		if (paginaAtual <= 0) {
			throw new IllegalArgumentException("A página atual deve ser maior que zero.");
		}
		if (limite <= 0) {
			throw new IllegalArgumentException("O limite deve ser maior que zero.");
		}
		return (paginaAtual - 1) * limite;
	}
}
