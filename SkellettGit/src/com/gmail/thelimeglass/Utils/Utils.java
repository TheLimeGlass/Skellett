package com.gmail.thelimeglass.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Bukkit;
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
