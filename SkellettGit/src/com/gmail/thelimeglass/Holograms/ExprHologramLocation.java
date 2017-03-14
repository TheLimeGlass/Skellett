package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] [skellett] holo[gram] location of %hologram%", "[skellett] holo[gram] %hologram%'s location"})
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprHologramLocation extends SimpleExpression<Location>{
	
	private Expression<Hologram> hologram;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		hologram = (Expression<Hologram>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] holo[gram] location of %hologram%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if (hologram != null) {
			return new Location[]{hologram.getSingle(e).getLocation()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (hologram != null) {
				hologram.getSingle(e).teleport((Location)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Location.class);
		}
		return null;
	}
}