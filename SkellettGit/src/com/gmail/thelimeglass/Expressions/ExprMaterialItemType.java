package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] [spigot] material [name] (from|of) %itemtype%", "%itemtype%'s [spigot] material [name]"})
@Config("MaterialName")
@PropertyType(ExpressionType.COMBINED)
public class ExprMaterialItemType extends SimpleExpression<String>{
	
	private Expression<ItemType> itemtype;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		itemtype = (Expression<ItemType>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [spigot] material name (from|of) %itemtype%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{itemtype.getSingle(e).getRandom().getType().toString()};
	}
}