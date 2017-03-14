package com.gmail.thelimeglass.Maps.Canvas;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.map.MapCursor;

import com.gmail.thelimeglass.Utils.Config;
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

@Syntax("[skellett] map cursor visibl(e|ity) [state] (of|for) [[map][ ]cursor] %mapcursor%")
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprCursorVisible extends SimpleExpression<Boolean> {
	
	private Expression<MapCursor> cursor;
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		cursor = (Expression<MapCursor>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[skellett] map cursor visibl(e|ity) [state] (of|for) [[map][ ]cursor] %mapcursor%";
	}
	@Nullable
	protected Boolean[] get(Event e) {
		if (cursor == null) {
			return null;
		}
		return new Boolean[]{cursor.getSingle(e).isVisible()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (cursor != null) {
				cursor.getSingle(e).setVisible((Boolean)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}