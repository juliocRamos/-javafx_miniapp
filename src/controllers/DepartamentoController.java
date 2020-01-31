package controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Departamento;
import model.services.DepartamentoService;

public class DepartamentoController implements Initializable {

	private DepartamentoService service;
	
	@FXML
	private TableView<Departamento> tableDepartamentos;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;

	@FXML
	private Button buttonInserir;
	
	private ObservableList<Departamento> observableListDepartamento;

	@FXML
	public void onButtonInserirAction() {
		System.out.println("Cliquei em inserir");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service de Departamento está nula");
		}

		List<Departamento> list = service.findAll();
		observableListDepartamento = FXCollections.observableArrayList(list);
		tableDepartamentos.setItems(observableListDepartamento);
	}
	
	public void setDepartamentoServico(DepartamentoService service) {
		this.service = service;
	}
}
