package com.alejandroquiles.fighttracker.app;

import com.alejandroquiles.fighttracker.ui.MainView;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

	@Override
	public void start(Stage stage) {
		MainView mainView = new MainView();
		Parent root = mainView.createView();

		Scene scene = new Scene(root, 1100, 720);

		stage.setTitle("Fight Training Tracker");
		stage.setScene(scene);
		stage.setMinWidth(1000);
		stage.setMinHeight(650);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}