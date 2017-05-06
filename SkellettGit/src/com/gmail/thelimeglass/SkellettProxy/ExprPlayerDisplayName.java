package com.gmail.thelimeglass.SkellettProxy;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"(skellett[ ][(cord|proxy)]|bungee[ ][cord]) [(player|uuid)] %string%['s] display name", "[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) display name of [(player|uuid)] %string%"})
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprPlayerDisplayName extends SimpleExpression<String>{
	
	private Expression<String> player;
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
		player = (Expression<String>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) display name of [(player|uuid)] %string%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (!(player.getSingle(e) instanceof String)) {
			if (Skellett.instance.getConfig().getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cSkellettProxy: Type must be String not " + player.getSingle(e)));
			}
			return null;
		}
		UUID uniqueId = null;
		try {
			uniqueId = UUID.fromString(player.getSingle(e));
		} catch (IllegalArgumentException ex) {}
		String name = null;
		if (uniqueId != null) {
			name = (String) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.PLAYERDISPLAYNAME));
		} else {
			name = (String) Sockets.send(new SkellettPacket(true, player.getSingle(e), SkellettPacketType.PLAYERDISPLAYNAME));
		}
		if (name != null) {
			return new String[]{name};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			UUID uniqueId = null;
			try {
				uniqueId = UUID.fromString(player.getSingle(e));
			} catch (IllegalArgumentException ex) {}
			if (uniqueId != null) {
				Sockets.send(new SkellettPacket(false, uniqueId, (String)delta[0], SkellettPacketType.PLAYERDISPLAYNAME));
			} else {
				Sockets.send(new SkellettPacket(false, player.getSingle(e), (String)delta[0], SkellettPacketType.PLAYERDISPLAYNAME));
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}