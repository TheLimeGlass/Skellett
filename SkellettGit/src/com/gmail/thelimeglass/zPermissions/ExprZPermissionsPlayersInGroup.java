package com.gmail.thelimeglass.zPermissions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("zPermissions [(the|all)] [of] [the] players (in|of|from) group %string%")
@Config("PluginHooks.zPermissions")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprZPermissionsPlayersInGroup extends SimpleExpression<String>{
	
	private Expression<String> group;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		group = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "zPermissions [(the|all)] [of] [the] players (in|of|from) group %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return Skellett.zPermissions.getGroupMembers(group.getSingle(e)).toArray(new String[Skellett.zPermissions.getGroupMembers(group.getSingle(e)).size()]);
	}
}