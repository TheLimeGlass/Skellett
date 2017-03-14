package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("draw text %string% at [coordinate[s]] [x] %number%(,| and) [y] %number% on [skellett] map %map%")
@Config("Main.Maps")
@FullConfig
public class EffMapDrawText extends Effect {
	
	private Expression<String> string;
	private Expression<Number> x, y;
	private Expression<MapView> map;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		x = (Expression<Number>) e[1];
		y = (Expression<Number>) e[2];
		map = (Expression<MapView>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "draw text %string% at [coordinate[s]] [x] %number%(,| and) [y] %number% on [skellett] map %map%";
	}
	@Override
	protected void execute(Event e) {
		SkellettMapRenderer render = SkellettMapRenderer.getRenderer(map.getSingle(e));
		if (render != null) {
			render.update(new MapRenderTask() {
				@Override
				public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
					mapCanvas.drawText(x.getSingle(e).intValue(), y.getSingle(e).intValue(), MinecraftFont.Font, ChatColor.stripColor(string.getSingle(e)));

				}
			});
		}
	}
}