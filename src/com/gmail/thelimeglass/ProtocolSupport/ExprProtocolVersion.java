package com.gmail.thelimeglass.ProtocolSupport;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ExprProtocolVersion extends SimpleExpression<ProtocolVersion>{
	
	//[skellett] protocol[ ][support] version of %player%
	//[skellett] %player%'s protocol[ ][support] version
	
	private Expression<Player> player;
	@Override
	public Class<? extends ProtocolVersion> getReturnType() {
		return ProtocolVersion.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] protocol[ ][support] version of %player%";
	}
	@Override
	@Nullable
	protected ProtocolVersion[] get(Event e) {
		return new ProtocolVersion[]{ProtocolSupportAPI.getProtocolVersion(player.getSingle(e))};
	}
}