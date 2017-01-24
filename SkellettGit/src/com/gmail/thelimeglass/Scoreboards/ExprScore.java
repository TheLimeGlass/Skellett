package com.gmail.thelimeglass.Scoreboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.scoreboard.Score;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprScore extends SimpleExpression<Number>{
	
	//(score[ ][board]|[skellett[ ]]board) (score|number|slot) (from|of) %score%
	//(score[ ][board]|[skellett[ ]]board) %score%'s (score|number|slot)
	
	private Expression<Score> score;
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
		score = (Expression<Score>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(score[ ][board]|[skellett[ ]]board) (score|number|slot) (from|of) %score%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{score.getSingle(e).getScore()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number scoreNew = (Number)delta[0];
		Number scoreNow = score.getSingle(e).getScore();
		if (mode == ChangeMode.SET) {
			score.getSingle(e).setScore(scoreNew.intValue());
		} else if (mode == ChangeMode.ADD) {
			score.getSingle(e).setScore(scoreNow.intValue() + scoreNew.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			score.getSingle(e).setScore(scoreNow.intValue() - scoreNew.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}