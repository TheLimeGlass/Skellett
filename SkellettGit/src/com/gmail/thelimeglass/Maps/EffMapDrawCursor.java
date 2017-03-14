package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("draw [map] cursor %string% pointing %number% at [coordinate[s]] [x] %number%(,| and) [y] %number% on [skellett] map %map%")
@Config("Main.Maps")
@FullConfig
@RegisterSimpleEnum(ExprClass=MapCursor.class, value="mapcursor")
public class EffMapDrawCursor extends Effect {
	
	private Expression<String> cursorType;
	private Expression<Number> x, y, direction;
	private Expression<MapView> map;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		cursorType = (Expression<String>) e[0];
		direction = (Expression<Number>) e[1];
		x = (Expression<Number>) e[2];
		y = (Expression<Number>) e[3];
		map = (Expression<MapView>) e[4];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "draw [map] cursor %cursormaptype% pointing %number% at [coordinate[s]] [x] %number%(,| and) [y] %number% on [skellett] map %map%";
	}
	@Override
	protected void execute(Event e) {
		SkellettMapRenderer render = SkellettMapRenderer.getRenderer(map.getSingle(e));
		if (render != null) {
			render.update(new MapRenderTask() {
				@SuppressWarnings("deprecation")
				@Override
				public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
					MapCursor cursor = new MapCursor((byte)x.getSingle(e).intValue(), (byte)y.getSingle(e).intValue(), (byte)direction.getSingle(e).intValue(), (byte) 2, true);
					try {
						MapCursor.Type type = MapCursor.Type.valueOf(cursorType.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
						if (type != null) {
							cursor.setType(type);
						}
					} catch (IllegalArgumentException error) {
						Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown mapcursor type " + cursorType.getSingle(e)));
						return;
					}
					mapCanvas.getCursors().addCursor(cursor);
				}
			});
		}
	}
}