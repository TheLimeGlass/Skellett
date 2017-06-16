package com.gmail.thelimeglass.Maps;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

public abstract class MapRenderTask {
	
	public abstract void render(MapView mapView, MapCanvas mapCanvas, Player player);
    
}
