package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"world of map %map%", "map %map%'s world"})
@Config("Main.Maps")
@FullConfig
@PropertyType("COMBINED")
public class ExprMapWorld extends SimpleExpression<World> {
	
	private Expression<MapView> map;
	public Class<? extends World> getReturnType() {
		return World.class;
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
		return "world of map %map%";
	}
	@Nullable
	protected World[] get(Event e) {
		if (map.getSingle(e) == null) {
			return null;
		}
		return new World[]{map.getSingle(e).getWorld()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (map.getSingle(e) == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			map.getSingle(e).setWorld((World)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(World.class);
		}
		return null;
	}
}