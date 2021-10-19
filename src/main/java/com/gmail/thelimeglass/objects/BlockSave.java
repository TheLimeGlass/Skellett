package com.gmail.thelimeglass.objects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockSave {

	private final Location location;
	private final BlockData data;

	public BlockSave(Block block) {
		this(block.getLocation(), block.getBlockData());
	}

	public BlockSave(Location location, BlockData data) {
		this.location = location;
		this.data = data;
	}

	public BlockData getBlockData() {
		return data;
	}

	public Location getLocation() {
		return location;
	}

}
