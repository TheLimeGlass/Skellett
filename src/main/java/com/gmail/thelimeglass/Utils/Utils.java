package com.gmail.thelimeglass.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.gmail.thelimeglass.Skellett;

public class Utils {

	public static Object getEnum(@SuppressWarnings("rawtypes") Class clazz, String object) {
		Object value = null;
		try {
			@SuppressWarnings("unchecked")
			final Method method = clazz.getMethod("valueOf", String.class);
			method.setAccessible(true);
			value = method.invoke(clazz, object.replace("\"", "").trim().replace(" ", "_").toUpperCase());
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException error) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown type " + object + " in " + clazz.getName()));
		}
		return value;
	}
	
	public static boolean ofRow(Integer row, Integer slot, Inventory inventory) {
		if (slot != null && row != null) {
			Integer mod = 9;
			if (inventory != null) {
				if (inventory.getType() == InventoryType.DISPENSER ||inventory.getType() == InventoryType.WORKBENCH || inventory.getType() == InventoryType.DROPPER) {
					mod = 3;
				} else if (inventory.getType() == InventoryType.CHEST || inventory.getType() == InventoryType.SHULKER_BOX || inventory.getType() == InventoryType.ENDER_CHEST || inventory.getType() == InventoryType.PLAYER){
					mod = 9;
				} else {
					mod = inventory.getSize();
				}
			}
			Integer calculate = row * mod;
			return slot >= calculate - mod && slot < calculate;
		}
		return false;
	}
	
	private static ArrayList<File> getListFiles(File root, final FilenameFilter filter, ArrayList<File> toAdd) {
		for (File f : root.listFiles()) {
			if (f.isDirectory()) toAdd.addAll(getListFiles(f, filter, toAdd));
			else if (filter.accept(f, f.getName())) toAdd.add(f);
		}
		return toAdd;
	}
	
	public static File[] getFiles(File root, FilenameFilter filter) {
		ArrayList<File> files = getListFiles(root, filter, new ArrayList<File>());
		return files.toArray(new File[files.size()]);
	}
}
