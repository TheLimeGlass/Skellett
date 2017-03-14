package com.gmail.thelimeglass.PlayerPoints;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax({"pay %string% %number% [player[ ]]points from %string%['s account]", "[(force|make)] %string% [to] pay %number% [player[ ]]points to %string%"})
@Config("PluginHooks.PlayerPoints")
@FullConfig
@MainConfig
public class EffPayPlayerPoints extends Effect {
	
	private Expression<String> taker, receiver;
	private Expression<Number> amount;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (matchedPattern == 0) {
			receiver = (Expression<String>) e[0];
			amount = (Expression<Number>) e[1];
			taker = (Expression<String>) e[2];
		} else {
			taker = (Expression<String>) e[0];
			receiver = (Expression<String>) e[1];
			amount = (Expression<Number>) e[2];
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[(force|make)] %string% [to] pay %number% [player[ ]]points to %string%";
	}
	@SuppressWarnings("deprecation")
	@Override
	protected void execute(Event e) {
		
		UUID takerUniqueId = null;
		UUID receiverUiqueId = null;
		try {
			takerUniqueId = UUID.fromString(taker.getSingle(e));
			receiverUiqueId = UUID.fromString(receiver.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		if (takerUniqueId == null && receiverUiqueId == null) {
			Skellett.playerPoints.getAPI().pay(taker.getSingle(e), receiver.getSingle(e), amount.getSingle(e).intValue());
		} else {
			Skellett.playerPoints.getAPI().pay(takerUniqueId, receiverUiqueId, amount.getSingle(e).intValue());
		}
	}
}