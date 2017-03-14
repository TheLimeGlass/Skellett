package com.gmail.thelimeglass.Disguises;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
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
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

@Syntax("[skellett] [[Libs]Disguises] [a] [new] disguise [with] type %disguisetype% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]")
@Config("PluginHooks.LibsDisguises")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprNewDisguise extends SimpleExpression<Disguise> {
	
	private Expression<DisguiseType> type;
	private Expression<Integer> block, data;
	private Expression<String> name;
	private Expression<Boolean> baby;
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
		type = (Expression<DisguiseType>) e[0];
		block = (Expression<Integer>) e[1];
		data = (Expression<Integer>) e[2];
		name = (Expression<String>) e[3];
		baby = (Expression<Boolean>) e[4];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [[Libs]Disguises] [a] [new] disguise [with] type %disguisetype% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]";
	}
	@Override
	@Nullable
	protected Disguise[] get(Event e) {
		if (type.getSingle(e).isMisc()) {
			if (data != null && block != null) {
				return new Disguise[]{new MiscDisguise(type.getSingle(e), block.getSingle(e), data.getSingle(e))};
			} else if (data != null && block == null) {
				return new Disguise[]{new MiscDisguise(type.getSingle(e), data.getSingle(e))};
			} else if (data == null && block != null) {
				return new Disguise[]{new MiscDisguise(type.getSingle(e), block.getSingle(e))};
			} else {
				return new Disguise[]{new MiscDisguise(type.getSingle(e))};
			}
		} else if (type.getSingle(e).isMob()) {
			if (baby != null) {
				return new Disguise[]{new MobDisguise(type.getSingle(e), baby.getSingle(e))};
			} else {
				return new Disguise[]{new MobDisguise(type.getSingle(e))};
			}
		} else if (type.getSingle(e).isPlayer()) {
			if (name != null) {
				return new Disguise[]{new PlayerDisguise(name.getSingle(e))};
			} else {
				return new Disguise[]{new PlayerDisguise("Notch")};
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown DisguiseType " + type.getSingle(e)));
		}
		return null;
	}
}