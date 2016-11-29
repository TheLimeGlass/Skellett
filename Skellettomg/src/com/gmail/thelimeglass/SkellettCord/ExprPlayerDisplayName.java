package com.gmail.thelimeglass.SkellettCord;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.dommi2212.BungeeBridge.packets.PacketCustom;

public class ExprPlayerDisplayName extends SimpleExpression<String>{
	
	//%player%'s bungee[[ ]cord] display name
	//bungee[[ ]cord] display name of %player%
	
	private Expression<Player> player;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "bungee[[ ]cord] display name of %player%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		String data = "PlayerDisplayName " + player.getSingle(e).getName().replace(" ", "");
		PacketCustom packet = new PacketCustom("SkellettCord", data);
        String name = (String) packet.send();
        if (name != null && !name.equals("null")) {
        	return new String[]{name};
        }
		return null;
	}
	@Override
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
	}
}