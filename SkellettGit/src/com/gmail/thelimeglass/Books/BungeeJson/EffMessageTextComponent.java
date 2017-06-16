package com.gmail.thelimeglass.Books.BungeeJson;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;

@Syntax("message text component %textcomponent% to %players%")
@Config("Main.ChatComponent")
@FullConfig
public class EffMessageTextComponent extends Effect {
	
	private Expression<TextComponent> component;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		component = (Expression<TextComponent>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "message text component %textcomponent% [to %players%]";
	}
	@Override
	protected void execute(Event e) {
		if (component == null || players == null) return;
		for (Player p : players.getAll(e)) {
			p.spigot().sendMessage(component.getSingle(e));
		}
	}
}