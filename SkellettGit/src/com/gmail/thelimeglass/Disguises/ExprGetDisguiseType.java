package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Dependency;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;

@Syntax({"[skellett] [[Libs]Disguises] Disguise type of %entities%[[']s]", "[skellett] [[Libs]Disguises] %entities%'s disguise type"})
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@Dependency("LibsDisguises")
@RegisterEnum("disguisetype")
public class ExprGetDisguiseType extends SimpleExpression<DisguiseType> {
	
	private Expression<Entity> entity;
	public Class<? extends DisguiseType> getReturnType() {
		return DisguiseType.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "Disguise of %entitys%";
	}
	@Override
	@Nullable
	protected DisguiseType[] get(Event e) {
		if (DisguiseAPI.getDisguise(entity.getSingle(e)) != null) {
			return new DisguiseType[]{DisguiseAPI.getDisguise(entity.getSingle(e)).getType()};
		} else {
			return null;
		}
	}
}