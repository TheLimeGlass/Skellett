package com.gmail.thelimeglass.Expressions;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"delay (of|from) spawner [at] %block%", "%block%'s spawn[er] delay", "spawn[er] delay (of|from) %block%"})
@Config("Main.Spawners")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprSpawnerDelay extends SimpleExpression<Number>{
	
	private Expression<Block> spawner;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		spawner = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "delay of spawner %block%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (spawner != null) {
			return new Number[]{((CreatureSpawner)spawner.getSingle(e).getState()).getDelay()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (spawner != null) {
			Number delay = (Number)delta[0];
			Number delayNow = ((CreatureSpawner)spawner.getSingle(e).getState()).getDelay();
			CreatureSpawner spawn = ((CreatureSpawner)spawner.getSingle(e).getState());
			if (mode == ChangeMode.SET) {
				spawn.setDelay(delay.intValue());
			} else if (mode == ChangeMode.ADD) {
				spawn.setDelay(delayNow.intValue() + delay.intValue());
			} else if (mode == ChangeMode.REMOVE) {
				spawn.setDelay(delayNow.intValue() - delay.intValue());
			}
			spawn.update();
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}