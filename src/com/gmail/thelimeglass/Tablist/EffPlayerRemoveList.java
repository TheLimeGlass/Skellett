package com.gmail.thelimeglass.Tablist;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.inventivetalent.tabapi.TabAPI;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

@Syntax("(remove|reset|delete) [the] tab[list] [items] (of|for) %players%")
@Config("PluginHooks.TabListAPI")
@FullConfig
@MainConfig
public class EffPlayerRemoveList extends Effect {
	
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		players = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(remove|reset|delete) [the] tab[list] [items] (of|for) %players%";
	}
	@Override
	protected void execute(Event e) {
		for (Player p : players.getAll(e)) {
			TabAPI.removeTab(p);
		}
	}
}
