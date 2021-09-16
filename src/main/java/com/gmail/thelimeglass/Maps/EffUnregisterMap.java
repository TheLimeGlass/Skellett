package com.gmail.thelimeglass.Maps;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(erase|clear|remove|delete|unregister) [skellett] map %map%")
@Config("Main.Maps")
@FullConfig
public class EffUnregisterMap extends Effect {
	
	private Expression<MapView> map;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(erase|clear|remove|delete|unregister) [skellett] map %map%";
	}
	@Override
	protected void execute(Event e) {
		SkellettMapRenderer render = SkellettMapRenderer.getRenderer(map.getSingle(e));
		if (render != null) {
			render.clearTasks();
		}
	}
}