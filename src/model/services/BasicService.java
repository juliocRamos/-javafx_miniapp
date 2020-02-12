package model.services;

import java.util.List;

import model.entities.BasicEntity;

public interface BasicService<T extends BasicEntity> {

	 List<T> findAll();

	 void saveOrUpdate(T entity);

	 void remove(T obj);
}
