package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Departamento;
import model.gui.util.Alerts;
import model.services.DepartamentoService;
import utils.GuiUtilities;

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
	public void onButtonInserirAction(ActionEvent event) {
		Stage parentStage = GuiUtilities.getCurrentStage(event);
		Departamento entity = new Departamento();
		createDialogForm(parentStage, "/model/gui/views/DepartamentoForm.fxml", entity);
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

	private void createDialogForm(Stage parentStage, String absoluteName, Departamento entity) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			DepartamentoFormController controller = loader.getController();
			controller.setDepartamento(entity);
			controller.setDepartamentoService(new DepartamentoService());
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
}
