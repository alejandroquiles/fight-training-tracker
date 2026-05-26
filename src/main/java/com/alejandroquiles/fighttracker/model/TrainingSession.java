package com.alejandroquiles.fighttracker.model;

import java.time.LocalDate;

public class TrainingSession {

	private String title;
	private TrainingType type;
	private LocalDate date;
	private int durationMinutes;
	private int intensity;
	private String notes;

	public TrainingSession(String title, TrainingType type, LocalDate date, int durationMinutes, int intensity, String notes) {
		this.title = title;
		this.type = type;
		this.date = date;
		this.durationMinutes = durationMinutes;
		this.intensity = intensity;
		this.notes = notes;
	}

	public String getTitle() {
		return this.title;
	}

	public TrainingType getType() {
		return this.type;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public int getDurationMinutes() {
		return this.durationMinutes;
	}

	public int getIntensity() {
		return this.intensity;
	}

	public String getNotes() {
		return this.notes;
	}
}