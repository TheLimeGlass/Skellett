package com.gmail.thelimeglass.SkellettProxy;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[the] [skellett[ ][(cord|proxy)]] (global|network) [var[iable]] [(from|of)] %object%")
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprNetworkVariable extends SimpleExpression<Object>{
	
	@SuppressWarnings("rawtypes")
	private Variable variable;
	private VariableString variableString;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return !variable.isList();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (e[0] instanceof Variable) {
			if (!((Variable)e[0]).isList()) {
				variable = (Variable) e[0];
				String var = variable.toString().substring(1, variable.toString().length() - 1);
				variableString = VariableString.newInstance(var, StringMode.VARIABLE_NAME);
				return true;
			}
			Skript.error("Network Variables can't be lists at the moment!");
			return false;
		}
		Skript.error("Network Variables must be a variable!");
		return false;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [skellett[ ][(cord|proxy)]] (global|network) [var[iable]] [(from|of)] %object%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		String ID = variableString.toString(e);
		//Variable test = Variables.getVariable(variableString.toString(e), e, false);
		Object var = (Object) Sockets.send(new SkellettPacket(true, ID, SkellettPacketType.NETWORKVARIABLE));
		if (var != null) {
			return new Object[]{var};
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		String ID = variableString.toString(e);
		if (mode == ChangeMode.SET) {
			Sockets.send(new SkellettPacket(false, ID, (Object)delta[0], SkellettPacketType.NETWORKVARIABLE));
		} else if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
			Sockets.send(new SkellettPacket(false, ID, null, SkellettPacketType.NETWORKVARIABLEDELETE));
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(Object.class);
		}
		return null;
	}
}