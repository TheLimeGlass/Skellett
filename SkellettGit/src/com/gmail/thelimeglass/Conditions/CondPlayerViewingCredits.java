package com.gmail.thelimeglass.Conditions;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[player] %player% (1¦is|2¦is(n't| not)) viewing [the] credits")
@Config("ViewingCredits")
public class CondPlayerViewingCredits extends Condition {
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[player] %player% (1¦is|2¦is(n't| not)) viewing [the] credits";
	}
	public boolean check(Event e) {
		try {
			Object nmsPlayer = ReflectionUtil.getHandle(player.getSingle(e));
			if (nmsPlayer != null) {
				return nmsPlayer.getClass().getField("viewingCredits").getBoolean(nmsPlayer) ? isNegated() : !isNegated();
			}
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchMethodException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}