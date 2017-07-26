package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Pastebin;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[(get|make|create)] [a] paste[ ]bin [link] with (data|lines) %strings%[(,| and|with)] (title|header) %string%[(,| and|with|for)] [api] key %string%")
@Config("Pastebin")
@PropertyType(ExpressionType.COMBINED)
public class ExprPastebin extends SimpleExpression<String>{
	
	private Expression<String> data, title, key;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		data = (Expression<String>) e[0];
		title = (Expression<String>) e[1];
		key = (Expression<String>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(get|make|create)] [a] paste[ ]bin [link] with (data|lines) %strings%[(,| and|with)] (title|header) %string%[(,| and|with|for)] [api] key %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (data != null && title != null && key != null) {
			Pastebin reporter = new Pastebin(key.getSingle(e));
			Pastebin.Paste paste = new Pastebin.Paste();
			for (String line : data.getAll(e)) {
				paste.addLine(line);
			}
			return new String[]{reporter.post(title.getSingle(e), paste)};
		}
		return null;
	}
}