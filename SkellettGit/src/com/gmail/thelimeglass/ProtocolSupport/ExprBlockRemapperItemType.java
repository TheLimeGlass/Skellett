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
import protocolsupport.api.remapper.BlockRemapperControl;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;

public class ExprBlockRemapperItemType extends SimpleExpression<MaterialAndData>{
	
	//[protocol[ ]support] remap[ped] block [of] %materialanddata% (for|of) [protocol] version %protocolversion%
	
	private Expression<MaterialAndData> md;
	private Expression<ProtocolVersion> version;
	@Override
	public Class<? extends MaterialAndData> getReturnType() {
		return MaterialAndData.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		md = (Expression<MaterialAndData>) e[0];
		version = (Expression<ProtocolVersion>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[protocol[ ]support] remap[ped] block [of] %materialanddata% (for|of) [protocol] version %protocolversion%";
	}
	@Override
	@Nullable
	protected MaterialAndData[] get(Event e) {
		BlockRemapperControl mapper = ProtocolSupportAPI.getBlockRemapper(version.getSingle(e));
		return new MaterialAndData[]{mapper.getRemap(md.getSingle(e))};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		BlockRemapperControl mapper = ProtocolSupportAPI.getBlockRemapper(version.getSingle(e));
		if (mode == ChangeMode.SET) {
			mapper.setRemap(md.getSingle(e), (MaterialAndData)delta[0]);
		} else if (mode == ChangeMode.RESET) {
			mapper.setRemap(md.getSingle(e), md.getSingle(e));
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(MaterialAndData.class);
		}
		return null;
	}
}