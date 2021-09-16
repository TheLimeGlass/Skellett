package com.gmail.thelimeglass.Books.BungeeJson;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

@Syntax("add hover event with action %hovereventaction% (and|with) [(value|text)] %string% to [text component] %textcomponent%")
@Config("Main.ChatComponent")
@FullConfig
public class EffAddHoverEvent extends Effect {
	
	private Expression<HoverEvent.Action> action;
	private Expression<String> data;
	private Expression<TextComponent> textcomponent;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		action = (Expression<HoverEvent.Action>) e[0];
		data = (Expression<String>) e[1];
		textcomponent = (Expression<TextComponent>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "add click event with action %clickeventaction% with [(value|text|link)] %string% to [text component] %textcomponent%";
	}
	@Override
	protected void execute(Event e) {
		if (textcomponent == null || action == null || data == null)
			return;
		textcomponent.getSingle(e).setHoverEvent(new HoverEvent(action.getSingle(e), new Text(new ComponentBuilder(data.getSingle(e)).create())));
	}
}