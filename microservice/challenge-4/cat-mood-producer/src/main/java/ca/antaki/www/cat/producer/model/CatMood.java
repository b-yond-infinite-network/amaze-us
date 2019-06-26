package ca.antaki.www.cat.producer.model;

import java.util.HashMap;
import java.util.Map;

public enum CatMood {
	
	MIAW("miaw", (byte) 0), 
	GROWL("grr",(byte) 1), 
	HISS("hiss", (byte) 2), 
	PURR("rrrrr", (byte) 3), 
	THROW_GLASS("cling bling",(byte) 4),
	ROLL_ON_FLOOR("fffff", (byte) 5), 
	SCRATCH_CHAIRS("SCRATCHCHAIRS", (byte) 6), 
	LOOK_DEEP_IN_EYES("LOOKDEEPINEYES", (byte) 7);

	private final byte id;
	private static final Map<Byte, CatMood> INT_TO_MOOD_MAP = new HashMap<>();
	public static final int MIN = 0;
	public static final int MAX = 8; //use MAX + 1 to save incrementing counter
	static {
		for(CatMood catMood: CatMood.values()) {
			INT_TO_MOOD_MAP.put(catMood.id, catMood);
		}
	}

	CatMood(String desc, byte id) {
		this.id = id;
		
		
	}
	
	public byte getId() {
		return id;
	}
	
	
	/**
	 * Utility method for O(1) performance which is better than a switch.
	 * @param id
	 * @return the CatMood
	 */
	public static CatMood getById(byte id) {
		return INT_TO_MOOD_MAP.get(id);
	}

}
