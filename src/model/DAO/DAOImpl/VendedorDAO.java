package model.DAO.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbconn.ConnectionHandler;
import dbconn.DbException;
import model.DAO.DAOInterfaces.IbasicDAO;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDAO implements IbasicDAO<Vendedor> {

	private Connection conn;

	public VendedorDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vendedor obj) {
		PreparedStatement st = null;

		try {
			String query = "" + " INSERT INTO vendedor" //
					+ "    (nome, email, nascimento, salario, departamentoid)" //
					+ " VALUES " + "    (?,?,?,?,?)";

			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, obj.getNascimento());
			st.setDouble(4, obj.getSalario());
			st.setInt(5, obj.getDepartamento().getId());

			int affectedRows = st.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int generatedId = rs.getInt(1);
					obj.setId(generatedId);
				}
				ConnectionHandler.closeResultSet(rs);

			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada");
			}
		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
		}
	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement st = null;

		try {
			String query = "" + " UPDATE vendedor " //
					+ "   SET nome = ?, email = ?, nascimento = ? " //
					+ "     salario = ?, departamentoid = ? " //
					+ " WHERE id = ?";

			st = conn.prepareStatement(query);
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3, obj.getNascimento());
			st.setDouble(4, obj.getSalario());
			st.setInt(5, obj.getDepartamento().getId());
			st.setInt(6, obj.getId());

			st.executeUpdate();
		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
		}
	}

	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			String query = "" //
					+ " DELETE FROM" //
					+ "   vendedor" //
					+ " WHERE id = ?";

			st = conn.prepareStatement(query);
			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
		}

	}

	@Override
	public Vendedor findEntityById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String query = "" + " SELECT vendedor.*" //
					+ "   departamento.nome as depNome " //
					+ " FROM vendedor " //
					+ "   JOIN departamento " //
					+ "      ON vendedor.departamentoid = departamento.id " //
					+ " WHERE vendedor.id = ?";

			st = conn.prepareStatement(query);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Departamento dep = Departamento.getInstanceFromResultSet(rs);
				Vendedor vendedor = Vendedor.getInstanceFromResultSet(rs, dep);

				return vendedor;
			}

			return null;

		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
			ConnectionHandler.closeResultSet(rs);
		}
	}

	@Override
	public List<Vendedor> findAllEntities() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			String query = "" + " SELECT vendedor.*, " //
					+ "    departamento.nome as depNome " //
					+ " FROM vendedor " // 
					+ "   JOIN departamento " + // 
					"     ON vendedor.departamentoid = departamento.id " //
					+ " ORDER BY nome ";

			st = conn.prepareStatement(query);
			rs = st.executeQuery();

			List<Vendedor> vendedores = new ArrayList<>();
			Map<Integer, Departamento> mapIdPorDepartamento = new HashMap<>();

			while (rs.next()) {
				Departamento dep = mapIdPorDepartamento.get(rs.getInt("departamentoid"));

				if (dep == null) {
					dep = Departamento.getInstanceFromResultSet(rs);
					mapIdPorDepartamento.put(rs.getInt("departamentoid"), dep);
				}

				Vendedor vendedor = Vendedor.getInstanceFromResultSet(rs, dep);
				vendedores.add(vendedor);
			}

			return vendedores;
		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
			ConnectionHandler.closeResultSet(rs);
		}
	}

	public List<Vendedor> findByDepartamento(int departamentoID) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("" + " SELECT vendedor.*, " + "  departamento.nome as DepNome "
					+ " FROM vendedor " + "  JOIN departamento " + "   ON vendedor.departamentoid = departamento.id "
					+ " WHERE departamentoid = ? " + " ORDER BY nome ");

			st.setInt(1, departamentoID);
			rs = st.executeQuery();

			List<Vendedor> vendedores = new ArrayList<>();
			Map<Integer, Departamento> mapaIdPorDepartamento = new HashMap<>();

			while (rs.next()) {
				Departamento dep = mapaIdPorDepartamento.get(rs.getInt("departamentoid"));

				if (dep == null) {
					dep = Departamento.getInstanceFromResultSet(rs);
					mapaIdPorDepartamento.put(rs.getInt("departamentoid"), dep);
				}

				Vendedor vendedor = Vendedor.getInstanceFromResultSet(rs, dep);
				vendedores.add(vendedor);
			}

			return vendedores;

		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeResultSet(rs);
			ConnectionHandler.closeStatement(st);
		}
	}

}
