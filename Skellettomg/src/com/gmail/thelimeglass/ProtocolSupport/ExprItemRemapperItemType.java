package com.gmail.thelimeglass.ProtocolSupport;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.ItemRemapperControl;

public class ExprItemRemapperItemType extends SimpleExpression<ItemType>{
	
	//[protocol[ ]support] remap[ped] item [of] %itemtype% (for|of) [protocol] version %protocolversion%
	
	private Expression<ItemType> item;
	private Expression<ProtocolVersion> version;
	@Override
	public Class<? extends ItemType> getReturnType() {
		return ItemType.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		item = (Expression<ItemType>) e[0];
		version = (Expression<ProtocolVersion>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[protocol[ ]support] remap[ped] item [of] %itemtype% (for|of) [protocol] version %protocolversion%";
	}
	@Override
	@Nullable
	protected ItemType[] get(Event e) {
		Material m = item.getSingle(e).getRandom().getType();
		ItemRemapperControl mapper = ProtocolSupportAPI.getItemRemapper(version.getSingle(e));
		@SuppressWarnings("deprecation")
		ItemType itemType = new ItemType(mapper.getRemap(mapper.getRemap(m).getId()));
		return new ItemType[]{itemType};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Material mbefore = item.getSingle(e).getRandom().getType();
		Material m = ((ItemType)delta[0]).getRandom().getType();
		ItemRemapperControl mapper = ProtocolSupportAPI.getItemRemapper(version.getSingle(e));
		if (mode == ChangeMode.SET) {
			mapper.setRemap(mbefore, m);
		} else if (mode == ChangeMode.RESET) {
			mapper.setRemap(mbefore, mbefore);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(ItemType.class);
		}
		return null;
	}
}