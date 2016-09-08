package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
	
public class ExprFixShootGetArrow extends SimpleExpression<Entity> {
	
	//[skellett] [(event|get)] [the] shot (arrow|projectile)
	
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)EntityShootBowEvent.class)) {
			Skript.error((String)"You can not use Get Arrow expression in any event but 'on entity shoot:' event!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Shooter Arrow";
	}
	@Nullable
	protected Entity[] get(Event e) {
		return new Entity[]{((EntityShootBowEvent)e).getProjectile()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			((EntityShootBowEvent)e).setProjectile((Entity)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Entity.class);
		}
		return null;
	}
}