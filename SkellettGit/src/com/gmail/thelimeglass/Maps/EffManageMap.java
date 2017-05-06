package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(manage|override|overwrite|create) [skellett] map %map% [[and] [with] override %-boolean%]")
@Config("Main.Maps")
@FullConfig
public class EffManageMap extends Effect {
	
	private Expression<MapView> map;
	private Expression<Boolean> override;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		override = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(manage|override|overwrite|create) [skellett] map %map% [[and] [with] override %-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		Boolean overwrite = true;
		if (override != null) {
			overwrite = override.getSingle(e);
		}
		SkellettMapRenderer.createHandler(map.getSingle(e), overwrite);
	}
}