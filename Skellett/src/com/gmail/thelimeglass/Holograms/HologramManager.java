package com.gmail.thelimeglass.Holograms;

import java.util.HashMap;

import de.inventivegames.hologram.Hologram;

public class HologramManager {
	
	/*
	[skellett] (create|spawn|summon|place) holo[gram] at [location] %location% (with|and) [(text|string)] %string% (with|and) id %integer% [[set] glow[ing]] %-boolean% [[is] small] %-boolean%
	[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%
	[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%
	[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%
	*/
	
	private static final HashMap<String, Hologram> hologramData = new HashMap<>();
	
	public static void add(String ID, Hologram holo) {
		hologramData.put(ID, holo);
	}
	
	public static void remove(String ID) {
		if (hologramData.containsKey(ID)) {
			hologramData.remove(ID);
		}
	}
	
	public static Boolean contains(String ID) {
		if (hologramData.containsKey(ID)) {
			return true;
		}
		return false;
	}
	
	public static Hologram get(String ID) {
		if (hologramData.containsKey(ID)) {
			return hologramData.get(ID);
	    }
		return null;
	}
}