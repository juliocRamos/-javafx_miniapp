package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Departamento;

public class DepartamentoService {

	// Mocka os dados de Departamento (Não é do banco)
	public List<Departamento> findAll(){
		List<Departamento> depList = new ArrayList<>();
		
		depList.add(new Departamento(1,"Livros"));
		depList.add(new Departamento(2,"Computadores"));
		depList.add(new Departamento(3,"Teste"));
		return depList;
	}
}
