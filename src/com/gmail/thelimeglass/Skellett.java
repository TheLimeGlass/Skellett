package com.gmail.thelimeglass;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.gmail.thelimeglass.Bungee.CondOnlinePlayer;
import com.gmail.thelimeglass.Bungee.EffBungeeKickAllPlayers;
import com.gmail.thelimeglass.Bungee.EffBungeeMessageAllPlayers;
import com.gmail.thelimeglass.Bungee.EffExecuteBungeeCommand;
import com.gmail.thelimeglass.Bungee.EffStopProxy;
import com.gmail.thelimeglass.Bungee.ExprBungeeCount;
import com.gmail.thelimeglass.Bungee.ExprBungeeCountServer;
import com.gmail.thelimeglass.Bungee.ExprBungeeServerMOTD;
import com.gmail.thelimeglass.Bungee.ExprBungeeUUID;
import com.gmail.thelimeglass.Bungee.ExprPlayerSlotsOfServer;
import com.gmail.thelimeglass.Bungee.ExprServerOnline;
import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondIsWhitelisted;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Conditions.CondScoreboardExists;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning2;
import com.gmail.thelimeglass.Disguises.CondIsDisguised;
import com.gmail.thelimeglass.Disguises.EffDisguiseToAll;
import com.gmail.thelimeglass.Disguises.EffUnDisguiseToAll;
import com.gmail.thelimeglass.Disguises.ExprGetDisguise;
import com.gmail.thelimeglass.Effects.EffBlockConstructor;
import com.gmail.thelimeglass.Effects.EffClearSlot;
import com.gmail.thelimeglass.Effects.EffCustomName;
import com.gmail.thelimeglass.Effects.EffDownload;
import com.gmail.thelimeglass.Effects.EffFilesCreate;
import com.gmail.thelimeglass.Effects.EffFilesDelete;
import com.gmail.thelimeglass.Effects.EffFilesSet;
import com.gmail.thelimeglass.Effects.EffForceRespawn;
import com.gmail.thelimeglass.Effects.EffMessageCenter;
import com.gmail.thelimeglass.Effects.EffPlaySound;
import com.gmail.thelimeglass.Effects.EffPlaySoundPlayer;
import com.gmail.thelimeglass.Effects.EffReloadWhitelist;
import com.gmail.thelimeglass.Effects.EffResetClientWeather;
import com.gmail.thelimeglass.Effects.EffSavePlayerList;
import com.gmail.thelimeglass.Effects.EffSetClientWeather;
import com.gmail.thelimeglass.Effects.EffSetCollidable;
import com.gmail.thelimeglass.Effects.EffSetStack;
import com.gmail.thelimeglass.Effects.EffSetTabName;
import com.gmail.thelimeglass.Effects.EffSetWhitelist;
import com.gmail.thelimeglass.Effects.EffSpawnParticle;
import com.gmail.thelimeglass.Effects.EffUnZip;
import com.gmail.thelimeglass.Events.EvtAsyncDamage;
import com.gmail.thelimeglass.Events.EvtDownload;
import com.gmail.thelimeglass.Expressions.ExprAbsoluteValue;
import com.gmail.thelimeglass.Expressions.ExprActivePotionEffects;
import com.gmail.thelimeglass.Expressions.ExprAmountOfDroppedItem;
import com.gmail.thelimeglass.Expressions.ExprAmountOfItem;
import com.gmail.thelimeglass.Expressions.ExprAmountOfVariables;
import com.gmail.thelimeglass.Expressions.ExprAsyncDamage;
import com.gmail.thelimeglass.Expressions.ExprBlockGetDrops;
import com.gmail.thelimeglass.Expressions.ExprBlockGetPower;
import com.gmail.thelimeglass.Expressions.ExprBreedingBreeder;
import com.gmail.thelimeglass.Expressions.ExprBreedingEntity;
import com.gmail.thelimeglass.Expressions.ExprBreedingFather;
import com.gmail.thelimeglass.Expressions.ExprBreedingItem;
import com.gmail.thelimeglass.Expressions.ExprBreedingMother;
import com.gmail.thelimeglass.Expressions.ExprBreedingXP;
import com.gmail.thelimeglass.Expressions.ExprExhaustion;
import com.gmail.thelimeglass.Expressions.ExprFilesGetBoolean;
import com.gmail.thelimeglass.Expressions.ExprFilesGetConfigurationSection;
import com.gmail.thelimeglass.Expressions.ExprFilesGetString;
import com.gmail.thelimeglass.Expressions.ExprFilesIsSet;
import com.gmail.thelimeglass.Expressions.ExprFinalDamage;
import com.gmail.thelimeglass.Expressions.ExprFixFishingGetCaught;
import com.gmail.thelimeglass.Expressions.ExprFixFishingGetHook;
import com.gmail.thelimeglass.Expressions.ExprFixFishingGetXP;
import com.gmail.thelimeglass.Expressions.ExprFixFishingState;
import com.gmail.thelimeglass.Expressions.ExprFixShootArrowSpeed;
import com.gmail.thelimeglass.Expressions.ExprFixShootGetArrow;
import com.gmail.thelimeglass.Expressions.ExprFixShootGetBow;
import com.gmail.thelimeglass.Expressions.ExprGetClientWeather;
import com.gmail.thelimeglass.Expressions.ExprGetItemOfEntity;
import com.gmail.thelimeglass.Expressions.ExprGlowingSpectralArrow;
import com.gmail.thelimeglass.Expressions.ExprInstaBreak;
import com.gmail.thelimeglass.Expressions.ExprInventory;
import com.gmail.thelimeglass.Expressions.ExprInventoryAction;
import com.gmail.thelimeglass.Expressions.ExprInventoryViewers;
import com.gmail.thelimeglass.Expressions.ExprInvulnerableState;
import com.gmail.thelimeglass.Expressions.ExprIsCollidable;
import com.gmail.thelimeglass.Expressions.ExprIsOnGround;
import com.gmail.thelimeglass.Expressions.ExprMaxDamageTicks;
import com.gmail.thelimeglass.Expressions.ExprMetadata;
import com.gmail.thelimeglass.Expressions.ExprNextEmptySlot;
import com.gmail.thelimeglass.Expressions.ExprSleepIgnored;
import com.gmail.thelimeglass.Expressions.ExprSlimeSize;
import com.gmail.thelimeglass.Expressions.ExprNumberOfSlots;
import com.gmail.thelimeglass.Expressions.ExprOfflinePlayers;
import com.gmail.thelimeglass.Expressions.ExprOperators;
import com.gmail.thelimeglass.Expressions.ExprParticles;
import com.gmail.thelimeglass.Expressions.ExprRedstoneNewCurrent;
import com.gmail.thelimeglass.Expressions.ExprRedstoneOldCurrent;
import com.gmail.thelimeglass.Expressions.ExprRoundDecimal;
import com.gmail.thelimeglass.Expressions.ExprSneakState;
import com.gmail.thelimeglass.Expressions.ExprSpawnReason;
import com.gmail.thelimeglass.Expressions.ExprSpectate;
import com.gmail.thelimeglass.Expressions.ExprSpreadSource;
import com.gmail.thelimeglass.Expressions.ExprSprintState;
import com.gmail.thelimeglass.Expressions.ExprTargetReason;
import com.gmail.thelimeglass.Expressions.ExprTeleportCause;
import com.gmail.thelimeglass.Expressions.ExprWorldChangeFrom;
import com.gmail.thelimeglass.Holograms.EffCreateHologram;
import com.gmail.thelimeglass.Holograms.EffRemoveHologram;
import com.gmail.thelimeglass.Holograms.EffRenameHologram;
import com.gmail.thelimeglass.Holograms.EffTeleportHologram;
import com.gmail.thelimeglass.Holograms.ExprGetHologramLocation;
import com.gmail.thelimeglass.Holograms.ExprGetHologramName;
import com.gmail.thelimeglass.Nametags.EffAddPlayerNametag;
import com.gmail.thelimeglass.Nametags.EffCreateNametag;
import com.gmail.thelimeglass.Nametags.EffDeleteNametag;
import com.gmail.thelimeglass.Nametags.EffRemovePlayerNametag;
import com.gmail.thelimeglass.Nametags.EffResetNametag;
import com.gmail.thelimeglass.Nametags.EffResetNametagPrefix;
import com.gmail.thelimeglass.Nametags.EffResetNametagSuffix;
import com.gmail.thelimeglass.Nametags.EffSetNametagPrefix;
import com.gmail.thelimeglass.Nametags.EffSetNametagSuffix;
import com.gmail.thelimeglass.Nametags.ExprGetNametagPrefix;
import com.gmail.thelimeglass.Nametags.ExprGetNametagSuffix;
import com.gmail.thelimeglass.Npcs.EffCreateNpc;
import com.gmail.thelimeglass.Npcs.EffDeleteNpc;
import com.gmail.thelimeglass.OITB.CondOITBHasCooldown;
import com.gmail.thelimeglass.OITB.EffOITBAddCoins;
import com.gmail.thelimeglass.OITB.EffOITBRemoveCoins;
import com.gmail.thelimeglass.OITB.EffOITBSetCoins;
import com.gmail.thelimeglass.OITB.EffOITBSetCooldown;
import com.gmail.thelimeglass.OITB.ExprOITBGetChallengeWins;
import com.gmail.thelimeglass.OITB.ExprOITBGetCoins;
import com.gmail.thelimeglass.OITB.ExprOITBGetDeaths;
import com.gmail.thelimeglass.OITB.ExprOITBGetHighestZombiesWave;
import com.gmail.thelimeglass.OITB.ExprOITBGetHits;
import com.gmail.thelimeglass.OITB.ExprOITBGetKills;
import com.gmail.thelimeglass.OITB.ExprOITBGetModifier;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayTime;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayerExp;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayerRank;
import com.gmail.thelimeglass.OITB.ExprOITBGetShotsfired;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopPlayers;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopPlayersWithScore;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopScores;
import com.gmail.thelimeglass.OITB.ExprOITBGetTournamentWins;
import com.gmail.thelimeglass.OITB.ExprOITBGetZombieKills;
import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;
import com.gmail.thelimeglass.SkellettAPI.SkellettHologramsUtils;
import com.gmail.thelimeglass.SkellettStateExpressions.ExprHotbarSwitchSlot;
import com.gmail.thelimeglass.Utils.ExprNewMaterial;
import com.gmail.thelimeglass.Utils.SkellettState;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;

@SuppressWarnings("deprecation")
public class Skellett extends JavaPlugin {
	
	public static String prefix = "&8[&aSkellett&8] &e";
	String enable = prefix + "&aHas been enabled!";
	String noConfig = prefix + "&cconfig.yml not found, generating a new config!";
	FileConfiguration config = getConfig();
	private static Skellett Instance = null;
	private static Plugin plugin = null;
	
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
		Instance = this;
		plugin = this;
		Skript.registerAddon(this);
		if (!Objects.equals(getDescription().getVersion(), config.getString("version"))) {
			File f = new File(getDataFolder(), "config.yml");
			if (f.exists()) {
				f.delete();
			}
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&6New update found! Regenerating config..."));
			saveDefaultConfig();
		}
		Classes.registerClass(new ClassInfo<SkellettState>(SkellettState.class, "skellettstate")
			.name("skellettstate").parser(new Parser<SkellettState>() {
				@Override
				@Nullable
				public SkellettState parse(String s, ParseContext context) {
					try {
						return SkellettState.valueOf(s.toUpperCase());
					} catch (Exception e) {}
					return null;
				}
				@Override
				public String toString(SkellettState c, int flags) {
					return null;
				}
				@Override
				public String toVariableNameString(SkellettState c) {
					return null;
				}
				public String getVariableNamePattern() {
					return ".+";
			}}));
		if (config.getBoolean("Syntax.Effects")) {
			if (config.getBoolean("Syntax.EffectsSyntax.Sound")) {
				Skript.registerEffect(EffPlaySoundPlayer.class, "[skellett] play [sound] %string% (for|to) [player] %player% (with|at|and) volume %number% (and|with|at) pitch %number%");
				Skript.registerEffect(EffPlaySound.class, "[skellett] play [sound] %string% at [location] %location% (with|at|and) volume %number% (and|with|at) pitch %number%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sound effects! (1)"));
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
			if (config.getBoolean("Syntax.EffectsSyntax.Holograms")) {
				if (Bukkit.getServer().getVersion().contains("MC: 1.9") || Bukkit.getServer().getVersion().contains("MC: 1.10")) {
					Skript.registerEffect(EffCreateHologram.class, "[skellett] (create|spawn|summon|place) holo[gram] at [location] %location% (with|and) [(text|string)] %string% (with|and) id %integer% [[set] glow[ing]] %-boolean% [[is] small] %-boolean%");
					Skript.registerEffect(EffRemoveHologram.class, "[skellett] (delete|remove|despawn|clear|kill) holo[gram] [with] id %integer%");
					Skript.registerEffect(EffTeleportHologram.class, "[skellett] (tp|teleport|move) holo[gram] [(with|and|of)] id %integer% [to] [location] %location%");
					Skript.registerEffect(EffRenameHologram.class, "[skellett] [set] [re]name holo[gram] [(with|and|of)] id %integer% [(to|with)] [(string|text)] %string%");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered hologram effects! (9)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Could not register hologram effects! (1.9 feature only) (9)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Nametags")) {
				Skript.registerEffect(EffSetNametagPrefix.class, "[skellett] [(set|make)] prefix [of] [the] [name][ ]tag [(with|of)] [id] %string% to [(string|text)] %string%");
				Skript.registerEffect(EffSetNametagSuffix.class, "[skellett] [(set|make)] suffix [of] [the] [name][ ]tag [(with|of)] [id] %string% to [(string|text)] %string%");
				Skript.registerEffect(EffResetNametag.class, "[skellett] reset [the] [name][ ]tag [(with|of)] [id] %string%");
				Skript.registerEffect(EffResetNametagPrefix.class, "[skellett] reset [the] [name][ ]tag prefix [(with|of)] [id] %string%");
				Skript.registerEffect(EffResetNametagSuffix.class, "[skellett] reset [the] [name][ ]tag suffix [(with|of)] [id] %string%");
				Skript.registerEffect(EffAddPlayerNametag.class, "[skellett] add %player% to [the] [name][ ]tag [(with|of)] [id] %string%");
				Skript.registerEffect(EffRemovePlayerNametag.class, "[skellett] remove %player% from [the] [name][ ]tag [(with|of)] [id] %string%");
				Skript.registerEffect(EffCreateNametag.class, "[skellett] [(create|set|make)] [name][ ]tag [ID] [(with|named)] [(name|string|text|id)] %string%");
				Skript.registerEffect(EffDeleteNametag.class, "[skellett] delete [the] [name][ ]tag [with] [id] %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nametag effects! (10)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Download")) {
				Skript.registerEffect(EffDownload.class, "[skellett] d[ownload][l] [from] %string% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered download effect! (11)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Whitelist")) {
				Skript.registerEffect(EffSetWhitelist.class, "[set] white[ ]list [to] %boolean%");
				Skript.registerEffect(EffReloadWhitelist.class, "reload [the] white[ ]list");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered whitelist effects! (12)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.SavePlayerList")) {
				Skript.registerEffect(EffSavePlayerList.class, "save [offline] player list");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered save player list effect! (13)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Files")) {
				Skript.registerEffect(EffFilesSet.class, "[skellett] set (file|[file] node) %string% (in|at) [file] %string% to %string%");
				Skript.registerEffect(EffFilesCreate.class, "[skellett] c[reate][ ][[f][ile]] %string%");
				Skript.registerEffect(EffFilesDelete.class, "[skellett] d[elete][ ][[f][ile]] %string%");
				Skript.registerEffect(EffUnZip.class, "[skellett] unzip %string% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered file effect! (14)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.OITB")) {
				Skript.registerEffect(EffOITBAddCoins.class, "[OITB] (add|give) %integer% coin[s] to %player%");
				Skript.registerEffect(EffOITBRemoveCoins.class, "[OITB] (remove|take|subtract) %integer% coin[s] from %player%");
				Skript.registerEffect(EffOITBSetCoins.class, "[OITB] [set] [player] coins of %player% to %integer%");
				Skript.registerEffect(EffOITBSetCooldown.class, "[OITB] [set] %player% cool[ ]down of [ability] %string% to %integer%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered OITB effects! (15)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.CustomName")) {
				Skript.registerEffect(EffCustomName.class, "[skellett] [set] custom[ ]name of %entity% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered custom name effect! (16)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.TabName")) {
				Skript.registerEffect(EffSetTabName.class, "[skellett] set tab name of %player% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered tab name effect! (17)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.CenterMessage")) {
				Skript.registerEffect(EffMessageCenter.class, "(message|send [message]) center[ed] %string% to %players% [[with[ text]] %-string%]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered center message effect! (18)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.BlockConstructor")) {
				Skript.registerEffect(EffBlockConstructor.class, "(create|start|make|build|construct) %string% with %string% at %location% [[with effect[s]] %-boolean%]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered block constructor effect! (19)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.SetStack")) {
				Skript.registerEffect(EffSetStack.class, "[skellett] [set] (size|number|amount) of %itemstack% to %number%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered set stack of item effect! (20)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.ClearSlot")) {
				Skript.registerEffect(EffClearSlot.class, "(clear|empty|reset) (inventory|menu|gui) [slot %-integer% [(of|in)]] %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clear slot effect! (21)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Expressions")) {
			if (config.getBoolean("Syntax.ExpressionsSyntax.SleepIgnored")) {
				Skript.registerExpression(ExprSleepIgnored.class,Boolean.class,ExpressionType.PROPERTY, "ignored sleep[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sleep ignored expression! (1)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SneakingState")) {
				Skript.registerExpression(ExprSneakState.class,Boolean.class,ExpressionType.PROPERTY, "(sneak|shift|crouch)[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sneak state expression! (2)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SprintingState")) {
				Skript.registerExpression(ExprSprintState.class,Boolean.class,ExpressionType.PROPERTY, "(sprint|run)[ing] [state] of %player%");
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
			if (config.getBoolean("Syntax.ExpressionsSyntax.Holograms")) {
				Skript.registerExpression(ExprGetHologramLocation.class,Location.class,ExpressionType.PROPERTY, "[get] location of holo[gram] [with] [id] %integer%");
				Skript.registerExpression(ExprGetHologramName.class,String.class,ExpressionType.PROPERTY, "[get] (string|text|name) of holo[gram] [with] [id] %integer%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered hologram expressions! (8)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Nametags")) {
				Skript.registerExpression(ExprGetNametagPrefix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] prefix [of] [the] [name][ ]tag [with] [id] %string%");
				Skript.registerExpression(ExprGetNametagSuffix.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] suffix [of] [the] [name][ ]tag [with] [id] %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nametag expressions! (9)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.OfflinePlayers")) {
				Skript.registerExpression(ExprOfflinePlayers.class,OfflinePlayer.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] offline[ ]player[s]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered offline player expression! (10)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Operators")) {
				Skript.registerExpression(ExprOperators.class,OfflinePlayer.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] Op[erator][(s|ed)] [players]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered operator expression! (11)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Files")) {
				Skript.registerExpression(ExprFilesGetString.class,String.class,ExpressionType.PROPERTY, "[skellett] [get] (string|value) [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesGetBoolean.class,Boolean.class,ExpressionType.PROPERTY, "[skellett] [get] boolean [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesGetConfigurationSection.class,ConfigurationSection.class,ExpressionType.PROPERTY, "[skellett] [get] [config][uration] section [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesIsSet.class,Boolean.class,ExpressionType.PROPERTY, "[skellett] node %string% (from|of) [file] %string% [is set]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered file expressions! (13)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.OITB")) {
				if (getServer().getPluginManager().isPluginEnabled("OneInTheBattle")) {
					Skript.registerExpression(ExprOITBGetChallengeWins.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] Challenge[s][ ](win[s]|won) of %player%");
					Skript.registerExpression(ExprOITBGetCoins.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] coins of %player%");
					Skript.registerExpression(ExprOITBGetDeaths.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] deaths of %player%");
					Skript.registerExpression(ExprOITBGetHighestZombiesWave.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] [Player]['][s][ ]High[est][ ][Zombie][s][ ]Wave of %player%");
					Skript.registerExpression(ExprOITBGetHits.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] hits of %player%");
					Skript.registerExpression(ExprOITBGetKills.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] kills of %player%");
					Skript.registerExpression(ExprOITBGetModifier.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] modifier of %player%");
					Skript.registerExpression(ExprOITBGetPlayerExp.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] [Player][ ]E[x]p[eri[(e|a)]nce] of %player%");
					Skript.registerExpression(ExprOITBGetPlayerRank.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] [Player][ ]rank of %player%");
					Skript.registerExpression(ExprOITBGetPlayTime.class,String.class,ExpressionType.PROPERTY, "[OITB] [get] play[er][ ]time of %player%");
					Skript.registerExpression(ExprOITBGetShotsfired.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] Shots[ ]fired of %player%");
					Skript.registerExpression(ExprOITBGetTopPlayers.class,String.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] top %integer% players of [the] [OITB] [stat][istic] %StatType%");
					Skript.registerExpression(ExprOITBGetTopPlayersWithScore.class,String.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] top %integer% player[s] with score[s] of [the] [OITB] [stat][istic] %StatType%");
					Skript.registerExpression(ExprOITBGetTopScores.class,Integer.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] top %integer% scores of [the] [OITB] [stat][istic] %StatType%");
					Skript.registerExpression(ExprOITBGetTournamentWins.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] Tournament[s][ ](win[s]|won) of %player%");
					Skript.registerExpression(ExprOITBGetZombieKills.class,Integer.class,ExpressionType.PROPERTY, "[OITB] [get] zombie[ ]kills of %player%");
				}
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered OITB expressions! (14)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Loops")) {
				Skript.registerExpression(ExprParticles.class,Effect.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] particle[[ ]types]");
				Skript.registerExpression(ExprInventoryViewers.class,HumanEntity.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] [player[']s] view(er[s]|ing) [of] %inventory%");
				Skript.registerExpression(ExprActivePotionEffects.class,PotionEffect.class,ExpressionType.PROPERTY, "[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered some loop expressions! (15)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SizeOfInventory")) {
				Skript.registerExpression(ExprNumberOfSlots.class,Integer.class,ExpressionType.PROPERTY, "[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered number of slots expression! (16)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.AmountOfItem")) {
				Skript.registerExpression(ExprAmountOfItem.class,Number.class,ExpressionType.PROPERTY, "(skellett|better|fixed|working|get) (size|number|amount) of %itemstack%");
				Skript.registerExpression(ExprAmountOfDroppedItem.class,Number.class,ExpressionType.PROPERTY, "[skellett] [get] (size|number|amount) of dropped %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered amount of item expression! (17)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedInventory")) {
				Skript.registerExpression(ExprInventory.class, InventoryType.class, ExpressionType.PROPERTY, "click[ed] inventory");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked inventory expression! (19)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedAction")) {
				Skript.registerExpression(ExprInventoryAction.class, InventoryAction.class, ExpressionType.PROPERTY, "(click[ed]|inventory) action");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked action expression! (20)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.AmountOfVariables")) {
				Skript.registerExpression(ExprAmountOfVariables.class, Number.class, ExpressionType.PROPERTY, "(size|amount) of [all] variables");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered amount of variables expression! (21)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.BlockStates")) {
				Skript.registerExpression(ExprBlockGetPower.class, Integer.class, ExpressionType.PROPERTY, "[redstone] power [[being] receiv(ed|ing) [(from|at)]] %location%", "%location% [redstone] power [[being] received]");
				Skript.registerExpression(ExprBlockGetDrops.class, ItemStack.class, ExpressionType.PROPERTY, "[(the|all)] [of] [the] [possible] drop[(ped|s)] [items] (from|of) [block [at]] %location% [(with|using) %-itemstack%]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered block state expressions! (22)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.FinalDamage")) {
				Skript.registerExpression(ExprFinalDamage.class, Double.class, ExpressionType.PROPERTY, "[skellett] final damage");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered final damage expression! (23)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.FixShoot")) {
				Skript.registerExpression(ExprFixShootArrowSpeed.class, Float.class, ExpressionType.SIMPLE, "(arrow|shot|velocity) speed [of (shot|arrow)]");
				Skript.registerExpression(ExprFixShootGetBow.class, ItemStack.class, ExpressionType.SIMPLE, "[skellett] [(event|get)] bow");
				Skript.registerExpression(ExprFixShootGetArrow.class, Entity.class, ExpressionType.SIMPLE, "[skellett] [(event|get)] [the] shot (arrow|projectile)");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered fixed shoot expressions! (24)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.FixFishing")) {
				Skript.registerExpression(ExprFixFishingGetCaught.class, Entity.class, ExpressionType.PROPERTY, "[skellett] caught (fish|item|entity)");
				Skript.registerExpression(ExprFixFishingGetXP.class, Integer.class, ExpressionType.PROPERTY, "[skellett] [fish[ing]] (xp|experience) [earned]");
				Skript.registerExpression(ExprFixFishingGetHook.class, Fish.class, ExpressionType.PROPERTY, "[skellett] [fish[ing]] hook");
				Skript.registerExpression(ExprFixFishingState.class, PlayerFishEvent.State.class, ExpressionType.PROPERTY, "[skellett] [fish[ing]] state");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered fixed fishing expressions! (25)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.InstaBreak")) {
				Skript.registerExpression(ExprInstaBreak.class, Boolean.class, ExpressionType.PROPERTY, "[event] inst(ant|a) break [state]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered insta break expression! (26)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.RedstoneCurrent")) {
				Skript.registerExpression(ExprRedstoneOldCurrent.class, Integer.class, ExpressionType.PROPERTY, "old [event] [redstone] current");
				Skript.registerExpression(ExprRedstoneNewCurrent.class, Integer.class, ExpressionType.PROPERTY, "new [event] [redstone] current");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered redstone current expressions! (27)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SpreadSource")) {
				Skript.registerExpression(ExprSpreadSource.class, Block.class, ExpressionType.PROPERTY, "[spread] source [block]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered spread source expression! (28)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Spectate")) {
				Skript.registerExpression(ExprSpectate.class, Entity.class, ExpressionType.PROPERTY, "(spec[tat(e|or|ing)]|view[ing]) [(target|state)] of %player%", "%player%'s (spec[tat(e|or|ing)]|view[ing]) [(target|state)]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered spectate expressions! (29)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Exhaustion")) {
				Skript.registerExpression(ExprExhaustion.class, Number.class, ExpressionType.PROPERTY, "exhaustion of %player%" ,"%player%'s exhaustion");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered exhaustion expression! (30)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.NextEmptySlot")) {
				Skript.registerExpression(ExprNextEmptySlot.class, Integer.class, ExpressionType.PROPERTY, "(next|first) empty slot of %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered empty slot expression! (31)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.MathExpressions")) {
				Skript.registerExpression(ExprAbsoluteValue.class, Number.class, ExpressionType.PROPERTY, "absolute [value] of %number%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered math expressions! (32)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ItemsWithin")) {
				Skript.registerExpression(ExprGetItemOfEntity.class, ItemStack.class, ExpressionType.SIMPLE, "[skellett] [get] item[s] (of|in|inside|within) %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered items within expression! (33)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SpectralArrow")) {
				if (Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
					Skript.registerExpression(ExprGlowingSpectralArrow.class, Number.class, ExpressionType.SIMPLE, "[spectral] [arrow] glowing time of %entity%", "%entity%'s [spectral] [arrow] glowing time");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered glowing spectral arrow expression! (34)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The spectral arrow expressions are only for 1.10+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SlimeSize")) {
				Skript.registerExpression(ExprSlimeSize.class, Number.class, ExpressionType.SIMPLE, "[skellett] slime size of %entity%", "[skellett] %entity%'s slime size");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered glowing spectral arrow expression! (34)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.GroundState")) {
				Skript.registerExpression(ExprIsOnGround.class, Boolean.class, ExpressionType.SIMPLE, "[(is|are)] [on] [the] ground [state] [of] [entity] %entity%", "[entity] %entity% [(is|are)] [on] [the] ground [state]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered ground state expression! (35)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Metadata")) {
				Skript.registerExpression(ExprMetadata.class, Object.class, ExpressionType.SIMPLE, "[(skellett|fixed)] meta[ ]data [value] %string% (of|in|within) %object%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered metadata expression! (36)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.MaxDamageTicks")) {
				Skript.registerExpression(ExprMaxDamageTicks.class, Timespan.class, ExpressionType.SIMPLE, "[skellett] [maximum] damage delay of %entity%", "[skellett] %entity%'s [maximum] damage delay");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered max damage ticks expression! (37)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SpawnReason")) {
				Skript.registerExpression(ExprSpawnReason.class, CreatureSpawnEvent.SpawnReason.class, ExpressionType.SIMPLE, "spawn reason");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered spawn reason expression! (38)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.TeleportCause")) {
				Skript.registerExpression(ExprTeleportCause.class, PlayerTeleportEvent.TeleportCause.class, ExpressionType.SIMPLE, "teleport cause");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered teleport cause expression! (39)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.TargetReason")) {
				Skript.registerExpression(ExprTargetReason.class, EntityTargetEvent.TargetReason.class, ExpressionType.SIMPLE, "target reason");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered target reason expression! (40)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.InvulnerableState")) {
				Skript.registerExpression(ExprInvulnerableState.class, Boolean.class, ExpressionType.SIMPLE, "invulnerable state of entity", "%entity%'s invulnerable state");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered invulnerable state expression! (41)"));
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
			if (config.getBoolean("Syntax.ConditionsSyntax.OITB")) {
				Skript.registerCondition(CondOITBHasCooldown.class, "[OITB] %player% has cool[ ]down [on] [ability] %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered OITB condition! (3)"));
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.ScoreboardExists")) {
				Skript.registerCondition(CondScoreboardExists.class, "score[ ][board] %string% (is set|exists)");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered scoreboard exists condition! (4)"));
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.Whitelist")) {
				Skript.registerCondition(CondIsWhitelisted.class, "[server] whitelist[ed] [state]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered whitelisted condition! (5)"));
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.SquidHQ")) {
				Skript.registerCondition(CondSquidHQrunning.class, "%player% is running [client] SquidHQ", "%player%'s client is SquidHQ");
				Skript.registerCondition(CondSquidHQrunning2.class, "%player% (is(n't| not)) running [client] SquidHQ", "%player%'s client (is(n't| not)) SquidHQ");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered SquidHQ condition! (6)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Events")) {
			if (config.getBoolean("Syntax.EventsSyntax.Download")) {
				Skript.registerEvent("On Download", SimpleEvent.class, EvtDownload.class, "[on] [skellett] download");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on download event! (1)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.FireworkExplode")) {
				try {
					Skript.registerEvent("On firework explode", SimpleEvent.class, FireworkExplodeEvent.class, "[on] [skellett] firework explo(de|sion)");
					EventValues.registerEventValue(FireworkExplodeEvent.class, Entity.class, new Getter<Entity, FireworkExplodeEvent>() {
						@Override
						public Entity get(FireworkExplodeEvent e) {
							return e.getEntity();
						}
					}, 0);
					}
				catch (NoClassDefFoundError ex) {
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cNo class found for FireworkExplode (Not supported spigot version)"));
					}
				}
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on firework explode event! (2)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.ItemDespawn")) {
				Skript.registerEvent("[On] [skellett] item[ ][stack] (despawn|remove|delete):", SimpleEvent.class, ItemDespawnEvent.class, "[on] [skellett] item[ ][stack] (despawn|remove|delete)");
				EventValues.registerEventValue(ItemDespawnEvent.class, Entity.class, new Getter<Entity, ItemDespawnEvent>() {
					@Override
					public Entity get(ItemDespawnEvent e) {
						return e.getEntity();
					}
				}, 0);
				EventValues.registerEventValue(ItemDespawnEvent.class, Location.class, new Getter<Location, ItemDespawnEvent>() {
					@Override
					public Location get(ItemDespawnEvent e) {
						return e.getLocation();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on item despawn event! (3)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.ItemMerge")) {
				Skript.registerEvent("[On] [skellett] item[ ][stack] (merge|combine[d]):", SimpleEvent.class, ItemMergeEvent.class, "[on] [skellett] item[ ][stack] (merge|combine[d])");
				EventValues.registerEventValue(ItemMergeEvent.class, Entity.class, new Getter<Entity, ItemMergeEvent>() {
					@Override
					public Entity get(ItemMergeEvent e) {
						return e.getEntity();
					}
				}, 0);
				EventValues.registerEventValue(ItemMergeEvent.class, Item.class, new Getter<Item, ItemMergeEvent>() {
					@Override
					public Item get(ItemMergeEvent e) {
						return e.getTarget();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on item despawn event! (3)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.MultiBlockPlace")) {
				Skript.registerEvent("[On] (multi[ple]|double)[ ][block][ ]place:", SimpleEvent.class, BlockMultiPlaceEvent.class, "[on] (multi[ple]|double)[ ][block][ ]place");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on multi block event! (3)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.ArrowPickup")) {
				if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
					Skript.registerEvent("[on] [skellett] arrow pickup:", SimpleEvent.class, PlayerPickupArrowEvent.class, "[on] [skellett] arrow pickup");
					EventValues.registerEventValue(PlayerPickupArrowEvent.class, Arrow.class, new Getter<Arrow, PlayerPickupArrowEvent>() {
						@Override
						public Arrow get(PlayerPickupArrowEvent e) {
							return e.getArrow();
						}
					}, 0);
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on arrow pickup event! (4)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The arrow pickup event is only for 1.8+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.OffhandSwitch")) {
				if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
					Skript.registerEvent("[on] [skellett] off[ ]hand (switch|move):", SimpleEvent.class, PlayerSwapHandItemsEvent.class, "[on] [skellett] off[ ]hand (switch|move)");
					EventValues.registerEventValue(PlayerSwapHandItemsEvent.class, ItemStack.class, new Getter<ItemStack, PlayerSwapHandItemsEvent>() {
						@Override
						public ItemStack get(PlayerSwapHandItemsEvent e) {
							return e.getMainHandItem();
						}
					}, 0);
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered Swap hand event event! (6)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The offhand switch event is only for 1.9+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.CreativeInventoryClick")) {
				Skript.registerEvent("[on] creative inventory click:", SimpleEvent.class, InventoryCreativeEvent.class, "[on] creative inventory click");
				EventValues.registerEventValue(InventoryCreativeEvent.class, ItemStack.class, new Getter<ItemStack, InventoryCreativeEvent>() {
					@Override
					public ItemStack get(InventoryCreativeEvent e) {
						return e.getCursor();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on creative inventory click event! (7)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.EntityTeleport")) {
				Skript.registerEvent("[on] entity teleport:", SimpleEvent.class, EntityTeleportEvent.class, "[on] entity teleport");
				EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
					@Override
					public Location get(EntityTeleportEvent e) {
						return e.getTo();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on entity teleport event! (8)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.EntityTeleport")) {
				Skript.registerEvent("[on] entity teleport:", SimpleEvent.class, EntityTeleportEvent.class, "[on] entity teleport");
				EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
					@Override
					public Location get(EntityTeleportEvent e) {
						return e.getTo();
					}
				}, 1);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on entity teleport event! (8)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.VehicleMove")) {
				Skript.registerEvent("[on] (vehicle|minecart|boat) move:", SimpleEvent.class, VehicleMoveEvent.class, "[on] (vehicle|minecart|boat) move");
				EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
					@Override
					public Location get(VehicleMoveEvent e) {
						return e.getFrom();
					}
				}, -1);
				EventValues.registerEventValue(VehicleMoveEvent.class, Location.class, new Getter<Location, VehicleMoveEvent>() {
					@Override
					public Location get(VehicleMoveEvent e) {
						return e.getTo();
					}
				}, 1);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on entity teleport event! (8)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.EntityBlockChange")) {
				Skript.registerEvent("[on] entity block (change|modify):", SimpleEvent.class, EntityChangeBlockEvent.class, "[on] entity block (change|modify)");
				Skript.registerExpression(ExprNewMaterial.class, Material.class, ExpressionType.PROPERTY, "[skellett] new [changed] material");
				EventValues.registerEventValue(EntityChangeBlockEvent.class, Block.class, new Getter<Block, EntityChangeBlockEvent>() {
					@Override
					public Block get(EntityChangeBlockEvent e) {
						return e.getBlock();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on entity block change event! (9)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.HotbarSwitch")) {
				Skript.registerEvent("[on] (hotbar|held [(item|slot)]|inventory slot) (switch|change):", SimpleEvent.class, PlayerItemHeldEvent.class, "[on] (hotbar|held [(item|slot)]|inventory slot) (switch|change)");
				Skript.registerExpression(ExprHotbarSwitchSlot.class, Integer.class, ExpressionType.SIMPLE, "%skellettstate% (hotbar|held) slot");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on hotbar switch event! (10)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.SmashHit")) {
				Skript.registerEvent("On (smashhit|async) damage:", SimpleEvent.class, EvtAsyncDamage.class, "[on] (smashhit|async) damage");
				EventValues.registerEventValue(EvtAsyncDamage.class, Entity.class, new Getter<Entity, EvtAsyncDamage>() {
					@Override
					public Entity get(EvtAsyncDamage e) {
						return e.getVictim();
					}
				}, 0);
				EventValues.registerEventValue(EvtAsyncDamage.class, Player.class, new Getter<Player, EvtAsyncDamage>() {
					@Override
					public Player get(EvtAsyncDamage e) {
						return e.getAttacker();
					}
				}, 0);
				EventValues.registerEventValue(EvtAsyncDamage.class, World.class, new Getter<World, EvtAsyncDamage>() {
					@Override
					public World get(EvtAsyncDamage e) {
						return e.getWorld();
					}
				}, 0);
				EventValues.registerEventValue(EvtAsyncDamage.class, Location.class, new Getter<Location, EvtAsyncDamage>() {
					@Override
					public Location get(EvtAsyncDamage e) {
						return e.getLocation();
					}
				}, 0);
				EventValues.registerEventValue(EvtAsyncDamage.class, Number.class, new Getter<Number, EvtAsyncDamage>() {
					@Override
					public Number get(EvtAsyncDamage e) {
						return e.getDamage();
					}
				}, 0);
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered async damage event! (11)"));
				}
				Skript.registerExpression(ExprAsyncDamage.class, Number.class, ExpressionType.SIMPLE, "(smashhit|async) damage [(received|taken)]");
			}
			if (config.getBoolean("Syntax.EventsSyntax.Breeding")) {
				if (Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
					Skript.registerEvent("[on] [skellett] bre[e]d[ing]:", SimpleEvent.class, EntityBreedEvent.class, "[on] [skellett] bre[e]d[ing]");
					Skript.registerExpression(ExprBreedingItem.class, ItemStack.class, ExpressionType.SIMPLE, "bre[e]d[ing] (item|material) [used]");
					Skript.registerExpression(ExprBreedingBreeder.class, LivingEntity.class, ExpressionType.SIMPLE, "breeder");
					Skript.registerExpression(ExprBreedingEntity.class, LivingEntity.class, ExpressionType.SIMPLE, "[final] bre[e]d[ed] entity");
					Skript.registerExpression(ExprBreedingXP.class, Number.class, ExpressionType.SIMPLE, "bre[e]d[ing] (xp|experience)");
					Skript.registerExpression(ExprBreedingFather.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] father");
					Skript.registerExpression(ExprBreedingMother.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] mother");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on breeding event + event expressions! (11)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The breeding event is only for 1.10+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.WorldChange")) {
				Skript.registerEvent("[on] [skellett] [player] world change:", SimpleEvent.class, PlayerChangedWorldEvent.class, "[on] [skellett] [player] world change");
				Skript.registerExpression(ExprWorldChangeFrom.class, World.class, ExpressionType.SIMPLE, "(previous|past) [changed] world");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on world change event + event expressions! (12)"));
				}
			}
		}
		if (config.getBoolean("bungee")) {
			String bungeeprefix = config.getString("BungeeSyntaxPrefix");
			if (bungeeprefix == null) {
				bungeeprefix = "[Skellett[ ][cord]]";
			}
			Skript.registerCondition(CondOnlinePlayer.class, bungeeprefix + "%string% is online bungee[[ ]cord]");
			Skript.registerExpression(ExprBungeeCount.class,Integer.class,ExpressionType.PROPERTY, bungeeprefix + "[Skellett[ ][cord]] [get] online bungee[[ ]cord] players");
			Skript.registerExpression(ExprBungeeCountServer.class,Integer.class,ExpressionType.PROPERTY, bungeeprefix + "[Skellett[ ][cord]] [get] online bungee[[ ]cord] players on [server] %string%");
			Skript.registerExpression(ExprPlayerSlotsOfServer.class,Integer.class,ExpressionType.PROPERTY, bungeeprefix + "[Skellett[ ][cord]] [get] [(player|server)] slot[s] of server %string%");
			Skript.registerExpression(ExprServerOnline.class,Boolean.class,ExpressionType.PROPERTY, bungeeprefix + "[Skellett[ ][cord]] [get] [(online|offline)] status of [bungee[ ][cord]] server %string%");
			Skript.registerEffect(EffBungeeKickAllPlayers.class, bungeeprefix + "[Skellett[ ][cord]] kick [all] [bungee[ ][cord]] players [from bungee[ ][cord]] [(by reason of|because [of]|on account of|due to)] %string%");
			Skript.registerEffect(EffBungeeMessageAllPlayers.class, bungeeprefix + "[Skellett[ ][cord]] (message|send|msg) %string% to [all] bungee[[ ][cord]] players");
			Skript.registerEffect(EffExecuteBungeeCommand.class, bungeeprefix + "[Skellett[ ][cord]] (make|run|execute) bungee[[ ][cord]] [console] command %string%");
			Skript.registerEffect(EffStopProxy.class, bungeeprefix + "[Skellett[ ][cord]] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (msg|string|text)] %string%");
			Skript.registerExpression(ExprBungeeUUID.class,String.class,ExpressionType.PROPERTY, bungeeprefix + "[skellett[cord]] [get] [player] bungee[[ ][cord]] uuid [of] %string%");
			Skript.registerExpression(ExprBungeeServerMOTD.class,String.class,ExpressionType.PROPERTY, bungeeprefix + "[skellett[cord]] [get] MOTD of [server] %string%");
		}
		if (config.getBoolean("Disguises")) {
			if (Bukkit.getPluginManager().getPlugin("LibsDisguises") != null) {
				Skript.registerExpression(ExprGetDisguise.class, DisguiseType.class, ExpressionType.PROPERTY, "[skellett] [[Libs]Disguises] Disguise of %entities%[[']s]", "[skellett] [[Libs]Disguises] %entity%'s disguise");
				Skript.registerEffect(EffDisguiseToAll.class, "[skellett] [[Libs]Disguises] [set] Disguise [of] %entities% (as|to) %string% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]");
				Skript.registerEffect(EffUnDisguiseToAll.class, "[skellett] [[Libs]Disguises] Un[( |-)]Disguise %entities%");
				Skript.registerCondition(CondIsDisguised.class, "[skellett] [[Libs]Disguises] [(entity|player)] %entities% [(is|are)] disguised");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered disguises"));
				}
			}
		}
		//BETA
		if (config.getBoolean("beta")) {
			Skript.registerEffect(EffCreateNpc.class, "[skellett] (create|spawn) [(a|an)] npc named %string% at %location%");
			Skript.registerEffect(EffDeleteNpc.class, "[skellett] (delete|remove) npc with (tag|id) %string%");
			Skript.registerEffect(EffSpawnParticle.class, "[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%");
		}
		Skript.registerEvent("[on] entity sho[o]t:", SimpleEvent.class, EntityShootBowEvent.class, "[on] entity sho[o]t");
		//register more event values
		EventValues.registerEventValue(PlayerTeleportEvent.class, PlayerTeleportEvent.TeleportCause.class, new Getter<PlayerTeleportEvent.TeleportCause, PlayerTeleportEvent>() {
			@Override
			public PlayerTeleportEvent.TeleportCause get(PlayerTeleportEvent e) {
				return e.getCause();
			}
		}, 0);
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHologramsUtils(this), (Plugin)this);
		Bukkit.getPluginManager().registerEvents((Listener)new SkellettHolograms(this), (Plugin)this);
		Bukkit.getConsoleSender().sendMessage(cc(enable));
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
			if (config.getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aMetrics registered!"));
			}
		} catch (IOException e) {
			if (config.getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cMetrics failed to register"));
			}
		}
	}
	public static String cc(String colour) {
		return ChatColor.translateAlternateColorCodes('&', colour);
	}
	public static int randomNumber(int Low, int High) {
		Random random = new Random();
		int Return = random.nextInt(High - Low) + Low;
		return Return;
	}
	public static Skellett getInstance() {
		return Instance;
	}
	@SuppressWarnings("static-access")
	public static Plugin getPlugin() {
		return Skellett.getInstance().plugin;
	}
}
/*
New Stuff:

#added particle testing (The effect is very experimental. Not really released or designed to work properly yet)
[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%

*/
/*
Todo:
- Fix redstone current and Fishing XP not being settable? Stupid skript.
- Make some effects settable expressions
- Add an option to hook into HologramsAPI
- Add option to change the center message length
- Finish syntax for Npc's
- LibsDisguises hook because I'm too tired to make a huge disguise class :3
- attack speed of %item%
- Set which direction the Shulker is attached to the wall.
- Skeleton horse trap support.
- Better boat support, + passenger of boat, and row speed of boat.
- https://forums.skunity.com/t/guardian-beam/5464/2
- Auto updater (Need a location to put this)
- update checker
- Arrow potions (Like set/get potions on an arrow + allow multiple potions per arrow maybe?)
- Statistics support
- Client side blocks and signs
- Vault prefix/suffix/permissions hook Skellett
- If player spectral view is show (Condition)
*/