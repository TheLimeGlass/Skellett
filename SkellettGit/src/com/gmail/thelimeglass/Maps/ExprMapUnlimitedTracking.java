package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"unlimited [tracking] state (of|from|in) [map] %map%", "[map] %map%'s unlimited [tracking] state"})
@Config("Main.Maps")
@FullConfig
@Version("1.11")
@PropertyType(ExpressionType.COMBINED)
public class ExprMapUnlimitedTracking extends SimpleExpression<Boolean> {
	
	private Expression<MapView> map;
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "unlimited [tracking] state (of|from|in) [map] %map%";
	}
	@Nullable
	protected Boolean[] get(Event e) {
		if (map.getSingle(e) == null) {
			return null;
		}
		return new Boolean[]{map.getSingle(e).isUnlimitedTracking()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (map.getSingle(e) == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			map.getSingle(e).setUnlimitedTracking((Boolean)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}