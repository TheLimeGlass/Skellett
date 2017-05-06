package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.material.Diode;
import org.bukkit.material.MaterialData;

import com.gmail.thelimeglass.Utils.Config;
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

@Syntax({"[skellett] [redstone] repeater[s] [(redstone|power)] delay of %block%", "[redstone] repeater %block%'s [(redstone|power)] delay"})
@Config("RepeaterDelay")
@PropertyType(ExpressionType.COMBINED)
public class ExprRepeaterDelay extends SimpleExpression<Number>{
	
	private Expression<Block> block;
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
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [redstone] repeater[s] [(redstone|power)] delay of %block%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		MaterialData repeater = block.getSingle(e).getState().getData();
		if (repeater instanceof Diode) {
			return new Number[]{((Diode)repeater).getDelay()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			BlockState state = block.getSingle(e).getState();
			MaterialData repeater = state.getData();
			if (repeater instanceof Diode) {
				Number delay = (Number)delta[0];
				((Diode)repeater).setDelay(delay.intValue());
				state.setData(repeater);
				state.update(true);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}