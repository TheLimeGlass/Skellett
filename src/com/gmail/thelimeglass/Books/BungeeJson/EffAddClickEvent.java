package com.gmail.thelimeglass.Books.BungeeJson;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

@Syntax("add click event with action %clickeventaction% (and|with|to) [(execute|text|link)] %string% to [text component] %textcomponent%")
@Config("Main.ChatComponent")
@FullConfig
public class EffAddClickEvent extends Effect {
	
	private Expression<ClickEvent.Action> action;
	private Expression<String> data;
	private Expression<TextComponent> component;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		action = (Expression<ClickEvent.Action>) e[0];
		data = (Expression<String>) e[1];
		component = (Expression<TextComponent>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "add click event with action %clickeventaction% (and|with) [(execute|link)] %string% to [text component] %textcomponent%";
	}
	@Override
	protected void execute(Event e) {
		if (component == null || action == null || data == null) return;
		component.getSingle(e).setClickEvent(new ClickEvent(action.getSingle(e), data.getSingle(e)));
	}
}