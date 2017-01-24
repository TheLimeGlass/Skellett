package com.gmail.thelimeglass;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.EulerAngle;
import org.golde.bukkit.corpsereborn.nms.Corpses.CorpseData;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsService;

import com.gmail.thelimeglass.BossBars.CondBarHasFlag;
import com.gmail.thelimeglass.BossBars.EffBarAddFlag;
import com.gmail.thelimeglass.BossBars.EffBarAddPlayer;
import com.gmail.thelimeglass.BossBars.EffBarHideAndShow;
import com.gmail.thelimeglass.BossBars.EffBarRemoveAllPlayers;
import com.gmail.thelimeglass.BossBars.EffBarRemoveFlag;
import com.gmail.thelimeglass.BossBars.EffBarRemovePlayer;
import com.gmail.thelimeglass.BossBars.ExprBarColour;
import com.gmail.thelimeglass.BossBars.ExprBarFlags;
import com.gmail.thelimeglass.BossBars.ExprBarPlayers;
import com.gmail.thelimeglass.BossBars.ExprBarProgress;
import com.gmail.thelimeglass.BossBars.ExprBarStyle;
import com.gmail.thelimeglass.BossBars.ExprBarTitle;
import com.gmail.thelimeglass.BossBars.ExprBarVisible;
import com.gmail.thelimeglass.BossBars.ExprNewBossBar;
import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondFileExists;
import com.gmail.thelimeglass.Conditions.CondIsWhitelisted;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning2;
import com.gmail.thelimeglass.Effects.EffFirework;
import com.gmail.thelimeglass.Expressions.ExprGetClientWeather;
import com.gmail.thelimeglass.Expressions.ExprIsCollidable;
import com.gmail.thelimeglass.Expressions.ExprRedstoneNewCurrent;
import com.gmail.thelimeglass.Expressions.ExprRedstoneOldCurrent;
import com.gmail.thelimeglass.Expressions.ExprYaml;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomDescription;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomFighters;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomHome;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdomName;
import com.gmail.thelimeglass.Feudal.ExprFeudalMessage;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdomName;
import com.gmail.thelimeglass.Maps.SkellettMapRenderer;
import com.gmail.thelimeglass.MySQL.EffMySQLConnect;
import com.gmail.thelimeglass.MySQL.EffMySQLDisconnect;
import com.gmail.thelimeglass.MySQL.EffMySQLUpdate;
import com.gmail.thelimeglass.MySQL.ExprMySQLDatabase;
import com.gmail.thelimeglass.MySQL.ExprMySQLHost;
import com.gmail.thelimeglass.MySQL.ExprMySQLPassword;
import com.gmail.thelimeglass.MySQL.ExprMySQLPort;
import com.gmail.thelimeglass.MySQL.ExprMySQLQuery;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryBoolean;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryInteger;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryNumber;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryObject;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryString;
import com.gmail.thelimeglass.MySQL.ExprMySQLUsername;
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
import com.gmail.thelimeglass.ProtocolSupport.ExprBlockRemapperItemType;
import com.gmail.thelimeglass.ProtocolSupport.ExprItemRemapperID;
import com.gmail.thelimeglass.ProtocolSupport.ExprItemRemapperItemType;
import com.gmail.thelimeglass.ProtocolSupport.ExprProtocolVersion;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveExists;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveIsModifiable;
import com.gmail.thelimeglass.Scoreboards.CondTeamHasEntry;
import com.gmail.thelimeglass.Scoreboards.EffDeleteScoreboard;
import com.gmail.thelimeglass.Scoreboards.EffRegisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffRegisterTeam;
import com.gmail.thelimeglass.Scoreboards.EffResetEntryScores;
import com.gmail.thelimeglass.Scoreboards.EffScoreboardClearSlot;
import com.gmail.thelimeglass.Scoreboards.EffTeamAddEntry;
import com.gmail.thelimeglass.Scoreboards.EffTeamRemoveEntry;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterTeam;
import com.gmail.thelimeglass.Scoreboards.ExprEntries;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryScores;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryTeam;
import com.gmail.thelimeglass.Scoreboards.ExprGetObjective;
import com.gmail.thelimeglass.Scoreboards.ExprGetScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprGetTeam;
import com.gmail.thelimeglass.Scoreboards.ExprNewScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplaySlot;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveGetScore;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectives;
import com.gmail.thelimeglass.Scoreboards.ExprObjectivesByCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprPlayerScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprScore;
import com.gmail.thelimeglass.Scoreboards.ExprScoreEntry;
import com.gmail.thelimeglass.Scoreboards.ExprScoreObjective;
import com.gmail.thelimeglass.Scoreboards.ExprTeamDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamEntries;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyFire;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyInvisibles;
import com.gmail.thelimeglass.Scoreboards.ExprTeamName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamOptions;
import com.gmail.thelimeglass.Scoreboards.ExprTeamPrefix;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSize;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSuffix;
import com.gmail.thelimeglass.Scoreboards.ExprTeams;
import com.gmail.thelimeglass.Stylishboards.StyleManager;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Dependency;
import com.gmail.thelimeglass.Utils.DetectVersion;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;
import com.sainttx.holograms.api.Hologram;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import com.sainttx.holograms.api.line.HologramLine;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import net.citizensnpcs.api.npc.NPC;
import nl.lolmewn.stats.api.StatsAPI;
import nl.lolmewn.stats.api.stat.Stat;
import nl.lolmewn.stats.api.user.StatsHolder;
import nl.lolmewn.stats.bukkit.api.event.StatsHolderUpdateEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

@SuppressWarnings("deprecation")
public class Skellett extends JavaPlugin {
	
	public static final HashMap<String, Scoreboard> skellettBoards = new HashMap<>();
	public static Skellett instance;
	public static Plugin plugin;
	public static String prefix = "&8[&aSkellett&8] &e";
	public FileConfiguration config = getConfig();
	private File ceFile;
	private FileConfiguration ceData;
	private File spFile;
	public static FileConfiguration spData;
	private File mysqlFile;
	public static FileConfiguration mysqlData;
	private File syntaxFile;
	private static FileConfiguration syntaxData;
	public static File syntaxToggleFile;
	public static FileConfiguration syntaxToggleData;
	public static ZPermissionsService zPermissions;
	public static HologramManager hologramManager;
	public static StatsAPI statsAPI;
	
	public void onEnable(){
		instance = this;
		plugin = this;
		getCommand("skellett").setExecutor(new Command());
		getServer().getPluginManager().registerEvents(new Command(), this);
		if (!Objects.equals(getDescription().getVersion(), config.getString("version"))) {
			File f = new File(getDataFolder(), "config.yml");
			if (f.exists()) {
				f.delete();
			}
			File f2 = new File(getDataFolder(), "SyntaxToggles.yml");
			if (f2.exists()) {
				f2.delete();
			}
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&6New update found! Updating files..."));
			saveDefaultConfig();
		}
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			ceFile = new File(getDataFolder(), "CustomEvents.yml");
			spFile = new File(getDataFolder(), "SkellettProxy.yml");
			mysqlFile = new File(getDataFolder(), "MySQL.yml");
			syntaxFile = new File(getDataFolder(), "Syntax.yml");
			syntaxToggleFile = new File(getDataFolder(), "SyntaxToggles.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
				saveDefaultConfig();
			}
			if (!ceFile.exists()) {
				ceFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cCustomEvents.yml not found, generating a new config!"));
				saveResource("CustomEvents.yml", false);
			}
			ceData = new YamlConfiguration();
			try {
				ceData.load(ceFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!spFile.exists()) {
				spFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSkellettProxy.yml not found, generating a new file!"));
				saveResource("SkellettProxy.yml", false);
			}
			spData = new YamlConfiguration();
			try {
				spData.load(spFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!mysqlFile.exists()) {
				mysqlFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cMySQL.yml not found, generating a new file!"));
				saveResource("MySQL.yml", false);
			}
			mysqlData = new YamlConfiguration();
			try {
				mysqlData.load(mysqlFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxFile.exists()) {
				syntaxFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntax.yml not found, generating a new file!"));
				saveResource("Syntax.yml", false);
			}
			syntaxData = new YamlConfiguration();
			try {
				syntaxData.load(syntaxFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxToggleFile.exists()) {
				syntaxToggleFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntaxToggles.yml not found, generating a new file!"));
				saveResource("SyntaxToggles.yml", false);
			}
			syntaxToggleData = new YamlConfiguration();
			try {
				syntaxToggleData.load(syntaxToggleFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
		} catch (Exception error) {
			error.printStackTrace();
		}
		Skript.registerAddon(this);
		RegisterEvents.register();
		EnumClassInfo.create(EquipmentSlot.class, "equipmentslot").register();
		if (syntaxToggleData.getBoolean("Syntax.Effects.PlayerWindowProperty")) {
			EnumClassInfo.create(InventoryView.Property.class, "inventoryproperty").register();
		}
		if (syntaxToggleData.getBoolean("Syntax.Effects.EntityEffect")) {
			EnumClassInfo.create(EntityEffect.class, "entityeffect").register();
		}
		if (syntaxToggleData.getBoolean("Syntax.Effects.SeamlessPotion")) {
			Classes.registerClass(new ClassInfo<PotionEffectType>(PotionEffectType.class, "potioneffecttype")
				.name("potioneffecttype")
				.description("A getter for Spigot PotionEffectTypes")
				.parser(new Parser<PotionEffectType>() {
					@Override
					@Nullable
					public PotionEffectType parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(PotionEffectType h, int flags) {
						return h.toString();
					}
					@Override
					public String toVariableNameString(PotionEffectType h) {
						return h.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
		}
		if (syntaxToggleData.getBoolean("Syntax.Expressions.InventoryType")) {
			if (getServer().getPluginManager().getPlugin("SkQuery") == null && getServer().getPluginManager().getPlugin("SkStuff") == null) {
				EnumClassInfo.create(InventoryType.class, "inventorytype").register();
			}
		}
		if (syntaxToggleData.getBoolean("Syntax.Expressions.CollidableState")) {
			Skript.registerExpression(ExprIsCollidable.class,Boolean.class,ExpressionType.SIMPLE, "collid(e|able) [state] [of] %entity%");
		}
		if (syntaxToggleData.getBoolean("Syntax.Expressions.ClientWeather")) {
			Skript.registerExpression(ExprGetClientWeather.class,String.class,ExpressionType.SIMPLE, "[get] [client] weather of %player%");
		}
		if (syntaxToggleData.getBoolean("Syntax.Expressions.RedstoneCurrent")) {
			Skript.registerExpression(ExprRedstoneOldCurrent.class, Integer.class, ExpressionType.SIMPLE, "old [event] [redstone] current");
			Skript.registerExpression(ExprRedstoneNewCurrent.class, Integer.class, ExpressionType.SIMPLE, "new [event] [redstone] current");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.LineOfSight")) {
			Skript.registerCondition(CondLineOfSight.class, "%entity% [can] (see|visibly see|line of sight) [can see] %entity%");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.ClientTime")) {
			Skript.registerCondition(CondClientTimeRelative.class, "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.Whitelist")) {
			Skript.registerCondition(CondIsWhitelisted.class, "[server] whitelist[ed] [state]");
		}
		if (config.getBoolean("PluginHooks.SquidHQ")) {
			Skript.registerCondition(CondSquidHQrunning.class, "%player% is running [client] SquidHQ", "%player%'s client is SquidHQ");
			Skript.registerCondition(CondSquidHQrunning2.class, "%player% (is(n't| not)) running [client] SquidHQ", "%player%'s client (is(n't| not)) SquidHQ");
		}
		if (config.getBoolean("PluginHooks.zPermissions")) {
			try {
				zPermissions = Bukkit.getServicesManager().load(ZPermissionsService.class);
			}
			catch (NoClassDefFoundError e) {}
		}
		if (config.getBoolean("PluginHooks.StatsAPI")) {
			try {
				@SuppressWarnings("unchecked")
				RegisteredServiceProvider<StatsAPI> stats = (RegisteredServiceProvider<StatsAPI>) Bukkit.getServicesManager().getRegistrations(nl.lolmewn.stats.api.StatsAPI.class);
				statsAPI = stats.getProvider();
				Skript.registerEvent("[on] stats[[ ]api] update:", SimpleEvent.class, StatsHolderUpdateEvent.class, "[on] stats[[ ]api] update");
				Classes.registerClass(new ClassInfo<StatsHolder>(StatsHolder.class, "statsholder")
					.name("statsholder")
					.description("A getter for StatsHolder from StatsAPI")
					.parser(new Parser<StatsHolder>() {
						@Override
						@Nullable
						public StatsHolder parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(StatsHolder h, int flags) {
							return h.toString();
						}
						@Override
						public String toVariableNameString(StatsHolder h) {
							return h.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				Classes.registerClass(new ClassInfo<Stat>(Stat.class, "stat")
					.name("stat")
					.description("A getter for Stat from StatsAPI")
					.parser(new Parser<Stat>() {
						@Override
						@Nullable
						public Stat parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(Stat h, int flags) {
							return h.toString();
						}
						@Override
						public String toVariableNameString(Stat h) {
							return h.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
			}
			catch (NoClassDefFoundError e) {}
		}
		if (config.getBoolean("PluginHooks.CorpseReborn")) {
			Classes.registerClass(new ClassInfo<CorpseData>(CorpseData.class, "corpse")
				.parser(new Parser<CorpseData>() {
					@Override
					@Nullable
					public CorpseData parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(CorpseData c, int flags) {
						return c.toString();
					}
					@Override
					public String toVariableNameString(CorpseData c) {
						return c.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
		}
		if (config.getBoolean("PluginHooks.Holograms")) {
			hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
			Classes.registerClass(new ClassInfo<Hologram>(Hologram.class, "hologram")
				.name("hologram")
				.description("A getter for Holograms")
				.parser(new Parser<Hologram>() {
					@Override
					@Nullable
					public Hologram parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(Hologram h, int flags) {
						return h.toString();
					}
					@Override
					public String toVariableNameString(Hologram h) {
						return h.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Classes.registerClass(new ClassInfo<HologramLine>(HologramLine.class, "hologramline")
				.name("hologramline")
				.description("A getter for HologramLines")
				.parser(new Parser<HologramLine>() {
					@Override
					@Nullable
					public HologramLine parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(HologramLine h, int flags) {
						return h.toString();
					}
					@Override
					public String toVariableNameString(HologramLine h) {
						return h.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
		}
		if (syntaxToggleData.getBoolean("Main.Brewing")) {
			Skript.registerEvent("[on] [skellett] brew[ing]:", SimpleEvent.class, BrewEvent.class, "[on] [skellett] brew[ing]");
			EventValues.registerEventValue(BrewEvent.class, Number.class, new Getter<Number, BrewEvent>() {
				@Override
				public Number get(BrewEvent e) {
					return e.getFuelLevel();
				}
			}, 0);
			Classes.registerClass(new ClassInfo<BrewerInventory>(BrewerInventory.class, "brewerinventory")
				.name("brewerinventory")
				.description("A getter for BrewerInventory")
				.parser(new Parser<BrewerInventory>() {
					@Override
					@Nullable
					public BrewerInventory parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(BrewerInventory e, int flags) {
						return e.toString();
					}
					@Override
					public String toVariableNameString(BrewerInventory b) {
						return b.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
		}
		if (syntaxToggleData.getBoolean("Main.PrepareEnchant")) {
			Skript.registerEvent("[on] ([item] enchant prepare|prepare [item] enchant):", SimpleEvent.class, PrepareItemEnchantEvent.class, "[on] ([item] enchant prepare|prepare [item] enchant)");
			EventValues.registerEventValue(PrepareItemEnchantEvent.class, Block.class, new Getter<Block, PrepareItemEnchantEvent>() {
				@Override
				public Block get(PrepareItemEnchantEvent e) {
					return e.getEnchantBlock();
				}
			}, 0);
			EventValues.registerEventValue(PrepareItemEnchantEvent.class, Player.class, new Getter<Player, PrepareItemEnchantEvent>() {
				@Override
				public Player get(PrepareItemEnchantEvent e) {
					return e.getEnchanter();
				}
			}, 0);
			EventValues.registerEventValue(PrepareItemEnchantEvent.class, Number.class, new Getter<Number, PrepareItemEnchantEvent>() {
				@Override
				public Number get(PrepareItemEnchantEvent e) {
					return e.getEnchantmentBonus();
				}
			}, 0);
			EventValues.registerEventValue(PrepareItemEnchantEvent.class, ItemStack.class, new Getter<ItemStack, PrepareItemEnchantEvent>() {
				@Override
				public ItemStack get(PrepareItemEnchantEvent e) {
					return e.getItem();
				}
			}, 0);
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10") && !Bukkit.getServer().getVersion().contains("MC: 1.11)") && !Bukkit.getServer().getVersion().contains("MC: 1.11.1")) {
				Classes.registerClass(new ClassInfo<EnchantmentOffer>(EnchantmentOffer.class, "enchantmentoffer")
					.name("enchantmentoffer")
					.description("A getter for enchantment offers")
					.parser(new Parser<EnchantmentOffer>() {
						@Override
						@Nullable
						public EnchantmentOffer parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(EnchantmentOffer e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(EnchantmentOffer e) {
							return e.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
			}
		}
		if (syntaxToggleData.getBoolean("Main.Maps")) {
			Skript.registerEvent("[on] [player] map [(initialize|open)]:", SimpleEvent.class, MapInitializeEvent.class, "[on] [player] map [(initialize|open)]");
			Classes.registerClass(new ClassInfo<MapView>(MapView.class, "map")
				.name("map")
				.description("A getter for MapView.")
				.parser(new Parser<MapView>() {
					@Override
					@Nullable
					public MapView parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(MapView m, int flags) {
						return m.toString();
					}
					@Override
					public String toVariableNameString(MapView m) {
						return m.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Classes.registerClass(new ClassInfo<MapCursor>(MapCursor.class, "mapcursor")
				.parser(new Parser<MapCursor>() {
					@Override
					public MapCursor parse(String s, ParseContext parseContext) {
						return null;
					}

					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(MapCursor cursor, int i) {
						return cursor.toString();
					}

					@Override
					public String toVariableNameString(MapCursor cursor) {
						return cursor.toString();
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}
				}));
			Classes.registerClass(new ClassInfo<Image>(Image.class, "mapimage")
				.parser(new Parser<Image>() {
					@Override
					public Image parse(String s, ParseContext parseContext) {
						return null;
					}

					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(Image image, int i) {
						return image.toString();
					}

					@Override
					public String toVariableNameString(Image image) {
						return image.toString();
					}

					@Override
					public String getVariableNamePattern() {
						return ".+";
					}
				}));
		}
		if (syntaxToggleData.getBoolean("Main.Bossbars")) {
			if (Bukkit.getServer().getVersion().contains("MC: 1.6") || Bukkit.getServer().getVersion().contains("MC: 1.7") || Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cBossBars are only 1.9+ at the moment!"));
			} else {
				EnumClassInfo.create(BarColor.class, "barcolour").register();
				EnumClassInfo.create(BarStyle.class, "barstyle").register();
				EnumClassInfo.create(BarFlag.class, "barflag").register();
				Classes.registerClass(new ClassInfo<BossBar>(BossBar.class, "bossbar")
					.name("bossbar")
					.description("A getter for bossbar.")
					.parser(new Parser<BossBar>() {
						@Override
						@Nullable
						public BossBar parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(BossBar b, int flags) {
							return b.toString();
						}
						@Override
						public String toVariableNameString(BossBar b) {
							return b.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				Skript.registerExpression(ExprNewBossBar.class, BossBar.class, ExpressionType.SIMPLE, "[skellett] [create] [a] new [boss[ ]]bar [with flag %-barflag%]");
				Skript.registerEffect(EffBarAddPlayer.class, "[skellett] add %player% to [the] [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarVisible.class, Boolean.class, ExpressionType.SIMPLE, "[the] [skellett] visib(le|ility) [(for|of)] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] visib(le|ility)");
				Skript.registerEffect(EffBarRemovePlayer.class, "[skellett] remove %player% from [the] [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarColour.class, BarColor.class, ExpressionType.SIMPLE, "[the] [skellett] colo[u]r of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] colo[u]r");
				Skript.registerExpression(ExprBarStyle.class, BarStyle.class, ExpressionType.SIMPLE, "[the] [skellett] style of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] style");
				Skript.registerExpression(ExprBarPlayers.class, Player.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s player[[']s]");
				Skript.registerExpression(ExprBarProgress.class, Number.class, ExpressionType.SIMPLE, "[the] [skellett] progress of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] progress");
				Skript.registerExpression(ExprBarTitle.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (title|name|header|string) of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] (title|name|header|string)");
				Skript.registerCondition(CondBarHasFlag.class, "[skellett] [boss[ ]][bar] %bossbar% (1¦(ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [(the|a)] [boss[ ]][bar] [flag] %barflag%");
				Skript.registerEffect(EffBarRemoveAllPlayers.class, "[skellett] remove [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%");
				Skript.registerEffect(EffBarRemoveFlag.class, "[skellett] remove [boss[ ]][bar] [flag] %barflag% from [the] [boss[ ]][bar] %bossbar%");
				Skript.registerEffect(EffBarAddFlag.class, "[skellett] add [boss[ ]][bar] [flag] %barflag% to [the] [boss[ ]][bar] %bossbar%");
				Skript.registerEffect(EffBarHideAndShow.class, "[skellett] (1¦hide|2¦show) [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarFlags.class, BarFlag.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] flag[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s flag[[']s]");
			}
		}
		if (syntaxToggleData.getBoolean("Main.ArmorStands")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7")) {
				Classes.registerClass(new ClassInfo<EulerAngle>(EulerAngle.class, "eulerangle")
					.name("euler angle")
					.description("A getter for euler angle.")
					.parser(new Parser<EulerAngle>() {
						@Override
						@Nullable
						public EulerAngle parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(EulerAngle e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(EulerAngle e) {
							return e.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
			}
		}
		if (syntaxToggleData.getBoolean("Main.Scoreboards")) {
			Classes.registerClass(new ClassInfo<Objective>(Objective.class, "objective")
				.name("scoreboard objective")
				.description("A getter for scoreboard objectives.")
				.parser(new Parser<Objective>() {
					@Override
					@Nullable
					public Objective parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(Objective o, int flags) {
						return o.toString();
					}
					@Override
					public String toVariableNameString(Objective o) {
						return o.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Classes.registerClass(new ClassInfo<Scoreboard>(Scoreboard.class, "scoreboard")
				.name("scoreboard")
				.description("A getter for skellett scoreboards.")
				.parser(new Parser<Scoreboard>() {
					@Override
					@Nullable
					public Scoreboard parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(Scoreboard s, int flags) {
						return s.toString();
					}
					@Override
					public String toVariableNameString(Scoreboard s) {
						return s.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Classes.registerClass(new ClassInfo<Score>(Score.class, "score")
				.name("scoreboard score")
				.description("A getter for scoreboard scores.")
				.parser(new Parser<Score>() {
					@Override
					@Nullable
					public Score parse(String score, ParseContext context) {
						return null;
					}
					@Override
					public String toString(Score s, int flags) {
						return s.toString();
					}
					@Override
					public String toVariableNameString(Score s) {
						return s.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Classes.registerClass(new ClassInfo<Team>(Team.class, "team")
				.name("scoreboard team")
				.description("A getter for scoreboard teams.")
				.parser(new Parser<Team>() {
					@Override
					@Nullable
					public Team parse(String team, ParseContext context) {
						return null;
					}
					@Override
					public String toString(Team t, int flags) {
						return t.toString();
					}
					@Override
					public String toVariableNameString(Team t) {
						return t.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				EnumClassInfo.create(Team.Option.class, "teamoption").register();
				if (getServer().getPluginManager().getPlugin("skRayFall") == null) {
					EnumClassInfo.create(Team.OptionStatus.class, "optionstatus").register();
				}
			}
			Skript.registerExpression(ExprGetScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[get] (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprNewScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[create] [a] new (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprPlayerScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "(score[ ][board]|[skellett[ ]]board) of [player] %player%", "%player%'s (score[ ][board]|[skellett[ ]]board)");
			Skript.registerEffect(EffDeleteScoreboard.class, "(delete|clear|remove) (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprGetObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectiveCriteria.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective criteria [of] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%'s objective criteria");
			Skript.registerEffect(EffRegisterObjective.class, "register [new] (score[ ][board]|[skellett[ ]]board) objective %string% with [criteria] %string% [[(in|from)] %-scoreboard%]", "register [new] objective %string% with [criteria] %string% [(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]");
			Skript.registerCondition(CondObjectiveExists.class, "objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]] (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))");
			Skript.registerExpression(ExprObjectives.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] [(score[ ][board]|board)[[']s]] objectives [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectivesByCriteria.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffUnregisterObjective.class, "unregister (score[ ][board]|[skellett[ ]]board) objective %objective%");
			Skript.registerExpression(ExprObjectiveDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective display name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] display name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] display name");
			Skript.registerExpression(ExprObjectiveName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] name");
			Skript.registerExpression(ExprObjectiveDisplaySlot.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [display] slot [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] [display] slot", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] [display] slot");
			Skript.registerCondition(CondObjectiveIsModifiable.class, "[the] (score[ ][board]|[skellett[ ]]board) objective %objective% (1¦is modifiable|2¦is(n't| not) modifiable)");
			Skript.registerExpression(ExprObjectiveGetScore.class, Score.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [objective] %objective%['s] score [(for|from|of)] [entry] %string%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] [objective['s]] score [(for|from|of)] [entry] %string%");
			Skript.registerEffect(EffScoreboardClearSlot.class, "clear (score[ ][board]|board) [display] slot %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s]) [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|board) team of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[the] (score[ ][board]|board) [entry] %string%'s team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryScores.class, Score.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffResetEntryScores.class, "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) reset [(the|all)] [of] [the] scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffRegisterTeam.class, "register [a] [new] (score[ ][board]|[skellett[ ]]board) team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprTeams.class, Team.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] teams [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) %string% team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) [get] team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprScoreEntry.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [get] entry [(for|from|of)] score %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s score entry");
			Skript.registerExpression(ExprScoreObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [(for|from|of)] score %score%", "[the] (score[ ][board]|[skellett[ ]]board) %score%'s scores objective");
			Skript.registerExpression(ExprScore.class, Number.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) (score|number|slot) [(for|from|of)] %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s (score|number|slot)");
			Skript.registerEffect(EffTeamAddEntry.class, "(score[ ][board]|[skellett[ ]]board) add [the] entry [(from|of)] %string% to [the] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyFire.class, Boolean.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] friendly [fire] state [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyInvisibles.class, Boolean.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [friendly] invisible[s] [state] [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] team display name [(for|from|of)] %team%");
			Skript.registerExpression(ExprTeamEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board)[[']s] entr(ies|y[[']s]) (in|within) [the] [team] %team%");
			Skript.registerExpression(ExprTeamName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board)) [team] name [(for|of)] [team] %team%");
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerExpression(ExprTeamOptions.class, Team.OptionStatus.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%");
			}
			Skript.registerExpression(ExprTeamPrefix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] prefix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSuffix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] suffix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSize.class, Number.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] size [(for|of)] [team] %team%");
			Skript.registerCondition(CondTeamHasEntry.class, "[the] (score[ ][board]|[skellett[ ]]board) (1¦(ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [the] [entry] %string% [(in|within)] the [team] %team%");
			Skript.registerEffect(EffTeamRemoveEntry.class, "[(score[ ][board]|[skellett[ ]]board)] remove [the] entry [(from|of)] %string% from [the] [team] %team%");
			Skript.registerEffect(EffUnregisterTeam.class, "unregister [the] (score[ ][board]|[skellett[ ]]board) team %team%");
		}
		if (config.getBoolean("PluginHooks.OITB")) {
			if (Bukkit.getPluginManager().getPlugin("OneInTheBattle") != null) {
				Skript.registerEffect(EffOITBAddCoins.class, "[OITB] (add|give) %integer% coin[s] to %player%");
				Skript.registerEffect(EffOITBRemoveCoins.class, "[OITB] (remove|take|subtract) %integer% coin[s] from %player%");
				Skript.registerEffect(EffOITBSetCoins.class, "[OITB] [set] [player] coins of %player% to %integer%");
				Skript.registerEffect(EffOITBSetCooldown.class, "[OITB] [set] %player% cool[ ]down of [ability] %string% to %integer%");
				Skript.registerExpression(ExprOITBGetChallengeWins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Challenge[s][ ](win[s]|won) of %player%");
				Skript.registerExpression(ExprOITBGetCoins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] coins of %player%");
				Skript.registerExpression(ExprOITBGetDeaths.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] deaths of %player%");
				Skript.registerExpression(ExprOITBGetHighestZombiesWave.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player]['][s][ ]High[est][ ][Zombie][s][ ]Wave of %player%");
				Skript.registerExpression(ExprOITBGetHits.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] hits of %player%");
				Skript.registerExpression(ExprOITBGetKills.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] kills of %player%");
				Skript.registerExpression(ExprOITBGetModifier.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] modifier of %player%");
				Skript.registerExpression(ExprOITBGetPlayerExp.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player][ ]E[x]p[eri[(e|a)]nce] of %player%");
				Skript.registerExpression(ExprOITBGetPlayerRank.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player][ ]rank of %player%");
				Skript.registerExpression(ExprOITBGetPlayTime.class,String.class,ExpressionType.SIMPLE, "[OITB] [get] play[er][ ]time of %player%");
				Skript.registerExpression(ExprOITBGetShotsfired.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Shots[ ]fired of %player%");
				Skript.registerExpression(ExprOITBGetTopPlayers.class,String.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% players of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTopPlayersWithScore.class,String.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% player[s] with score[s] of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTopScores.class,Integer.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% scores of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTournamentWins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Tournament[s][ ](win[s]|won) of %player%");
				Skript.registerExpression(ExprOITBGetZombieKills.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] zombie[ ]kills of %player%");
				Skript.registerCondition(CondOITBHasCooldown.class, "[OITB] %player% has cool[ ]down [on] [ability] %string%");
			}
		}
		if (config.getBoolean("PluginHooks.Npc")) {
			EnumClassInfo.create(EntityType.class, "skellettentitytype").register();
			if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
				Classes.registerClass(new ClassInfo<NPC>(NPC.class, "npc")
					.name("npc")
					.description("A getter for npc")
					.parser(new Parser<NPC>() {
						@Override
						@Nullable
						public NPC parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(NPC e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(NPC e) {
							return e.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
			}
		}
		if (config.getBoolean("PluginHooks.ProtocolSupport")) {
			if (Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null) {
				Classes.registerClass(new ClassInfo<ProtocolVersion>(ProtocolVersion.class, "protocolversion")
					.name("protocol version")
					.description("A getter for protocol support's version")
					.parser(new Parser<ProtocolVersion>() {
						@Override
						@Nullable
						public ProtocolVersion parse(String ver, ParseContext context) {
							return null;
						}
						@Override
						public String toString(ProtocolVersion v, int flags) {
							return v.toString();
						}
						@Override
						public String toVariableNameString(ProtocolVersion v) {
							return v.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				Classes.registerClass(new ClassInfo<MaterialAndData>(MaterialAndData.class, "materialanddata")
					.name("material and data")
					.description("A getter for protocol support's material and data")
					.parser(new Parser<MaterialAndData>() {
						@Override
						@Nullable
						public MaterialAndData parse(String md, ParseContext context) {
							return null;
						}
						@Override
						public String toString(MaterialAndData md, int flags) {
							return md.toString();
						}
						@Override
						public String toVariableNameString(MaterialAndData md) {
							return md.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				Skript.registerExpression(ExprBlockRemapperItemType.class, MaterialAndData.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] block [of] %itemtype%(:| (with|and) data )%number% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprItemRemapperItemType.class, ItemType.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] item [of] %itemtype% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprItemRemapperID.class, Number.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] item [of] [ID] %number% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprProtocolVersion.class, ProtocolVersion.class, ExpressionType.SIMPLE, "[skellett] protocol[ ][support] version of %player%", "[skellett] %player%'s protocol[ ][support] version");
			}
		}
		if (config.getBoolean("PluginHooks.Feudal")) {
			if (Bukkit.getPluginManager().getPlugin("Feudal").isEnabled()) {
				String feudalprefix = config.getString("FeudalSyntaxPrefix");
				if (feudalprefix == null) {
					feudalprefix = "feudal [kingdom]";
				}
				Classes.registerClass(new ClassInfo<Kingdom>(Kingdom.class, "kingdom")
					.name("feudal kingdom")
					.description("A getter for Feudal kingdoms.")
					.parser(new Parser<Kingdom>() {
						@Override
						@Nullable
						public Kingdom parse(String kingdom, ParseContext context) {
							try {
								for(Kingdom k : Feudal.getKingdoms()){
									if(k.getName().equals(kingdom)) {
										return k;
									}
								}
							} catch (Exception e) {}
							return null;
						}
						@Override
						public String toString(Kingdom k, int flags) {
							return k.toString();
						}
						@Override
						public String toVariableNameString(Kingdom k) {
							return k.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				Skript.registerExpression(ExprFeudalPlayerKingdomName.class, String.class, ExpressionType.SIMPLE, feudalprefix + " name of %player%", "%player%'s feudal [kingdom] name");
				Skript.registerExpression(ExprFeudalLocationKingdomName.class, String.class, ExpressionType.SIMPLE, feudalprefix + " name at [location] %location%");
				Skript.registerExpression(ExprFeudalPlayerKingdom.class, Kingdom.class, ExpressionType.SIMPLE, feudalprefix + " of %player%", "%player%'s feudal [kingdom]");
				Skript.registerExpression(ExprFeudalLocationKingdom.class, Kingdom.class, ExpressionType.SIMPLE, feudalprefix + " at [location] %location%");
				Skript.registerExpression(ExprFeudalMessage.class, String.class, ExpressionType.SIMPLE, feudalprefix + " (config|files|messages) [message] %string%");
				Skript.registerExpression(ExprFeudalKingdomDescription.class, String.class, ExpressionType.SIMPLE, feudalprefix + " description of %kingdom%", "%kingdom%'s feudal [kingdom] description");
				Skript.registerExpression(ExprFeudalKingdomHome.class, Location.class, ExpressionType.SIMPLE, feudalprefix + " home of %kingdom%", "%kingdom%'s feudal [kingdom] home");
				Skript.registerExpression(ExprFeudalKingdomFighters.class, String.class, ExpressionType.SIMPLE, "(the|all)] [of] [the]" + feudalprefix + "fighter[[']s] of %kingdom%", "[(the|all)] [of] [the] %kingdom%'s" + feudalprefix + "fighter[[']s]", "[(the|all)] [of] [the] fighter[[']s] of " + feudalprefix + "%kingdom%");
			}
		}
		if (syntaxToggleData.getBoolean("Syntax.Effects.Particles")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				if (Classes.getExactClassInfo(Particle.class) == null) {
					EnumClassInfo.create(Particle.class, "particle").register();
				}
			}
		}
		if (getServer().getPluginManager().getPlugin("SkQuery") == null) {
			if (Classes.getExactClassInfo(Sound.class) == null) {
				EnumClassInfo.create(Sound.class, "sound").register();
			}
		}
		if (mysqlData.getBoolean("MySQL")) {
			Classes.registerClass(new ClassInfo<ResultSet>(ResultSet.class, "resultset")
				.name("mysql resultset")
				.description("A getter for mysql's resultset")
				.parser(new Parser<ResultSet>() {
					@Override
					@Nullable
					public ResultSet parse(String rs, ParseContext context) {
						return null;
					}
					@Override
					public String toString(ResultSet  rs, int flags) {
						return rs.toString();
					}
					@Override
					public String toVariableNameString(ResultSet rs) {
						return rs.toString();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
			Skript.registerEffect(EffMySQLConnect.class, "[skellett] connect [to] mysql");
			Skript.registerEffect(EffMySQLDisconnect.class, "[skellett] disconnect [from] mysql");
			Skript.registerEffect(EffMySQLUpdate.class, "[skellett] mysql update %string%");
			Skript.registerExpression(ExprMySQLHost.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] host");
			Skript.registerExpression(ExprMySQLUsername.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] username");
			Skript.registerExpression(ExprMySQLPassword.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] password");
			Skript.registerExpression(ExprMySQLPort.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql['s] port");
			Skript.registerExpression(ExprMySQLDatabase.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] database");
			Skript.registerExpression(ExprMySQLQuery.class, ResultSet.class, ExpressionType.SIMPLE, "[skellett] mysql result of query %string%");
			Skript.registerExpression(ExprMySQLQueryString.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql string[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryInteger.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql integer[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryNumber.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql (number|float)[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryBoolean.class, Boolean.class, ExpressionType.SIMPLE, "[skellett] mysql boolean[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryObject.class, Object.class, ExpressionType.SIMPLE, "[skellett] mysql object[s] %string% (in|from|of) %resultset%");
		}
		if (ceData.getBoolean("CustomEvents")) {
			for(int i = 1; i <= ceData.getInt("CustomEventSetup.NumberOfEvents"); i++) {
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Event> classType = ((Class<? extends Event>) Class.forName(ceData.getString("CustomEventSetup." + i + ".Event")));
					Skript.registerEvent(ceData.getString("CustomEventSetup." + i + ".Syntax"), SimpleEvent.class, classType, "[skellett] " + ceData.getString("CustomEventSetup." + i + ".Syntax"));
					RegisterEvents.add(classType);
					Bukkit.getConsoleSender().sendMessage(cc("&aRegistered custom event: &5" + ceData.getString("CustomEventSetup." + i + ".Syntax")));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (getServer().getPluginManager().getPlugin("SkQuery") == null) {
			if (syntaxToggleData.getBoolean("Main.Yaml")) {
				Skript.registerExpression(ExprYaml.class, Object.class, ExpressionType.SIMPLE, "[skellett] (file|y[a]ml) [file] (1¦value|2¦node[s]|3¦node[s with] keys|4¦list) %string% (in|at|from) [file] %string%");
			}
		}
		Skript.registerCondition(CondFileExists.class, "[skellett] [file] exist(s|ence) [(at|of)] %string% [is %-boolean%]");
		Skript.registerEffect(EffFirework.class, "[skellett] (launch|deploy) [%-strings%] firework[s] at %locations% [with] (duration|timed|time) %number% [colo[u]r[ed] (%-strings%|%-color%)]");
		Skript.registerEvent("[on] entity sho[o]t:", SimpleEvent.class, EntityShootBowEvent.class, "[on] entity sho[o]t");
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
		try {
			register();
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | InstantiationException e2) {
			e2.printStackTrace();
		}
		if (spData.get("Host") == null) {
			spData.set("Host", "localhost");
		}
		if (spData.get("Post") == null) {
			spData.set("Port", 7332);
		}
		try {
			spData.save(spFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (spData.getBoolean("SkellettProxy", false)) {
			Sockets.connect();
		}
		int version = Integer.parseInt(getDescription().getVersion().replaceAll("[^0-9]", ""));
		int update = 0;
		String updateString = null;
		if (config.getBoolean("VersionChecker", true)) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=34361")
					.getBytes("UTF-8"));
				updateString = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				update =  Integer.parseInt(updateString.replaceAll("[^0-9]", ""));
				if (version < update) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eThere is a new update for Skellett! Version: " + updateString));
				} else if (version > update) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRunning a beta version of Skellett! Use with caution"));
				} else if (!getDescription().getVersion().equals(updateString)) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRunning a Skellett Snapshot/Fix version!"));			
				}
			} catch (Exception ex) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cFailed to check for an update on spigot"));
			}
		}
		if (!config.getBoolean("DisableRegisteredInfo", false)) {
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aHas been enabled!"));
		}
	}
	public static String cc(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	public static int getTicks(Timespan time) {
		if (Skript.getVersion().toString().contains("2.2")) {
			Number tick = time.getTicks_i();
			return tick.intValue();
		} else {
			return time.getTicks();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void register() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		int effN = 0;
		int condN = 0;
		int exprN = 0;
		
		Set<Class<?>> classes = new HashSet<>();		
		Method method = JavaPlugin.class.getDeclaredMethod("getFile");
		
		method.setAccessible(true);
		File file = (File) method.invoke(this);
		try {
			JarFile jar = new JarFile(file);
			for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
				String name = entry.nextElement().getName().replace("/", ".");
				if (name.startsWith("com.gmail.thelimeglass") && name.endsWith(".class")) {
					try {
						classes.add(Class.forName(name.substring(0, name.length() - 6)));
					} catch (ClassNotFoundException error) {
						error.printStackTrace();
					} catch (NoClassDefFoundError | ExceptionInInitializerError e) {}
				}
			}
			jar.close();
		} catch(Exception error) {
			error.printStackTrace();
		}
		if (classes != null) {
			run: for (Class c : classes) {
				for (Annotation a : c.getAnnotations()) {
					if (a instanceof Disabled) {
						continue run;
					}
					if (a instanceof SkellettProxy) {
						if (!spData.getBoolean("SkellettProxy", false)) {
							continue run;
						}
					}
					if (a instanceof Dependency) {
						if (Bukkit.getPluginManager().getPlugin(((Dependency) c.getAnnotation(Dependency.class)).value()) == null) {
							continue run;
						}
					}
					if (a instanceof DetectVersion) {
						if (Bukkit.getVersion().contains("1.8") && !c.getName().contains("1_8")){
							continue run;
						} else if ((Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.1")) && !(c.getName().contains("1_9") && c.getName().contains("R1"))){
							continue run;
						} else if ((Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.2")) && !(c.getName().contains("1_9") && c.getName().contains("R2"))){
							continue run;
						} else if (Bukkit.getVersion().contains("1.10") && !c.getName().contains("1_10")){
							continue run;
						} else if (Bukkit.getVersion().contains("1.11") && !c.getName().contains("1_11")){
							continue run;
						}
					}
					if (a instanceof Version) {
						String[] versions = {"1.8R3", "1.9R1", "1.9R2", "1.10" , "1.11", "1.11.2"};
						Integer server = null;
						Integer serverTag = null;
						for (int i = 0; i < versions.length; i++) {
							if (versions[i].contains("R")) {
								String[] subVersion = versions[i].split("R");
								if (Bukkit.getVersion().contains(subVersion[0]) && Bukkit.getVersion().contains("R0." + subVersion[1])) {
									server = i;
								}
							} else {
								if (Bukkit.getVersion().contains(versions[i])) {
									server = i;
								}
							}
							if (versions[i].equals(((Version) c.getAnnotation(Version.class)).value())) {
								serverTag = i;
							}
						}
						if (serverTag == null || server == null || serverTag > server) {
							if (!config.getBoolean("DisableCompatableWarning") && c.isAnnotationPresent(Config.class)) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + ((Config) c.getAnnotation(Config.class)).value() + " syntax is for " + versions[serverTag] + "+ spigot versions!"));
							}
							continue run;
						}
					}
				}
				if (c.isAnnotationPresent(Syntax.class) && c.isAnnotationPresent(Config.class)) {
					String node = ((Config) c.getAnnotation(Config.class)).value();
					String[] syntax = ((Syntax) c.getAnnotation(Syntax.class)).value();
					if (syntaxData.get(node) == null) {
						List<String> list = new ArrayList<String>(Arrays.asList(syntax));
						if (list.size() <= 1) {
							list.add(null);
						}
						syntaxData.set(node, list);
					} if (syntaxData.get(node) != null) {
						if (syntaxData.getList(node) == null) {
							syntaxData.getList(node).add(null);
						}
						List<Object> list = (List<Object>) syntaxData.getList(node);
						for (String s : syntax) {
							if (!syntaxData.getList(node).contains(s)) {
								list.add(s);
							}
							list.remove(null);
						}
					}
					try {
						syntaxData.save(syntaxFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					FileConfiguration data = syntaxToggleData;
					if (c.isAnnotationPresent(MainConfig.class)) {
						data = config;
					}
					if (c.isAnnotationPresent(SkellettProxy.class)) {
						data = spData;
					}
					if (ch.njol.skript.lang.Effect.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering effect " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering effect " + node));
							}
						}
						if (!c.isAnnotationPresent(FullConfig.class)) {
							node = "Syntax.Effects." + node;
						}
						if (data.getBoolean(node)) {
							Skript.registerEffect(c, syntax);
							effN++;
							/*if (syntaxData.getList(node) != null) {
								strings.addAll((Collection<? extends String>) syntaxData.get(node));
								Skript.registerEffect(c, strings.toArray(new String[strings.size()]));
							} else {
								Skript.registerEffect(c, syntaxData.getString(node));
							}*/
						}
					} else if (Condition.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering condition " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering condition " + node));
							}
						}
						if (!c.isAnnotationPresent(FullConfig.class)) {
							node = "Syntax.Conditions." + node;
						}
						if (data.getBoolean(node)) {
							Skript.registerCondition(c, syntax);
							condN++;
						}
					} else if (Expression.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering expression " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering expression " + node));
							}
						}
						if (c.isAnnotationPresent(PropertyType.class)) {
							if (!c.isAnnotationPresent(FullConfig.class)) {
								node = "Syntax.Expressions." + node;
							}
							if (c.isAnnotationPresent(RegisterEnum.class)) {
								if (Classes.getExactClassInfo(((Expression) c.newInstance()).getReturnType()) == null) {
									EnumClassInfo.create(((Expression) c.newInstance()).getReturnType(), ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).value()).register();
								}
							}
							if (data.getBoolean(node)) {
								for (ExpressionType et : ExpressionType.values()) {
									if (et.name().equals(((PropertyType) c.getAnnotation(PropertyType.class)).value())) {
										try {
											Skript.registerExpression(c, ((Expression) c.newInstance()).getReturnType(), et, syntax);
											exprN++;
										} catch (IllegalAccessException e) {
											Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
											e.printStackTrace();
										} catch (InstantiationException e) {
											Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
											e.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (!config.getBoolean("DisableRegisteredInfo", false)) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "Registered &a" + effN + " &eEffects, &a" + condN + "&e Conditions, &a" + exprN + "&e Expressions and &a" + RegisterEvents.getClasses().size() + "&e Events"));
		}
	}
	public void onDisable(){
		StyleManager.dump();
		SkellettMapRenderer.dump();
		RegisterEvents.dump();
	}
}
/*
Needs fixing:

#This is disabled
[a] new euler angle

angle of body (from|on) armo[u]r stand %entity%
armo[u]r stand %entity%['s] body angle

New Stuff:

#Effects:

	[skellett] hide [player] %player% from %players%
	
	[skellett] show [player] %player% to %players%

#Expressions:

	#Loopable
	[(the|all)] [of] [the] hidden players (of|from) %player%
	[all] [of] %player%'s hidden players

Added Asynchronously methods to the Regenerators. This option can be changed in the config.yml
		
*/