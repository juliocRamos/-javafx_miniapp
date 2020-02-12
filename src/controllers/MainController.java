package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.DepartamentoService;
import model.services.IBasicService;
import model.services.VendedorService;

public class MainController implements Initializable {

	@FXML
	private MenuItem cadastroVendedorMenuItem;

	@FXML
	private MenuItem cadastroDepartamentomenuItem;

	@FXML
	private MenuItem sobreMenuItem;

	@FXML
	public void onCadastroVendedorMenuItemAction() {
		loadView("/model/gui/views/VendedorView.fxml", (VendedorController controller) -> {
			controller.setVendedorService(IBasicService.getService(VendedorService.class));
			controller.updateTableView();
		});
	}

	@FXML
	public void onCadastroDepartamentoMenuItemAction() {
		loadView("/model/gui/views/DepartamentoView.fxml", (DepartamentoController controller) -> {
			controller.setDepartamentoService(new DepartamentoService());
			controller.updateTableView();
		});
	}

	@FXML
	public void onSobreMenuItemAction() {
		loadView("/model/gui/views/SobreView.fxml", x -> {
		});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializeAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

			T controller = loader.getController();
			initializeAction.accept(controller);

		} catch (IOException ex) {
			Alerts.showAlert("Exception occurred", "Error loading view", ex.getMessage(), AlertType.ERROR);
		}
	}
}
