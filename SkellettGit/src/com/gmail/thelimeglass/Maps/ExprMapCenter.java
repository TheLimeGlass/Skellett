package com.gmail.thelimeglass.Maps;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"center (1¦x|2¦z)[( |-)][(position|coord[inate])] (of|from|in) [map] %map%", "%map%'s center (1¦x|2¦z)[( |-)][(position|coord[inate])]"})
@Config("Main.Maps")
@FullConfig
@PropertyType("COMBINED")
public class ExprMapCenter extends SimpleExpression<Number> {
	
	private Expression<MapView> map;
	private Integer marker;
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		marker = parser.mark;
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "center (1¦x|2¦z) [(position|coord[inate])] (of|from|in) [map] %map%";
	}
	@Nullable
	protected Number[] get(Event e) {
		if (map.getSingle(e) == null) {
			return null;
		}
		if (marker == 1) {
			return new Number[]{map.getSingle(e).getCenterX()};
		} else {
			return new Number[]{map.getSingle(e).getCenterZ()};
		}
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number num = (Number)delta[0];
		Number numNow = 0;
		if (marker == 1) {
			numNow = map.getSingle(e).getCenterX();
		} else {
			numNow = map.getSingle(e).getCenterZ();
		}
		if (mode == ChangeMode.SET) {
			if (marker == 1) {
				map.getSingle(e).setCenterX(num.intValue());
			} else {
				map.getSingle(e).setCenterZ(num.intValue());
			}
		} else if (mode == ChangeMode.ADD) {
			if (marker == 1) {
				map.getSingle(e).setCenterX(num.intValue() + numNow.intValue());
			} else {
				map.getSingle(e).setCenterZ(num.intValue() + numNow.intValue());
			}
		} else if (mode == ChangeMode.REMOVE) {
			if (marker == 1) {
				map.getSingle(e).setCenterX(num.intValue() - numNow.intValue());
			} else {
				map.getSingle(e).setCenterZ(num.intValue() - numNow.intValue());
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}