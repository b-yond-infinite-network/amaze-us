package ca.antaki.www.cat.producer.model;

public class Cat {

	private final String name;
	
	//using a byte (primitive type) in order to save space if we want to have millions of Cats
	private byte mood = CatMood.MIAW.getId();

	public Cat(String name) {
		super();
		this.name = name;
	}
	
	public int getMood() {
		return mood;
	}
	
	public void setMood(byte mood) {
		this.mood = mood;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(name).append(" ").append(mood).toString();
	}

	@Override
	public int hashCode() { //I could use HashCodeBuilder but this is faster
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { //I could use EqualsBuilder but this is faster
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cat other = (Cat) obj;
		return name.equals(other.name);
	}
	
	
}
