package com.gmail.thelimeglass.Maps;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [a] new map[[ ]view] (for|from|with) [world] %world%")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprNewMap extends SimpleExpression<MapView> {
	
	private Expression<World> world;
	public Class<? extends MapView> getReturnType() {
		return MapView.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		world = (Expression<World>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "new map";
	}
	@Nullable
	protected MapView[] get(Event e) {
		return new MapView[]{Bukkit.createMap(world.getSingle(e))};
	}
}