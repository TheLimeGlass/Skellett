package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.npc.NPC;

@Syntax({"(id|number) (of|from) (npc|citizen) %npc%", "(npc|citizen) %npc%'s (id|number)"})
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNpcID extends SimpleExpression<Number>{
	
	private Expression<NPC> npc;
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
		return "(id|number) (of|from) (npc|citizen) %npc%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		return new Number[]{npc.getSingle(e).getId()};
	}
}