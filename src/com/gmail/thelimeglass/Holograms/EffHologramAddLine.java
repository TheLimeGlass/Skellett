package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.line.HologramLine;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] add %hologramline% to holo[gram] %hologram% [(on|to) line %-number%]")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
public class EffHologramAddLine extends Effect {
	
	private Expression<HologramLine> hologramline;
	private Expression<Hologram> hologram;
	private Expression<Number> line;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		hologramline = (Expression<HologramLine>) e[0];
		hologram = (Expression<Hologram>) e[1];
		line = (Expression<Number>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] add %hologramline% to holo[gram] %hologram% [(on|to) line %-number%]";
	}
	@Override
	protected void execute(Event e) {
		if (hologram != null) {
			if (line != null) {
				hologram.getSingle(e).addLine(hologramline.getSingle(e), line.getSingle(e).intValue());
			} else {
				hologram.getSingle(e).addLine(hologramline.getSingle(e));
			}
		}
	}
}