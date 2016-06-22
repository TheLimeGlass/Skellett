/*

Copyright 2016 Sean T Grover

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

All rights reserved to the author

*/
package com.gmail.thelimeglass;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Effects.EffCreateHologram;
import com.gmail.thelimeglass.Effects.EffForceRespawn;
import com.gmail.thelimeglass.Effects.EffPlaySound;
import com.gmail.thelimeglass.Effects.EffPlaySoundPlayer;
import com.gmail.thelimeglass.Effects.EffRemoveHologram;
import com.gmail.thelimeglass.Effects.EffRenameHologram;
import com.gmail.thelimeglass.Effects.EffResetClientTime;
import com.gmail.thelimeglass.Effects.EffResetClientWeather;
import com.gmail.thelimeglass.Effects.EffResetNametag;
import com.gmail.thelimeglass.Effects.EffResetNametagPrefix;
import com.gmail.thelimeglass.Effects.EffResetNametagSuffix;
//import com.gmail.thelimeglass.Effects.EffScoreboardClearSlot;
//import com.gmail.thelimeglass.Effects.EffScoreboardCreate;
//import com.gmail.thelimeglass.Effects.EffScoreboardDelete;
//import com.gmail.thelimeglass.Effects.EffScoreboardSetSlot;
//import com.gmail.thelimeglass.Effects.EffScoreboardSetTitle;
import com.gmail.thelimeglass.Effects.EffSetClientNametagPrefix;
import com.gmail.thelimeglass.Effects.EffSetClientNametagSuffix;
import com.gmail.thelimeglass.Effects.EffSetClientTime;
import com.gmail.thelimeglass.Effects.EffSetClientWeather;
import com.gmail.thelimeglass.Effects.EffSetCollidable;
import com.gmail.thelimeglass.Effects.EffSetNametagPrefix;
import com.gmail.thelimeglass.Effects.EffSetNametagSuffix;
import com.gmail.thelimeglass.Effects.EffSetSleepIgnored;
import com.gmail.thelimeglass.Effects.EffSetSneakState;
import com.gmail.thelimeglass.Effects.EffSetSprintState;
import com.gmail.thelimeglass.Effects.EffTeleportHologram;
import com.gmail.thelimeglass.Expressions.ExprGetClientTime;
import com.gmail.thelimeglass.Expressions.ExprGetClientTimeOffset;
import com.gmail.thelimeglass.Expressions.ExprGetClientWeather;
import com.gmail.thelimeglass.Expressions.ExprGetHologramLocation;
import com.gmail.thelimeglass.Expressions.ExprGetHologramName;
import com.gmail.thelimeglass.Expressions.ExprGetNametagPrefix;
import com.gmail.thelimeglass.Expressions.ExprGetNametagSuffix;
import com.gmail.thelimeglass.Expressions.ExprIsCollidable;
import com.gmail.thelimeglass.Expressions.ExprIsSleepIgnored;
import com.gmail.thelimeglass.Expressions.ExprRoundDecimal;
import com.gmail.thelimeglass.Expressions.ExprSneakState;
import com.gmail.thelimeglass.Expressions.ExprSprintState;
import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;
import com.gmail.thelimeglass.SkellettAPI.SkellettHologramsUtils;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class Main extends JavaPlugin{
	
	public static String prefix = "&8[&aSkellett&8] &e";
	String enable = prefix + "&aHas been enabled!";
	
	public void onEnable(){
		Skript.registerAddon(this);
		Skript.registerEffect(EffPlaySoundPlayer.class, "[skellett] play [sound] %string% (for|to) [player] %player% (with|at|and) volume %number% (and|with|at) pitch %number%");
		Skript.registerEffect(EffPlaySound.class, "[skellett] play [sound] %string% at [location] %location% (with|at|and) volume %number% (and|with|at) pitch %number%");
		Skript.registerEffect(EffSetSleepIgnored.class, "[set] [toggle] ignored sleep [state] of %player% to %boolean%");
		Skript.registerEffect(EffSetSprintState.class, "[set] [toggle] sprint[ing] [state] of %player% to %boolean%");
		Skript.registerEffect(EffSetSneakState.class, "[set] [toggle] sneak[ing] [state] of %player% to %boolean%");
		Skript.registerEffect(EffForceRespawn.class, "[skellett] [force] respawn %player%");
		Skript.registerEffect(EffSetCollidable.class, "[set] collid(e|able) [state] [of] %entity% to %boolean%");
		Skript.registerEffect(EffSetClientWeather.class, "[make] [client] weather of [player] %player% to %string%");
		Skript.registerEffect(EffSetClientTime.class, "[make] [client] time of [player] %player% to %integer%");
		Skript.registerEffect(EffResetClientWeather.class, "(reset|sync) [client] weather of [player] %player% [with server]");
		Skript.registerEffect(EffResetClientTime.class, "(reset|sync) [client] time of [player] %player% [with server]");
		Skript.registerEffect(EffCreateHologram.class, "[skellett] (create|spawn|summon|place) holo[gram] at [location] %location% (with|and) [(text|string)] %string% (with|and) id %integer% [[set] glow[ing]] %-boolean% [[is] small] %-boolean%");
		Skript.registerEffect(EffRemoveHologram.class, "[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%");
		Skript.registerEffect(EffTeleportHologram.class, "[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%");
		Skript.registerEffect(EffRenameHologram.class, "[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%");
		Skript.registerEffect(EffSetNametagPrefix.class, "[skellett] [(make|set)] [name]tag prefix [of] %player% to [(text|string)] %string%");
		Skript.registerEffect(EffSetClientNametagPrefix.class, "[skellett] [(make|set)] [client] [name]tag prefix [of] %player% to [(text|string)] %string% (for|to) %player%");
		Skript.registerEffect(EffSetNametagSuffix.class, "[skellett] [(make|set)] [name]tag suffix [of] %player% to [(text|string)] %string%");
		Skript.registerEffect(EffSetClientNametagSuffix.class, "[skellett] [(make|set)] [client] [name]tag suffix [of] %player% to [(text|string)] %string% (for|to) %player%");
		Skript.registerEffect(EffResetNametag.class, "[skellett] reset [name]tag [of] %player%");
		Skript.registerEffect(EffResetNametagPrefix.class, "[skellett] reset [name]tag prefix [of] %player%");
		Skript.registerEffect(EffResetNametagSuffix.class, "[skellett] reset [name]tag suffix [of] %player%");
		//Skript.registerEffect(EffScoreboardCreate.class, "[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% (for|to) %player%");
		//Skript.registerEffect(EffScoreboardSetSlot.class, "[skellett] [set] [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [(text|string)] [in] [(line|value|slot)] %integer% of %player% [board] to %string%");
		//Skript.registerEffect(EffScoreboardClearSlot.class, "[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%");
		//Skript.registerEffect(EffScoreboardSetTitle.class, "[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%");
		//Skript.registerEffect(EffScoreboardDelete.class, "[skellett] (delete|clear|remove) [the] [(score|skellett)][ ]board of %player%");
		
		Skript.registerExpression(ExprIsSleepIgnored.class,Boolean.class,ExpressionType.PROPERTY, "ignored sleep [state] of %player%");
		Skript.registerExpression(ExprSneakState.class,Boolean.class,ExpressionType.PROPERTY, "sneak[ing] [state] of %player%");
		Skript.registerExpression(ExprSprintState.class,Boolean.class,ExpressionType.PROPERTY, "sprint[ing] [state] of %player%");
		Skript.registerExpression(ExprIsCollidable.class,Boolean.class,ExpressionType.PROPERTY, "collid(e|able) [state] [of] %entity%");
		Skript.registerExpression(ExprRoundDecimal.class,Number.class,ExpressionType.PROPERTY, "[Skellett] %number% round[ed] [to] %number% decimal (digit[s]|place[s]");
		Skript.registerExpression(ExprGetClientWeather.class,String.class,ExpressionType.PROPERTY, "[get] [client] weather of %player%");
		Skript.registerExpression(ExprGetClientTime.class,Long.class,ExpressionType.PROPERTY, "[get] [client] time of %player%");
		Skript.registerExpression(ExprGetClientTimeOffset.class,Long.class,ExpressionType.PROPERTY, "[get] (offset|difference) [(between|of)] [client] time of %player% [and server]");
		Skript.registerExpression(ExprGetHologramLocation.class,Location.class,ExpressionType.PROPERTY, "[get] location of holo[gram] [with] [id] %integer%");
		Skript.registerExpression(ExprGetHologramName.class,String.class,ExpressionType.PROPERTY, "[get] (string|text|name) of holo[gram] [with] [id] %integer%");
		Skript.registerExpression(ExprGetNametagPrefix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] [name]tag prefix [of] %player%");
		Skript.registerExpression(ExprGetNametagSuffix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] [name]tag suffix [of] %player%");
		
		Skript.registerCondition(CondLineOfSight.class, "%entity% [can] (see|visibly see|line of sight) [can see] %entity%");
		Skript.registerCondition(CondClientTimeRelative.class, "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]");
		
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHologramsUtils(this), (Plugin)this);
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHolograms(this), (Plugin)this);
		Bukkit.getConsoleSender().sendMessage(cc(enable));
	}
	public static String cc(String x) {
		return ChatColor.translateAlternateColorCodes((char)'&', (String)x);
	}
	public static int randomNumber(int Low, int High) {
		Random random = new Random();
		int Return = random.nextInt(High - Low) + Low;
		return Return;
	}
}
sender.sendMessage(prefix + "Players running on 1.7.x:");
for (Player p : Bukkit.getOnlinePlayers()) {
    CraftPlayer craftPlayer = (CraftPlayer)p;
    Integer version = craftPlayer.getHandle().playerConnection.networkManager.getVersion();
    if (version != 5 && version != 4) continue;
    sender.sendMessage(p.getDisplayName());
}
sender.sendMessage(prefix + "Done");
/*
New Stuff:

[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% (for|to) %player%
[skellett] [set] [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [(text|string)] [in] [(line|value|slot)] %integer% of %player% [board] to %string%
[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%
[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%
[skellett] (delete|clear|remove) [the] [(score|skellett)][ ]board of %player%

Fixed nametag longer than 16 kick issues

*/

//Ideas:
//attack speed of %item%
//Set which direction the Shulker is attached to the wall.
//Skeleton horse trap support.
//Better boat support, + passenger of boat, and row speed of boat.
//https://forums.skunity.com/t/guardian-beam/5464/2
