package com.gmail.thelimeglass.Npcs;

import javax.annotation.Nullable;

import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import net.citizensnpcs.api.npc.NPC;

@Syntax({"[the] entity [type] (of|from) (npc|citizen) %npc%", "(npc|citizen) %npc%'s entity [type]"})
@Config("PluginHooks.Npc")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNpcEntityType extends SimpleExpression<EntityType>{
	
	private Expression<NPC> npc;
	@Override
	public Class<? extends EntityType> getReturnType() {
		return EntityType.class;
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
		return "[the] entity [type] (of|from) (npc|citizen) %npc%";
	}
	@Override
	@Nullable
	protected EntityType[] get(Event e) {
		return new EntityType[]{npc.getSingle(e).getEntity().getType()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			npc.getSingle(e).setBukkitEntityType((EntityType)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(EntityType.class);
		}
		return null;
	}
}