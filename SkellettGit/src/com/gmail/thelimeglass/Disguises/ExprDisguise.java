package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Dependency;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;

@Syntax({"[the] [skellett] [[Libs]Disguises] disguise [of] %entity% [(to|from) player %player%]", "[skellett] [[Libs]Disguises] %entity%'s disguise [(to|from) player %player%]"})
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@Dependency("LibsDisguises")
@RegisterEnum("disguise")
public class ExprDisguise extends SimpleExpression<Disguise> {
	
	private Expression<Entity> entity;
	private Expression<Player> player;
	public Class<? extends Disguise> getReturnType() {
		return Disguise.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		entity = (Expression<Entity>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "disguise of %entity% [(to|from) player %player%]";
	}
	@Override
	@Nullable
	protected Disguise[] get(Event e) {
		if (DisguiseAPI.getDisguise(entity.getSingle(e)) != null) {
			if (player != null) {
				return new Disguise[]{DisguiseAPI.getDisguise(player.getSingle(e), entity.getSingle(e))};
			} else {
				return new Disguise[]{DisguiseAPI.getDisguise(entity.getSingle(e))};
			}
		} else {
			return null;
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (entity != null) {
				if (player != null) {
					DisguiseAPI.disguiseToPlayers(entity.getSingle(e), (Disguise)delta[0], player.getSingle(e));
				} else {
					DisguiseAPI.disguiseToAll(entity.getSingle(e), (Disguise)delta[0]);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Disguise.class);
		}
		return null;
	}
}