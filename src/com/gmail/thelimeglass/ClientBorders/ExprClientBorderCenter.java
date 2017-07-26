package com.gmail.thelimeglass.ClientBorders;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.ReflectionSyntax.ClientBorderManager;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[client [side]] [world] border center [location] (for|of) %player%", "%player%'s [client [side]] [world] border center [location]"})
@Config("Main.ClientWorldBorders")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprClientBorderCenter extends SimpleExpression<Location>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "[client [side]] [world] border center [location] (for|of) %player%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if (player != null) {
			return new Location[]{ClientBorderManager.getCenter(player.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (player != null) {
			ClientBorderManager.setCenter(player.getSingle(e), (Location)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Location.class);
		}
		return null;
	}
}