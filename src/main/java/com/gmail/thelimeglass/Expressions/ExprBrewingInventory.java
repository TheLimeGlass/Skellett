package com.gmail.thelimeglass.Expressions;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] [event] brew[ing] [stand] inventory", "[skellett] event-brewinginventory"})
@Config("Main.Brewing")
@FullConfig
@PropertyType(ExpressionType.SIMPLE)
@RegisterSimpleEnum(ExprClass=BrewerInventory.class, value="brewerinventory")
public class ExprBrewingInventory extends SimpleExpression<BrewerInventory>{
	
	@Override
	public Class<? extends BrewerInventory> getReturnType() {
		return BrewerInventory.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (!ScriptLoader.isCurrentEvent(BrewEvent.class)) {
			Skript.error("You can not use brewing inventory expression in any event but on brew event!");
			return false;
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] brew[ing] [stand] inventory";
	}
	@Override
	@Nullable
	protected BrewerInventory[] get(Event e) {
		return new BrewerInventory[]{((BrewEvent)e).getContents()};
	}
}