package com.gmail.thelimeglass.Regenerator;

import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.material.MaterialData;

public class Regenerator implements Serializable {

	private static final long serialVersionUID = -6563294985070311493L;
	private Location pos1, pos2;
	private String ID;
	private HashMap<Location, MaterialData> hashy;
	
	public Regenerator(Location pos1, Location pos2, String ID, HashMap<Location, MaterialData> hashy) {
		this.pos1 = pos1;
		this.pos2 = pos2;
		this.ID = ID;
		this.hashy = hashy;
	}

	public Location getPos1() {
		return this.pos1;
	}
	
	public Location getPos2() {
		return this.pos2;
	}
	
	public String getID() {
		return this.ID;
	}

	public HashMap<Location, MaterialData> getHashmap() {
		return this.hashy;
	}
}