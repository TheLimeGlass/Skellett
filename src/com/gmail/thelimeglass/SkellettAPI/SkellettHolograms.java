package com.gmail.thelimeglass.SkellettAPI;

import java.util.HashMap;

import com.gmail.thelimeglass.Main;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.plugin.Plugin;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SkellettHolograms implements Listener {
	private static Main registerclass;
	public static HashMap<Integer, Entity> Holo;
	public static Integer NewId;
	static {
		Holo = new HashMap();
	}
	public SkellettHolograms(Main main) {
		registerclass = main;
	}
	@EventHandler
	public void z(EntityCombustEvent e) {
		if (e.getEntityType() == EntityType.ARMOR_STAND) {
			e.setCancelled(true);
		}
	}
	public static void createHologram(Location loc, String Name, Integer ID, boolean glowing, boolean small) {
		if (!Holo.containsKey(ID)) {
			ArmorStand stand = (ArmorStand)loc.getWorld().spawn(loc.add(0.5, 0.0, 0.5), (Class)ArmorStand.class);
			stand.setVisible(false);
			stand.setCustomName(Main.cc(Name));
			stand.setCustomNameVisible(true);
			stand.setInvulnerable(true);
			stand.setGravity(false);
			stand.setCollidable(false);
			stand.setCanPickupItems(false);
			stand.setGlowing(glowing);
			stand.setSmall(small);
			stand.setGliding(false);
			Holo.put(ID, (Entity)stand);
		} else {
			Bukkit.getConsoleSender().sendMessage(Main.cc(Main.prefix + "&cHologram " + ID + " already exists!"));
		}
	}
	public static void createTempHologram(Location loc, String name, Integer ID, Integer time, boolean glowing, boolean small, boolean firework, Color color1, Color color2, Color color3) {
		if (!Holo.containsKey(ID)) {
			SkellettHolograms.createHologram(loc, name, ID, glowing, small);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)registerclass, new Runnable(){
				public void run() {
					SkellettHolograms.removeHologram(time);
				}
			}, (long)(time * 20));
		} else {
			int NewID = 0;
			while(Holo.containsKey(NewID)) {
				NewID++;
			}
			SkellettHolograms.createHologram(loc, name, NewId, glowing, small);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)registerclass, new Runnable(){
				public void run() {
					SkellettHolograms.removeHologram(SkellettHolograms.NewId);
				}
			}, (long)(time * 20));
		}
	}
	public static void removeHologram(Integer ID) {
		if (Holo.containsKey(ID)) {
			Holo.get(ID).remove();
			Holo.remove(ID);
		} else {
			return;
		}
	}
	public static void teleportHologram(Integer ID, Location loc) {
		if (Holo.containsKey(ID)) {
			Holo.get(ID).teleport(loc.add(0.5, 0.0, 0.5));
		} else {
			return;
		}
	}
	public static String getHologramName(Integer ID) {
		if (Holo.containsKey(ID)) {
			return Holo.get(ID).getCustomName();
		} else {
			return null;
		}
	}
	public static Location getHologramLocation(Integer ID) {
		if (Holo.containsKey(ID)) {
			return Holo.get(ID).getLocation();
		} else {
			return null;
		}
	}
	public static void renameHologram(Integer ID, String Name) {
		if (Holo.containsKey(ID)) {
			Holo.get(ID).setCustomName(Main.cc(Name));
			Holo.get(ID).setCustomNameVisible(true);
		} else {
			return;
		}
	}
}
