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

@Syntax({"zPermissions [(the|all)] [of] [the] groups", "zPermissions's groups"})
@Config("PluginHooks.zPermissions")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprZPermissionsAllGroups extends SimpleExpression<String>{
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parse) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "zPermissions [(the|all)] [of] [the] groups";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return Skellett.zPermissions.getAllGroups().toArray(new String[Skellett.zPermissions.getAllGroups().size()]);
	}
}