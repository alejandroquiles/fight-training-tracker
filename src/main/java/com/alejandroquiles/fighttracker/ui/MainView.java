package com.alejandroquiles.fighttracker.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView {

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
				createPanel("Nuevo entrenamiento"),
				createPanel("Historial")
		);

		mainContent.getChildren().addAll(headerBox, statsBox, contentBox);
		root.setCenter(mainContent);

		return root;
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
		panel.setPrefSize(350, 280);
		panel.setStyle(
				"-fx-background-color: #1E1E24;" +
				"-fx-background-radius: 12;" +
				"-fx-border-color: #33333D;" +
				"-fx-border-radius: 12;"
		);

		return panel;
	}
}