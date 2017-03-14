package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HologramLine;
import com.sainttx.holograms.api.line.ItemLine;
import com.sainttx.holograms.api.line.TextLine;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [a] [new] [holo[gram]] line (for|in|of) holo[gram] %hologram% [and] (1安ith [(text|string)] %-string%|2安ith [item[(stack|type)]] %-itemstack%)")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprHologramLineCreate extends SimpleExpression<HologramLine>{
	
	private Expression<Hologram> hologram;
	private Expression<String> string;
	private Expression<ItemStack> item;
	private Integer marker;
	@Override
	public Class<? extends HologramLine> getReturnType() {
		return HologramLine.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		hologram = (Expression<Hologram>) e[0];
		string = (Expression<String>) e[1];
		item = (Expression<ItemStack>) e[2];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [a] [new] [holo[gram]] line (for|in|of) holo[gram] %hologram% [and] (1安ith [(text|string)] %-string%|2安ith [item[(stack|type)]] %-itemstack%)";
	}
	@Override
	@Nullable
	protected HologramLine[] get(Event e) {
		if (hologram != null) {
			if (marker == 1) {
				if (string != null) {
					return new HologramLine[]{new TextLine(hologram.getSingle(e), string.getSingle(e))};
				}
			} else {
				if (item != null) {
					return new HologramLine[]{new ItemLine(hologram.getSingle(e), item.getSingle(e))};
				}
			}
		}
		return null;
	}
}