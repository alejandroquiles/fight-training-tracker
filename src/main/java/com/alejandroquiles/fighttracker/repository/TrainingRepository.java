package com.alejandroquiles.fighttracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.alejandroquiles.fighttracker.model.TrainingSession;
import com.alejandroquiles.fighttracker.model.TrainingType;

public class TrainingRepository {

	private static final Path FILE_PATH = Path.of("training_sessions.csv");

	public void saveSessions(ArrayList<TrainingSession> sessions) {
		ArrayList<String> lines = new ArrayList<>();

		for (TrainingSession session : sessions) {
			lines.add(toCsvLine(session));
		}

		try {
			Files.write(
					FILE_PATH,
					lines,
					StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING
			);
		} catch (IOException e) {
			System.out.println("Error al guardar entrenamientos: " + e.getMessage());
		}
	}

	public ArrayList<TrainingSession> loadSessions() {
		ArrayList<TrainingSession> sessions = new ArrayList<>();

		if (!Files.exists(FILE_PATH)) {
			return sessions;
		}

		try {
			List<String> lines = Files.readAllLines(FILE_PATH);

			for (String line : lines) {
				if (!line.trim().isEmpty()) {
					TrainingSession session = fromCsvLine(line);
					sessions.add(session);
				}
			}
		} catch (IOException e) {
			System.out.println("Error al cargar entrenamientos: " + e.getMessage());
		}

		return sessions;
	}

	private String toCsvLine(TrainingSession session) {
		return escape(session.getTitle()) + "," +
				session.getType().name() + "," +
				session.getDate() + "," +
				session.getDurationMinutes() + "," +
				session.getIntensity() + "," +
				escape(session.getNotes());
	}

	private TrainingSession fromCsvLine(String line) {
		String[] parts = line.split(",", -1);

		String title = unescape(parts[0]);
		TrainingType type = TrainingType.valueOf(parts[1]);
		LocalDate date = LocalDate.parse(parts[2]);
		int durationMinutes = Integer.parseInt(parts[3]);
		int intensity = Integer.parseInt(parts[4]);
		String notes = unescape(parts[5]);

		return new TrainingSession(title, type, date, durationMinutes, intensity, notes);
	}

	private String escape(String text) {
		return text.replace(",", ";");
	}

	private String unescape(String text) {
		return text;
	}
}