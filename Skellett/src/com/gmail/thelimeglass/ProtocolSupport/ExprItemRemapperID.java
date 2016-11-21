package com.gmail.thelimeglass.ProtocolSupport;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
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

public class ExprItemRemapperID extends SimpleExpression<Number>{
	
	//[protocol[ ]support] remap[ped] item [of] [ID] %number% (for|of) [protocol] version %protocolversion%
	
	private Expression<Number> item;
	private Expression<ProtocolVersion> version;
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
		item = (Expression<Number>) e[0];
		version = (Expression<ProtocolVersion>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[protocol[ ]support] remap[ped] item [of] [ID] %number% (for|of) [protocol] version %protocolversion%";
	}
	@Override
	@Nullable
	protected Number[] get(Event e) {
		Number itemMaterial = item.getSingle(e);
		ItemRemapperControl mapper = ProtocolSupportAPI.getItemRemapper(version.getSingle(e));
		return new Number[]{mapper.getRemap(itemMaterial.intValue())};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		Number itemAfter = (Number)delta[0];
		Number itemBefore = item.getSingle(e);
		ItemRemapperControl mapper = ProtocolSupportAPI.getItemRemapper(version.getSingle(e));
		if (mode == ChangeMode.SET) {
			mapper.setRemap(itemBefore.intValue(), itemAfter.intValue());
		} else if (mode == ChangeMode.RESET) {
			mapper.setRemap(itemAfter.intValue(), itemAfter.intValue());
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
}