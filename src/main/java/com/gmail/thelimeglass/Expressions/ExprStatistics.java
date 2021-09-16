package com.gmail.thelimeglass.Expressions;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Utils;
import com.gmail.thelimeglass.Utils.Annotations.Config;
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

@Syntax("[the] stat[istic][s] %string% (of|from) %player% [[(with|from|for|of)] entity[[ ]type] %-string%] [[(with|from|for|of)] material %-string%]")
@Config("Statistics")
@PropertyType(ExpressionType.COMBINED)
public class ExprStatistics extends SimpleExpression<Number> {
	
	private Expression<String> statistic, entity, material;
	private Expression<Player> player;
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		statistic = (Expression<String>) e[0];
		player = (Expression<Player>) e[1];
		entity = (Expression<String>) e[2];
		material = (Expression<String>) e[3];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[the] stat[istic][s] %string% (of|from) %player% [[(with|from|for|of)] entity[[ ]type] %-string%] [[(with|from|for|of)] material %-string%]";
	}
	@Nullable
	protected Number[] get(Event e) {
		Statistic stat = (Statistic) Utils.getEnum(Statistic.class, statistic.getSingle(e));
		if (entity != null) {
			EntityType type = (EntityType) Utils.getEnum(EntityType.class, entity.getSingle(e));
			return new Number[]{player.getSingle(e).getStatistic(stat, type)};
		}
		if (material != null) {
			Material m = (Material) Utils.getEnum(Material.class, material.getSingle(e));
			return new Number[]{player.getSingle(e).getStatistic(stat, m)};
		}
		return new Number[]{player.getSingle(e).getStatistic(stat)};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Integer statNow = (Arrays.asList(get(e)).get(0)).intValue();
		if (mode == ChangeMode.SET) {
			Integer stat = ((Number)delta[0]).intValue();
			if (entity != null) {
				setEntity(statistic.getSingle(e), player.getSingle(e), entity.getSingle(e), stat);
			} else if (material != null) {
				setMaterial(statistic.getSingle(e), player.getSingle(e), material.getSingle(e), stat);
			} else {
				set(statistic.getSingle(e), player.getSingle(e), stat);
			}
		} else if (mode == ChangeMode.REMOVE) {
			Integer stat = ((Number)delta[0]).intValue();
			if (entity != null) {
				setEntity(statistic.getSingle(e), player.getSingle(e), entity.getSingle(e), statNow - stat);
			} else if (material != null) {
				setMaterial(statistic.getSingle(e), player.getSingle(e), material.getSingle(e), statNow - stat);
			} else {
				set(statistic.getSingle(e), player.getSingle(e), statNow - stat);
			}
		} else if (mode == ChangeMode.ADD) {
			Integer stat = ((Number)delta[0]).intValue();
			if (entity != null) {
				setEntity(statistic.getSingle(e), player.getSingle(e), entity.getSingle(e), statNow + stat);
			} else if (material != null) {
				setMaterial(statistic.getSingle(e), player.getSingle(e), material.getSingle(e), statNow + stat);
			} else {
				set(statistic.getSingle(e), player.getSingle(e), statNow + stat);
			}
		} else if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE_ALL) {
			set(statistic.getSingle(e), player.getSingle(e), 0);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE || mode == ChangeMode.ADD || mode == ChangeMode.DELETE || mode == ChangeMode.REMOVE_ALL) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
	
	private void set(String statistic, Player player, Integer value) {
		Statistic stat = (Statistic) Utils.getEnum(Statistic.class, statistic);
		player.setStatistic(stat, value);
	}
	
	private void setEntity(String statistic, Player player, String entity, Integer value) {
		Statistic stat = (Statistic) Utils.getEnum(Statistic.class, statistic);
		if (entity != null) {
			EntityType type = (EntityType) Utils.getEnum(EntityType.class, entity);
			player.setStatistic(stat, type, value);
		}
	}
	
	private void setMaterial(String statistic, Player player, String material, Integer value) {
		Statistic stat = (Statistic) Utils.getEnum(Statistic.class, statistic);
		if (material != null) {
			Material m = (Material) Utils.getEnum(Material.class, material);
			player.setStatistic(stat, m, value);
		}
		player.setStatistic(stat, value);
	}
}