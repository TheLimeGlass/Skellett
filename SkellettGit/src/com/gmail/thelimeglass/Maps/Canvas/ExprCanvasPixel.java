package com.gmail.thelimeglass.Maps.Canvas;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Maps.SkellettMapRenderer;
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

@Syntax("[skellett] map pixle [colo[u]r] at [coordinate[s]] [x] %number%(,| and) [y] %number% (on|in) [skellett] map %map%")
@Config("Main.Maps")
@FullConfig
@PropertyType("COMBINED")
public class ExprCanvasPixel extends SimpleExpression<Number> {
	
	private Expression<Number> x, y;
	private Expression<MapView> map;
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		x = (Expression<Number>) e[0];
		y = (Expression<Number>) e[1];
		map = (Expression<MapView>) e[2];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[skellett] map pixle [colo[u]r] at [coordinate[s]] [x] %number%(,| and) [y] %number% (on|in) [skellett] map %map%";
	}
	@Nullable
	protected Number[] get(Event e) {
		if (map == null || x == null || y == null) {
			return null;
		}
		return new Number[]{SkellettMapRenderer.getCanvas(map.getSingle(e)).getPixel(x.getSingle(e).intValue(), y.getSingle(e).intValue())};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number number = (Number)delta[0];
		if (mode == ChangeMode.SET) {
			if (map != null || x != null || y != null) {
				byte b = (byte)number.intValue();
				SkellettMapRenderer.getCanvas(map.getSingle(e)).setPixel(x.getSingle(e).intValue(), y.getSingle(e).intValue(), b);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}