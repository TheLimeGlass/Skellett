package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

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
import me.libraryaddict.disguise.disguisetypes.Disguise;

@Syntax({"[skellett] [[Libs]Disguises] [(the|all)] [of] [the] armo[u]r of disguise %disguise%", "[skellett] [[Libs]Disguises] %disguise%'s armo[u]r"})
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprDisguiseArmour extends SimpleExpression<ItemStack> {
	
	private Expression<Disguise> disguise;
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public boolean isSingle() {
		return false;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		disguise = (Expression<Disguise>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] armo[u]r of disguise %disguise%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (disguise != null) {
			return disguise.getSingle(e).getWatcher().getArmor();
		}
		return null;
	}
}