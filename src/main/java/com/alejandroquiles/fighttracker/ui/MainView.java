package com.alejandroquiles.fighttracker.ui;

import java.time.LocalDate;

import com.alejandroquiles.fighttracker.model.TrainingSession;
import com.alejandroquiles.fighttracker.model.TrainingType;
import com.alejandroquiles.fighttracker.service.TrainingManager;

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

	private TrainingManager trainingManager;

	private Label totalSessionsValueLabel;
	private Label totalMinutesValueLabel;
	private Label averageIntensityValueLabel;
	private Label feedbackLabel;

	public Parent createView() {
		this.trainingManager = new TrainingManager();

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

		this.totalSessionsValueLabel = createStatValueLabel("0");
		this.totalMinutesValueLabel = createStatValueLabel("0");
		this.averageIntensityValueLabel = createStatValueLabel("0.0");

		HBox statsBox = new HBox(16);
		statsBox.getChildren().addAll(
				createStatCard("Sesiones", this.totalSessionsValueLabel),
				createStatCard("Minutos", this.totalMinutesValueLabel),
				createStatCard("Intensidad media", this.averageIntensityValueLabel)
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

		this.feedbackLabel = new Label(" ");
		this.feedbackLabel.setStyle(
				"-fx-text-fill: #A1A1AA;" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;"
		);

		this.addSessionButton.setOnAction(event -> addTrainingSession());

		VBox formPanel = new VBox(10);
		formPanel.setPadding(new Insets(18));
		formPanel.setPrefSize(350, 390);
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
				this.addSessionButton,
				this.feedbackLabel
		);

		return formPanel;
	}

	private void addTrainingSession() {
		String title = this.titleField.getText();
		TrainingType type = this.typeComboBox.getValue();
		LocalDate date = this.datePicker.getValue();
		String durationText = this.durationField.getText();
		String intensityText = this.intensityField.getText();
		String notes = this.notesArea.getText();

		if (title.trim().isEmpty()) {
			showFeedback("El título no puede estar vacío.", "#F87171");
			return;
		}

		if (date == null) {
			showFeedback("La fecha no puede estar vacía.", "#F87171");
			return;
		}

		int durationMinutes;
		int intensity;

		try {
			durationMinutes = Integer.parseInt(durationText.trim());
			intensity = Integer.parseInt(intensityText.trim());
		} catch (NumberFormatException e) {
			showFeedback("Duración e intensidad deben ser números.", "#F87171");
			return;
		}

		if (durationMinutes <= 0) {
			showFeedback("La duración debe ser mayor que 0.", "#F87171");
			return;
		}

		if (intensity < 1 || intensity > 5) {
			showFeedback("La intensidad debe estar entre 1 y 5.", "#F87171");
			return;
		}

		TrainingSession session = new TrainingSession(
				title.trim(),
				type,
				date,
				durationMinutes,
				intensity,
				notes.trim()
		);

		this.trainingManager.addSession(session);

		clearForm();
		updateStats();

		showFeedback("Sesión añadida correctamente.", "#22C55E");
	}

	private void clearForm() {
		this.titleField.clear();
		this.durationField.clear();
		this.intensityField.clear();
		this.notesArea.clear();

		this.typeComboBox.setValue(TrainingType.TECNICA);
		this.datePicker.setValue(LocalDate.now());
		this.titleField.requestFocus();
	}

	private void updateStats() {
		this.totalSessionsValueLabel.setText(String.valueOf(this.trainingManager.getTotalSessions()));
		this.totalMinutesValueLabel.setText(String.valueOf(this.trainingManager.getTotalMinutes()));
		this.averageIntensityValueLabel.setText(String.format("%.1f", this.trainingManager.getAverageIntensity()));
	}

	private void showFeedback(String message, String color) {
		this.feedbackLabel.setText(message);
		this.feedbackLabel.setStyle(
				"-fx-text-fill: " + color + ";" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;"
		);
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

	private Label createStatValueLabel(String value) {
		Label valueLabel = new Label(value);
		valueLabel.setStyle(
				"-fx-text-fill: #FFFFFF;" +
				"-fx-font-size: 26px;" +
				"-fx-font-weight: bold;"
		);

		return valueLabel;
	}

	private VBox createStatCard(String title, Label valueLabel) {
		Label titleLabel = new Label(title);
		titleLabel.setStyle(
				"-fx-text-fill: #A1A1AA;" +
				"-fx-font-size: 13px;"
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
		panel.setPrefSize(350, 390);
		panel.setStyle(
				"-fx-background-color: #1E1E24;" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: #33333D;" +
				"-fx-border-radius: 12;"
		);

		return panel;
	}
}