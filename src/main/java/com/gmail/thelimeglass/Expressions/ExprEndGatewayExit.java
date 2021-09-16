package com.gmail.thelimeglass.Expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Disabled;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] exit [location] of end[ ]gate[way] %block%", "[skellett] %block%'s end[ ]gate[way] exit [location]"})
@Config("Endgateway") //re-add this in the config
@Version("1.9R1")
@Disabled
@PropertyType(ExpressionType.COMBINED)
public class ExprEndGatewayExit extends SimpleExpression<Location>{
	
	private Expression<Block> block;
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] exit [location] of end[ ]gate[way] %block%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if (block != null) {
			if (block.getSingle(e) instanceof EndGateway) {
				return new Location[]{((EndGateway)block.getSingle(e)).getExitLocation()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (block != null) {
				if (block.getSingle(e) instanceof EndGateway) {
					((EndGateway)block.getSingle(e)).setExitLocation((Location)delta[0]);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Location.class);
		}
		return null;
	}
}