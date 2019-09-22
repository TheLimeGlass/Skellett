package com.gmail.thelimeglass.Npcs;

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

@Syntax({"[the] (clone|duplicate|copy) (of|from) (npc|citizen) %citizen%", "(npc|citizen) %citizen%'s (clone|duplicate|copy)"})
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNpcClone extends SimpleExpression<NPC>{
	
	private Expression<NPC> npc;
	@Override
	public Class<? extends NPC> getReturnType() {
		return NPC.class;
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
		return "[the] (clone|duplicate|copy) (of|from) (npc|citizen) %npc%";
	}
	@Override
	@Nullable
	protected NPC[] get(Event e) {
		return new NPC[]{npc.getSingle(e).clone()};
	}
}