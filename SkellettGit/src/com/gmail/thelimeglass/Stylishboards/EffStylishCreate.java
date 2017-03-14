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

@Syntax("[skellett] create [a] [new] (stylish|style|simple) [score][ ]board [with] [name[d]] %string%")
@Config("Main.StylishBoards")
@FullConfig
public class EffStylishCreate extends Effect {
	
	private Expression<String> name;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		name = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] create [a] [new] (stylish|style|simple) [score][ ]board [named] %string%";
	}
	@Override
	protected void execute(Event e) {
		StyleManager.createBoard(name.getSingle(e));
	}
}