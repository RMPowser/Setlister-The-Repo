package com.example.Setlister.SetlistObjects;

import java.sql.Time;
import java.util.Random;

/* SetlistEntity is just a base class that exists so I can put sets, song entries, and setlist
headers in the same array. */
public class SetlistEntity {
	protected transient String type;
	
	/*identifier is used in the SetlistViewAdapter and the ManagementViewAdapter to return a
	pseudo-unique item id in order for animations to work. The item id is pseudo-unique because
	it is generated randomly. It is in fact possible for two identifiers to be the exact same,
	albeit very unlikely.*/
	private long identifier;
	
	public SetlistEntity(String type){
		this.type = type;
		setIdentifier(new Random().nextLong());
	}
	
	public long getIdentifier() {
		return identifier;
	}
	
	private void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	@Override
	public String toString() {
		return "SetlistEntity{type=" + type + '}';
	}
}
