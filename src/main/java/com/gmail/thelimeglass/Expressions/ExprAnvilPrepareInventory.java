package com.gmail.thelimeglass.Expressions;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] [skellett] [event] anvil[[']s] inv[entory]", "event-anvilinventory"})
@Config("Syntax.Events.AnvilPrepare")
@FullConfig
@Version("1.11.2")
@PropertyType(ExpressionType.COMBINED)
public class ExprAnvilPrepareInventory extends SimpleExpression<AnvilInventory>{
	
	@Override
	public Class<? extends AnvilInventory> getReturnType() {
		return AnvilInventory.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent(PrepareAnvilEvent.class)) {
			Skript.error("You can not use anvil inventory expression in any event but anvil prepare event!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [skellett] [event] anvil[[']s] [repair] cost";
	}
	@Override
	@Nullable
	protected AnvilInventory[] get(Event e) {
		return new AnvilInventory[]{((PrepareAnvilEvent)e).getInventory()};
	}
}