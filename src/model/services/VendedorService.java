package model.services;

import java.util.List;

import model.DAO.DAOFactory;
import model.DAO.DAOImpl.VendedorDAO;
import model.entities.Vendedor;

public class VendedorService {

	private VendedorDAO dao = DAOFactory.createVendedorDAO();

	public List<Vendedor> findAll() {
		return dao.findAllEntities();
	}

	public void saveOrUpdate(Vendedor entity) {
		if (entity.getId() == null) {
			dao.insert(entity);
		} else {
			dao.update(entity);
		}
	}

	public void remove(Vendedor obj) {
		dao.deleteById(obj.getId());
	}
}
