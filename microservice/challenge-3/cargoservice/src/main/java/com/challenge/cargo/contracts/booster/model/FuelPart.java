package com.challenge.cargo.contracts.booster.model;

public class FuelPart {
    private String title;
    private String priority;
    private boolean done;
    private String deadline;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "FuelPart{" +
				"title='" + title + '\'' +
				", priority='" + priority + '\'' +
				", done=" + done +
				", deadline=" + deadline +
				'}';
	}
}
