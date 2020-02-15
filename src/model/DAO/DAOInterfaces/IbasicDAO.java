package model.DAO.DAOInterfaces;

import java.util.List;

/**
 * Interface gen�rica para minimizar a duplica��o de c�digo.
 */
public interface IbasicDAO<T> {
    void insert(T obj);
    
    void update(T obj);
    
    void deleteById(int id);
    
    T findEntityById(int id);
    
    List<T> findAllEntities();
    
}
