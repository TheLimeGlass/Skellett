package com.gmail.thelimeglass.Expressions;

import org.bukkit.event.Event;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[the] [skellett] (1¦(new|future)|2¦(old|past)) [event] [redstone] current")
@Config("RedstoneCurrent")
@PropertyType(ExpressionType.COMBINED)
public class ExprRedstoneCurrent extends SimpleExpression<Integer> {
	
	private Integer time;
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(BlockRedstoneEvent.class)) {
			Skript.error("You can not use New Redstone Current expression in any event but on redstone changing event!");
			return false;
		}
		time = parser.mark;
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[the] [skellett] (1¦(new|future)|2¦(old|past)) [event] [redstone] current";
	}
	@Nullable
	protected Integer[] get(Event e) {
		if (time == 1) {
			return new Integer[]{((BlockRedstoneEvent)e).getNewCurrent()};
		}
		return new Integer[]{((BlockRedstoneEvent)e).getOldCurrent()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (time == 1) {
			if (mode == ChangeMode.SET) {
				((BlockRedstoneEvent)e).setNewCurrent(((Integer)delta[0]));
			} else if (mode == ChangeMode.ADD) {
				((BlockRedstoneEvent)e).setNewCurrent((((BlockRedstoneEvent)e).getNewCurrent() + (Integer)delta[0]));
			} else if (mode == ChangeMode.REMOVE) {
				((BlockRedstoneEvent)e).setNewCurrent((((BlockRedstoneEvent)e).getNewCurrent() - (Integer)delta[0]));
			} else if (mode == ChangeMode.RESET) {
				((BlockRedstoneEvent)e).setNewCurrent(((BlockRedstoneEvent)e).getNewCurrent());
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}