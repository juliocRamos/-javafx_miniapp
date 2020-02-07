package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import dbconn.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.exceptions.ValidationException;
import model.gui.listeners.IDataChangeListener;
import model.gui.util.Alerts;
import model.gui.util.Constraints;
import model.services.DepartamentoService;
import utils.GuiUtilities;
import utils.NumberUtilities;

public class DepartamentoFormController implements Initializable {

	private Departamento entity;

	private DepartamentoService service;

	private List<IDataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField textID;

	@FXML
	private TextField textNome;

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

		}
		catch(ValidationException ex) {
			showAlertMessage(ex.getIssues());
		}
		catch (DbException ex) {
			Alerts.showAlert("Erro ao salvar Departamento", null, ex.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyDataChangeListeners() {
		for (IDataChangeListener listener : dataChangeListeners) {
			listener.onDataChange();
		}

	}

	private Departamento getFormData() {
		Departamento dep = new Departamento();
		ValidationException exception = new ValidationException("Erro de validação");

		dep.setId(NumberUtilities.tryParseToInt(textID.getText()));

		if (textNome.getText() == null || textNome.getText().trim().equals("")) {
			exception.setIssues("nome", "Campo nome não pode ser vazio");
		}

		dep.setNome(textNome.getText());

		if (exception.getIssues().size() > 0) {
			throw exception;
		}

		return dep;
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
	}

	public void setDepartamento(Departamento entity) {
		this.entity = entity;
	}

	// Adiciona um novo changeListener.
	public void addDataChangeListener(IDataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	private void showAlertMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("nome")) {
			Alerts.showAlert("Erro ao salvar novo Departamento", "Erro na persistência", errors.get("nome"),
					AlertType.WARNING);
		}
	}

	public void setDepartamentoService(DepartamentoService service) {
		this.service = service;
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Não foi possível bindear a entidade com o form\nNull entity");
		}

		textID.setText(String.valueOf(entity.getId()));
		textNome.setText(entity.getNome());
	}
}
