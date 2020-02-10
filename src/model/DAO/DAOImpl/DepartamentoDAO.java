package model.DAO.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconn.ConnectionHandler;
import dbconn.DbException;
import dbconn.DbIntegrityException;
import model.DAO.DAOInterfaces.IbasicDAO;
import model.entities.Departamento;

public class DepartamentoDAO implements IbasicDAO<Departamento> {

	private Connection conn;

	public DepartamentoDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Departamento obj) {
		PreparedStatement st = null;

		try {
			String query = ""   //
					+ "INSERT INTO departamento" //
					+ "  (nome)" //
					+ "VALUES" //
					+ "  (?)"; //

			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());

			int affectedRows = st.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int generatedID = rs.getInt(1);
					obj.setId(generatedID);
				}
				ConnectionHandler.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha afetada ou dado persistido");
			}
		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
		}

	}

	@Override
	public void update(Departamento obj) {
		PreparedStatement st = null;
		try {
			String query = ""  //
					+ "UPDATE departamento" //
					+ "  SET nome = ? " //
					+ "WHERE id = ?"; //

			st = conn.prepareStatement(query);
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());

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
					+ " DELETE FROM " //
					+ "  departamento " //
					+ " WHERE id = ?"; //

			st = conn.prepareStatement(query);
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException ex) {
			throw new DbIntegrityException(ex.getMessage());
		} finally {
			ConnectionHandler.closeStatement(st);
		}
	}

	@Override
	public Departamento findEntityById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "" //
					+ "SELECT * "  //
					+ "  FROM departamento "  //
					+ "WHERE id = ?";  //
			
			st = conn.prepareStatement(query);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if (rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("id"));
				dep.setNome(rs.getString("nome"));
				return dep;
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
	public List<Departamento> findAllEntities() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String query = ""
					+ "SELECT * " //
					+ "  FROM departamento " //
					+ " ORDER BY nome";  //
			
			st = conn.prepareStatement(query);
			rs = st.executeQuery();
			List<Departamento> departamentos = new ArrayList<>();

			while (rs.next()) {
				Departamento dep = new Departamento();
				dep.setId(rs.getInt("id"));
				dep.setNome(rs.getString("nome"));
				departamentos.add(dep);
			}
			return departamentos;

		} catch (SQLException ex) {
			throw new DbException(ex.getMessage());
		} finally {
            ConnectionHandler.closeStatement(st);
            ConnectionHandler.closeResultSet(rs);
        }	
	}
}
