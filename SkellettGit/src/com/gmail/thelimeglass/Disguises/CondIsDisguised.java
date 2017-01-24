package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;

@Syntax("[skellett] [[Libs]Disguises] [(entity|player)] %entity% (1¦(is|[does] (ha(s|ve)))|2¦(do[es](n't| not) have|is(n't| not))) [a] disguise[d]")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
public class CondIsDisguised extends Condition {
	
	private Expression<Entity> entity;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] [(entity|player)] %entity% (1¦(is|[does] (ha(s|ve)))|2¦(do[es](n't| not) have|is(n't| not))) [a] disguise[d]";
	}
	public boolean check(Event e) {
		if (DisguiseAPI.isDisguised(entity.getSingle(e))) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}