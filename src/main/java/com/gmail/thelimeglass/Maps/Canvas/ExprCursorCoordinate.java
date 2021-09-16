package com.gmail.thelimeglass.Maps.Canvas;

import org.bukkit.event.Event;
import org.bukkit.map.MapCursor;
import org.eclipse.jdt.annotation.Nullable;

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

@Syntax("[skellett] map cursor (1¦x|2¦y)(-| )(coord[inate]|pos[ition]|loc[ation])[s] of [[map][ ]cursor] %mapcursor%")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprCursorCoordinate extends SimpleExpression<Number> {
	
	private Expression<MapCursor> cursor;
	private Integer marker;
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		cursor = (Expression<MapCursor>) e[0];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] map cursor (1¦x|2¦y|3¦z)(-| )(coord[inate]|pos[ition]|loc[ation])[s] of [[map][ ]cursor] %mapcursor%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (cursor == null) {
			return null;
		}
		if (marker == 1) {
			return new Number[]{cursor.getSingle(e).getX()};
		} else {
			return new Number[]{cursor.getSingle(e).getY()};
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (cursor != null) {
			Number num = (Number)delta[0];
			byte b = (byte)num.intValue();
			if (mode == ChangeMode.SET) {
				if (marker == 1) {
					cursor.getSingle(e).setX(b);
				} else {
					cursor.getSingle(e).setY(b);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}