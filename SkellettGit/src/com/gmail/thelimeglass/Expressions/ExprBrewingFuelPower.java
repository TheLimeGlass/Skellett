package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] [the] brew[ing] [stand[[']s]] fuel [power]", "[skellett] [the] fuel [power] of [the] brew[ing] [stand]", "[skellett] event-fuel[power]"})
@Config("Syntax.Events.BrewingFuel")
@FullConfig
@Version("1.11.2")
@PropertyType("SIMPLE")
public class ExprBrewingFuelPower extends SimpleExpression<Number> {
	
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(BrewingStandFuelEvent.class)) {
			Skript.error("You can not use brewing stand fuel expression in any event but brewing stand fuel event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "brewing stand fuel event fuel power";
	}
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{((BrewingStandFuelEvent)e).getFuelPower()};

	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = ((BrewingStandFuelEvent)e).getFuelPower();
		if (mode == ChangeMode.SET) {
			((BrewingStandFuelEvent)e).setFuelPower(num.intValue());
		} else if (mode == ChangeMode.RESET) {
			((BrewingStandFuelEvent)e).setFuelPower(0);
		} else if (mode == ChangeMode.ADD) {
			((BrewingStandFuelEvent)e).setFuelPower(numNow.intValue() + num.intValue());
		} else if (mode == ChangeMode.REMOVE) {
			((BrewingStandFuelEvent)e).setFuelPower(numNow.intValue() - num.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}