package com.gmail.thelimeglass.Regenerator;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("re[ ]configure [the] [skellett] regenerator with ID %string%")
@Config("Main.Regenerator")
@FullConfig
public class EffReconfigureRegenerator extends Effect {
	
	private Expression<String> ID;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "re[ ]configure [the] [skellett] regenerator with ID %string%";
	}
	@Override
	protected void execute(Event e) {
		RegeneratorManager.reconfigure(ID.getSingle(e), RegeneratorManager.getPos1(ID.getSingle(e)), RegeneratorManager.getPos2(ID.getSingle(e)));
	}
}