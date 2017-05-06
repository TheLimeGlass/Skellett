package com.gmail.thelimeglass.Tablist;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(remove|delete) tab[list] item [[with] id] %string%")
@Config("PluginHooks.TabListAPI")
@FullConfig
@MainConfig
public class EffDeleteTabItem extends Effect {
	
	private Expression<String> ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		ID = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(remove|delete) tab[list] item [[with] id] %string%";
	}
	@Override
	protected void execute(Event e) {
		if (TablistManager.containsTabItem(ID.getSingle(e))) {
			TablistManager.removeTabItem(ID.getSingle(e));
		}
	}
}
