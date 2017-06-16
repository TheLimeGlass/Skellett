package com.gmail.thelimeglass.Regenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.boydti.fawe.util.TaskManager;
import com.gmail.thelimeglass.Skellett;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;

public class RegeneratorManager {
	
	//TODO: tool: regenerate over a timespan
	//TODO: expr: Change specified blocks within the regenerator
	//TODO: expr: Change all blocks within the regenerator
	//TODO: exrp: last modified block in regenerator + entity who edited
	//TODO: cond: regenerator contains block
	//TODO: cond: regenerator contains location (SkQuery has dis)
	//TODO: cond: regenerator has unchanged block values
	//TODO: cond/expr: regenerator at location
	//TODO: tool: a way to set percents of blocks like a prison mine
	
	private static final HashMap<String, Regenerator> regenerators = new HashMap<>();
	
	public static void addRegenerator(String ID, Location pos1, Location pos2) {
		if (!regenerators.containsKey(ID)) {
			HashMap<Location, MaterialData> hashy = new HashMap<>();
			if (Skellett.instance.getConfig().getBoolean("Async", false)) {
				TaskManager.IMP.async(new Runnable() {
				    @Override
				    public void run() {
				    	for (Location loc : cuboid(pos1, pos2)) {
							hashy.put(loc, loc.getBlock().getState().getData());
						}
				    }
				});
			} else {
				for (Location loc : cuboid(pos1, pos2)) {
					hashy.put(loc, loc.getBlock().getState().getData());
				}
			}
			regenerators.put(ID, new Regenerator(pos1, pos2, ID, hashy));
		}
	}
	
	public static void reconfigure(String ID, Location pos1, Location pos2) {
		if (regenerators.containsKey(ID)) {
			removeRegenerator(ID, true);
		}
		HashMap<Location, MaterialData> hashy = new HashMap<>();
		for (Location loc : cuboid(pos1, pos2)) {
			hashy.put(loc, loc.getBlock().getState().getData());
		}
		regenerators.put(ID, new Regenerator(pos1, pos2, ID, hashy));
	}
	
	public static void removeRegenerator(String ID, Boolean regenerate) {
		if (regenerators.containsKey(ID)) {
			if (regenerate) {
				regenerate(ID);
			}
			regenerators.remove(ID);
		}
	}
	
	public static void regenerate(String ID) {
		if (regenerators.containsKey(ID)) {
			HashMap<Location, MaterialData> hashy = regenerators.get(ID).getHashmap();
			if (Skellett.instance.getConfig().getBoolean("Async", false)) {
				List<Location> locs = new ArrayList<Location>(hashy.keySet());
				String world = locs.get(0).getWorld().getName();
				EditSession editSession = new EditSessionBuilder(FaweAPI.getWorld(world))
						.autoQueue(Skellett.instance.getConfig().getBoolean("RegeneratorAutoQueue", false))
						.fastmode(true)
						.build();
				for (Location loc : hashy.keySet()) {
					if (loc.getBlock().getType() != hashy.get(loc).getItemType()) {
						if (Skellett.instance.getConfig().getBoolean("Async", false)) {
							MaterialData data = hashy.get(loc);
							@SuppressWarnings("deprecation")
							BaseBlock block = new BaseBlock(data.getItemTypeId(), data.getData());
							try {
								editSession.setBlock(new Vector(loc.getX(), loc.getY(), loc.getZ()), block);
							} catch (WorldEditException e) {
								e.printStackTrace();
							}
						}
					}
				}
				editSession.flushQueue();
			} else {
				for (int i = 0; i < 2; i++) {
					for (Location loc : hashy.keySet()) {
						if (loc.getBlock().getType() != hashy.get(loc).getItemType()) {
							try {
								loc.getBlock().setType(hashy.get(loc).getItemType());
								BlockState state = loc.getBlock().getState();
								state.setData(hashy.get(loc));
								state.update(true);
							} catch (Exception e) {}
						}
					}
				}
			}
		}
	}
	
	public static Location getPos1(String ID) {
		if (regenerators.containsKey(ID)) {
			return regenerators.get(ID).getPos1();
		}
		return null;
	}
	
	public static Location getPos2(String ID) {
		if (regenerators.containsKey(ID)) {
			return regenerators.get(ID).getPos2();
		}
		return null;
	}
	
	public static boolean contains(String ID) {
		if (regenerators.containsKey(ID)) {
			return true;
		}
		return false;
	}
	
	public static String[] getAll() {
		return regenerators.keySet().toArray(new String[regenerators.keySet().size()]);
	}
	
	public static HashMap<String, Regenerator> getHashmap() {
		return regenerators;
	}
	
	public static Location[] cuboid(Location pos1, Location pos2) {
		if (pos1 == null || pos2 == null) {
			return null;
		}
		ArrayList<Location> locations = new ArrayList<Location>();
		int topX = pos1.getBlockX() < pos2.getBlockX() ? pos2.getBlockX() : pos1.getBlockX();
		int topY = pos1.getBlockY() < pos2.getBlockY() ? pos2.getBlockY() : pos1.getBlockY();
		int topZ = pos1.getBlockZ() < pos2.getBlockZ() ? pos2.getBlockZ() : pos1.getBlockZ();
		int bottomX = pos1.getBlockX() > pos2.getBlockX() ? pos2.getBlockX() : pos1.getBlockX();
		int bottomY = pos1.getBlockY() > pos2.getBlockY() ? pos2.getBlockY() : pos1.getBlockY();
		int bottomZ = pos1.getBlockZ() > pos2.getBlockZ() ? pos2.getBlockZ() : pos1.getBlockZ();
		for (int x = bottomX; x <= topX; x++) {
			for (int z = bottomZ; z <= topZ; z++) {
				for (int y = bottomY; y <= topY; y++) {
					locations.add(new Location(pos1.getWorld(), x, y, z));
				}
			}
		}
		return locations.toArray(new Location[locations.size()]);
	}
}