package com.challenge.cargo.model;

public class CreateTankRequest {
    private String title;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "CreateTankRequest [title=" + title + "]";
	}
	
	
}
