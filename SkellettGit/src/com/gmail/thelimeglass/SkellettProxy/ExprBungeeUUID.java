package com.gmail.thelimeglass.SkellettProxy;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.SkellettPacket;
import com.gmail.thelimeglass.SkellettPacketType;
import com.gmail.thelimeglass.Sockets;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] (uuid|unique[ ]id) of [player] %player%", "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) %player%['s] (uuid|unique[ ]id)"})
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType("COMBINED")
public class ExprBungeeUUID extends SimpleExpression<String>{
	
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
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] (uuid|unique[ ]id) of [player] %player%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		Player p = player.getSingle(e);
		if (player != null) {
			String uuid = (String) Sockets.send(new SkellettPacket(true, p.getName(), SkellettPacketType.PLAYERUUID));
			if (uuid != null) {
				return new String[]{uuid};
			}
		}
		return null;
	}
}