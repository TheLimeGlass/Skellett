package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] [skellett] holo[gram] with ID %string%")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=Hologram.class, value="hologram")
public class ExprHologramGet extends SimpleExpression<Hologram>{
	
	private Expression<String> string;
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
		string= (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [skellett] holo[gram] with ID %string%";
	}
	@Override
	@Nullable
	protected Hologram[] get(Event e) {
		if (Skellett.hologramManager.getHologram(string.getSingle(e)) != null) {
			return new Hologram[]{Skellett.hologramManager.getHologram(string.getSingle(e))};
		}
		return null;
	}
}