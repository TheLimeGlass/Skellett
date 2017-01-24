package com.gmail.thelimeglass.versionControl;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.DetectVersion;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] absorption hearts of %player%", "[skellett] %player%'s absorption hearts"})
@Config("AbsorptionHearts")
@DetectVersion
@PropertyType("COMBINED")
public class ExprAbsorptionHearts1_9_R2 extends SimpleExpression<Number>{
	
	private Expression<Player> player;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] absorption hearts of %player%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{((CraftPlayer) player.getSingle(e)).getHandle().getAbsorptionHearts()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = ((CraftPlayer) player.getSingle(e)).getHandle().getAbsorptionHearts();
		if (mode == ChangeMode.SET) {
			((CraftPlayer) player.getSingle(e)).getHandle().setAbsorptionHearts(num.floatValue());
		} else if (mode == ChangeMode.RESET) {
			((CraftPlayer) player.getSingle(e)).getHandle().setAbsorptionHearts(0);
		} else if (mode == ChangeMode.ADD) {
			((CraftPlayer) player.getSingle(e)).getHandle().setAbsorptionHearts(numNow.floatValue() + num.floatValue());
		} else if (mode == ChangeMode.REMOVE) {
			((CraftPlayer) player.getSingle(e)).getHandle().setAbsorptionHearts(numNow.floatValue() - num.floatValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}