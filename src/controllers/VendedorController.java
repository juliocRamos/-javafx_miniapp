package controllers;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dbconn.DbIntegrityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Vendedor;
import model.gui.listeners.IDataChangeListener;
import model.gui.util.Alerts;
import model.services.VendedorService;
import utils.GuiUtilities;

public class VendedorController implements Initializable, IDataChangeListener {

	private VendedorService service;

	private ObservableList<Vendedor> observableListVendedor;

	@FXML
	private TableView<Vendedor> tableVendedores;

	@FXML
	private TableColumn<Vendedor, Integer> tableColumnId;

	@FXML
	private TableColumn<Vendedor, String> tableColumnNome;

	@FXML
	private TableColumn<Vendedor, String> tableColumnEmail;

	@FXML
	private TableColumn<Vendedor, Date> tableColumnNascimento;

	@FXML
	private TableColumn<Vendedor, Double> tableColumnSalario;

	@FXML
	private Button buttonInserir;

	@FXML
	private Button buttonEditar;

	@FXML
	private Button buttonRemover;

	@FXML
	public void onButtonInserirAction(ActionEvent event) {
		Stage parentStage = GuiUtilities.getCurrentStage(event);
		Vendedor entity = new Vendedor();
		createDialogForm(parentStage, "/model/gui/views/VendedorForm.fxml", entity);
	}

	@FXML
	public void onButtonEditarAction(ActionEvent event) {
		Stage parentStage = GuiUtilities.getCurrentStage(event);
		Vendedor entity = tableVendedores.getSelectionModel().getSelectedItem();

		if (entity == null) {
			Alerts.showAlert("Erro na edição", null, "Selecione ao menos um registro para editar", AlertType.WARNING);
		}

		createDialogForm(parentStage, "/model/gui/views/VendedorForm.fxml", entity);
		updateTableView();
	}

	@FXML
	public void onButtonRemoveAction(ActionEvent event) {
		Vendedor entity = tableVendedores.getSelectionModel().getSelectedItem();

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
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnNascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));
		GuiUtilities.formatTableColumnDate(tableColumnNascimento, "dd/MM/yyyy");
		tableColumnSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
		GuiUtilities.formatTableColumnDouble(tableColumnSalario, 2);
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service de Vendedor está nula");
		}

		List<Vendedor> list = service.findAll();
		observableListVendedor = FXCollections.observableArrayList(list);
		tableVendedores.setItems(observableListVendedor);
		tableVendedores.refresh();
	}

	public void setVendedorService(VendedorService service) {
		this.service = service;
	}

	private void createDialogForm(Stage parentStage, String absoluteName, Vendedor entity) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//
//			VendedorFormController controller = loader.getController();
//			controller.setVendedor(entity);
//			controller.setVendedorService(new VendedorService());
//			// Inscreve este Controller para receber o evento.
//			controller.addDataChangeListener(this);
//			controller.updateFormData();
//
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Novo Vendedor");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//		} catch (IOException ex) {
//			Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), AlertType.ERROR);
//		}
	}

	/**
	 * Implementação das ações de um observer para atualizar os dados da tabela dada
	 * uma ação que é executada em VendedorFormcontroller.
	 */
	@Override
	public void onDataChange() {
		updateTableView();
	}
}
