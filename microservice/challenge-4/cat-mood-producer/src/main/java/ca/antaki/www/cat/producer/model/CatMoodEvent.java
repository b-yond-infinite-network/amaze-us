package ca.antaki.www.cat.producer.model;

public final class CatMoodEvent {

	private final Cat cat;
	private final long time;
	
	public CatMoodEvent(Cat cat) {
		this.cat = cat;
		this.time = System.nanoTime();
	}
	
	public Cat getCat() {
		return cat;
	}
	
	public long getTime() {
		return time;
	}
	
}
