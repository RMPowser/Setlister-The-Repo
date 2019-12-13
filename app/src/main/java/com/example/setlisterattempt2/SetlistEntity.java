package com.example.setlisterattempt2;

// SetlistEntity is just a base class that exists so I can put sets, song entries, and setlist
// headers in the same array.
public class SetlistEntity {
	protected transient String type;
	
	public SetlistEntity(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "SetlistEntity{type=" + type + '}';
	}
}
