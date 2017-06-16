package com.gmail.thelimeglass.Books.BungeeJson;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;

@Syntax("[a] [new] text component [with [text]] %string%")
@Config("Main.ChatComponent")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNewTextComponent extends SimpleExpression<TextComponent>{
	
	private Expression<String> string;
	@Override
	public Class<? extends TextComponent> getReturnType() {
		return TextComponent.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[a] [new] text component [with [text]] %string%";
	}
	@Override
	@Nullable
	protected TextComponent[] get(Event e) {
		return string != null ? new TextComponent[]{new TextComponent(string.getSingle(e))} : null;
	}
}