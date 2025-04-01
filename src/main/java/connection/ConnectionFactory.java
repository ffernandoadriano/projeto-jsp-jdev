package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Obtém conexões para o banco de dados
 */
public final class ConnectionFactory {

	private static final String URL = "jdbc:postgresql://localhost/curso-jsp";
	private static final String USER = "nando";
	private static final String PASSWORD = "admin";

	/**
	 * Conexão
	 */
	private static Connection connection;

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

	// Bloco estático é executado antes do construtor
	static {
		connect();
	}

	/**
	 * construtor privado para não permitir instanciação da classe
	 */
	private ConnectionFactory() {
	}

	/**
	 * Padrão Singleton
	 */
	private static void connect() {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver"); // foi necessário usar para funcionar
				// Abre uma nova conexão com o banco de dados
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				// transação automática desativada
				connection.setAutoCommit(false);
			} catch (SQLException | ClassNotFoundException e) {
				LOGGER.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			}
		}
	}

	/**
	 * @return Nova conexão
	 */
	public static Connection getConnection() {
		return connection;
	}
}
