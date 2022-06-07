package me.limeglass.skellett.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;

@Name("Fishing State")
@Description("The fishing state of a fishing event.")
public class ExprFishingState extends EventValueExpression<State> {

	static {
		Skript.registerExpression(ExprFishingState.class, State.class, ExpressionType.SIMPLE, "[the] [event-]fish[ing]( |-)state");
	}

	public ExprFishingState() {
		super(State.class);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the fishing state";
	}

}
