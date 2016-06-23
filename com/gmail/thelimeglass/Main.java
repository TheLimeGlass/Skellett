package com.gmail.thelimeglass;

import java.io.File;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Effects.EffCreateHologram;
import com.gmail.thelimeglass.Effects.EffDownload;
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
import com.gmail.thelimeglass.Effects.EffScoreboardClearSlot;
import com.gmail.thelimeglass.Effects.EffScoreboardCreate;
import com.gmail.thelimeglass.Effects.EffScoreboardDelete;
import com.gmail.thelimeglass.Effects.EffScoreboardSetSlot;
import com.gmail.thelimeglass.Effects.EffScoreboardSetTitle;
import com.gmail.thelimeglass.Effects.EffSetClientNametagPrefix;
import com.gmail.thelimeglass.Effects.EffSetClientNametagSuffix;
import com.gmail.thelimeglass.Effects.EffSetClientTime;
import com.gmail.thelimeglass.Effects.EffSetClientWeather;
import com.gmail.thelimeglass.Effects.EffSetClientWorldBorder;
import com.gmail.thelimeglass.Effects.EffSetCollidable;
import com.gmail.thelimeglass.Effects.EffSetNametagPrefix;
import com.gmail.thelimeglass.Effects.EffSetNametagSuffix;
import com.gmail.thelimeglass.Effects.EffSetSleepIgnored;
import com.gmail.thelimeglass.Effects.EffSetSneakState;
import com.gmail.thelimeglass.Effects.EffSetSprintState;
import com.gmail.thelimeglass.Effects.EffTeleportHologram;
import com.gmail.thelimeglass.Events.EvtDownload;
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
import ch.njol.skript.lang.util.SimpleEvent;

public class Main extends JavaPlugin{
	
	public static String prefix = "&8[&aSkellett&8] &e";
	String enable = prefix + "&aHas been enabled!";
	String noConfig = prefix + "&cconfig.yml not found, generating a new config!";
	FileConfiguration config = getConfig();
	
	public void onEnable(){
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(noConfig));
				saveDefaultConfig();
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		Skript.registerAddon(this);
		if (config.getBoolean("Syntax.Effects")) {
			if (config.getBoolean("Syntax.EffectsSyntax.Sound")) {
				Skript.registerEffect(EffPlaySoundPlayer.class, "[skellett] play [sound] %string% (for|to) [player] %player% (with|at|and) volume %number% (and|with|at) pitch %number%");
				Skript.registerEffect(EffPlaySound.class, "[skellett] play [sound] %string% at [location] %location% (with|at|and) volume %number% (and|with|at) pitch %number%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sound effects! (1)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.SleepIgnored")) {
				Skript.registerEffect(EffSetSleepIgnored.class, "[set] [toggle] ignored sleep [state] of %player% to %boolean%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sleep ignored effect! (2)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.SprintingState")) {
				Skript.registerEffect(EffSetSprintState.class, "[set] [toggle] sprint[ing] [state] of %player% to %boolean%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sprint state effect! (3)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.SneakingState")) {
				Skript.registerEffect(EffSetSneakState.class, "[set] [toggle] sneak[ing] [state] of %player% to %boolean%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sneak state effect! (4)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.ForceRespawn")) {
				Skript.registerEffect(EffForceRespawn.class, "[skellett] [force] respawn %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered force respawn effect! (5)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.CollidableState")) {
				Skript.registerEffect(EffSetCollidable.class, "[set] collid(e|able) [state] [of] %entity% to %boolean%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered collidable state effect! (6)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.ClientWeather")) {
				Skript.registerEffect(EffSetClientWeather.class, "[make] [client] weather of [player] %player% to %string%");
				Skript.registerEffect(EffResetClientWeather.class, "(reset|sync) [client] weather of [player] %player% [with server]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client weather effects! (7)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.ClientTime")) {
				Skript.registerEffect(EffSetClientTime.class, "[make] [client] time of [player] %player% to %integer%");
				Skript.registerEffect(EffResetClientTime.class, "(reset|sync) [client] time of [player] %player% [with server]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client time effects! (8)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Holograms")) {
				Skript.registerEffect(EffCreateHologram.class, "[skellett] (create|spawn|summon|place) holo[gram] at [location] %location% (with|and) [(text|string)] %string% (with|and) id %integer% [[set] glow[ing]] %-boolean% [[is] small] %-boolean%");
				Skript.registerEffect(EffRemoveHologram.class, "[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%");
				Skript.registerEffect(EffTeleportHologram.class, "[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%");
				Skript.registerEffect(EffRenameHologram.class, "[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered hologram effects! (9)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Nametags")) {
				Skript.registerEffect(EffSetNametagPrefix.class, "[skellett] [(make|set)] [name]tag prefix [of] %player% to [(text|string)] %string%");
				Skript.registerEffect(EffSetClientNametagPrefix.class, "[skellett] [(make|set)] [client] [name]tag prefix [of] %player% to [(text|string)] %string% (for|to) %player%");
				Skript.registerEffect(EffSetNametagSuffix.class, "[skellett] [(make|set)] [name]tag suffix [of] %player% to [(text|string)] %string%");
				Skript.registerEffect(EffSetClientNametagSuffix.class, "[skellett] [(make|set)] [client] [name]tag suffix [of] %player% to [(text|string)] %string% (for|to) %player%");
				Skript.registerEffect(EffResetNametag.class, "[skellett] reset [name]tag [of] %player%");
				Skript.registerEffect(EffResetNametagPrefix.class, "[skellett] reset [name]tag prefix [of] %player%");
				Skript.registerEffect(EffResetNametagSuffix.class, "[skellett] reset [name]tag suffix [of] %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nametag effects! (10)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Scoreboard")) {
				Skript.registerEffect(EffScoreboardCreate.class, "[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% (for|to) %player%");
				Skript.registerEffect(EffScoreboardSetSlot.class, "[skellett] [set] [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [(text|string)] [in] [(line|value|slot)] %integer% of %player% [board] to %string%");
				Skript.registerEffect(EffScoreboardClearSlot.class, "[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%");
				Skript.registerEffect(EffScoreboardSetTitle.class, "[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%");
				Skript.registerEffect(EffScoreboardDelete.class, "[skellett] (delete|clear|remove) [the] [(score|skellett)][ ]board of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered scoreboard effects! (11)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Download")) {
				Skript.registerEffect(EffDownload.class, "[skellett] d[ownload][l] [from] %string% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered download effect! (12)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.ClientWorldborder")) {
				Skript.registerEffect(EffSetClientWorldBorder.class, "[skellett] [set] (client|player) [world][ ]border [of] %player% to [radius] %integer%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client worldborder effects! (13)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Expressions")) {
			if (config.getBoolean("Syntax.ExpressionsSyntax.SleepIgnored")) {
				Skript.registerExpression(ExprIsSleepIgnored.class,Boolean.class,ExpressionType.PROPERTY, "ignored sleep [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sleep ignored expression! (1)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SneakingState")) {
				Skript.registerExpression(ExprSneakState.class,Boolean.class,ExpressionType.PROPERTY, "sneak[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sneak state expression! (2)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SprintingState")) {
				Skript.registerExpression(ExprSprintState.class,Boolean.class,ExpressionType.PROPERTY, "sprint[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sprint state expression! (3)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.CollidableState")) {
				Skript.registerExpression(ExprIsCollidable.class,Boolean.class,ExpressionType.PROPERTY, "collid(e|able) [state] [of] %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered collidable state expression! (4)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.RoundDecimal")) {
				Skript.registerExpression(ExprRoundDecimal.class,Number.class,ExpressionType.PROPERTY, "[Skellett] %number% round[ed] [to] %number% decimal (digit[s]|place[s]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered round decimal expression! (5)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClientWeather")) {
				Skript.registerExpression(ExprGetClientWeather.class,String.class,ExpressionType.PROPERTY, "[get] [client] weather of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client weather expression! (6)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClientTime")) {
				Skript.registerExpression(ExprGetClientTime.class,Long.class,ExpressionType.PROPERTY, "[get] [client] time of %player%");
				Skript.registerExpression(ExprGetClientTimeOffset.class,Long.class,ExpressionType.PROPERTY, "[get] (offset|difference) [(between|of)] [client] time of %player% [and server]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client time expressions! (7)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Holograms")) {
				Skript.registerExpression(ExprGetHologramLocation.class,Location.class,ExpressionType.PROPERTY, "[get] location of holo[gram] [with] [id] %integer%");
				Skript.registerExpression(ExprGetHologramName.class,String.class,ExpressionType.PROPERTY, "[get] (string|text|name) of holo[gram] [with] [id] %integer%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered hologram expressions! (8)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Nametags")) {
				Skript.registerExpression(ExprGetNametagPrefix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] [name]tag prefix [of] %player%");
				Skript.registerExpression(ExprGetNametagSuffix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] [name]tag suffix [of] %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nametag expressions! (9)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Conditions")) {
			if (config.getBoolean("Syntax.ConditionsSyntax.LineOfSight")) {
				Skript.registerCondition(CondLineOfSight.class, "%entity% [can] (see|visibly see|line of sight) [can see] %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered line of sight condition! (1)"));
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.ClientTime")) {
				Skript.registerCondition(CondClientTimeRelative.class, "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client time condition! (2)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Events")) {
			if (config.getBoolean("Syntax.EventsSyntax.Download")) {
				Skript.registerEvent("On Download", SimpleEvent.class, EvtDownload.class, "[on] [skellett] download:");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on download event! (1)"));
				}
			}
		}
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHologramsUtils(this), (Plugin)this);
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHolograms(this), (Plugin)this);
		Bukkit.getConsoleSender().sendMessage(cc(enable));
	}
	public static String cc(String x) {
		return ChatColor.translateAlternateColorCodes((char)'&', (String)x);
	}
	public static Main pluginInstance() {
		return Main.getPlugin(Main.class);
	}
	public static int randomNumber(int Low, int High) {
		Random random = new Random();
		int Return = random.nextInt(High - Low) + Low;
		return Return;
	}
}
/*
New Stuff:

[skellett] (create|make|register) [new] [(score|skellett)][ ]board [with] [title] %string% (for|to) %player%
[skellett] [set] [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [(text|string)] [in] [(line|value|slot)] %integer% of %player% [board] to %string%
[skellett] clear [(line|value|slot)] [(text|string)] [of] [(score|skellett)][ ]board [in] [(line|value|slot)] %integer% of %player%
[skellett] set title [(text|string)] [of] [player] [(score|skellett)][ ]board of %player% [(to|with)] %string%
[skellett] (delete|clear|remove) [the] [(score|skellett)][ ]board of %player%

[skellett] d[ownload][l] [from] %string% to %string%
[on] [skellett] download:

Added offline player support so nametags now update for every single player if client side is not set. :D

*/

//Ideas:
//attack speed of %item%
//Set which direction the Shulker is attached to the wall.
//Skeleton horse trap support.
//Better boat support, + passenger of boat, and row speed of boat.
//https://forums.skunity.com/t/guardian-beam/5464/2
