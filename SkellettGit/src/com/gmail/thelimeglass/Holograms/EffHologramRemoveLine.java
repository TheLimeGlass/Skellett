package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HologramLine;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] remove %hologramline% from holo[gram] %hologram%")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
public class EffHologramRemoveLine extends Effect {
	
	private Expression<HologramLine> hologramline;
	private Expression<Hologram> hologram;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		hologramline = (Expression<HologramLine>) e[0];
		hologram = (Expression<Hologram>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] remove %hologramline% from holo[gram] %hologram%";
	}
	@Override
	protected void execute(Event e) {
		if (hologram != null || hologramline != null) {
			hologram.getSingle(e).removeLine(hologramline.getSingle(e));
		}
	}
}