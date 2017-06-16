package com.gmail.thelimeglass.zPermissions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[zPermissions] [(the|all)] [of] [the] groups of [player] %string%")
@Config("PluginHooks.zPermissions")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprZPermissionsPlayerGroups extends SimpleExpression<String>{
	
	private Expression<String> player;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[zPermissions] [(the|all)] [of] [the] groups of [player] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return Skellett.zPermissions.getPlayerGroups(player.getSingle(e)).toArray(new String[Skellett.zPermissions.getPlayerGroups(player.getSingle(e)).size()]);
	}
}