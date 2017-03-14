package com.gmail.thelimeglass.Stylishboards;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (delete|remove) [the] id [based] [score] [(with [id]|named)] %string% (in|from|for|of) (stylish|style|simple) [score][ ]board [with] [name[d]] %string%")
@Config("Main.StylishBoards")
@FullConfig
public class EffStylishSlotDelete extends Effect {
	
	private Expression<String> ID, board;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		board = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (delete|remove) [the] id [based] [score] [(with [id]|named)] %string% (in|for|of) (stylish|style|simple) [score][ ]board [named] %string%";
	}
	@Override
	protected void execute(Event e) {
		StyleManager.deleteScore(ID.getSingle(e), board.getSingle(e));
	}
}