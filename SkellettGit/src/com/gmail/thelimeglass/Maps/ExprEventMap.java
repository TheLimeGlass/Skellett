package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [the] event[(-| )]map")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
@RegisterSimpleEnum(ExprClass=MapView.class, value="map")
public class ExprEventMap extends SimpleExpression<MapView> {
	
	public Class<? extends MapView> getReturnType() {
		return MapView.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(MapInitializeEvent.class)) {
			Skript.error("You can not use event-map expression in any event but map initialize event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "event-map";
	}
	@Nullable
	protected MapView[] get(Event e) {
		return new MapView[]{((MapInitializeEvent)e).getMap()};
	}
}