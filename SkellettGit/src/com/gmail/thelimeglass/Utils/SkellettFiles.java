package com.gmail.thelimeglass.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import com.gmail.thelimeglass.Skellett;
import com.google.common.io.Files;

public class SkellettFiles {
	public static String getString(String fileLoc, String tag, boolean color) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		if (configuration.get(tag) != null) {
			if (color) {
				return Skellett.cc(configuration.getString(tag));
			} else {
				return configuration.getString(tag);
			}
		} else {
			return tag;
		}
	}
	public static void set(String fileLoc, String tag, Object data) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		configuration.set(tag, data);
		try {
			configuration.save(f);
		}
		catch (IOException error) {
			error.printStackTrace();
		}
	}
	public static List<String> getConfigurationList(String fileLoc, String tag) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		if (configuration.get(tag) != null) {
			return configuration.getStringList(tag);
		} else {
			return null;
		}
	}
	public static void createFile(String file) {
		File f = new File(file);
		if (f.exists()) {
			return;
		}
		try {
			f.mkdirs();
			f.createNewFile();
		}
		catch (IOException error) {
			error.printStackTrace();
		}	
	}
	
	public static void moveFile(String file, String fileto) {
		File f = new File(file);
		File f2 = new File(fileto);
		f2.mkdirs();
		if (!f.exists() || !f2.exists()) return;
		try {
			if (f.isDirectory()) {
				FileUtils.moveDirectory(f, f2);
			} else {
				Files.move(f, f2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(String file, String fileto) {
		File f = new File(file);
		File f2 = new File(fileto);
		f2.mkdirs();
		if (!f.exists() || !f2.exists()) return;
		try {
			if (f.isDirectory()) {
				FileUtils.copyDirectory(f, f2);
			} else {
				Files.copy(f, f2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(String file) {
		File f = new File(file);
		if (!f.exists()) {
			return;
		}
		if (f.isDirectory()) {
			try {
				FileUtils.deleteDirectory(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		f.delete();
	}
	
	public static void unzip(String zipLoc, String fileLoc) throws IOException {
		ZipEntry entry;
		File f1 = new File(fileLoc);
		File f2 = new File(zipLoc);
		ZipInputStream in = new ZipInputStream(new FileInputStream(f1));
		while ((entry = in.getNextEntry()) != null) {
			String name = entry.getName();
			File output = new File(f2, name);
			if (!entry.isDirectory()) {
				int byteRead;
				createFileAndPath(output);
				FileOutputStream out = new FileOutputStream(output);
				byte[] buffer = new byte[4096];
				while ((byteRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, byteRead);
				}
				out.flush();
				out.close();
				continue;
			}
			output.mkdirs();
		}
		in.close();
	}
	@SuppressWarnings("unused")
	public static void createFileAndPath(File file) throws IOException {
		if (!file.exists()) {
			String folderPath;
			File folder;
			String filePath = file.getPath();
			int index = filePath.lastIndexOf(File.separator);
			if (index >= 0 && !(folder = new File(folderPath = filePath.substring(0, index))).exists()) {
				folder.mkdirs();
			}
			file.createNewFile();
		}
	}
	public static boolean getBoolean(String fileLoc, String tag) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		return configuration.get(tag) != null && configuration.getBoolean(tag);
	}
	public static boolean isSet(String fileLoc, String tag) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		return configuration.isSet(tag);
	}
	public static ConfigurationSection getSection(String fileLoc, String tag) {
		File f = new File(fileLoc);
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration((File)f);
		return configuration.getConfigurationSection(tag);
	}
}