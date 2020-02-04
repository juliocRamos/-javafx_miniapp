package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import dbconn.DbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Departamento;
import model.gui.util.Alerts;
import model.gui.util.Constraints;
import model.services.DepartamentoService;
import utils.GuiUtilities;
import utils.NumberUtilities;

public class DepartamentoFormController implements Initializable {

	private Departamento entity;

	private DepartamentoService service;

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
			GuiUtilities.getCurrentStage(event).close();
			
		} catch (DbException ex) {
			Alerts.showAlert("Erro ao salvar Departamento", null, ex.getMessage(), AlertType.ERROR);
		}

	}

	private Departamento getFormData() {
		Departamento dep = new Departamento();
		dep.setId(NumberUtilities.tryParseToInt(textID.getText()));
		dep.setNome(textNome.getText());
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
