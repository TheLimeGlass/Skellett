package com.gmail.thelimeglass.Conditions;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Utils;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("%player% (1¦(has|does)|2¦(has|does)(n't| not)) [(have|got)] [a] cool[ ]down for [(item|material)] %string%")
@Config("ItemCooldown")
public class CondHasCooldown extends Condition {
	
	private Expression<Player> player;
	private Expression<String> material;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		material = (Expression<String>) e[1];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "%player% (1¦does|2¦does(n't| not)) have [a] cool[ ]down for [(item|material)] %string%";
	}
	public boolean check(Event e) {
		if (material != null && player != null) {
			Material m = (Material) Utils.getEnum(Material.class, material.getSingle(e));
			if (((HumanEntity)player.getSingle(e)).hasCooldown(m)) {
				return ((HumanEntity)player.getSingle(e)).hasCooldown(m) ? isNegated() : !isNegated();
			}
		}
		return false;
	}
}