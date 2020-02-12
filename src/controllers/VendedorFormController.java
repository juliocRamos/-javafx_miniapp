package controllers;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import dbconn.DbException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.entities.Vendedor;
import model.exceptions.ValidationException;
import model.gui.listeners.IDataChangeListener;
import model.gui.util.Alerts;
import model.gui.util.Constraints;
import model.services.DepartamentoService;
import model.services.IBasicService;
import model.services.VendedorService;
import utils.GuiUtilities;
import utils.NumberUtilities;

public class VendedorFormController implements Initializable {

	private Vendedor entity;

	private VendedorService service;

	private List<IDataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField textID;

	@FXML
	private TextField textNome;

	@FXML
	private TextField textEmail;

	@FXML
	private DatePicker datePickerNascimento;

	@FXML
	private TextField textSalario;

	@FXML
	private ComboBox<Departamento> comboDepartamentos;

	@FXML
	private Button inserirButton;

	@FXML
	private Button cancelarButton;

	@FXML
	public void onInserirButtonAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Null Entity");
		}

		if (service == null) {
			throw new IllegalStateException("Null service");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			GuiUtilities.getCurrentStage(event).close();

		} catch (ValidationException ex) {
			showAlertMessage(ex.getIssues());
		} catch (DbException ex) {
			Alerts.showAlert("Erro ao salvar Vendedor", null, ex.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyDataChangeListeners() {
		for (IDataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}

	}

	private Vendedor getFormData() {
		Vendedor vendedor = new Vendedor();
		ValidationException exception = new ValidationException("Erro de validação");

		vendedor.setId(NumberUtilities.tryParseToInt(textID.getText()));

		if (textNome.getText() == null || textNome.getText().trim().equals("")) {
			exception.setIssues("nome", "Nome não pode ser vazio");
		}

		if (textEmail.getText() == null || textEmail.getText().trim().equals("")) {
			exception.setIssues("email", "Email não pode ser vazio");
		}

		if (datePickerNascimento.getValue() == null) {
			exception.setIssues("aniversario", "Aniversário não pode ser vazio");
		}
		
		if (textSalario.getText() == null || textSalario.getText().trim().equals("")) {
			exception.setIssues("salario", "Salário não pode ser vazio");
		}
		
		if (exception.getIssues().size() > 0) {
			throw exception;
		}
		
		Instant instant = Instant.from(datePickerNascimento.getValue() //
				.atStartOfDay(ZoneId.systemDefault()));

		vendedor.setSalario(NumberUtilities.tryParseToDouble(textSalario.getText()));
		vendedor.setNascimento(new java.sql.Date(instant.toEpochMilli()));
		vendedor.setEmail(textEmail.getText());
		vendedor.setNome(textNome.getText());
		vendedor.setDepartamento(comboDepartamentos.getSelectionModel().getSelectedItem());

		if (exception.getIssues().size() > 0) {
			throw exception;
		}

		return vendedor;
	}

	@FXML
	public void onCancelarButtonAction(ActionEvent event) {
		GuiUtilities.getCurrentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(textID);
		Constraints.setTextFieldMaxLength(textNome, 35);
		Constraints.setTextFieldDouble(textSalario);
		Constraints.setTextFieldMaxLength(textEmail, 50);
		GuiUtilities.formatDatePicker(datePickerNascimento, "dd/MM/yyyy");
		GuiUtilities.configureEntityComboBox(comboDepartamentos);
	}

	public void setVendedor(Vendedor entity) {
		this.entity = entity;
	}

	// Adiciona um novo changeListener.
	public void addDataChangeListener(IDataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	private void showAlertMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			Alerts.showAlert("Erro ao salvar novo Vendedor", "Erro na persistência", errors.get("nome"),
					AlertType.WARNING);
		}
	}

	public void setVendedorService(VendedorService service) {
		this.service = service;
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Não foi possível bindear a entidade com o form\nNull entity");
		}

		textID.setText(String.valueOf(entity.getId()));
		textNome.setText(entity.getNome());
		textEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		textSalario.setText(String.format("%.2f", entity.getSalario()));
		if (entity.getNascimento() != null) {
			datePickerNascimento.setValue(entity.getNascimento().toLocalDate());
		}

		DepartamentoService service = IBasicService.getService(DepartamentoService.class);
		List<Departamento> depList = service.findAll();
		comboDepartamentos.setItems(FXCollections.observableArrayList(depList));

		if (entity.getDepartamento() == null) {
			comboDepartamentos.getSelectionModel().selectFirst();
		} else {
			comboDepartamentos.getSelectionModel().select(entity.getDepartamento());
		}
	}
}
