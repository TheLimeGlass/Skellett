package com.gmail.thelimeglass.Expressions;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Utils;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] cool[ ]down of [(item|material)] %string% (for|of) %players%", "[(item|material)] %string%'s cool[ ]down (for|of) %players%"})
@Config("ItemCooldown")
@PropertyType(ExpressionType.COMBINED)
@Version("1.11.2")
public class ExprItemCooldown extends SimpleExpression<Number>{
	
	private Expression<String> material;
	private Expression<Player> players;
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
		material = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] cool[ ]down of [(item|material)] %string% (for|of) %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (material != null && players != null) {
			Material m = (Material) Utils.getEnum(Material.class, material.getSingle(e));
			ArrayList<Number> ticks = new ArrayList<Number>();
			for (Player p : players.getAll(e)) {
				if (p.hasCooldown(m)) {
					ticks.add(((HumanEntity)p).getCooldown(m));
				}
			}
			return ticks.toArray(new Number[ticks.size()]);
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (material != null && players != null) {
			Material m = (Material) Utils.getEnum(Material.class, material.getSingle(e));
			for (Player p : players.getAll(e)) {
				if (mode == ChangeMode.SET) {
					((HumanEntity)p).setCooldown(m, ((Number)delta[0]).intValue());
				} else if (mode == ChangeMode.RESET) {
					((HumanEntity)p).setCooldown(m, 0);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}