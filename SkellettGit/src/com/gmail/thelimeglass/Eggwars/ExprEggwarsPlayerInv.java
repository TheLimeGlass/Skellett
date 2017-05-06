package com.gmail.thelimeglass.Eggwars;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import es.minetsii.eggwars.objects.Arena;
import es.minetsii.eggwars.objects.EwPlayer;

@Syntax({"[the] egg[ ]wars arena of player %ewplayer%", "%player%'s egg[ ]war[s] arena"})
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=Arena.class, value="ewarena")
public class ExprEggwarsPlayerInv extends SimpleExpression<Arena>{
	
	private Expression<EwPlayer> player;
	@Override
	public Class<? extends Arena> getReturnType() {
		return Arena.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<EwPlayer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] egg[ ]wars arena of player %ewplayer%";
	}
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		if (player != null) {
			return new Arena[]{player.getSingle(e).getArena()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (player != null) {
				player.getSingle(e).setArena((Arena)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Arena.class);
		return null;
	}
}