package com.gmail.thelimeglass.Corpse;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
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

@Syntax({"[the] player of corpse %corpse%", "corpse %corpse%'s player"})
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprCorpsePlayer extends SimpleExpression<Player>{
	
	private Expression<CorpseData> corpse;
	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		corpse = (Expression<CorpseData>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] player of corpse %corpse%";
	}
	@Override
	@Nullable
	protected Player[] get(Event e) {
		if (corpse != null) {
			return new Player[]{corpse.getSingle(e).getPlayer()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (corpse != null) {
				corpse.getSingle(e).setPlayer((Player)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Player.class);
		}
		return null;
	}
}