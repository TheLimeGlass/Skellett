package com.gmail.thelimeglass.Stylishboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[skellett] [(update|edit)] [the] slot [of] id [based] [score] [(with [id]|named)] %string%")
@Config("Main.StylishBoards")
@FullConfig
@PropertyType("COMBINED")
public class ExprStylishSlotNumber extends SimpleExpression<Number>{
	
	private Expression<String> ID;
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] slot [of] id [based] [score] [(with [id]|named)] %string%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		StyleBoard score = StyleManager.getMemory().get(ID.getSingle(e));
		if (score != null) {
			return new Number[]{score.getScore().getScore()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			Number num = (Number)delta[0];
			StyleBoard board = StyleManager.getMemory().get(ID.getSingle(e));
			if (board != null) {
				StyleManager.updateScore(ID.getSingle(e), board.getBoard(), board.getScore().getEntry(), num.intValue());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}