package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[(the|all)] [of] [the] scoreboard tag[s] (of|from) %entity%", "%entity%'s scoreboard tag[s]"})
@Config("EntityScoreboardTag")
@Version("1.11")
@PropertyType(ExpressionType.COMBINED)
public class ExprEntityScoreboardTag extends SimpleExpression<String>{
	
	private Expression<Entity> entity;
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
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] scoreboard tag (of|from) %entity%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return entity.getSingle(e).getScoreboardTags().toArray(new String[entity.getSingle(e).getScoreboardTags().size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.ADD) {
			entity.getSingle(e).addScoreboardTag((String)delta[0]);
		} else if (mode == ChangeMode.REMOVE) {
			if (entity.getSingle(e).getScoreboardTags().contains((String)delta[0])) {
				entity.getSingle(e).removeScoreboardTag((String)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}