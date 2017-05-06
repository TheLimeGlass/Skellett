package com.gmail.thelimeglass.Corpse;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.golde.bukkit.corpsereborn.CorpseAPI.CorpseAPI;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(spawn|create) [a] corpse [with] [player [data]] %player% at %location% [with] id %string% [[with] inventory %-itemstacks%] [[with] helmet %-itemstack%[(,| and)] chestplate %-itemstack%[(,| and)] leg[ging][s] %-itemstack%[(,| and)] boot[s] %-itemstack%] [holding [item] %-itemstack%] [[and] offhand [item] %-itemstack%]]")
@Config("PluginHooks.CorpseReborn")
@FullConfig
@MainConfig
public class EffCorpse extends Effect {
	
	private Expression<Player> player;
	private Expression<Location> location;
	private Expression<String> ID;
	private Expression<ItemStack> inventory, helmet, chestplate, leggings, boots, hand, offhand;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		location = (Expression<Location>) e[1];
		ID = (Expression<String>) e[2];
		inventory = (Expression<ItemStack>) e[3];
		helmet = (Expression<ItemStack>) e[4];
		chestplate = (Expression<ItemStack>) e[5];
		leggings = (Expression<ItemStack>) e[6];
		boots = (Expression<ItemStack>) e[7];
		hand = (Expression<ItemStack>) e[8];
		offhand = (Expression<ItemStack>) e[9];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(spawn|create) [a] corpse [with] [player [data]] %player% at %location% [with] id %string% [[with] inventory %-itemstacks%] [[with] helmet %-itemstack%[(,| and)] chestplate %-itemstack%[(,| and)] leg[ging][s] %-itemstack%[(,| and)] boot[s] %-itemstack%] [holding [item] %-itemstack%] [[and] offhand [item] %-itemstack%]]";
	}
	@Override
	protected void execute(Event e) {
		if (location != null && player != null && ID != null) {
			if (inventory == null && helmet == null && hand == null && offhand == null) {
				CorpseAPI.spawnCorpse(player.getSingle(e), location.getSingle(e));
				
			} else if (inventory != null && helmet == null && hand == null && offhand == null) {
				CorpseAPI.spawnCorpse(player.getSingle(e), location.getSingle(e), inventory.getAll(e));
			} else if (inventory != null && helmet != null && hand == null && offhand == null) {
				CorpseAPI.spawnCorpse(player.getSingle(e), location.getSingle(e), inventory.getAll(e), helmet.getSingle(e), chestplate.getSingle(e), leggings.getSingle(e), boots.getSingle(e));
			} else if (inventory != null && helmet != null && hand != null && offhand == null) {
				CorpseAPI.spawnCorpse(player.getSingle(e), location.getSingle(e), inventory.getAll(e), helmet.getSingle(e), chestplate.getSingle(e), leggings.getSingle(e), boots.getSingle(e), hand.getSingle(e));
			} else if (inventory != null && helmet != null && hand != null && offhand != null) {
				CorpseAPI.spawnCorpse(player.getSingle(e), player.getSingle(e).getName(),location.getSingle(e), inventory.getAll(e), helmet.getSingle(e), chestplate.getSingle(e), leggings.getSingle(e), boots.getSingle(e), hand.getSingle(e), offhand.getSingle(e));
			}
		}
	}
}