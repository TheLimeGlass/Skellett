package me.limeglass.skellett.elements.expressions;

import org.bukkit.entity.FishHook;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Name("Fishing State")
@Description("The fishing state of a fishing event.")
public class ExprFishingHook extends SimpleExpression<FishHook> {

	static {
		Skript.registerExpression(ExprFishingHook.class, FishHook.class, ExpressionType.SIMPLE, "[the] [event-]fish[ing]( |-)hook");
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends FishHook> getReturnType() {
		return FishHook.class;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (!getParser().isCurrentEvent(PlayerFishEvent.class)) {
			Skript.error("The fishing hook can only be used in the fishing event.");
			return false;
		}
		return true;
	}

	@Override
	protected @Nullable FishHook[] get(Event event) {
		return CollectionUtils.array(((PlayerFishEvent)event).getHook());
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the fishing hook";
	}

}
