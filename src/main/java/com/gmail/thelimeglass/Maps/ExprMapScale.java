package com.gmail.thelimeglass.Maps;

import org.bukkit.event.Event;
import org.bukkit.map.MapView;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
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

@Syntax({"map (scale|size) of %map%", "map %map%'s (scale|size)", "(scale|size) of map %map%"})
@Config("Main.Maps")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterEnum("mapscale")
public class ExprMapScale extends SimpleExpression<MapView.Scale> {
	
	private Expression<MapView> map;
	public Class<? extends MapView.Scale> getReturnType() {
		return MapView.Scale.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		map = (Expression<MapView>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "map (scale|size) of %map%";
	}

	protected MapView.Scale[] get(Event e) {
		if (map.getSingle(e) == null) {
			return null;
		}
		return new MapView.Scale[]{map.getSingle(e).getScale()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (map.getSingle(e) == null) {
			return;
		}
		if (mode == ChangeMode.SET) {
			map.getSingle(e).setScale((MapView.Scale)delta[0]);
		} else if (mode == ChangeMode.RESET) {
			map.getSingle(e).setScale(MapView.Scale.CLOSEST);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(MapView.Scale.class);
		}
		return null;
	}
}