package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionFactory;

public class VersionadorDbDao {

	private Connection connection;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public VersionadorDbDao() {
		this.connection = ConnectionFactory.getConnection();
	}

	public void insert(String nomeArquivo) throws DaoException {
		String sql = "INSERT INTO versionadordb (arquivo_sql) VALUES (?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nomeArquivo);
			
			// Executa a query
			pstmt.executeUpdate();
			
			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao tentar inserir dados: ", e);
		}
	}
	
	public void executeUpdate(String sql) throws DaoException {
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			
			// Executa a query
			pstmt.executeUpdate();
			
			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao tentar inserir sql: ", e);
		}
	}

	public boolean existeArquivo(String nomeArquivo) throws DaoException {
		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM versionadordb WHERE arquivo_sql = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nomeArquivo);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next();
			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao verificar arquivo", e);
		}
	}

}
