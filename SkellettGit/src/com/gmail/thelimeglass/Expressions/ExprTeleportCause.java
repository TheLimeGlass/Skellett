package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax("[the] teleport cause")
@Config("TeleportCause")
@PropertyType(ExpressionType.SIMPLE)
@RegisterEnum("teleportcause")
public class ExprTeleportCause extends SimpleExpression<PlayerTeleportEvent.TeleportCause> {
	
	public Class<? extends PlayerTeleportEvent.TeleportCause> getReturnType() {
		return PlayerTeleportEvent.TeleportCause.class;
	}
	public boolean isSingle() {
		return true;
	}
	public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PlayerTeleportEvent.class)) {
			Skript.error("You can not use TeleportCause expression in any event but on teleport!");
			return false;
		}
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "Teleport cause";
	}
	@Nullable
	protected PlayerTeleportEvent.TeleportCause[] get(Event e) {
		return new PlayerTeleportEvent.TeleportCause[]{((PlayerTeleportEvent)e).getCause()};
	}
}