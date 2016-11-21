package com.gmail.thelimeglass.Bungee;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketRunCommand;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

public class EffExecuteBungeeCommand extends Effect {

	//[skellett[cord]] (make|run|execute) bungee[[ ][cord]] [console] command %string%
	
	private Expression<String> cmd;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		cmd = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett[cord]] (make|run|execute) bungee[[ ][cord]] [console] command %string%";
	}
	@Override
	protected void execute(Event e) {
		PacketRunCommand packet = new PacketRunCommand(cmd.getSingle(e));
		packet.send();
	}
}
