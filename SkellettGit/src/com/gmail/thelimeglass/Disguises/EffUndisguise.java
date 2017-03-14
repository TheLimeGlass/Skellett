package com.gmail.thelimeglass.Disguises;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] [[Libs]Disguises] un[( |-)]disguise %entity%")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
public class EffUndisguise extends Effect {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[[Libs]Disguises] un[( |-)]disguise %entity%";
	}
	@Override
	protected void execute(Event e) {
		if (DisguiseAPI.isDisguised(entity.getSingle(e))) {
			DisguiseAPI.undisguiseToAll(entity.getSingle(e));
		}
	}
}
