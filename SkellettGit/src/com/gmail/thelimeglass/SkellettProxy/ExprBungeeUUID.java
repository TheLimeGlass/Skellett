package com.gmail.thelimeglass.SkellettProxy;

import java.util.UUID;

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
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] (uuid|unique[ ]id) of [player] (1¦%-player%|2¦%-string%)", "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) (1¦%-player%|2¦%-string%)['s] (uuid|unique[ ]id)"})
@Config("SkellettProxy")
@FullConfig
@SkellettProxy
@PropertyType(ExpressionType.COMBINED)
public class ExprBungeeUUID extends SimpleExpression<String>{
	
	private Expression<Player> player;
	private Expression<String> data;
	private Integer marker;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		data = (Expression<String>) e[1];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] (uuid|unique[ ]id) of [player] (1¦%-player%|2¦%-string%)";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		if (marker == 1) {
			if (player != null) {
				String uuid = (String) Sockets.send(new SkellettPacket(true, player.getSingle(e).getName(), SkellettPacketType.PLAYERUUID));
				if (uuid != null) {
					return new String[]{uuid};
				}
			}
		} else {
			UUID uniqueId = null;
			try {
				uniqueId = UUID.fromString(data.getSingle(e));
			} catch (IllegalArgumentException ex) {}
			if (uniqueId != null) {
				String uuid = (String) Sockets.send(new SkellettPacket(true, uniqueId, SkellettPacketType.PLAYERUUID));
				if (uuid != null) {
					return new String[]{uuid};
				}
			} else {
				String uuid = (String) Sockets.send(new SkellettPacket(true, data.getSingle(e), SkellettPacketType.PLAYERUUID));
				if (uuid != null) {
					return new String[]{uuid};
				}
			}
		}
		return null;
	}
}