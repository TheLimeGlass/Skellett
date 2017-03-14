package com.gmail.thelimeglass.PlayerPoints;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] [player[ ]]points of [(player|uuid)] %string%", "[(player|uuid)] %string%'s [player[ ]]points"})
@Config("PluginHooks.PlayerPoints")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprPlayerPoints extends SimpleExpression<Number>{
	
	private Expression<String> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [player[ ]]points of [(player|uuid)] %string%";
	}
	@SuppressWarnings("deprecation")
	@Override
	@Nullable
	protected Number[] get(Event e) {
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		if (uniqueId != null) {
			return new Number[]{Skellett.playerPoints.getAPI().look(uniqueId)};
		} else {
			return new Number[]{Skellett.playerPoints.getAPI().look(player.getSingle(e))};
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		Number amount = (Number)delta[0];
		if (mode == ChangeMode.SET) {
			if (uniqueId != null) {
				Skellett.playerPoints.getAPI().set(uniqueId, amount.intValue());
			} else {
				Skellett.playerPoints.getAPI().set(player.getSingle(e), amount.intValue());
			}
		} else if (mode == ChangeMode.ADD) {
			if (uniqueId != null) {
				Skellett.playerPoints.getAPI().give(uniqueId, amount.intValue());
			} else {
				Skellett.playerPoints.getAPI().give(player.getSingle(e), amount.intValue());
			}
		} else if (mode == ChangeMode.REMOVE) {
			if (uniqueId != null) {
				Skellett.playerPoints.getAPI().take(uniqueId, amount.intValue());
			} else {
				Skellett.playerPoints.getAPI().take(player.getSingle(e), amount.intValue());
			}
		} else if (mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
			if (uniqueId != null) {
				Skellett.playerPoints.getAPI().reset(uniqueId);
			} else {
				Skellett.playerPoints.getAPI().reset(player.getSingle(e), 0);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE_ALL || mode == ChangeMode.DELETE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}