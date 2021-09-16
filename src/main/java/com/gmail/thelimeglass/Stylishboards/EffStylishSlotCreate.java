package com.gmail.thelimeglass.Stylishboards;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] create [a[n]] [new] id [based] [score] [(with [id]|named)] %string% [(with|and)] [(text|string)] %string% [(in|with|for|and)] slot %number% (in|for|of) (stylish|style|simple) [score][ ]board [with] [name[d]] %string%")
@Config("Main.StylishBoards")
@FullConfig
public class EffStylishSlotCreate extends Effect {
	
	private Expression<String> ID, value, board;
	private Expression<Number> slot;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		value = (Expression<String>) e[1];
		slot = (Expression<Number>) e[2];
		board = (Expression<String>) e[3];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] create [a] [new] id [based] [score] [(with [id]|named)] %string% [(with|and)] [(text|string)] %string% [(in|with|for|and)] slot %number% (in|for|of) (stylish|style|simple) [score][ ]board [named] %string%";
	}
	@Override
	protected void execute(Event e) {
		Number number = slot.getSingle(e);
		StyleManager.createScore(ID.getSingle(e), board.getSingle(e), value.getSingle(e), number.intValue());
	}
}