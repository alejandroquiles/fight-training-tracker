package com.alejandroquiles.fighttracker.service;

import java.util.ArrayList;
import java.util.Comparator;

import com.alejandroquiles.fighttracker.model.TrainingSession;

public class TrainingManager {

	private ArrayList<TrainingSession> sessions;

	public TrainingManager() {
		this.sessions = new ArrayList<>();
	}

	public void addSession(TrainingSession session) {
		this.sessions.add(session);
		sortSessionsByDateDesc();
	}

	public ArrayList<TrainingSession> getSessions() {
		return this.sessions;
	}

	public boolean removeSession(int index) {
		if (index < 0 || index >= this.sessions.size()) {
			return false;
		}

		this.sessions.remove(index);
		return true;
	}

	public int getTotalSessions() {
		return this.sessions.size();
	}

	public int getTotalMinutes() {
		int totalMinutes = 0;

		for (TrainingSession session : this.sessions) {
			totalMinutes += session.getDurationMinutes();
		}

		return totalMinutes;
	}

	public double getAverageIntensity() {
		if (this.sessions.isEmpty()) {
			return 0;
		}

		int totalIntensity = 0;

		for (TrainingSession session : this.sessions) {
			totalIntensity += session.getIntensity();
		}

		return (double) totalIntensity / this.sessions.size();
	}

	public TrainingSession getLastSession() {
		if (this.sessions.isEmpty()) {
			return null;
		}

		return this.sessions.get(0);
	}

	private void sortSessionsByDateDesc() {
		this.sessions.sort(Comparator.comparing(TrainingSession::getDate).reversed());
	}
}