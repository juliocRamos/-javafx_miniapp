package utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GuiUtilities {

	public static Stage getCurrentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}

	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<>() {

				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}

	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {

			DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(format);

			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dtFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dtFormatter);
				} else {
					return null;
				}
			}
		});
	}

	public static <T> void configureEntityComboBox(ComboBox<T> comboBox) {
		Callback<ListView<T>, ListCell<T>> factory = lv -> new ListCell<T>() {
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.toString());
			};
		};
		
		comboBox.setCellFactory(factory);
		comboBox.setButtonCell(factory.call(null));
	}
}
