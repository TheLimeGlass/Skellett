package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBossBarPlayers extends SimpleExpression<Player> {

	static {
		Skript.registerExpression(ExprBossBarPlayers.class, Player.class, ExpressionType.PROPERTY, "[all [of]] [the] players (from|of) %bossbars%", "[all [of]] [the] %bossbars%'[s] players");
	}

	private Expression<BossBar> bossbars;

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		bossbars = (Expression<BossBar>) exprs[0];
		return true;
	}

	@Override
	protected @Nullable Player[] get(Event event) {
		return bossbars.stream(event).flatMap(bossbar -> bossbar.getPlayers().stream()).toArray(Player[]::new);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "players of bossbars " + bossbars.toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(Player[].class, Player.class);
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, ChangeMode mode) {
		Player[] players = delta == null ? null : (Player[]) delta;
		if (players == null)
			return;
		switch (mode) {
			case ADD:
				for (BossBar bossbar : bossbars.getArray(event)) {
					for (Player player : players)
						bossbar.addPlayer(player);
				}
				break;
			case RESET:
			case DELETE:
				for (BossBar bossbar : bossbars.getArray(event))
					bossbar.removeAll();
				break;
			case REMOVE:
			case REMOVE_ALL:
				for (BossBar bossbar : bossbars.getArray(event)) {
					for (Player player : players)
						bossbar.removePlayer(player);
				}
				break;
			case SET:
				for (BossBar bossbar : bossbars.getArray(event)) {
					bossbar.removeAll();
					for (Player player : players)
						bossbar.addPlayer(player);
				}
				break;
			default:
				break;
		}
	}

}
