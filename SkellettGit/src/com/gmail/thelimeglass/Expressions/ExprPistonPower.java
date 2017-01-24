package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.material.MaterialData;
import org.bukkit.material.PistonBaseMaterial;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[skellett] piston[s] (power|toggle) [state] of %block%", "%block%'s piston (power|toggle) [state]"})
@Config("PistonPower")
@PropertyType("COMBINED")
public class ExprPistonPower extends SimpleExpression<Boolean>{
	
	private Expression<Block> block;
	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
		return "[skellett] piston[s] (power|toggle) [state] of %block%";
	}
	@Override
	@Nullable
	protected Boolean[] get(Event e) {
		MaterialData piston = block.getSingle(e).getState().getData();
		if (piston instanceof PistonBaseMaterial) {
			return new Boolean[]{((PistonBaseMaterial)piston).isPowered()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			BlockState state = block.getSingle(e).getState();
			MaterialData piston = state.getData();
			if (piston instanceof PistonBaseMaterial) {
				((PistonBaseMaterial)piston).setPowered((Boolean)delta[0]);
				state.setData(piston);
				state.update(true, false);
				Bukkit.getLogger().info(((PistonBaseMaterial)piston).isPowered() + " TEST1");
				Bukkit.getLogger().info(((PistonBaseMaterial)block.getSingle(e).getState().getData()).isPowered() + " TEST2");
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
	}
}