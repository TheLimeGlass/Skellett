package com.gmail.thelimeglass.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class SkellettMapRenderer extends MapRenderer implements Listener {
	
	private final static ArrayList<UUID> mapPlayers = new ArrayList<>();
	private ArrayList<MapRenderTask> tasks = new ArrayList<>();
	private static final HashMap<MapView, MapCanvas> canvases = new HashMap<>();
	
	public SkellettMapRenderer() {
		super(false);
	}

	public static MapCanvas getCanvas(MapView mapView) {
		if (mapView == null || canvases.get(mapView) == null) {
			return null;
		}
		return canvases.get(mapView);
	}
	
	@Override
	public void render(MapView mapView, MapCanvas canvas, Player player) {
		UUID uuid = player.getUniqueId();
		Integer size = 128;
		if (mapView.getScale() == MapView.Scale.CLOSE) {
			size = 256;
		} else if (mapView.getScale() == MapView.Scale.NORMAL) {
			size = 512;
		} else if (mapView.getScale() == MapView.Scale.FAR) {
			size = 1024;
		} else if (mapView.getScale() == MapView.Scale.FARTHEST) {
			size = 2048;
		}
		if (canvases.containsKey(mapView)) {
			canvases.remove(mapView);
		}
		if (!canvases.containsKey(mapView)) {
			canvases.put(mapView, canvas);
		}
		if (mapPlayers.contains(uuid)) {
			for (int y = 0; y < size; ++y) {
				for (int x = 0; x < size; ++x) {
					canvas.setPixel(x, y, (byte)0);
				}
			}
			for (MapRenderTask r : tasks) {
				r.render(mapView, canvas, player);
			}
			mapPlayers.remove(uuid);
		}
	}

	public void redraw() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!mapPlayers.contains(p.getUniqueId())) {
				mapPlayers.add(p.getUniqueId());
			}
		}
	}

	public static void createHandler(MapView mapView, boolean overwrite) {
		if (mapView != null) {
			if (overwrite && mapView.getRenderers() != null) {
				for (MapRenderer renderer : mapView.getRenderers()) {
					mapView.removeRenderer(renderer);
				}
			}
			mapView.addRenderer(new SkellettMapRenderer());
		}
	}
	
	public static void dump() {
		mapPlayers.clear();
		canvases.clear();
	}

	public void clearTasks() {
		tasks = new ArrayList<>();
		redraw();
	}

	public void update(final MapRenderTask task) {
		tasks.add(task);
		redraw();
	}

	public static SkellettMapRenderer getRenderer(MapView mapView) {
		if (mapView == null || mapView.getRenderers() == null) {
			return null;
		}
		for (MapRenderer renderer : mapView.getRenderers()) {
			if (renderer instanceof SkellettMapRenderer) {
				return (SkellettMapRenderer)renderer;
			}
		}
		return null;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		mapPlayers.remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (!mapPlayers.contains(e.getPlayer().getUniqueId())) {
			mapPlayers.add(e.getPlayer().getUniqueId());
		}
	}
}
