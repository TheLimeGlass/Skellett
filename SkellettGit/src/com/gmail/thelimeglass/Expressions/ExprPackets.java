package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import com.gmail.thelimeglass.Events.EvtPacket;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[the] [skellett] packet (data|value|field) %string% [[is] [a] loop[able] %-boolean%]")
@Config("Main.Packets")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprPackets extends SimpleExpression<Object> {
	
	private Boolean loopable = true;
	private Expression<Boolean> loop;
	private Expression<String> field;
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	public boolean isSingle() {
		return loopable;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(EvtPacket.class)) {
			Skript.error("You can only modify packets in the 'on packet revieve:' event!");
			return false;
		}
		field = (Expression<String>) e[0];
		loop = (Expression<Boolean>) e[1];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[the] [skellett] packet (data|value|field) %string% [[is] [a] loop[able] %-boolean%]";
	}
	@Nullable
	protected Object[] get(Event e) {
		if (loop != null) {
			loopable = loop.getSingle(e);
		}
		return new Object[]{((EvtPacket)e).getPacket().getValue(field.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		((EvtPacket)e).getPacket().setValue(field.getSingle(e), (Object)delta[0]);
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Object.class);
		}
		return null;
	}
}