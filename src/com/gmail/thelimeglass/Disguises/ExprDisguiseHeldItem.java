package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.libraryaddict.disguise.disguisetypes.Disguise;

@Syntax({"[skellett] [[Libs]Disguises] [held] item of disguise %disguise%", "[skellett] [[Libs]Disguises] %disguise%'s [held] item"})
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprDisguiseHeldItem extends SimpleExpression<ItemStack> {
	
	private Expression<Disguise> disguise;
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		disguise = (Expression<Disguise>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [held] item of disguise %disguise%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (disguise != null) {
			return new ItemStack[]{disguise.getSingle(e).getWatcher().getItemInMainHand()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (disguise != null) {
				disguise.getSingle(e).getWatcher().setItemInMainHand((ItemStack)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}
}