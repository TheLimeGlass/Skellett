package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.disguisetypes.Disguise;

@Syntax("[skellett] [[Libs]Disguises] disguise %disguise% (1¦(is|[does] (ha(s|ve)))|2¦(do[es](n't| not) have|is(n't| not))) [a] [custom] name")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
public class CondDisguiseHasCustomName extends Condition {
	
	private Expression<Disguise> disguise;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		disguise = (Expression<Disguise>) e[0];
		setNegated(parser.mark == 1);
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] disguise %disguise% (1¦(is|[does] (ha(s|ve)))|2¦(do[es](n't| not) have|is(n't| not))) [a] [custom] name";
	}
	public boolean check(Event e) {
		if (disguise.getSingle(e).getWatcher().hasCustomName()) {
			return isNegated();
		} else {
			return !isNegated();
		}
	}
}