package com.alejandroquiles.fighttracker.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alejandroquiles.fighttracker.model.TrainingSession;
import com.alejandroquiles.fighttracker.model.TrainingType;
import com.alejandroquiles.fighttracker.repository.TrainingRepository;
import com.alejandroquiles.fighttracker.service.TrainingManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView {

	private static final String BACKGROUND = "#0F1014";
	private static final String PANEL = "#1A1B21";
	private static final String PANEL_LIGHT = "#23252D";
	private static final String BORDER = "#343640";
	private static final String TEXT = "#F4F4F5";
	private static final String MUTED = "#A1A1AA";
	private static final String RED = "#EF4444";
	private static final String ORANGE = "#F97316";
	private static final String GREEN = "#22C55E";
	private static final String BLUE = "#3B82F6";
	private static final String PURPLE = "#A855F7";
	private static final String GRAY = "#71717A";

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private TextField titleField;
	private ComboBox<TrainingType> typeComboBox;
	private DatePicker datePicker;
	private TextField durationField;
	private TextField intensityField;
	private TextArea notesArea;
	private Button addSessionButton;

	private TrainingManager trainingManager;
	private TrainingRepository trainingRepository;

	private Label totalSessionsValueLabel;
	private Label totalMinutesValueLabel;
	private Label averageIntensityValueLabel;
	private Label lastSessionValueLabel;
	private Label feedbackLabel;

	private VBox sessionsListBox;
	private ComboBox<String> filterComboBox;

	public Parent createView() {
		this.trainingManager = new TrainingManager();
		this.trainingRepository = new TrainingRepository();

		loadSavedSessions();

		VBox mainContent = new VBox(26);
		mainContent.setPadding(new Insets(32));
		mainContent.setStyle("-fx-background-color: " + BACKGROUND + ";");

		mainContent.getChildren().addAll(
				createHeader(),
				createStatsBox(),
				createMainContent()
		);

		updateStats();
		refreshSessionsList();

		ScrollPane scrollPane = new ScrollPane(mainContent);
		scrollPane.setFitToWidth(true);
		scrollPane.setStyle(
				"-fx-background: " + BACKGROUND + ";" +
				"-fx-background-color: " + BACKGROUND + ";"
		);

		return scrollPane;
	}

	private void loadSavedSessions() {
		for (TrainingSession session : this.trainingRepository.loadSessions()) {
			this.trainingManager.addSession(session);
		}
	}

	private VBox createHeader() {
		Label titleLabel = new Label("Fight Training Tracker");
		titleLabel.setStyle(
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-font-size: 36px;" +
				"-fx-font-weight: bold;"
		);

		Label subtitleLabel = new Label("Registra tus entrenamientos, controla tu carga de trabajo y visualiza tu progreso.");
		subtitleLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 15px;"
		);

		return new VBox(6, titleLabel, subtitleLabel);
	}

	private HBox createStatsBox() {
		this.totalSessionsValueLabel = createStatValueLabel("0");
		this.totalMinutesValueLabel = createStatValueLabel("0");
		this.averageIntensityValueLabel = createStatValueLabel("0.0");
		this.lastSessionValueLabel = createStatValueLabel("-");

		HBox statsBox = new HBox(16);
		statsBox.setAlignment(Pos.CENTER_LEFT);

		statsBox.getChildren().addAll(
				createStatCard("Sesiones", this.totalSessionsValueLabel, BLUE),
				createStatCard("Minutos", this.totalMinutesValueLabel, ORANGE),
				createStatCard("Intensidad media", this.averageIntensityValueLabel, GREEN),
				createStatCard("Último entrenamiento", this.lastSessionValueLabel, RED)
		);

		return statsBox;
	}

	private HBox createMainContent() {
		HBox contentBox = new HBox(22);
		contentBox.setAlignment(Pos.TOP_LEFT);

		VBox formPanel = createFormPanel();
		VBox historyPanel = createHistoryPanel();

		HBox.setHgrow(historyPanel, Priority.ALWAYS);

		contentBox.getChildren().addAll(formPanel, historyPanel);

		return contentBox;
	}

	private VBox createFormPanel() {
		Label titleLabel = new Label("Nuevo entrenamiento");
		titleLabel.setStyle(
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-font-size: 20px;" +
				"-fx-font-weight: bold;"
		);

		Label descriptionLabel = new Label("Añade una sesión a tu historial.");
		descriptionLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 12px;"
		);

		Label autosaveLabel = new Label("Los entrenamientos se guardan automáticamente en archivo local.");
		autosaveLabel.setStyle(
				"-fx-text-fill: #71717A;" +
				"-fx-font-size: 11px;"
		);

		this.titleField = new TextField();
		this.titleField.setPromptText("Ej: Striking y defensa");

		this.typeComboBox = new ComboBox<>();
		this.typeComboBox.getItems().addAll(TrainingType.values());
		this.typeComboBox.setValue(TrainingType.TECNICA);
		this.typeComboBox.setMaxWidth(Double.MAX_VALUE);

		this.datePicker = new DatePicker(LocalDate.now());
		this.datePicker.setMaxWidth(Double.MAX_VALUE);

		this.durationField = new TextField();
		this.durationField.setPromptText("Minutos");

		this.intensityField = new TextField();
		this.intensityField.setPromptText("1 - 5");

		this.notesArea = new TextArea();
		this.notesArea.setPromptText("Notas del entrenamiento...");
		this.notesArea.setPrefRowCount(4);

		styleInput(this.titleField);
		styleInput(this.durationField);
		styleInput(this.intensityField);
		styleTextArea(this.notesArea);

		this.addSessionButton = new Button("Añadir sesión");
		this.addSessionButton.setMaxWidth(Double.MAX_VALUE);
		this.addSessionButton.setStyle(
				"-fx-background-color: " + RED + ";" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 14px;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 10;" +
				"-fx-padding: 12 16;"
		);

		this.feedbackLabel = new Label(" ");
		this.feedbackLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;"
		);

		this.addSessionButton.setOnAction(event -> addTrainingSession());

		HBox durationIntensityBox = new HBox(12);
		durationIntensityBox.getChildren().addAll(
				createFieldGroup("Duración", this.durationField),
				createFieldGroup("Intensidad", this.intensityField)
		);

		VBox formPanel = new VBox(12);
		formPanel.setPadding(new Insets(22));
		formPanel.setPrefWidth(420);
		formPanel.setMinWidth(390);
		formPanel.setStyle(createPanelStyle());

		formPanel.getChildren().addAll(
				titleLabel,
				descriptionLabel,
				autosaveLabel,
				createFieldGroup("Título", this.titleField),
				createFieldGroup("Tipo", this.typeComboBox),
				createFieldGroup("Fecha", this.datePicker),
				durationIntensityBox,
				createFieldGroup("Notas", this.notesArea),
				this.addSessionButton,
				this.feedbackLabel
		);

		return formPanel;
	}

	private VBox createHistoryPanel() {
		Label titleLabel = new Label("Historial");
		titleLabel.setStyle(
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-font-size: 20px;" +
				"-fx-font-weight: bold;"
		);

		Label descriptionLabel = new Label("Últimas sesiones registradas.");
		descriptionLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 12px;"
		);

		VBox titleBox = new VBox(4, titleLabel, descriptionLabel);

		this.filterComboBox = new ComboBox<>();
		this.filterComboBox.getItems().add("Todos");

		for (TrainingType type : TrainingType.values()) {
			this.filterComboBox.getItems().add(type.toString());
		}

		this.filterComboBox.setValue("Todos");
		this.filterComboBox.setPrefWidth(150);
		this.filterComboBox.setOnAction(event -> refreshSessionsList());

		HBox historyHeader = new HBox(16, titleBox, this.filterComboBox);
		historyHeader.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(titleBox, Priority.ALWAYS);

		this.sessionsListBox = new VBox(12);

		ScrollPane scrollPane = new ScrollPane(this.sessionsListBox);
		scrollPane.setFitToWidth(true);
		scrollPane.setStyle(
				"-fx-background: transparent;" +
				"-fx-background-color: transparent;"
		);

		VBox historyPanel = new VBox(12, historyHeader, scrollPane);
		historyPanel.setPadding(new Insets(22));
		historyPanel.setPrefWidth(650);
		historyPanel.setStyle(createPanelStyle());

		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		return historyPanel;
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
		this.trainingRepository.saveSessions(this.trainingManager.getSessions());

		clearForm();
		updateStats();
		refreshSessionsList();

		showFeedback("Sesión añadida y guardada correctamente.", "#22C55E");
	}

	private void deleteTrainingSession(int index) {
		boolean removed = this.trainingManager.removeSession(index);

		if (removed) {
			this.trainingRepository.saveSessions(this.trainingManager.getSessions());
			updateStats();
			refreshSessionsList();
			showFeedback("Sesión eliminada correctamente.", "#22C55E");
		} else {
			showFeedback("No se pudo eliminar la sesión.", "#F87171");
		}
	}

	private void refreshSessionsList() {
		this.sessionsListBox.getChildren().clear();

		if (this.trainingManager.getSessions().isEmpty()) {
			Label emptyLabel = new Label("Todavía no hay entrenamientos registrados.");
			emptyLabel.setStyle(
					"-fx-text-fill: #71717A;" +
					"-fx-font-size: 13px;"
			);

			this.sessionsListBox.getChildren().add(emptyLabel);
			return;
		}

		boolean hasVisibleSessions = false;
		String selectedFilter = this.filterComboBox.getValue();

		for (int i = 0; i < this.trainingManager.getSessions().size(); i++) {
			TrainingSession session = this.trainingManager.getSessions().get(i);

			if (shouldShowSession(session, selectedFilter)) {
				this.sessionsListBox.getChildren().add(createSessionCard(session, i));
				hasVisibleSessions = true;
			}
		}

		if (!hasVisibleSessions) {
			Label emptyFilterLabel = new Label("No hay entrenamientos para este filtro.");
			emptyFilterLabel.setStyle(
					"-fx-text-fill: #71717A;" +
					"-fx-font-size: 13px;"
			);

			this.sessionsListBox.getChildren().add(emptyFilterLabel);
		}
	}

	private boolean shouldShowSession(TrainingSession session, String selectedFilter) {
		if (selectedFilter == null || selectedFilter.equals("Todos")) {
			return true;
		}

		return session.getType().toString().equals(selectedFilter);
	}

	private VBox createSessionCard(TrainingSession session, int index) {
		String typeColor = getTypeColor(session.getType());

		Label typeLabel = new Label(session.getType().toString());
		typeLabel.setStyle(
				"-fx-text-fill: white;" +
				"-fx-background-color: " + typeColor + ";" +
				"-fx-font-size: 11px;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 999;" +
				"-fx-padding: 4 9;"
		);

		Label titleLabel = new Label(session.getTitle());
		titleLabel.setStyle(
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-font-size: 15px;" +
				"-fx-font-weight: bold;"
		);

		HBox leftHeaderBox = new HBox(8, typeLabel, titleLabel);
		leftHeaderBox.setAlignment(Pos.CENTER_LEFT);

		Button deleteButton = new Button("Eliminar");
		deleteButton.setStyle(
				"-fx-background-color: transparent;" +
				"-fx-text-fill: #F87171;" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;" +
				"-fx-border-color: #7F1D1D;" +
				"-fx-border-radius: 8;" +
				"-fx-background-radius: 8;" +
				"-fx-padding: 5 10;"
		);
		deleteButton.setOnAction(event -> deleteTrainingSession(index));

		HBox headerBox = new HBox(12, leftHeaderBox, deleteButton);
		headerBox.setAlignment(Pos.CENTER_LEFT);
		HBox.setHgrow(leftHeaderBox, Priority.ALWAYS);

		Label detailsLabel = new Label(
				session.getDurationMinutes() + " min · " +
				"Intensidad " + session.getIntensity() + "/5 · " +
				session.getDate().format(DATE_FORMATTER)
		);
		detailsLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 12px;"
		);

		VBox card;

		if (session.getNotes().isEmpty()) {
			card = new VBox(7, headerBox, detailsLabel);
		} else {
			Label notesLabel = new Label(session.getNotes());
			notesLabel.setWrapText(true);
			notesLabel.setStyle(
					"-fx-text-fill: #D4D4D8;" +
					"-fx-font-size: 12px;"
			);

			card = new VBox(7, headerBox, detailsLabel, notesLabel);
		}

		card.setPadding(new Insets(14));
		card.setStyle(
				"-fx-background-color: " + PANEL_LIGHT + ";" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: " + typeColor + ";" +
				"-fx-border-width: 0 0 0 4;" +
				"-fx-border-radius: 12;"
		);

		return card;
	}

	private String getTypeColor(TrainingType type) {
		switch (type) {
			case FUERZA:
				return RED;
			case CARDIO:
				return ORANGE;
			case TECNICA:
				return BLUE;
			case SPARRING:
				return PURPLE;
			case MOVILIDAD:
				return GREEN;
			case OTRO:
			default:
				return GRAY;
		}
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

		TrainingSession lastSession = this.trainingManager.getLastSession();

		if (lastSession == null) {
			this.lastSessionValueLabel.setText("-");
		} else {
			this.lastSessionValueLabel.setText(lastSession.getType().toString());
		}
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
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 12px;" +
				"-fx-font-weight: bold;"
		);

		VBox group = new VBox(5, label, field);
		HBox.setHgrow(group, Priority.ALWAYS);

		return group;
	}

	private Label createStatValueLabel(String value) {
		Label valueLabel = new Label(value);
		valueLabel.setStyle(
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-font-size: 28px;" +
				"-fx-font-weight: bold;"
		);

		return valueLabel;
	}

	private VBox createStatCard(String title, Label valueLabel, String accentColor) {
		Label titleLabel = new Label(title);
		titleLabel.setStyle(
				"-fx-text-fill: " + MUTED + ";" +
				"-fx-font-size: 13px;"
		);

		VBox card = new VBox(8, titleLabel, valueLabel);
		card.setPadding(new Insets(18));
		card.setPrefWidth(250);
		card.setStyle(
				"-fx-background-color: " + PANEL + ";" +
				"-fx-background-radius: 14;" +
				"-fx-border-color: " + accentColor + ";" +
				"-fx-border-width: 0 0 0 4;" +
				"-fx-border-radius: 14;"
		);

		return card;
	}

	private void styleInput(TextField field) {
		field.setStyle(
				"-fx-background-color: " + PANEL_LIGHT + ";" +
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-prompt-text-fill: #71717A;" +
				"-fx-background-radius: 8;" +
				"-fx-border-color: " + BORDER + ";" +
				"-fx-border-radius: 8;" +
				"-fx-padding: 8 10;"
		);
	}

	private void styleTextArea(TextArea area) {
		area.setStyle(
				"-fx-control-inner-background: " + PANEL_LIGHT + ";" +
				"-fx-text-fill: " + TEXT + ";" +
				"-fx-prompt-text-fill: #71717A;" +
				"-fx-background-radius: 8;" +
				"-fx-border-color: " + BORDER + ";" +
				"-fx-border-radius: 8;" +
				"-fx-padding: 8 10;"
		);
	}

	private String createPanelStyle() {
		return "-fx-background-color: " + PANEL + ";" +
				"-fx-background-radius: 16;" +
				"-fx-border-color: " + BORDER + ";" +
				"-fx-border-radius: 16;";
	}
}