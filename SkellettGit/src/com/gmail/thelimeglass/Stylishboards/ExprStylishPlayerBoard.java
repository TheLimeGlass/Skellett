package com.gmail.thelimeglass.Stylishboards;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Scoreboard;

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

@Syntax({"[the] (stylish|style|simple) [score][ ][board] of %player%", "%player%'s (stylish|style|simple) [score][ ][board]"})
@Config("Main.StylishBoards")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprStylishPlayerBoard extends SimpleExpression<Scoreboard>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Scoreboard> getReturnType() {
		return Scoreboard.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (stylish|style|simple) [score][ ][board] of %player%";
	}
	@Override
	@Nullable
	protected Scoreboard[] get(Event e) {
		return new Scoreboard[]{player.getSingle(e).getScoreboard()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			player.getSingle(e).setScoreboard(StyleManager.get((String)delta[0]));
		} else if (mode == ChangeMode.RESET) {
			player.getSingle(e).setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}