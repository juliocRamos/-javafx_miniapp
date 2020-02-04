package model.DAO;

import dbconn.ConnectionHandler;
import model.DAO.DAOImpl.DepartamentoDAO;
import model.DAO.DAOImpl.VendedorDAO;

public class DAOFactory {
    public static VendedorDAO createVendedorDAO() {
		return new VendedorDAO(ConnectionHandler.getConnection());
    }
    
    public static DepartamentoDAO createDepartamentoDAO() {
        return new DepartamentoDAO(ConnectionHandler.getConnection());
    }
}
