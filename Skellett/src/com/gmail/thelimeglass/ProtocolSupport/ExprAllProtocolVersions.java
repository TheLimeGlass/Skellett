package com.gmail.thelimeglass.ProtocolSupport;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class ExprAllProtocolVersions extends SimpleExpression<ProtocolVersion>{
	
	//[(the|all)] [of] [the] protocol[ ][support] versions
	
	@Override
	public Class<? extends ProtocolVersion> getReturnType() {
		return ProtocolVersion.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(the|all)] [of] [the] protocol[ ][support] versions";
	}
	@Override
	@Nullable
	protected ProtocolVersion[] get(Event e) {
		
		//This is a dummy expression until the next ProtocolSupport version is released
		//This expression is not registered
		
		//return ProtocolSupportAPI.getConnections();
		
		return new ProtocolVersion[]{ProtocolSupportAPI.getProtocolVersion(Bukkit.getOnlinePlayers().stream().findAny().get())};
	}
}