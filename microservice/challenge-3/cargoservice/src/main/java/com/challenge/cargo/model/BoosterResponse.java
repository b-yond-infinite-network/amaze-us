package com.challenge.cargo.model;

public class BoosterResponse {
    private String uuid;
    private String title;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BoosterResponse [uuid=" + uuid + ", title=" + title + "]";
	}
	
}
