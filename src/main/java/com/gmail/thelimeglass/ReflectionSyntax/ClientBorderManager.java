package com.gmail.thelimeglass.ReflectionSyntax;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.ReflectionUtil;

public class ClientBorderManager {
	
	private static HashMap<Player, Double> WorldborderSize = new HashMap<>();
	private static HashMap<Player, Double> WorldborderDamageBuffer = new HashMap<>();
	private static HashMap<Player, Double> WorldborderDamage = new HashMap<>();
	private static HashMap<Player, Location> WorldborderCenter = new HashMap<>();
	private static HashMap<Player, Integer> WorldborderWarning = new HashMap<>();
	private static HashMap<Player, Integer> WorldborderWarningDistance = new HashMap<>();
	
	/**
	 * @author LimeGlass https://github.com/TheLimeGlass
	 */
	
	private static Boolean normal() {
		if (Skellett.instance.getConfig().getBoolean("NormalBorders", false)) {
			return true;
		}
		return false;
	}
	
	public static Object getWorldBorder(Player player, boolean createNew) {
		try {
			if (createNew) {
				return ReflectionUtil.getNMSClass("WorldBorder").getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
			} else {
				Object nmsPlayer = ReflectionUtil.getHandle(player);
				Object world = nmsPlayer.getClass().getField("world").get(nmsPlayer);
				return world.getClass().getMethod("getWorldBorder").invoke(world);
			}
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getBorderPacket(Player player, String borderAction, Object border) {
		try {
			Class<Enum> enumWorldBorderAction = (Class<Enum>) ReflectionUtil.getNMSClass("PacketPlayOutWorldBorder$EnumWorldBorderAction");
			Enum enumType = Enum.valueOf(enumWorldBorderAction, borderAction);
			Class<?> borderPacket = ReflectionUtil.getNMSClass("PacketPlayOutWorldBorder");
			Object packet = borderPacket.getConstructor(ReflectionUtil.getNMSClass("WorldBorder"), enumWorldBorderAction).newInstance(border, enumType);
			return packet;
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setSize(Player player, Double size) {
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setSize", Double.TYPE);
			sizeMethod.invoke(border, size);
			Object packet = getBorderPacket(player, "SET_SIZE", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderSize.containsKey(player)) {
					WorldborderSize.remove(player);
				}
				WorldborderSize.put(player, size);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Double getSize(Player player) {
		if (WorldborderSize.containsKey(player)) {
			return WorldborderSize.get(player);
		}
		return null;
	}
	
	public static void setCenter(Player player, Location location) {
		Double x = location.getX();
		Double z = location.getZ();
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setCenter", Double.TYPE, Double.TYPE);
			sizeMethod.invoke(border, x, z);
			Object packet = getBorderPacket(player, "SET_CENTER", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderCenter.containsKey(player)) {
					WorldborderCenter.remove(player);
				}
				WorldborderCenter.put(player, location);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Location getCenter(Player player) {
		if (WorldborderCenter.containsKey(player)) {
			return WorldborderCenter.get(player);
		}
		return null;
	}
	
	public static void setDamageBuffer(Player player, Double value) {
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setDamageBuffer", Double.TYPE);
			sizeMethod.invoke(border, value);
			Object packet = getBorderPacket(player, "INITIALIZE", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderDamageBuffer.containsKey(player)) {
					WorldborderDamageBuffer.remove(player);
				}
				WorldborderDamageBuffer.put(player, value);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Double getDamageBuffer(Player player) {
		if (WorldborderDamageBuffer.containsKey(player)) {
			return WorldborderDamageBuffer.get(player);
		}
		return null;
	}
	
	public static void setDamageAmount(Player player, Double value) {
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setDamageAmount", Double.TYPE);
			sizeMethod.invoke(border, value);
			Object packet = getBorderPacket(player, "INITIALIZE", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderDamage.containsKey(player)) {
					WorldborderDamage.remove(player);
				}
				WorldborderDamage.put(player, value);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Double getDamageAmount(Player player) {
		if (WorldborderDamage.containsKey(player)) {
			return WorldborderDamage.get(player);
		}
		return null;
	}
	
	public static void setWarningTime(Player player, Integer value) {
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setWarningTime", Integer.TYPE);
			sizeMethod.invoke(border, value);
			Object packet = getBorderPacket(player, "SET_WARNING_TIME", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderWarning.containsKey(player)) {
					WorldborderWarning.remove(player);
				}
				WorldborderWarning.put(player, value);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer getWarningTime(Player player) {
		if (WorldborderWarning.containsKey(player)) {
			return WorldborderWarning.get(player);
		}
		return null;
	}
	
	public static void setWarningDistance(Player player, Integer value) {
		try {
			Object border = getWorldBorder(player, !normal());
			Method sizeMethod = ReflectionUtil.getNMSClass("WorldBorder").getMethod("setWarningDistance", Integer.TYPE);
			sizeMethod.invoke(border, value);
			Object packet = getBorderPacket(player, "SET_WARNING_BLOCKS", border);
			ReflectionUtil.sendPacket(player, packet);
			if (Skellett.instance.getConfig().getBoolean("ClientWorldBordersSave", true)) {
				if (WorldborderWarningDistance.containsKey(player)) {
					WorldborderWarningDistance.remove(player);
				}
				WorldborderWarningDistance.put(player, value);
			}
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer getWarningDistance(Player player) {
		if (WorldborderWarningDistance.containsKey(player)) {
			return WorldborderWarningDistance.get(player);
		}
		return null;
	}
}