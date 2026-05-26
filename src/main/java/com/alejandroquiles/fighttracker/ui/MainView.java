package com.alejandroquiles.fighttracker.ui;

import java.time.LocalDate;

import com.alejandroquiles.fighttracker.model.TrainingType;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

	private TextField titleField;
	private ComboBox<TrainingType> typeComboBox;
	private DatePicker datePicker;
	private TextField durationField;
	private TextField intensityField;
	private TextArea notesArea;
	private Button addSessionButton;

	public Parent createView() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(24));
		root.setStyle("-fx-background-color: #121216;");

		VBox mainContent = new VBox(24);
		mainContent.setAlignment(Pos.TOP_LEFT);

		Label titleLabel = new Label("Fight Training Tracker");
		titleLabel.setStyle(
				"-fx-text-fill: #F5F5F5;" +
				"-fx-font-size: 32px;" +
				"-fx-font-weight: bold;"
		);

		Label subtitleLabel = new Label("Registra tus entrenamientos y visualiza tu progreso.");
		subtitleLabel.setStyle(
				"-fx-text-fill: #A1A1AA;" +
				"-fx-font-size: 15px;"
		);

		VBox headerBox = new VBox(6, titleLabel, subtitleLabel);

		HBox statsBox = new HBox(16);
		statsBox.getChildren().addAll(
				createStatCard("Sesiones", "0"),
				createStatCard("Minutos", "0"),
				createStatCard("Intensidad media", "0.0")
		);

		HBox contentBox = new HBox(20);
		contentBox.getChildren().addAll(
				createFormPanel(),
				createPanel("Historial")
		);

		mainContent.getChildren().addAll(headerBox, statsBox, contentBox);
		root.setCenter(mainContent);

		return root;
	}

	private VBox createFormPanel() {
		Label titleLabel = new Label("Nuevo entrenamiento");
		titleLabel.setStyle(
				"-fx-text-fill: #FFFFFF;" +
				"-fx-font-size: 18px;" +
				"-fx-font-weight: bold;"
		);

		this.titleField = new TextField();
		this.titleField.setPromptText("Ej: Striking y defensa");

		this.typeComboBox = new ComboBox<>();
		this.typeComboBox.getItems().addAll(TrainingType.values());
		this.typeComboBox.setValue(TrainingType.TECNICA);

		this.datePicker = new DatePicker(LocalDate.now());

		this.durationField = new TextField();
		this.durationField.setPromptText("Minutos");

		this.intensityField = new TextField();
		this.intensityField.setPromptText("1 - 5");

		this.notesArea = new TextArea();
		this.notesArea.setPromptText("Notas del entrenamiento...");
		this.notesArea.setPrefRowCount(3);

		this.addSessionButton = new Button("Añadir sesión");

		VBox formPanel = new VBox(10);
		formPanel.setPadding(new Insets(18));
		formPanel.setPrefSize(350, 360);
		formPanel.setStyle(
				"-fx-background-color: #1E1E24;" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: #33333D;" +
				"-fx-border-radius: 12;"
		);

		formPanel.getChildren().addAll(
				titleLabel,
				createFieldGroup("Título", this.titleField),
				createFieldGroup("Tipo", this.typeComboBox),
				createFieldGroup("Fecha", this.datePicker),
				createFieldGroup("Duración", this.durationField),
				createFieldGroup("Intensidad", this.intensityField),
				createFieldGroup("Notas", this.notesArea),
				this.addSessionButton
		);

		return formPanel;
	}

	private VBox createFieldGroup(String labelText, Node field) {
		Label label = new Label(labelText);
		label.setStyle(
				"-fx-text-fill: #A1A1AA;" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;"
		);

		VBox group = new VBox(4, label, field);
		return group;
	}

	private VBox createStatCard(String title, String value) {
		Label titleLabel = new Label(title);
		titleLabel.setStyle(
				"-fx-text-fill: #A1A1AA;" +
				"-fx-font-size: 13px;"
		);

		Label valueLabel = new Label(value);
		valueLabel.setStyle(
				"-fx-text-fill: #FFFFFF;" +
				"-fx-font-size: 26px;" +
				"-fx-font-weight: bold;"
		);

		VBox card = new VBox(8, titleLabel, valueLabel);
		card.setPadding(new Insets(16));
		card.setPrefWidth(180);
		card.setStyle(
				"-fx-background-color: #1E1E24;" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: #33333D;" +
				"-fx-border-radius: 12;"
		);

		return card;
	}

	private VBox createPanel(String title) {
		Label titleLabel = new Label(title);
		titleLabel.setStyle(
				"-fx-text-fill: #FFFFFF;" +
				"-fx-font-size: 18px;" +
				"-fx-font-weight: bold;"
		);

		VBox panel = new VBox(12, titleLabel);
		panel.setPadding(new Insets(18));
		panel.setPrefSize(350, 360);
		panel.setStyle(
				"-fx-background-color: #1E1E24;" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: #33333D;" +
				"-fx-border-radius: 12;"
		);

		return panel;
	}
}