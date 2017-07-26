package com.gmail.thelimeglass.Maps;

import java.awt.Image;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("draw [buffered] image %mapimage% [at [coordinate[s]] [x] %number%(,| and) [y] %number%] on [skellett] map %map%")
@Config("Main.Maps")
@FullConfig
@RegisterSimpleEnum(ExprClass=Image.class, value="mapimage")
public class EffMapDrawImage extends Effect {
	
	private Expression<Image> image;
	private Expression<Number> x, y;
	private Expression<MapView> map;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		image = (Expression<Image>) e[0];
		x = (Expression<Number>) e[1];
		y = (Expression<Number>) e[2];
		map = (Expression<MapView>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "draw [buffered] image %image% at [coordinate[s]] [x] %number%(,| and) [y] %number% on [skellett] map %map%";
	}
	@Override
	protected void execute(Event e) {
		SkellettMapRenderer render = SkellettMapRenderer.getRenderer(map.getSingle(e));
		if (render != null && image != null) {
			Integer xget = 0;
			Integer yget = 0;
			if (x != null || y != null) {
				xget = x.getSingle(e).intValue();
				yget = y.getSingle(e).intValue();
			}
			final Integer xcoord = xget;
			final Integer ycoord = yget;
			render.update(new MapRenderTask() {
				@Override
				public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
					mapCanvas.drawImage(xcoord, ycoord, image.getSingle(e));

				}
			});
		}
	}
}