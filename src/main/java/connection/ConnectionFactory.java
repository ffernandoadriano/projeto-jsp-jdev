package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
	private static final String URL = "jdbc:postgresql://localhost/curso-jsp?autoReconnect=true";
	private static final String USER = "nando";
	private static final String PASSWORD = "admin";

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

	private ConnectionFactory() {
	}

	public static Connection openConnection() {

		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Erro ao conectar ao banco de dados", e);
			return null;
		}
	}

}
