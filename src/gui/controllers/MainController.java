package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("Departamento item click");
	}

	@FXML
	public void onSobreMenuItemAction() {
		System.out.println("Ajuda item click");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

}
