package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
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

@Syntax({"(entity|mob|creature) [type] (of|from) spawner [at] %block%", "%block%'s spawn[er] (entity|mob|creature) [type]"})
@Config("Main.Spawners")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprSpawnerType extends SimpleExpression<String>{
	
	private Expression<Block> spawner;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
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
		return "(entity|mob|creature) [type] (of|from) spawner %block%";
	}
	@SuppressWarnings("deprecation")
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (spawner != null) {
			return new String[]{((CreatureSpawner)spawner.getSingle(e).getState()).getCreatureTypeName()};
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (spawner != null) {
			CreatureSpawner spawn = ((CreatureSpawner)spawner.getSingle(e).getState());
			if (mode == ChangeMode.SET) {
				spawn.setCreatureTypeByName((String)delta[0]);
			}
			spawn.update();
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}