package com.gmail.thelimeglass.Disguises;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] [[Libs]Disguises] [set] Disguise [of] next [spawned] (as|to) %disguise%")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
public class EffDisguiseNextEntity extends Effect {
	
	private Expression<Disguise> disguise;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		disguise = (Expression<Disguise>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] [[Libs]Disguises] [set] Disguise [of] next [spawned] (as|to) %disguise%";
	}
	@Override
	protected void execute(Event e) {
		if (disguise != null) {
			DisguiseAPI.disguiseNextEntity(disguise.getSingle(e));
		}
	}
}
