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

@Syntax("[skellett] (delete|remove) [the] (stylish|style|simple) [score][ ][board] [with] [name[d]] %string%")
@Config("Main.StylishBoards")
@FullConfig
public class EffStylishDelete extends Effect {
	
	private Expression<String> name;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		name = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (delete|remove) (stylish|style|simple) [score][ ][board] [named] %string%";
	}
	@Override
	protected void execute(Event e) {
		StyleManager.deleteBoard(name.getSingle(e));
	}
}