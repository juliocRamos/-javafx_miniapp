package model.DAO.DAOInterfaces;

import java.util.List;

/**
 * Interface genérica para minimizar a duplicação de código.
 */
public interface IbasicDAO<T> {
    void insert(T obj);
    
    void update(T obj);
    
    void deleteById(int id);
    
    T findEntityById(int id);
    
    List<T> findAllEntities();
    
}
