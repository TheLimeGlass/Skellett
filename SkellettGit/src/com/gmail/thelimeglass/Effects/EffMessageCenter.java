package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(message|send [message]) center[ed] %strings% to %players% [[with[ text]] %-string%]")
@Config("CenterMessage")
public class EffMessageCenter extends Effect {
	
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
		for (String s : string.getAll(e)) {
			String message = "";
			int num = (Skellett.plugin.getConfig().getInt("CenterMessageSize", 62) - s.length()) / 2;
			for (int i = 0; i < num; i++ ) {
				if (centersymbol != null) {
					message += Skellett.cc("&r" + centersymbol.getSingle(e));
				} else {
					message += " ";
				}
			}
			message += s;
			for ( int i = 0; i < num; i++ ) {
				if (centersymbol != null) {
					message += Skellett.cc("&r" + centersymbol.getSingle(e));
				} else {
					message += " ";
				}
			}
			for (Player p : player.getAll(e)) {
				p.sendMessage(message);
			}
		}
	}
}