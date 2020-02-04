package model.services;

import java.util.List;

import model.DAO.DAOFactory;
import model.DAO.DAOImpl.DepartamentoDAO;
import model.entities.Departamento;

public class DepartamentoService {

	private DepartamentoDAO dao = DAOFactory.createDepartamentoDAO();

	public List<Departamento> findAll() {
		return dao.findAllEntities();
	}
	
	public void saveOrUpdate(Departamento entity) {
		if (entity.getId() == null) {
			dao.insert(entity);
		} else {
			dao.update(entity);
		}
	}
}
