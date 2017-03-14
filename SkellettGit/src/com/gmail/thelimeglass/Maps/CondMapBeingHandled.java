package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[map] %map% (1¦is|2¦is(n't| not)) being handled [by skellett]")
@Config("Main.Maps")
@FullConfig
public class CondMapBeingHandled extends Condition {
	
	private Expression<MapView> map;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[map] %map% (1¦is|2¦is(n't| not)) virtual";
	}
	public boolean check(Event e) {
		if (map.getSingle(e) == null) {
			return false;
		}
		if (SkellettMapRenderer.getRenderer(map.getSingle(e)) != null) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}