package com.gmail.thelimeglass.Maps;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

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