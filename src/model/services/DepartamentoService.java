package model.services;

import java.util.List;

import model.DAO.DAOFactory;
import model.DAO.DAOImpl.DepartamentoDAO;
import model.entities.Departamento;

public class DepartamentoService implements BasicService<Departamento> {

	private DepartamentoDAO dao = DAOFactory.createDepartamentoDAO();

	@Override
	public List<Departamento> findAll() {
		return dao.findAllEntities();
	}

	@Override
	public void saveOrUpdate(Departamento entity) {
		if (entity.getId() == null) {
			dao.insert(entity);
		} else {
			dao.update(entity);
		}
	}

	@Override
	public void remove(Departamento entity) {
		dao.deleteById(entity.getId());
	}
}
