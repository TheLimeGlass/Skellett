package com.gmail.thelimeglass.Feudal;

import java.util.ArrayList;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

public class ExprFeudalKingdomFighters extends SimpleExpression<String>{
	
	//[(the|all)] [of] [the] Feudal [kingdom] fighter[[']s] of %kingdom%
	//[(the|all)] [of] [the] %kingdom%'s Feudal kingdom fighter[[']s]
	//[(the|all)] [of] [the] fighter[[']s] of Feudal [kingdom] %kingdom%
	
	Feudal feudal = Feudal.getPlugin();
	private Expression<Kingdom> kingdom;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		kingdom = (Expression<Kingdom>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] fighter[[']s] of Feudal [kingdom] %kingdom%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> fighters = new ArrayList<>();
		fighters.addAll(kingdom.getSingle(e).getFighters());
		return fighters.toArray(new String[fighters.size()]);
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.ADD) {
			if (delta[0] instanceof Player) {
				kingdom.getSingle(e).addFigher(((Player)delta[0]).getUniqueId().toString());
			} else {
				kingdom.getSingle(e).addFigher((String)delta[0]);
			}
		} else if (mode == ChangeMode.REMOVE) {
			if (delta[0] instanceof Player) {
				kingdom.getSingle(e).removeFigher(((Player)delta[0]).getUniqueId().toString());
			} else {
				kingdom.getSingle(e).removeFigher((String)delta[0]);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Player.class, String.class);
		}
		return null;
	}
}