package com.challenge.booster.model;

/**
 * Contains booster fields in response
 *
 */
public class BoosterResponse {
    private String uuid;
    private String title;

    public BoosterResponse(String uuid, String title) {
        this.uuid = uuid;
        this.title = title;
    }
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

	@Override
	public String toString() {
		return "BoosterResponse [uuid=" + uuid + ", title=" + title + "]";
	}
}
