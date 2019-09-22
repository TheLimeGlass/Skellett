package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

@Syntax("[skellett] [[Libs]Disguises] [a] [new] disguise [with] type (1¦%-string%|2¦%-disguisetype%) [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNewDisguise extends SimpleExpression<Disguise> {
	
	private Expression<DisguiseType> type;
	private Expression<Integer> block, data;
	private Expression<String> name, typeString;
	private Expression<Boolean> baby;
	private Boolean typeToggle = false;
	public Class<? extends Disguise> getReturnType() {
		return Disguise.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		typeString = (Expression<String>) e[0];
		if (typeString != null) {
			typeToggle = true;
		}
		type = (Expression<DisguiseType>) e[1];
		block = (Expression<Integer>) e[2];
		data = (Expression<Integer>) e[3];
		name = (Expression<String>) e[4];
		baby = (Expression<Boolean>) e[5];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] [a] [new] disguise [with] type %disguisetype% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]";
	}
	@Override
	@Nullable
	protected Disguise[] get(Event e) {
		DisguiseType disguiseType = null;
		if (typeToggle) {
			for (DisguiseType dtype : DisguiseType.values()) {
				if (typeString.getSingle(e).equals(dtype.name())) {
					disguiseType = dtype;
				}
			}
		} else {
			disguiseType = type.getSingle(e);
		}
		if (disguiseType != null) {
			if (disguiseType.isMisc()) {
				if (data != null && block != null) {
					return new Disguise[]{new MiscDisguise(disguiseType, block.getSingle(e), data.getSingle(e))};
				} else if (data != null && block == null) {
					return new Disguise[]{new MiscDisguise(disguiseType, data.getSingle(e))};
				} else if (data == null && block != null) {
					return new Disguise[]{new MiscDisguise(disguiseType, block.getSingle(e))};
				} else {
					return new Disguise[]{new MiscDisguise(disguiseType)};
				}
			} else if (disguiseType.isMob()) {
				if (baby != null) {
					return new Disguise[]{new MobDisguise(disguiseType, baby.getSingle(e))};
				} else {
					return new Disguise[]{new MobDisguise(disguiseType)};
				}
			} else if (disguiseType.isPlayer()) {
				if (name != null) {
					return new Disguise[]{new PlayerDisguise(name.getSingle(e))};
				} else {
					return new Disguise[]{new PlayerDisguise("Notch")};
				}
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown DisguiseType " + disguiseType));
			}
		}
		return null;
	}
}