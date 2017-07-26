package com.gmail.thelimeglass.Holograms;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.sainttx.holograms.api.Hologram;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (despawn|clear) holo[gram] %hologram%")
@Config("PluginHooks.Holograms")
@FullConfig
@MainConfig
public class EffHologramDespawn extends Effect {
	
	private Expression<Hologram> hologram;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		hologram = (Expression<Hologram>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (despawn|clear) [the] holo[gram] %hologram%";
	}
	@Override
	protected void execute(Event e) {
		if (hologram != null) {
			hologram.getSingle(e).despawn();
		}
	}
}