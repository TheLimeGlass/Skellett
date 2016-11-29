package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class ExprBungeeYAML extends SimpleExpression<Object>{
	
	//bungee[[ ]cord] y[a]ml (1宅alue|2好odes|3好odes with keys|4奸ist) [of node] %string% (from|in) [file] %string%
	
	private static enum Types {
		VALUE, NODES, NODES_KEYS, LIST
	}
	private Expression<String> node, file;
	private Types type;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return type == Types.VALUE ? true : false;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
		if (parse.mark == 1) {
			type = Types.VALUE;
		} else if (parse.mark == 2) {
			type = Types.NODES;
		} else if (parse.mark == 3) {
			type = Types.NODES_KEYS;
		} else if (parse.mark == 4) {
			type = Types.LIST;
		}
		node = (Expression<String>) e[0];
		file = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "bungee[[ ]cord] y[a]ml (1宅alue|2好odes|3好odes with keys|4奸ist) [of node] %string% (from|in) [file] %string%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		String data = type + " " + node.getSingle(e).replace(" ", "") + " " + file.getSingle(e).replace(" ", "");
		PacketCustom packet = new PacketCustom("SkellettCord", data);
        Object value = (Object) packet.send();
        if (value != null && !value.equals("null")) {
        	return new Object[]{value};
        }
		return null;
	}
	/*@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			String data = "SetPlayerDisplayName " + player.getSingle(e).getName().replace(" ", "") + " " + (String)delta[0];
			PacketCustom packet = new PacketCustom("SkellettCord", data);
	        packet.send();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}*/
}