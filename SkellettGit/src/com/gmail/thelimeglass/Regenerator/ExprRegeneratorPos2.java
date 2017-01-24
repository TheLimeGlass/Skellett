package com.gmail.thelimeglass.Regenerator;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;
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

@Syntax("pos[ition][ ]2 of [skellett] regenerator [[with] id] %string%")
@Config("Main.Regenerator")
@FullConfig
@PropertyType("COMBINED")
public class ExprRegeneratorPos2 extends SimpleExpression<Location> {
	
	private Expression<String> ID;
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		ID = (Expression<String>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "pos[ition][ ]2 of [skellett] regenerator [[with] id] %string%";
	}
	@Nullable
	protected Location[] get(Event e) {
		return new Location[]{RegeneratorManager.getPos1(ID.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			RegeneratorManager.reconfigure(ID.getSingle(e), RegeneratorManager.getPos1(ID.getSingle(e)), (Location)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Location.class);
		}
		return null;
	}
}