package com.gmail.thelimeglass.Tablist;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.inventivetalent.tabapi.TabAPI;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("clear [the] tab[list] [items] (of|for) %players%")
@Config("PluginHooks.TabListAPI")
@FullConfig
@MainConfig
public class EffPlayerClearList extends Effect {
	
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		players = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "clear [the] tab[list] [items] (of|for) %players%";
	}
	@Override
	protected void execute(Event e) {
		for (Player p : players.getAll(e)) {
			if (Skellett.getPlugin(Skellett.class).getConfig().getBoolean("TabClearPlayer")) {
				if (TabAPI.getItems(p) != null) {
					TabAPI.clearAllItems(p);
				}
			} else {
				TabAPI.clearAllItems(p);
			}
		}
	}
}
