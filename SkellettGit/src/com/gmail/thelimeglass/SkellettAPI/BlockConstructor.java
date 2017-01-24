package com.gmail.thelimeglass.SkellettAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockConstructor {
	List<BlockLayer> layers = new ArrayList<BlockLayer>();
	public BlockConstructor(BlockLayer... layers) {
		for (BlockLayer layer : layers) {
			this.layers.add(layer);
		}
	}	
	public void addLayer(BlockLayer layer) {
		layers.add(layer);
	}
	private int i = 0;
	public void generateWithTime(final Plugin plugin, final Location startLocation, final Long timeInTicks, final boolean effect) {
		long ticks = (long) Math.ceil(timeInTicks / layers.size());
		new BukkitRunnable() {
			public void run() {
				final BlockLayer layer = layers.get(i);
				final Location layerStart = startLocation.clone().add(0, layers.indexOf(layer), 0);
				new BukkitRunnable() {
					public void run() {
						layer.generateLayer(layerStart, effect);
					}
				}.runTask(plugin);
				i++;
				if (i == layers.size()) {
					i = 0;
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(plugin, 1L, ticks);
	}
	public void generate(Location startLocation, boolean effect) {
		for (BlockLayer layer : layers) {
			Location layerStart = startLocation.clone().add(0, layers.indexOf(layer), 0);
			layer.generateLayer(layerStart, effect);
		}
	}
	public static class BlockLayer {
		private String layer;
		private HashMap<Character, Material> chars = new HashMap<Character, Material>();
		public BlockLayer(String layer) {
			this.layer = layer;
		}
		public BlockLayer setBlockType(char character, Material material) {
			chars.put(character, material);
			return this;
		}
		@SuppressWarnings("deprecation")
		public void generateLayer(Location start, boolean effect) {
			int skip = 0;
			int rowZ = 0;
			int rowY = 0;
			for (int i = 0; i < layer.length(); i++) {
				char character = layer.charAt(i);
				if (character == ':' || character == ',') {
					rowY ++;
					skip = i + 1;
				}
				if (character == ';' || character == '.') {
					rowZ ++;
					skip = i + 1;
				} else {
					Block block = start.getWorld().getBlockAt(start.getBlockX() + i - skip, start.getBlockY() + rowY, start.getBlockZ() + rowZ);
					if (chars.containsKey(character)) {
						block.setType(chars.get(character));
						if (effect) {
							block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
						}
					}
				}
			}
		}	
	}
}