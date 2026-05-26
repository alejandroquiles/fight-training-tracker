package com.alejandroquiles.fighttracker.model;

public enum TrainingType {
	FUERZA("Fuerza"),
	CARDIO("Cardio"),
	TECNICA("Técnica"),
	SPARRING("Sparring"),
	MOVILIDAD("Movilidad"),
	OTRO("Otro");

	private String displayName;

	TrainingType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	@Override
	public String toString() {
		return this.displayName;
	}
}