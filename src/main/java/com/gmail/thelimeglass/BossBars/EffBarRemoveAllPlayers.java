package com.gmail.thelimeglass.BossBars;

import javax.annotation.Nullable;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffBarRemoveAllPlayers extends Effect {
	
	//[skellett] remove [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%
	
	private Expression<BossBar> bar;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		bar = (Expression<BossBar>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] remove [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%";
	}
	@Override
	protected void execute(Event e) {
		if (bar == null) {
			return;
		}
		bar.getSingle(e).removeAll();
	}
}