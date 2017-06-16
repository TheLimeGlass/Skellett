package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Rotation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] rotation (of|from) item[ ]frame %entity%", "[skellett] %entity%'s item[ ]frame rotation", "[skellett] item[ ]frame %entity%'s rotation"})
@Config("ItemframeRotation")
@PropertyType(ExpressionType.COMBINED)
@RegisterEnum("rotation")
public class ExprItemframeRotation extends SimpleExpression<Rotation>{
	
	private Expression<Entity> entity;
	@Override
	public Class<? extends Rotation> getReturnType() {
		return Rotation.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "rotation (of|from) item[ ]frame %entity%";
	}
	@Override
	@Nullable
	protected Rotation[] get(Event e) {
		if (entity != null) {
			if (entity.getSingle(e) instanceof ItemFrame) {
				return new Rotation[]{((ItemFrame)entity.getSingle(e)).getRotation()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (entity != null) {
				if (entity.getSingle(e) instanceof ItemFrame) {
					((ItemFrame)entity.getSingle(e)).setRotation((Rotation)delta[0]);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Rotation.class);
		}
		return null;
	}
}