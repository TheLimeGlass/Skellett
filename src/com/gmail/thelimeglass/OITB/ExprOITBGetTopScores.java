package com.gmail.thelimeglass.OITB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.wazup.oitb.Enums.StatType;
import me.wazup.oitb.OneInTheBattle;
import me.wazup.oitb.OneInTheBattleAPI;

public class ExprOITBGetTopScores extends SimpleExpression<Integer> {
	
	//[(the|all)] [of] [the] top %integer% scores of [the] [OITB] [stat][istic] %StatType%
	
	private Expression<Integer> number;
	private Expression<StatType> stat;
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
		number = (Expression<Integer>) e[0];
		stat = (Expression<StatType>) e[1];
		return true;
	}
	@Override
	public String toString(Event event, boolean b) {
		return "[(the|all)] [of] [the] top %integer% scores of [the] [OITB] [stat][istic] %OITBStatEnum%";
	}
	@Override
	protected Integer[] get(final Event e) {
		OneInTheBattleAPI api = OneInTheBattle.api;
		ArrayList<Integer> topplayers = new ArrayList<>();
		List<Entry<String, Integer>> top = api.getTopPlayers(stat.getSingle(e), 10);
		for(int i = 0; i < number.getSingle(e); i++){
			topplayers.add(top.get(i).getValue());
		}
		return topplayers.toArray(new Integer[topplayers.size()]);
	}
}