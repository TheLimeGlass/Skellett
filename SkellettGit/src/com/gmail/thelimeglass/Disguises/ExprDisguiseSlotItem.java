package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.libraryaddict.disguise.disguisetypes.Disguise;

@Syntax({"[skellett] [[Libs]Disguises] [the] (item|equipment) [in] slot %equipmentslot% (of|in) disguise %disguise%", "[skellett] [[Libs]Disguises] slot %equipmentslot% (of|in) disguise %disguise%"})
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType("COMBINED")
public class ExprDisguiseSlotItem extends SimpleExpression<ItemStack> {
	
	private Expression<EquipmentSlot> slot;
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
		slot = (Expression<EquipmentSlot>) e[0];
		disguise = (Expression<Disguise>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] [the] (item|equipment) [in] slot %equipmentslot% (of|in) disguise %disguise%";
	}
	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		if (disguise != null && slot != null) {
			return new ItemStack[]{disguise.getSingle(e).getWatcher().getItemStack(slot.getSingle(e))};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (disguise != null && slot != null) {
				disguise.getSingle(e).getWatcher().setItemStack(slot.getSingle(e), (ItemStack)delta[0]);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemStack.class);
		}
		return null;
	}
}