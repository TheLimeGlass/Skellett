package com.gmail.thelimeglass.Corpse;

import java.util.HashMap;

import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

public class CorpseManager {
	private static final HashMap<String, CorpseData> corpseStorage = new HashMap<>();
	
	public static void addCorpse(String ID, CorpseData corpse) {
		corpseStorage.put(ID, corpse);
	}
	
	public static void removeCorpse(String ID) {
		if (corpseStorage.containsKey(ID)) {
			CorpseAPI.removeCorpse(corpseStorage.get(ID));
			corpseStorage.remove(ID);
		}
	}
	
	public static void removeCorpse(CorpseData corpse) {
		CorpseAPI.removeCorpse(corpse);
		for (final String s : corpseStorage.keySet()) {
			if (corpseStorage.get(s) == corpse) {
				corpseStorage.remove(s);
			}
		}
	}
	
	public static boolean contains(String ID) {
		if (corpseStorage.containsKey(ID)) {
			return true;
		}
		return false;
	}
	
	public static CorpseData get(String ID) {
		if (corpseStorage.containsKey(ID)) {
			return corpseStorage.get(ID);
		}
		return null;
	}
	
	public static void unregisterAll() {
		for (final String s : corpseStorage.keySet()) {
			removeCorpse(s);
		}
	}
	
	public static CorpseData[] getAll() {
		return corpseStorage.values().toArray(new CorpseData[corpseStorage.values().size()]);
	}
}