package com.gmail.thelimeglass.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil {
	
	private static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
	}
	
	public static Class<?> getNMSClass(String classString) throws ClassNotFoundException {
		String name = "net.minecraft.server." + getVersion() + classString;
		Class<?> nmsClass = Class.forName(name);
		return nmsClass;
	}
	
	public static Class<?> getOBCClass(String classString) {
		String name = "org.bukkit.craftbukkit." + getVersion() + classString;
		@SuppressWarnings("rawtypes")
		Class obcClass = null;
		try {
			obcClass = Class.forName(name);
		}
		catch (ClassNotFoundException error) {
			error.printStackTrace();
			return null;
		}
		return obcClass;
	}
	
	public static Object getField(Class<?> classString, String fieldString, Object object) {
		try {
			Field f = classString.getDeclaredField(fieldString);
			f.setAccessible(true);
			return f.get(object);
		}
		catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	public static Object getField(Class<?> clazz, String field) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			return f;
		}
		catch (NoSuchFieldException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	public static void setField(Class<?> classString, String fieldString, Object object, Object newObject) {
		try {
			Field f = classString.getDeclaredField(fieldString);
			f.setAccessible(true);
			f.set(object, newObject);
		}
		catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException error) {
			error.printStackTrace();
		}
	}
	
	public static Object getConnection(Player player) throws SecurityException, NoSuchMethodException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method getHandle = player.getClass().getMethod("getHandle");
		Object nmsPlayer = getHandle.invoke(player);
		Field connectionField = nmsPlayer.getClass().getField("playerConnection");
		return connectionField.get(nmsPlayer);
	}
	
	public static void sendPacket(Player player, Object object) throws NoSuchMethodException {
		Method method = null;
		try {
			method = getConnection(player).getClass().getMethod("sendPacket", getNMSClass("Packet"));
			method.invoke(getConnection(player), object);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Method getMethod(Class<?> clazz, String methodString, Class<?>... parameters) {
		try {
			Method method = clazz.getMethod(methodString, parameters);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Method getMethod(Class<?> clazz, String methodString) {
		try {
			Method method = clazz.getMethod(methodString);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
