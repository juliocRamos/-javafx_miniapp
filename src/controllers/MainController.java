package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.gui.util.Alerts;

public class MainController implements Initializable {

	@FXML
	private MenuItem cadastroVendedorMenuItem;

	@FXML
	private MenuItem cadastroDepartamentomenuItem;

	@FXML
	private MenuItem sobreMenuItem;

	@FXML
	public void onCadastroVendedorMenuItemAction() {
		System.out.println("Vendedor item click");
	}

	@FXML
	public void onCadastroDepartamentoMenuItemAction() {
		loadView("/model/gui/views/DepartamentoView.fxml");
	}

	@FXML
	public void onSobreMenuItemAction() {
		loadView("/model/gui/views/SobreView.fxml");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}

	private synchronized void loadView(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException ex) {
			Alerts.showAlert("Exception occurred", "Error loading view", ex.getMessage(), AlertType.ERROR);
		}
	}

}
