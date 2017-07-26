package com.gmail.thelimeglass.BossBars;

import javax.annotation.Nullable;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondBarHasFlag extends Condition {
	
	//[skellett] [boss[ ]]bar %bossbar% (1¦[does] (ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [(the|a)] [boss[ ]]bar [flag] %barflag%
	
	private Expression<BossBar> bar;
	private Expression<BarFlag> flag;
	Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		bar = (Expression<BossBar>) e[0];
		flag = (Expression<BarFlag>) e[1];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [boss[ ]]bar %bossbar% (1¦(ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [(the|a)] [boss[ ]]bar [flag] %barflag%";
	}
	public boolean check(Event e) {
		if (bar == null) {
			return false ;
		}
		if (bar.getSingle(e).hasFlag(flag.getSingle(e))) {
			if (boo == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if (boo == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}