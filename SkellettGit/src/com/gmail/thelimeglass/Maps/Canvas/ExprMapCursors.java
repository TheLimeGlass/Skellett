package com.gmail.thelimeglass.Maps.Canvas;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapView;

import com.gmail.thelimeglass.Maps.SkellettMapRenderer;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[skellett] [(the|all)] [of] [the] map cursors (in|on|for) [skellett] [map] %map%")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprMapCursors extends SimpleExpression<MapCursor>{
	
	private Expression<MapView> map;
	@Override
	public Class<? extends MapCursor> getReturnType() {
		return MapCursor.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [(the|all)] [of] [the] map cursors (in|on|for) [skellett] [map] %map%";
	}
	@Override
	@Nullable
	protected MapCursor[] get(Event e) {
		ArrayList<MapCursor> cursors = new ArrayList<MapCursor>();
		MapCursorCollection collection = SkellettMapRenderer.getCanvas(map.getSingle(e)).getCursors();
		for (int i = 0; i < collection.size(); i++) {
			cursors.add(collection.getCursor(i));
		}
		return cursors.toArray(new MapCursor[cursors.size()]);
	}
}