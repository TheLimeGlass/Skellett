package com.gmail.thelimeglass.Stylishboards;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] [(update|edit)] [the] [display] title of (stylish|style|simple) [score][ ][board] [named] %string%", "[skellett] [(update|edit)] [the] (stylish|style|simple) [score][ ][board] %string%['s] [display] title"})
@Config("Main.StylishBoards")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprStylishTitle extends SimpleExpression<String>{
	
	private Expression<String> board;
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
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		board = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [skellett] [display] title of (stylish|style|simple) [score][ ][board] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{StyleManager.getTitle(board.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			StyleManager.setTitle(board.getSingle(e), (String)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}