package com.gmail.thelimeglass.Disguises;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;

public class EffDisguiseToAll extends Effect {

	//[skellett] [[Libs]Disguises] [set] Disguise [of] %entity% (as|to) %string% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]
	
	private Expression<Entity> entity;
	private Expression<String> type;
	private Expression<Integer> block;
	private Expression<Integer> data;
	private Expression<String> name;
	private Expression<Boolean> baby;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<Entity>) e[0];
		type = (Expression<String>) e[1];
		block = (Expression<Integer>) e[2];
		data = (Expression<Integer>) e[3];
		name = (Expression<String>) e[4];
		baby = (Expression<Boolean>) e[5];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[[Libs]Disguises] [set] Disguise [of] %entitys% (as|to) %string% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [with baby [state] %-boolean%] [(and|with) sound %-boolean%]";
	}
	@Override
	protected void execute(Event e) {
		for (DisguiseType d : DisguiseType.values()) {
			if (d.toString().equals(type.getSingle(e))) {
				if (d.isMisc()) {
					if (data != null && block != null) {
						MiscDisguise Disguise = new MiscDisguise(d, block.getSingle(e), data.getSingle(e));
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					} else if (data != null && block == null) {
						MiscDisguise Disguise = new MiscDisguise(d, data.getSingle(e));
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					} else if (data == null && block != null) {
						MiscDisguise Disguise = new MiscDisguise(d, block.getSingle(e));
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					} else {
						MiscDisguise Disguise = new MiscDisguise(d);
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					}
				} else if (d.isMob()) {
					if (baby != null) {
						MobDisguise Disguise = new MobDisguise(d, baby.getSingle(e));
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					} else {
						MobDisguise Disguise = new MobDisguise(d);
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					}
				} else if (d.isPlayer()) {
					if (name != null) {
						PlayerDisguise Disguise = new PlayerDisguise(name.getSingle(e));
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					} else {
						PlayerDisguise Disguise = new PlayerDisguise("Notch");
						DisguiseAPI.disguiseToAll(entity.getSingle(e), Disguise);
						break;
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown DisguiseType " + d));
					break;
				}
			}
		}
		return;
	}
}
