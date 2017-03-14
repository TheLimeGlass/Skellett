package com.gmail.thelimeglass.ArmorStands;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.util.EulerAngle;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("(1¦x|2¦y|3¦z)(-| )(coord[inate]|pos[ition]|loc[ation])[s] of euler angle %eulerangle%")
@Config("Main.ArmorStands")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
@Disabled
public class ExprEulerAngleCoordinate extends SimpleExpression<Number>{
	
	private Expression<EulerAngle> angle;
	private Integer coord;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		angle = (Expression<EulerAngle>) e[0];
		coord = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(1¦x|2¦y|3¦z)(-| )(coord[inate]|pos[ition]|loc[ation])[s] of euler angle %eulerangle%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (coord == 1) {
			return new Number[]{angle.getSingle(e).getX()};
		} else if (coord == 2) {
			return new Number[]{angle.getSingle(e).getY()};
		} else if (coord == 3) {
			return new Number[]{angle.getSingle(e).getZ()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (coord == null) {
			return;
		}
		Number num = (Number)delta[0];
		if (mode == ChangeMode.SET) {
			if (coord == 1) {
				angle.getSingle(e).setX(num.doubleValue());
			} else if (coord == 2) {
				angle.getSingle(e).setY(num.doubleValue());
			} else {
				angle.getSingle(e).setZ(num.doubleValue());
			}
		} else if (mode == ChangeMode.ADD) {
			if (coord == 1) {
				angle.getSingle(e).setX(angle.getSingle(e).getX() + num.doubleValue());
			} else if (coord == 2) {
				angle.getSingle(e).setY(angle.getSingle(e).getY() + num.doubleValue());
			} else {
				angle.getSingle(e).setZ(angle.getSingle(e).getZ() + num.doubleValue());
			}
		} else if (mode == ChangeMode.REMOVE) {
			if (coord == 1) {
				angle.getSingle(e).setX(angle.getSingle(e).getX() - num.doubleValue());
			} else if (coord == 2) {
				angle.getSingle(e).setY(angle.getSingle(e).getY() - num.doubleValue());
			} else {
				angle.getSingle(e).setZ(angle.getSingle(e).getZ() - num.doubleValue());
			}
		}		
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}