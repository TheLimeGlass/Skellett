package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [create] [a] [new] holo[gram] at %location% [with] id %string%")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprHologramCreate extends SimpleExpression<Hologram>{
	
	private Expression<Location> location;
	private Expression<String> ID;
	@Override
	public Class<? extends Hologram> getReturnType() {
		return Hologram.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		location = (Expression<Location>) e[0];
		ID = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [create] [a] [new] holo[gram] at %location% [with] id %string%";
	}
	@Override
	@Nullable
	protected Hologram[] get(Event e) {
		Hologram hologram = new Hologram(ID.getSingle(e), location.getSingle(e));
	    Skellett.hologramManager.addActiveHologram(hologram);
		return new Hologram[]{hologram};
	}
}