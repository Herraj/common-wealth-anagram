package common_wealth_assignment;

public class WordEntry {
	
	// attributes
	private String key;
	private int value;
	
	// default constructor
	public WordEntry(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	// getter methods
	public String getKey() {
		return key;
	}
	
	public int getValue() {
		return value;
	}
	
	// setter methods
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return("Word: " + key + ", Score: " + value);
	}

}
