package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Departamento;

public class DepartamentoController implements Initializable {

	@FXML
	private TableView<Departamento> tableDepartamentos;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnNome;

	@FXML
	private Button buttonInserir;
	
	
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
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}
}
