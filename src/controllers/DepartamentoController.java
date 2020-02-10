package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dbconn.DbIntegrityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.gui.listeners.IDataChangeListener;
import model.gui.util.Alerts;
import model.services.DepartamentoService;
import utils.GuiUtilities;

public class DepartamentoController implements Initializable, IDataChangeListener {

	private DepartamentoService service;

	private ObservableList<Departamento> observableListDepartamento;

	@FXML
	private TableView<Departamento> tableDepartamentos;

	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;

	@FXML
	private TableColumn<Departamento, String> tableColumnNome;

	@FXML
	private Button buttonInserir;

	@FXML
	private Button buttonEditar;

	@FXML
	private Button buttonRemover;

	@FXML
	public void onButtonInserirAction(ActionEvent event) {
		Stage parentStage = GuiUtilities.getCurrentStage(event);
		Departamento entity = new Departamento();
		createDialogForm(parentStage, "/model/gui/views/DepartamentoForm.fxml", entity);
	}

	@FXML
	public void onButtonEditarAction(ActionEvent event) {
		Stage parentStage = GuiUtilities.getCurrentStage(event);
		Departamento entity = tableDepartamentos.getSelectionModel().getSelectedItem();

		if (entity == null) {
			Alerts.showAlert("Erro na edição", null, "Selecione ao menos um registro para editar", AlertType.WARNING);
		}

		createDialogForm(parentStage, "/model/gui/views/DepartamentoForm.fxml", entity);
		updateTableView();
	}

	@FXML
	public void onButtonRemoveAction(ActionEvent event) {
		Stage pareStage = GuiUtilities.getCurrentStage(event);
		Departamento entity = tableDepartamentos.getSelectionModel().getSelectedItem();

		if (entity == null) {
			Alerts.showAlert("Erro na remoção", null, "Selecione ao menos um registro para remover", AlertType.WARNING);
		}

		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Deseja realmente remover?");

		if (result.get() == ButtonType.OK) {
			try {
				service.remove(entity);
				updateTableView();
			} catch (DbIntegrityException ex) {
				Alerts.showAlert("Error", null, ex.getMessage(), AlertType.ERROR);
			}
		}
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
		tableDepartamentos.refresh();
	}

	public void setDepartamentoServico(DepartamentoService service) {
		this.service = service;
	}

	private void createDialogForm(Stage parentStage, String absoluteName, Departamento entity) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartamentoFormController controller = loader.getController();
			controller.setDepartamento(entity);
			controller.setDepartamentoService(new DepartamentoService());
			// Inscreve este Controller para receber o evento.
			controller.addDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Novo Departamento");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException ex) {
			Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), AlertType.ERROR);
		}
	}

	/**
	 * Implementação das ações de um observer para atualizar os dados da tabela dada
	 * uma ação que é executada em DepartamentoFormcontroller.
	 */
	@Override
	public void onDataChange() {
		updateTableView();
	}
}
