package com.gmail.thelimeglass.Npcs;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

@Syntax({"[the] (uuid|unique [id]) (of|from) (npc|citizen) %citizen%", "(npc|citizen) %citizen%'s (uuid|unique [id])"})
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNpcUUID extends SimpleExpression<UUID>{
	
	private Expression<NPC> npc;
	@Override
	public Class<? extends UUID> getReturnType() {
		return UUID.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		npc = (Expression<NPC>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "(uuid|unique [id]) (of|from) (npc|citizen) %npc%";
	}
	@Override
	@Nullable
	protected UUID[] get(Event e) {
		return new UUID[]{npc.getSingle(e).getUniqueId()};
	}
}