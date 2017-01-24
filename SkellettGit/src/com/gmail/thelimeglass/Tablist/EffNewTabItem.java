package com.gmail.thelimeglass.Tablist;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.GameMode;
import org.bukkit.event.Event;
import org.inventivetalent.tabapi.TabItem;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[(make|create)] [a] [new] tab[list] [item] [[with] id] %string% with [display] name %string%[(,| and| with)] [(skin|head|skull) [icon] %-string%][(,| and| with)] [(ping|latency) %-number%][(,| and| with)] [spectator [mode] %-boolean%]")
@Config("PluginHooks.TabListAPI")
@FullConfig
@MainConfig
public class EffNewTabItem extends Effect {
	
	private Expression<String> ID, name, skin;
	private Expression<Number> ping;
	private Expression<Boolean> gamemode;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<String>) e[0];
		name = (Expression<String>) e[1];
		skin = (Expression<String>) e[2];
		ping = (Expression<Number>) e[3];
		gamemode = (Expression<Boolean>) e[4];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(make|create)] [a] [new] tab[list] [item] [[with] id] %string% with [display] name %string%[(,| and| with)] [(skin|head|skull) [icon] %-string%][(,| and| with)] [(ping|latency) %-number%][(,| and| with)] [spectator [mode] %-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		TabItem tabitem = new TabItem(name.getSingle(e));
		if (ping != null && gamemode != null && skin == null) {
			if (gamemode.getSingle(e)) {
				tabitem = new TabItem(name.getSingle(e), ping.getSingle(e).intValue(), GameMode.SPECTATOR);
			} else {
				tabitem = new TabItem(name.getSingle(e), ping.getSingle(e).intValue(), GameMode.CREATIVE);
			}
		} else if (ping != null && gamemode != null && skin != null) {
			if (gamemode.getSingle(e)) {
				tabitem = new TabItem(name.getSingle(e), skin.getSingle(e), ping.getSingle(e).intValue(), GameMode.SPECTATOR);
			} else {
				tabitem = new TabItem(name.getSingle(e), skin.getSingle(e), ping.getSingle(e).intValue(), GameMode.CREATIVE);
			}
		}
		if (TablistManager.containsTabItem(ID.getSingle(e))) {
			return;
		}
		TablistManager.addTabItem(ID.getSingle(e), tabitem);
	}
}
