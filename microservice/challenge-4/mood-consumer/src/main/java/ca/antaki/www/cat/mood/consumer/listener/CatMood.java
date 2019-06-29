package ca.antaki.www.cat.mood.consumer.listener;

public final class CatMood {
	private final String name;
	private final byte mood;
	private final long time;
	
	public CatMood(String name, byte mood, long time) {
		super();
		this.name = name;
		this.mood = mood;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public byte getMood() {
		return mood;
	}
	public long getTime() {
		return time;
	}
	
}