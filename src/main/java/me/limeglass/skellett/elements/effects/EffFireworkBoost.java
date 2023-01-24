package me.limeglass.skellett.elements.effects;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.RequiredPlugins;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Firework Boost")
@Description("Boosts a player based on the provided item.")
@Examples("boost the player using a long firework rocket")
@RequiredPlugins("MC 1.19.3+")
@Since("2.0.9")
public class EffFireworkBoost extends Effect {

	static {
		if (Skript.methodExists(HumanEntity.class, "fireworkBoost", ItemStack.class))
			Skript.registerEffect(EffFireworkBoost.class, "(boost|launch) %players% using [firework [rocket]] %itemstack%");
	}

	private Expression<ItemStack> firework;
	private Expression<Player> players;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		this.players = (Expression<Player>) exprs[0];
		this.firework = (Expression<ItemStack>) exprs[1];
		return true;
	}

	@Override
	protected void execute(Event event) {
		ItemStack firework = this.firework.getSingle(event);
		if (firework == null)
			return;
		for (HumanEntity player : players.getArray(event))
			player.fireworkBoost(firework);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "boost " + players.toString(event, debug) + " using firework rocket " + firework.toString(event, debug);
	}

}
