package com.gmail.thelimeglass.Conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[player] %player% (1¦can|2¦can([ ]no|')t) see [player] %player%")
@Config("PlayerCanSee")
public class CondCanSeePlayer extends Condition {
	
	private Expression<Player> viewer, player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		viewer = (Expression<Player>) e[0];
		player = (Expression<Player>) e[1];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[player] %player% (1¦can|2¦can([ ]no|')t) see [player] %player%";
	}
	public boolean check(Event e) {
		return viewer.getSingle(e).canSee(player.getSingle(e)) ? isNegated() : !isNegated();
	}
}