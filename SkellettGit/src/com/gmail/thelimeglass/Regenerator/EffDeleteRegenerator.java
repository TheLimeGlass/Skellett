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

@Syntax("(delete|remove) [the] [skellett] regenerator with ID %string% [re[ ]build %-boolean%]")
@Config("Main.Regenerator")
@FullConfig
public class EffDeleteRegenerator extends Effect {
	
	private Expression<String> ID;
	private Expression<Boolean> rebuild;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		rebuild = (Expression<Boolean>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(delete|remove) [the] [skellett] regenerator with ID %string% [re[ ]build %-boolean]";
	}
	@Override
	protected void execute(Event e) {
		Boolean rebuilding = true;
		if (rebuild != null) {
			rebuilding = rebuild.getSingle(e);
		}
		RegeneratorManager.removeRegenerator(ID.getSingle(e), rebuilding);
	}
}