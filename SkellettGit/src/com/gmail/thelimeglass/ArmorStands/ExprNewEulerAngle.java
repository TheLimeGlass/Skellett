package com.gmail.thelimeglass.ArmorStands;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.util.EulerAngle;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[a] new euler angle")
@Config("Main.ArmorStands")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprNewEulerAngle extends SimpleExpression<EulerAngle>{
	
	@Override
	public Class<? extends EulerAngle> getReturnType() {
		return EulerAngle.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "armo[u]r stand boots of %entity%";
	}
	@Override
	@Nullable
	protected EulerAngle[] get(Event e) {
		return new EulerAngle[]{new EulerAngle(0, 0, 0)};
	}
}