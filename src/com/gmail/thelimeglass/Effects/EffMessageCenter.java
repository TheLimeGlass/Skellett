package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffMessageCenter extends Effect {
	
	//(message|send [message]) center[ed] %strings% to %players% [[with[ text]] %-string%]
	
	private Expression<String> string;
	private Expression<Player> player;
	private Expression<String> centersymbol;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		player = (Expression<Player>) e[1];
		centersymbol = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(message|send [message]) center[ed] %strings% to %players% [[with[ text]] %-string%]";
	}
	@Override
	protected void execute(Event e) {
		String message = "";
		int num = (62 - string.getSingle(e).length()) / 2;
		for ( int i = 0; i < num; i++ ) {
			if (centersymbol != null) {
				message += Skellett.cc("&r" + centersymbol.getSingle(e));
			} else {
				message += " ";
			}
		}
		message += string.getSingle(e);
		for ( int i = 0; i < num; i++ ) {
			if (centersymbol != null) {
				message += Skellett.cc("&r" + centersymbol.getSingle(e));
			} else {
				message += " ";
			}
		}
		player.getSingle(e).sendMessage(message);
	}
}