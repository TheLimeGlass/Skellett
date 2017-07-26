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

@Syntax("(delete|remove) tab[list] item [[with] id] %-string% from %players%")
@Config("PluginHooks.TabListAPI")
@FullConfig
@MainConfig
public class EffPlayerRemoveTabItem extends Effect {
	
	private Expression<String> ID;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|remove) tab[list] item ([[with] id] %-string%|%-tabitem%) from %players%";
	}
	@Override
	protected void execute(Event e) {
		if (!TablistManager.containsTabItem(ID.getSingle(e))) {
			return;
		}
		for (Player p : players.getAll(e)) {
			TabAPI.removeItem(p, TablistManager.get(ID.getSingle(e)));
		}
	}
}
