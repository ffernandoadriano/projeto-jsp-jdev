package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import connection.ConnectionFactory;
import dto.MediaSalarialDTO;
import model.Endereco;
import model.Telefone;
import model.Usuario;
import model.enums.Perfil;
import model.enums.Sexo;
import util.PaginacaoUtil;

public class UsuarioDao {

	private static final int LIMITE_DE_PAGINA = 10;

	private Connection connection;

	private EnderecoDao enderecoDao;

	private TelefoneDao telefoneDao;

	/**
	 * Construtor que inicializa a conexão com o banco de dados.
	 */
	public UsuarioDao() {
		this.connection = ConnectionFactory.getConnection();
		// a conexão precisa ser a mesma para o rollback funcionar
		this.enderecoDao = new EnderecoDao(connection);
		this.telefoneDao = new TelefoneDao();
	}

	/**
	 * Valida as credenciais de autenticação do usuário.
	 *
	 * @param usuario o usuário com login e senha informados
	 * @return true se o usuário existir com essas credenciais, false caso contrário
	 * @throws DaoException em caso de erro ao acessar o banco de dados
	 */
	public boolean validarAutenticacao(Usuario usuario) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(login) = UPPER(?) AND senha = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, usuario.getSenha());

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // Será autenticado se for encontrado; caso contrário, não será autenticado.
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * Insere um novo usuário com endereço associado.
	 *
	 * @param usuario         o usuário a ser inserido
	 * @param idUsuarioLogado o ID do usuário logado que está realizando a inserção
	 * @throws DaoException em caso de falha durante a inserção
	 */
	public void inserir(Usuario usuario, Long idUsuarioLogado) throws DaoException {

		String insertSql = "INSERT INTO usuario (nome, email, login, senha, usuario_id, perfil_id, sexo, endereco_id, data_nascimento, renda_mensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			enderecoDao.inserir(usuario.getEndereco());

			try (PreparedStatement pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, usuario.getNome());
				pstmt.setString(2, usuario.getEmail());
				pstmt.setString(3, usuario.getLogin());
				pstmt.setString(4, usuario.getSenha());
				pstmt.setLong(5, idUsuarioLogado);
				pstmt.setInt(6, usuario.getPerfil().getId());
				pstmt.setString(7, usuario.getSexo().getSigla());
				pstmt.setLong(8, usuario.getEndereco().getId());
				pstmt.setObject(9, usuario.getDataNascimento());
				pstmt.setDouble(10, usuario.getRendaMensal());

				// Executa a query
				int linhas = pstmt.executeUpdate();

				if (linhas > 0) {
					// Recupera as chaves geradas (o ID gerado)
					try (ResultSet rs = pstmt.getGeneratedKeys()) {
						if (rs.next()) {
							// Insere o ID na referência do objeto.
							usuario.setId(rs.getLong(1));
						}
					}
				}
			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao salvar usuário com endereço: " + e.getMessage(), e);
		}
	}

	/**
	 * Busca um usuário por ID, incluindo seu endereço, desde que pertença ao
	 * usuário logado.
	 *
	 * @param id              ID do usuário a ser buscado
	 * @param idUsuarioLogado ID do usuário logado
	 * @return o usuário encontrado ou null se não existir ou não pertencer
	 * @throws DaoException em caso de erro de acesso ao banco
	 */
	public Usuario buscarPorIdComEndereco(Long id, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login, senha, perfil_id, sexo, endereco_id FROM usuario WHERE id = ? AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					Long enderecoId = rs.getLong("endereco_id");

					if (enderecoId != null && enderecoId > 0) {
						Endereco endereco = enderecoDao.buscarPorId(enderecoId);
						usuario.setEndereco(endereco);
					}

					return usuario;
				}

			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao buscar usuário com endereço: " + e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Busca um usuário por ID, sem restrição de usuário logado, incluindo endereço.
	 *
	 * @param id ID do usuário
	 * @return o usuário encontrado ou null
	 * @throws DaoException em caso de falha na busca
	 */
	public Usuario buscarPorIdComEndereco(Long id) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login, senha, admin, perfil_id, sexo, endereco_id FROM usuario WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setAdmin(rs.getBoolean("admin"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));

					Long enderecoId = rs.getLong("endereco_id");

					if (enderecoId != null && enderecoId > 0) {
						Endereco endereco = enderecoDao.buscarPorId(enderecoId);
						usuario.setEndereco(endereco);
					}

					return usuario;
				}

			}

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			throw new DaoException("Erro ao buscar usuário: " + e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Busca um usuário pelo login (sem restrições de administrador).
	 *
	 * @param login o login do usuário
	 * @return um Optional contendo o usuário, se encontrado
	 * @throws DaoException em caso de erro
	 */
	public Optional<Usuario> buscarPorLogin(String login) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login, senha, admin, perfil_id, sexo, endereco_id FROM usuario WHERE UPPER(login) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setAdmin(rs.getBoolean("admin"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));

					Long enderecoId = rs.getLong("endereco_id");

					if (enderecoId != null && enderecoId > 0) {
						Endereco endereco = enderecoDao.buscarPorId(enderecoId);
						usuario.setEndereco(endereco);
					}

					return Optional.of(usuario);
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return Optional.empty();
	}

	/**
	 * Busca um usuário pelo login com restrição de vínculo ao usuário logado.
	 *
	 * @param login           login a ser buscado
	 * @param idUsuarioLogado ID do usuário logado
	 * @return Optional com o usuário encontrado, se existir
	 * @throws DaoException em caso de erro
	 */
	public Optional<Usuario> buscarPorLogin(String login, Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login, senha, perfil_id, sexo FROM usuario WHERE UPPER(login) = UPPER(?) AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setSenha(rs.getString("senha"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));

					return Optional.of(usuario);
				}

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return Optional.empty();
	}

	/**
	 * Lista os usuários por nome com paginação, vinculados ao usuário logado.
	 *
	 * @param nome            nome parcial a ser buscado
	 * @param idUsuarioLogado ID do usuário logado
	 * @param offset          posição inicial para paginação
	 * @return lista de usuários encontrados
	 * @throws DaoException em caso de erro
	 */
	public List<Usuario> listarTodosPorNome(String nome, Long idUsuarioLogado, int offset) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login, senha, perfil_id, sexo FROM usuario WHERE UPPER(nome) LIKE CONCAT('%',UPPER(?),'%') AND admin is false AND usuario_id = ? ORDER BY id OFFSET ? LIMIT ?";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nome);
			pstmt.setLong(2, idUsuarioLogado);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, LIMITE_DE_PAGINA);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setSexo(Sexo.fromSigla(rs.getString("sexo")));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					usuario.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	/**
	 * Lista todos os usuários vinculados ao usuário logado com paginação.
	 *
	 * @param idUsuarioLogado ID do usuário logado
	 * @param offset          posição inicial para paginação
	 * @return lista de usuários
	 * @throws DaoException em caso de falha
	 */
	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado, int offset) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login FROM usuario WHERE admin is false AND usuario_id = ? ORDER BY id OFFSET ? LIMIT ?";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, LIMITE_DE_PAGINA);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	/**
	 * Lista todos os usuários não administradores associados ao usuário logado
	 * informado.
	 * <p>
	 * A consulta retorna usuários cujo campo {@code admin} é {@code false} e que
	 * possuem o {@code usuario_id} igual ao ID do usuário logado fornecido. Os
	 * dados retornados incluem informações básicas do usuário e seus telefones
	 * associados.
	 * </p>
	 *
	 * @param idUsuarioLogado o ID do usuário logado (usado para filtrar os usuários
	 *                        associados)
	 * @return uma lista de {@link Usuario} contendo os usuários encontrados
	 * @throws DaoException se ocorrer um erro ao acessar o banco de dados
	 */
	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login FROM usuario WHERE admin is false AND usuario_id = ? ORDER BY id";

		List<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();

					usuario.setId(rs.getLong("id"));
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					// CUIDADO: N+1
					// Melhorar essa query futuramente
					List<Telefone> telefones = telefoneDao.listarTodosPorUsuarioId(usuario.getId());
					usuario.setTelefones(telefones);

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	public List<Usuario> listarPorUsuarioLogado(Long idUsuarioLogado, String dataNascInicial, String dataNascFinal)
			throws DaoException {

		String sql = "SELECT id, nome, data_nascimento, renda_mensal, email, login FROM usuario WHERE admin is false AND usuario_id = ? AND data_nascimento BETWEEN ? AND ? ORDER BY id";

		List<Usuario> usuarios = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		LocalDate dataInicio = LocalDate.parse(dataNascInicial, formatter);
		LocalDate dataFinal = LocalDate.parse(dataNascFinal, formatter);

		if (dataInicio.isAfter(dataFinal)) {
			throw new DaoException("Data inicial não pode ser posterior à data final.");
		}

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);
			pstmt.setObject(2, dataInicio);
			pstmt.setObject(3, dataFinal);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					Usuario usuario = new Usuario();
					long id = rs.getLong("id");

					usuario.setId(id);
					usuario.setNome(rs.getString("nome"));
					usuario.setDataNascimento(rs.getObject("data_nascimento", LocalDate.class));
					usuario.setRendaMensal(rs.getDouble("renda_mensal"));
					usuario.setEmail(rs.getString("email"));
					usuario.setLogin(rs.getString("login"));
					// CUIDADO: N+1
					// Melhorar essa query futuramente
					List<Telefone> telefones = telefoneDao.listarTodosPorUsuarioId(usuario.getId());
					usuario.setTelefones(telefones);

					usuarios.add(usuario);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	/**
	 * Verifica se existe um usuário com o email informado.
	 *
	 * @param email o email a verificar
	 * @return true se existir, false caso contrário
	 * @throws DaoException em caso de erro
	 */
	public boolean existeEmail(String email) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(email) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, email);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // true se encontrou, false se não

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * Verifica se existe um usuário com o login informado.
	 *
	 * @param login o login a verificar
	 * @return true se existir, false caso contrário
	 * @throws DaoException em caso de erro
	 */
	public boolean existeLogin(String login) throws DaoException {

		// Mais leve e rápido ⚡ Só quer saber se existe ou não
		String sql = "SELECT 1 FROM usuario WHERE UPPER(login) = UPPER(?)";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, login);

			try (ResultSet rs = pstmt.executeQuery()) {

				return rs.next(); // true se encontrou, false se não

			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * Atualiza os dados do usuário e do seu endereço.
	 *
	 * @param usuario o usuário com dados atualizados
	 * @throws DaoException em caso de falha na atualização
	 */
	public void atualizarComEndereco(Usuario usuario) throws DaoException {

		String sql = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ?, perfil_id = ?, sexo = ?, data_nascimento = ?, renda_mensal = ? WHERE id = ?";

		try {
			// Atualiza o endereço
			enderecoDao.atualizar(usuario.getEndereco());

			// Atualiza o usuário
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setString(1, usuario.getNome());
				pstmt.setString(2, usuario.getEmail());
				pstmt.setString(3, usuario.getLogin());
				pstmt.setString(4, usuario.getSenha());
				pstmt.setInt(5, usuario.getPerfil().getId());
				pstmt.setString(6, usuario.getSexo().getSigla());
				pstmt.setObject(7, usuario.getDataNascimento());
				pstmt.setDouble(8, usuario.getRendaMensal());
				pstmt.setLong(9, usuario.getId());

				// Executa a query
				pstmt.executeUpdate();

				// Confirma a transação no banco
				connection.commit();

			}
		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao atualizar usuário com endereço: " + e.getMessage(), e);
		}
	}

	/**
	 * Remove um usuário pelo ID, desde que não seja um administrador.
	 *
	 * @param id o ID do usuário
	 * @throws DaoException em caso de erro ao deletar
	 */
	public void deletarPorId(Long id) throws DaoException {

		String deleteSql = "DELETE FROM usuario WHERE id = ? AND admin is false";

		try (PreparedStatement pstmt = connection.prepareStatement(deleteSql)) {
			pstmt.setLong(1, id);

			// Executa a query
			pstmt.executeUpdate();

			// Confirma a transação no banco
			connection.commit();

		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao remover um registro " + e.getMessage(), e);
		}
	}

	/**
	 * Retorna uma lista com a média salarial agrupada por perfil, considerando
	 * apenas os usuários subordinados ao usuário logado (usuário pai).
	 * <p>
	 * A média é calculada com duas casas decimais usando a função
	 * {@code ROUND(AVG(...), 2)} no banco de dados. O agrupamento é feito com base
	 * no {@code perfil_id} de cada usuário.
	 *
	 * @param idUsuarioLogado ID do usuário logado (usuário pai), utilizado para
	 *                        filtrar os registros.
	 * @return Lista de {@link MediaSalarialDTO} contendo a média salarial por
	 *         perfil dos usuários vinculados.
	 * @throws DaoException Caso ocorra erro de SQL, falha de conexão ou durante o
	 *                      rollback em caso de exceção.
	 */
	public List<MediaSalarialDTO> listarMediaSalarialPorPerfil(Long idUsuarioLogado) throws DaoException {

		String sql = "SELECT ROUND(AVG(renda_mensal),2) as media_salarial, perfil_id FROM usuario WHERE usuario_id = ? GROUP BY perfil_id  ORDER BY 1";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);

			List<MediaSalarialDTO> mediaSalarial = new ArrayList<>();

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					MediaSalarialDTO mediaSalarialDTO = new MediaSalarialDTO();

					mediaSalarialDTO.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));
					mediaSalarialDTO.setMediaSalarial(rs.getDouble("media_salarial"));

					mediaSalarial.add(mediaSalarialDTO);
				}
			}

			return mediaSalarial;

		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao consultar média salarial: " + e.getMessage(), e);
		}

	}

	/**
	 * Retorna uma lista com a média salarial agrupada por perfil, considerando
	 * apenas os usuários subordinados ao usuário logado (usuário pai) e cuja data
	 * de nascimento esteja dentro do intervalo especificado.
	 * <p>
	 * A média é calculada com duas casas decimais ({@code ROUND(AVG(...), 2)}) e
	 * agrupada por {@code perfil_id}.
	 * <p>
	 * Os resultados são ordenados pela média salarial.
	 *
	 * @param idUsuarioLogado ID do usuário logado (usuário pai), utilizado como
	 *                        filtro.
	 * @param dataInicial     Data inicial do intervalo de nascimento dos usuários a
	 *                        serem considerados.
	 * @param dataFinal       Data final do intervalo de nascimento dos usuários a
	 *                        serem considerados.
	 * @return Lista de {@link MediaSalarialDTO} contendo a média salarial por
	 *         perfil dos usuários no intervalo informado.
	 * @throws DaoException Caso ocorra erro de SQL, falha de conexão ou durante o
	 *                      rollback em caso de exceção.
	 */
	public List<MediaSalarialDTO> listarMediaSalarialPorPerfil(Long idUsuarioLogado, LocalDate dataInicial,
			LocalDate dataFinal) throws DaoException {

		String sql = "SELECT ROUND(AVG(renda_mensal),2) as media_salarial, perfil_id FROM usuario WHERE usuario_id = ? AND data_nascimento >= ? AND data_nascimento <= ? GROUP BY perfil_id  ORDER BY 1";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);
			pstmt.setObject(2, dataInicial);
			pstmt.setObject(3, dataFinal);

			List<MediaSalarialDTO> mediaSalarial = new ArrayList<>();

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {
					MediaSalarialDTO mediaSalarialDTO = new MediaSalarialDTO();

					mediaSalarialDTO.setPerfil(Perfil.fromId(rs.getInt("perfil_id")));
					mediaSalarialDTO.setMediaSalarial(rs.getDouble("media_salarial"));

					mediaSalarial.add(mediaSalarialDTO);
				}
			}

			return mediaSalarial;

		} catch (SQLException e) {
			rollback();
			throw new DaoException("Erro ao consultar média salarial: " + e.getMessage(), e);
		}

	}

	/**
	 * Calcula o número total de páginas de usuários cadastrados pelo usuário
	 * logado.
	 *
	 * @param idUsuarioLogado ID do usuário logado
	 * @return total de páginas
	 * @throws DaoException em caso de erro na contagem
	 */
	public int calcularTotalPaginas(Long idUsuarioLogado) throws DaoException {
		int totalRegistro = contarRegistros(idUsuarioLogado);
		return PaginacaoUtil.calcularTotalPaginas(totalRegistro, LIMITE_DE_PAGINA);
	}

	private int contarRegistros(Long idUsuarioLogado) throws DaoException {
		String sql = "SELECT COUNT(1) FROM usuario WHERE usuario_id = ? AND admin is false";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setLong(1, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				} else {

					return 0; // Se não tiver resultado, retorna 0
				}
			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao consultar total de registros " + e.getMessage(), e);
		}
	}

	/**
	 * Calcula o total de páginas filtrando pelo nome, considerando o usuário
	 * logado.
	 *
	 * @param nome            nome parcial
	 * @param idUsuarioLogado ID do usuário logado
	 * @return total de páginas
	 * @throws DaoException em caso de erro
	 */
	public int calcularTotalPaginasPorNome(String nome, Long idUsuarioLogado) throws DaoException {
		int totalPaginas = contarRegistrosPorNome(nome, idUsuarioLogado);
		return PaginacaoUtil.calcularTotalPaginas(totalPaginas, LIMITE_DE_PAGINA);
	}

	private int contarRegistrosPorNome(String nome, Long idUsuarioLogado) throws DaoException {
		String sql = "SELECT COUNT(1) FROM usuario WHERE UPPER(nome) LIKE CONCAT('%',UPPER(?),'%') AND admin is false AND usuario_id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, nome);
			pstmt.setLong(2, idUsuarioLogado);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				} else {

					return 0; // Se não tiver resultado, retorna 0
				}
			}

		} catch (SQLException e) {
			throw new DaoException("Erro ao consultar total de registros por nome " + e.getMessage(), e);
		}
	}

	/**
	 * Retorna o limite fixo de registros por página.
	 *
	 * @return o limite de paginação
	 */
	public int getLimitePagina() {
		return LIMITE_DE_PAGINA;
	}

	/**
	 * Realiza rollback na conexão atual.
	 *
	 * @throws DaoException se ocorrer erro ao desfazer transações
	 */
	private void rollback() throws DaoException {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
}