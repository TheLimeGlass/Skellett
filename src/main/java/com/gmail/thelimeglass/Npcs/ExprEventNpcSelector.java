package com.gmail.thelimeglass.Npcs;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.event.NPCSelectEvent;

@Syntax("[the] (citizen|npc) [player] selector")
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.SIMPLE)
public class ExprEventNpcSelector extends SimpleExpression<CommandSender>{
	
	@Override
	public Class<? extends CommandSender> getReturnType() {
		return CommandSender.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(NPCSelectEvent.class)) {
			Skript.error("You can not use npc selector expression in any event but on npc select!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] (citizen|npc) [player] selector";
	}
	@Override
	@Nullable
	protected CommandSender[] get(Event e) {
		return new CommandSender[]{((NPCSelectEvent)e).getSelector()};
	}
}