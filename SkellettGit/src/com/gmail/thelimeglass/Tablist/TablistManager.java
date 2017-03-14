package com.gmail.thelimeglass.Tablist;

import java.util.ArrayList;
import java.util.HashMap;

import org.inventivetalent.tabapi.TabItem;

public class TablistManager {
	private static final HashMap<String, TabItem> tabItems = new HashMap<>();
	
	public static void addTabItem(String ID, TabItem tabitem) {
		tabItems.put(ID, tabitem);
	}
	
	public static void removeTabItem(String ID, TabItem tabitem) {
		tabItems.remove(ID, tabitem);
	}
	
	public static void removeTabItem(String ID) {
		if (tabItems.containsKey(ID)) {
	        tabItems.remove(ID);
	    }
	}
	
	public static boolean containsTabItem(String ID) {
		if (tabItems.containsKey(ID)) {
	        return true;
	    }
		return false;
	}
	public static boolean containsTabItem(TabItem item) {
		if (tabItems.containsValue(item)) {
	        return true;
	    }
		return false;
	}
	
	public static TabItem get(String ID) {
		if (tabItems.containsKey(ID)) {
			return tabItems.get(ID);
	    }
		return null;
	}
	
	public static TabItem get(TabItem item) {
		if (tabItems.containsValue(item)) {
			return tabItems.get(item);
	    }
		return null;
	}
	
	public static String[] getAll() {
		ArrayList<String> ids = new ArrayList<>();
		for (final String s : tabItems.keySet()) {
			ids.add(s);
		}
		return ids.toArray(new String[ids.size()]);
	}
}