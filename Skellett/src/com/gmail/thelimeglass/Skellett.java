package com.gmail.thelimeglass;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.ResultSet;
import java.util.Objects;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.inventory.ClickType;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Books.ExprBookAuthor;
import com.gmail.thelimeglass.Books.ExprBookTitle;
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
import com.gmail.thelimeglass.Bungee.CondOnlinePlayer;
import com.gmail.thelimeglass.Bungee.EffBungeeActionbar;
import com.gmail.thelimeglass.Bungee.EffBungeeChat;
import com.gmail.thelimeglass.Bungee.EffBungeeKickAllPlayers;
import com.gmail.thelimeglass.Bungee.EffBungeeKickPlayer;
import com.gmail.thelimeglass.Bungee.EffBungeeMessageAllPlayers;
import com.gmail.thelimeglass.Bungee.EffBungeeMessagePlayer;
import com.gmail.thelimeglass.Bungee.EffBungeeSendServer;
import com.gmail.thelimeglass.Bungee.EffExecuteBungeeCommand;
import com.gmail.thelimeglass.Bungee.EffStopProxy;
import com.gmail.thelimeglass.Bungee.ExprBungeeCount;
import com.gmail.thelimeglass.Bungee.ExprBungeeCountServer;
import com.gmail.thelimeglass.Bungee.ExprBungeeGetPlayers;
import com.gmail.thelimeglass.Bungee.ExprBungeeGetPlayersServer;
import com.gmail.thelimeglass.Bungee.ExprBungeeGetServers;
import com.gmail.thelimeglass.Bungee.ExprBungeePlayerIP;
import com.gmail.thelimeglass.Bungee.ExprBungeePlayerName;
import com.gmail.thelimeglass.Bungee.ExprBungeePlayerServer;
import com.gmail.thelimeglass.Bungee.ExprBungeeServerIP;
import com.gmail.thelimeglass.Bungee.ExprBungeeServerMOTD;
import com.gmail.thelimeglass.Bungee.ExprBungeeUUID;
import com.gmail.thelimeglass.Bungee.ExprPlayerSlotsOfServer;
import com.gmail.thelimeglass.Bungee.ExprServerOnline;
import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondEventCancelled;
import com.gmail.thelimeglass.Conditions.CondFileExists;
import com.gmail.thelimeglass.Conditions.CondIsWhitelisted;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Conditions.CondScoreboardExists;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning;
import com.gmail.thelimeglass.Conditions.CondSquidHQrunning2;
import com.gmail.thelimeglass.Disguises.CondIsDisguised;
import com.gmail.thelimeglass.Disguises.EffDisguiseNextEntity;
import com.gmail.thelimeglass.Disguises.EffDisguiseToAll;
import com.gmail.thelimeglass.Disguises.EffUnDisguiseToAll;
import com.gmail.thelimeglass.Disguises.ExprGetDisguise;
import com.gmail.thelimeglass.Disguises.ExprSelfViewDisguise;
import com.gmail.thelimeglass.Effects.EffBlockConstructor;
import com.gmail.thelimeglass.Effects.EffClearSlot;
import com.gmail.thelimeglass.Effects.EffDownload;
import com.gmail.thelimeglass.Effects.EffFilesCreate;
import com.gmail.thelimeglass.Effects.EffFilesDelete;
import com.gmail.thelimeglass.Effects.EffFirework;
import com.gmail.thelimeglass.Effects.EffForceRespawn;
import com.gmail.thelimeglass.Effects.EffLeashBlock;
import com.gmail.thelimeglass.Effects.EffMessageCenter;
import com.gmail.thelimeglass.Effects.EffOpenInventory;
import com.gmail.thelimeglass.Effects.EffPlaySound;
import com.gmail.thelimeglass.Effects.EffPlaySoundPlayer;
import com.gmail.thelimeglass.Effects.EffPlayerTeleportWorldSpawn;
import com.gmail.thelimeglass.Effects.EffReloadWhitelist;
import com.gmail.thelimeglass.Effects.EffResetClientWeather;
import com.gmail.thelimeglass.Effects.EffSavePlayerList;
import com.gmail.thelimeglass.Effects.EffSetClientWeather;
import com.gmail.thelimeglass.Effects.EffSetCollidable;
import com.gmail.thelimeglass.Effects.EffSetInventoryProperty;
import com.gmail.thelimeglass.Effects.EffSetStack;
import com.gmail.thelimeglass.Effects.EffSetTabName;
import com.gmail.thelimeglass.Effects.EffSetWhitelist;
import com.gmail.thelimeglass.Effects.EffSpawnParticle;
import com.gmail.thelimeglass.Effects.EffUnZip;
import com.gmail.thelimeglass.Events.EvtAsyncDamage;
import com.gmail.thelimeglass.Expressions.ExprAbsoluteValue;
import com.gmail.thelimeglass.Expressions.ExprActivePotionEffects;
import com.gmail.thelimeglass.Expressions.ExprAmountOfDroppedItem;
import com.gmail.thelimeglass.Expressions.ExprAmountOfItem;
import com.gmail.thelimeglass.Expressions.ExprAmountOfVariables;
import com.gmail.thelimeglass.Expressions.ExprAsyncDamage;
import com.gmail.thelimeglass.Expressions.ExprBlockGetDrops;
import com.gmail.thelimeglass.Expressions.ExprBlockGetPower;
import com.gmail.thelimeglass.Expressions.ExprBlockXP;
import com.gmail.thelimeglass.Expressions.ExprBreedingBreeder;
import com.gmail.thelimeglass.Expressions.ExprBreedingEntity;
import com.gmail.thelimeglass.Expressions.ExprBreedingFather;
import com.gmail.thelimeglass.Expressions.ExprBreedingItem;
import com.gmail.thelimeglass.Expressions.ExprBreedingMother;
import com.gmail.thelimeglass.Expressions.ExprBreedingXP;
import com.gmail.thelimeglass.Expressions.ExprClickedCursor;
import com.gmail.thelimeglass.Expressions.ExprClickedInventory;
import com.gmail.thelimeglass.Expressions.ExprClickedItem;
import com.gmail.thelimeglass.Expressions.ExprClickedRawSlot;
import com.gmail.thelimeglass.Expressions.ExprClickedSlot;
import com.gmail.thelimeglass.Expressions.ExprClickedSlotType;
import com.gmail.thelimeglass.Expressions.ExprClickedType;
import com.gmail.thelimeglass.Expressions.ExprClickedTypeNumber;
import com.gmail.thelimeglass.Expressions.ExprCropState;
import com.gmail.thelimeglass.Expressions.ExprCropStateOfBlock;
import com.gmail.thelimeglass.Expressions.ExprCustomName;
import com.gmail.thelimeglass.Expressions.ExprEnchantmentNumber;
import com.gmail.thelimeglass.Expressions.ExprEntityGlowing;
import com.gmail.thelimeglass.Expressions.ExprEntityID;
import com.gmail.thelimeglass.Expressions.ExprExhaustion;
import com.gmail.thelimeglass.Expressions.ExprFallDistance;
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
import com.gmail.thelimeglass.Expressions.ExprFuseTime;
import com.gmail.thelimeglass.Expressions.ExprGetClientWeather;
import com.gmail.thelimeglass.Expressions.ExprGetItemOfEntity;
import com.gmail.thelimeglass.Expressions.ExprGlowingSpectralArrow;
import com.gmail.thelimeglass.Expressions.ExprGroundState;
import com.gmail.thelimeglass.Expressions.ExprHideEnchants;
import com.gmail.thelimeglass.Expressions.ExprInstaBreak;
import com.gmail.thelimeglass.Expressions.ExprInventoryAction;
import com.gmail.thelimeglass.Expressions.ExprInventoryType;
import com.gmail.thelimeglass.Expressions.ExprInventoryViewers;
import com.gmail.thelimeglass.Expressions.ExprInvulnerableState;
import com.gmail.thelimeglass.Expressions.ExprIsCollidable;
import com.gmail.thelimeglass.Expressions.ExprLlamaColorType;
import com.gmail.thelimeglass.Expressions.ExprLlamaInventory;
import com.gmail.thelimeglass.Expressions.ExprLlamaInventoryDecor;
import com.gmail.thelimeglass.Expressions.ExprMaxDamageTicks;
import com.gmail.thelimeglass.Expressions.ExprMetadata;
import com.gmail.thelimeglass.Expressions.ExprNearbyEntities;
import com.gmail.thelimeglass.Expressions.ExprNextEmptySlot;
import com.gmail.thelimeglass.Expressions.ExprNoItemNBT;
import com.gmail.thelimeglass.Expressions.ExprSleepIgnored;
import com.gmail.thelimeglass.Expressions.ExprSlimeSize;
import com.gmail.thelimeglass.Expressions.ExprNumberOfSlots;
import com.gmail.thelimeglass.Expressions.ExprOfflinePlayers;
import com.gmail.thelimeglass.Expressions.ExprOperators;
import com.gmail.thelimeglass.Expressions.ExprParticles;
import com.gmail.thelimeglass.Expressions.ExprPlayerInventoryCursor;
import com.gmail.thelimeglass.Expressions.ExprRedstoneNewCurrent;
import com.gmail.thelimeglass.Expressions.ExprRedstoneOldCurrent;
import com.gmail.thelimeglass.Expressions.ExprRemoveItemNBT;
import com.gmail.thelimeglass.Expressions.ExprRoundDecimal;
import com.gmail.thelimeglass.Expressions.ExprSneakState;
import com.gmail.thelimeglass.Expressions.ExprSpawnReason;
import com.gmail.thelimeglass.Expressions.ExprSpectate;
import com.gmail.thelimeglass.Expressions.ExprSpreadSource;
import com.gmail.thelimeglass.Expressions.ExprSprintState;
import com.gmail.thelimeglass.Expressions.ExprTargetReason;
import com.gmail.thelimeglass.Expressions.ExprTeleportCause;
import com.gmail.thelimeglass.Expressions.ExprUnleashHitch;
import com.gmail.thelimeglass.Expressions.ExprUnleashReason;
import com.gmail.thelimeglass.Expressions.ExprWorldChangeFrom;
import com.gmail.thelimeglass.Expressions.ExprYaml;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomDescription;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomFighters;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomHome;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdomName;
import com.gmail.thelimeglass.Feudal.ExprFeudalMessage;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdomName;
import com.gmail.thelimeglass.Holograms.EffDeleteHologram;
import com.gmail.thelimeglass.Holograms.EffNewHologram;
import com.gmail.thelimeglass.MySQL.EffMySQLConnect;
import com.gmail.thelimeglass.MySQL.EffMySQLDisconnect;
import com.gmail.thelimeglass.MySQL.EffMySQLUpdate;
import com.gmail.thelimeglass.MySQL.ExprMySQLDatabase;
import com.gmail.thelimeglass.MySQL.ExprMySQLHost;
import com.gmail.thelimeglass.MySQL.ExprMySQLPassword;
import com.gmail.thelimeglass.MySQL.ExprMySQLQuery;
import com.gmail.thelimeglass.MySQL.ExprMySQLUsername;
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
import com.gmail.thelimeglass.Scoreboards.ExprGetTeam;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplaySlot;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveGetScore;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectives;
import com.gmail.thelimeglass.Scoreboards.ExprObjectivesByCriteria;
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
import com.gmail.thelimeglass.SkellettCord.CondUsingForge;
import com.gmail.thelimeglass.SkellettCord.EffSkellettCordTest;
import com.gmail.thelimeglass.SkellettCord.ExprBungeeYAML;
import com.gmail.thelimeglass.SkellettCord.ExprBungeecordVersion;
import com.gmail.thelimeglass.SkellettCord.ExprPlayerDisplayName;
import com.gmail.thelimeglass.SkellettCord.ExprPlayerLimit;
import com.gmail.thelimeglass.SkellettCord.ExprPlayerPing;
import com.gmail.thelimeglass.SkellettStateExpressions.ExprHotbarSwitchSlot;
import com.gmail.thelimeglass.Tablist.EffDeleteTabItem;
import com.gmail.thelimeglass.Tablist.EffNewTabItem;
import com.gmail.thelimeglass.Tablist.EffPlayerAddTabItem;
import com.gmail.thelimeglass.Tablist.EffPlayerClearList;
import com.gmail.thelimeglass.Tablist.EffPlayerFillDefaults;
import com.gmail.thelimeglass.Tablist.EffPlayerRemoveList;
import com.gmail.thelimeglass.Tablist.EffPlayerRemoveTabItem;
import com.gmail.thelimeglass.Tablist.EffPlayerUpdateTab;
import com.gmail.thelimeglass.Tablist.ExprAllTabItems;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.ExprNewMaterial;
import com.gmail.thelimeglass.Utils.SkellettState;
import com.gmail.thelimeglass.versionControl.EffActionbarv1_10_R1;
import com.gmail.thelimeglass.versionControl.EffActionbarv1_8_R3;
import com.gmail.thelimeglass.versionControl.EffActionbarv1_9_R1;
import com.gmail.thelimeglass.versionControl.EffActionbarv1_9_R2;
import com.gmail.thelimeglass.versionControl.EffTitlev1_10_R1;
import com.gmail.thelimeglass.versionControl.EffTitlev1_8_R3;
import com.gmail.thelimeglass.versionControl.EffTitlev1_9_R1;
import com.gmail.thelimeglass.versionControl.EffTitlev1_9_R2;
import com.gmail.thelimeglass.versionControl.ExprGravityState;
import com.gmail.thelimeglass.versionControl.ExprSilentState;
import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
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
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

@SuppressWarnings("deprecation")
public class Skellett extends JavaPlugin {
	
	public static Skellett instance;
	public static Plugin plugin;
	public static String prefix = "&8[&aSkellett&8] &e";
	private FileConfiguration config = getConfig();
	private File ceFile;
	private FileConfiguration ceData;
	private File spFile;
	private FileConfiguration spData;
	private File mysqlFile;
	public static FileConfiguration mysqlData;
	
	public void onEnable(){
		instance = this;
		plugin = this;
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			ceFile = new File(getDataFolder(), "CustomEvents.yml");
			spFile = new File(getDataFolder(), "SkellettProxy.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
				saveDefaultConfig();
			}
			if (!ceFile.exists()) {
				ceFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
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
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cCustomEvents.yml not found, generating a new file!"));
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
		} catch (Exception error) {
			error.printStackTrace();
		}
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
			.name("skellettstate")
			.description("Returns a state used in Skellett")
			.parser(new Parser<SkellettState>() {
				@Override
				@Nullable
				public SkellettState parse(String state, ParseContext context) {
					try {
						return SkellettState.valueOf(state.toUpperCase());
					} catch (Exception e) {}
					return null;
				}
				@Override
				public String toString(SkellettState s, int flags) {
					return s.toString();
				}
				@Override
				public String toVariableNameString(SkellettState s) {
					return s.toString();
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
				Skript.registerEffect(EffDownload.class, "[skellett] d[ownload][l] [from] [url] %string% to %string%");
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
				Skript.registerEffect(EffFilesCreate.class, "[skellett] c[reate][ ][[f][ile]] %string%");
				Skript.registerEffect(EffFilesDelete.class, "[skellett] d[elete][ ][[f][ile]] %string%");
				Skript.registerEffect(EffUnZip.class, "[skellett] unzip %string% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered file effect! (14)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.TabName")) {
				Skript.registerEffect(EffSetTabName.class, "[skellett] set tab name of %player% to %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered tab name effect! (17)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.CenterMessage")) {
				Skript.registerEffect(EffMessageCenter.class, "(message|send [message]) center[ed] %strings% to %players% [[with[ text]] %-string%]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered center message effect! (18)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.BlockConstructor")) {
				Skript.registerEffect(EffBlockConstructor.class, "(create|start|make|build|construct) %string% with %itemtype% at %location% [[with effect[s]] %-boolean%]");
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
				Skript.registerEffect(EffClearSlot.class, "(clear|empty|reset) (inventory|menu|gui) [slot %-integer%] [(of|in)] %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clear slot effect! (21)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.OpenInventory")) {
				Skript.registerEffect(EffOpenInventory.class, "[skellett] open [[better] inventory [type]] %string% [with %-number% row[s]] [named %-string%] to %players%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered open inventory effect! (22)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.PlayerWindowProperty")) {
				EnumClassInfo.create(InventoryView.Property.class, "inventoryproperty").register();
				Skript.registerEffect(EffSetInventoryProperty.class, "(set|change) %player%['s] (window|[current] inventory) property [of] %inventoryproperty% to %number%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered window property effect! (23)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.Actionbar")) {
				if (Bukkit.getVersion().contains("1.8")){
					Skript.registerEffect(EffActionbarv1_8_R3.class, "[skellett] (send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%");
				} else if (Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.1")){
					Skript.registerEffect(EffActionbarv1_9_R1.class, "[skellett] (send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%");
				} else if (Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.2")){
					Skript.registerEffect(EffActionbarv1_9_R2.class, "[skellett] (send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%");
				} else if (Bukkit.getVersion().contains("1.10")){
					Skript.registerEffect(EffActionbarv1_10_R1.class, "[skellett] (send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%");
				}
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered actionbar effect! (24)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.TitleAndSubtitle")) {
				String TAS1 = config.getString("Syntax.EffectsSyntax.TitleAndSubtitleSyntax1");
				String TAS2 = config.getString("Syntax.EffectsSyntax.TitleAndSubtitleSyntax2");
				if (TAS1 == null) {
					TAS1 = "[skellett] (send|show) [a] (1¦subtitle|2¦title) [from] %string% [(with|and) [subtitle] %-string%] (to|for) %players% for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]";
				}
				if (TAS2 == null) {
					TAS2 = "[skellett] (send|show) %players% [a] (1¦subtitle|2¦title) [(with|from)] %string% [(with|and) [subtitle] %-string%] for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]";
				}
				if (Bukkit.getVersion().contains("1.8")){
					Skript.registerEffect(EffTitlev1_8_R3.class, TAS1, TAS2);
				} else if (Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.1")){
					Skript.registerEffect(EffTitlev1_9_R1.class, TAS1, TAS2);
				} else if (Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.2")){
					Skript.registerEffect(EffTitlev1_9_R2.class, TAS1, TAS2);
				} else if (Bukkit.getVersion().contains("1.10")){
					Skript.registerEffect(EffTitlev1_10_R1.class, TAS1, TAS2);
				}
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered Title effect! (25)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.TeleportPlayerWorldSpawn")) {
				Skript.registerEffect(EffPlayerTeleportWorldSpawn.class, "teleport %players% to [world] spawn (of|in) [world] %string%", "[skellett] teleport %players% to world %string% [spawn]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered player teleport to world spawn effect! (26)"));
				}
			}
			if (config.getBoolean("Syntax.EffectsSyntax.LeashBlock")) {
				Skript.registerEffect(EffLeashBlock.class, "(leash|lead) %livingentities% to %block%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered leash block effect! (27)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Expressions")) {
			if (config.getBoolean("Syntax.ExpressionsSyntax.SleepIgnored")) {
				Skript.registerExpression(ExprSleepIgnored.class,Boolean.class,ExpressionType.SIMPLE, "ignored sleep[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sleep ignored expression! (1)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SneakingState")) {
				Skript.registerExpression(ExprSneakState.class,Boolean.class,ExpressionType.SIMPLE, "(sneak|shift|crouch)[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sneak state expression! (2)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SprintingState")) {
				Skript.registerExpression(ExprSprintState.class,Boolean.class,ExpressionType.SIMPLE, "(sprint|run)[ing] [state] of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered sprint state expression! (3)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.CollidableState")) {
				Skript.registerExpression(ExprIsCollidable.class,Boolean.class,ExpressionType.SIMPLE, "collid(e|able) [state] [of] %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered collidable state expression! (4)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.RoundDecimal")) {
				if (!config.getBoolean("Addons.ExtraSk")) {
					Skript.registerExpression(ExprRoundDecimal.class,Number.class,ExpressionType.SIMPLE, "[Skellett] %number% round[ed] [to] %number% decimal (digit[s]|place[s]");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered round decimal expression! (5)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClientWeather")) {
				Skript.registerExpression(ExprGetClientWeather.class,String.class,ExpressionType.SIMPLE, "[get] [client] weather of %player%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered client weather expression! (6)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Nametags")) {
				Skript.registerExpression(ExprGetNametagPrefix.class,String.class,ExpressionType.SIMPLE, "[skellett] [get] prefix [of] [the] [name][ ]tag [with] [id] %string%");
				Skript.registerExpression(ExprGetNametagSuffix.class,String.class,ExpressionType.SIMPLE, "[skellett] [get] suffix [of] [the] [name][ ]tag [with] [id] %string%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nametag expressions! (9)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.OfflinePlayers")) {
				Skript.registerExpression(ExprOfflinePlayers.class,OfflinePlayer.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] offline[ ]player[s]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered offline player expression! (10)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Operators")) {
				Skript.registerExpression(ExprOperators.class,OfflinePlayer.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] Op[erator][(s|ed)] [players]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered operator expression! (11)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Files")) {
				Skript.registerExpression(ExprFilesGetString.class,String.class,ExpressionType.SIMPLE, "[skellett] [get] (string|value) [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesGetBoolean.class,Boolean.class,ExpressionType.SIMPLE, "[skellett] [get] boolean [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesGetConfigurationSection.class,ConfigurationSection.class,ExpressionType.SIMPLE, "[skellett] [get] [config][uration] section [of] [node] %string% from [file] %string%");
				Skript.registerExpression(ExprFilesIsSet.class,Boolean.class,ExpressionType.SIMPLE, "[skellett] node %string% (from|of) [file] %string% [is set]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered file expressions! (13)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Loops")) {
				Skript.registerExpression(ExprParticles.class,Effect.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] particle[[ ]types]");
				Skript.registerExpression(ExprInventoryViewers.class,HumanEntity.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] [player[']s] view(er[s]|ing) [of] %inventory%");
				Skript.registerExpression(ExprActivePotionEffects.class, String.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] [active] potion[s] [effects] (on|of) %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered some loop expressions! (15)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SizeOfInventory")) {
				Skript.registerExpression(ExprNumberOfSlots.class,Integer.class,ExpressionType.SIMPLE, "[skellett] (gui|menu|inventory|chest|window) (size|number|slots) (of|from) %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered number of slots expression! (16)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.AmountOfItem")) {
				Skript.registerExpression(ExprAmountOfItem.class,Number.class,ExpressionType.SIMPLE, "(skellett|better|fixed|working|get) (size|number|amount) of %itemstack%");
				Skript.registerExpression(ExprAmountOfDroppedItem.class,Number.class,ExpressionType.SIMPLE, "[skellett] [get] (size|number|amount) of dropped %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered amount of item expression! (17)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedInventory")) {
				Skript.registerExpression(ExprClickedInventory.class, Inventory.class, ExpressionType.SIMPLE, "click[ed] inventory");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked inventory expression! (19)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedAction")) {
				Skript.registerExpression(ExprInventoryAction.class, InventoryAction.class, ExpressionType.SIMPLE, "(click[ed]|inventory) action");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked action expression! (20)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.AmountOfVariables")) {
				Skript.registerExpression(ExprAmountOfVariables.class, Number.class, ExpressionType.SIMPLE, "(size|amount) of [all] variables");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered amount of variables expression! (21)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.BlockStates")) {
				Skript.registerExpression(ExprBlockGetPower.class, Integer.class, ExpressionType.SIMPLE, "[redstone] power [[being] receiv(ed|ing) [(from|at)]] %location%", "%location% [redstone] power [[being] received]");
				Skript.registerExpression(ExprBlockGetDrops.class, ItemStack.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] [possible] drop[(ped|s)] [items] (from|of) [block [at]] %location% [(with|using) %-itemstack%]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered block state expressions! (22)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.FinalDamage")) {
				Skript.registerExpression(ExprFinalDamage.class, Double.class, ExpressionType.SIMPLE, "[skellett] final damage");
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
				Skript.registerExpression(ExprFixFishingGetCaught.class, Entity.class, ExpressionType.SIMPLE, "[skellett] caught (fish|item|entity)");
				Skript.registerExpression(ExprFixFishingGetXP.class, Integer.class, ExpressionType.SIMPLE, "[skellett] [fish[ing]] (xp|experience) [earned]");
				Skript.registerExpression(ExprFixFishingGetHook.class, Fish.class, ExpressionType.SIMPLE, "[skellett] [fish[ing]] hook");
				Skript.registerExpression(ExprFixFishingState.class, PlayerFishEvent.State.class, ExpressionType.SIMPLE, "[skellett] [fish[ing]] state");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered fixed fishing expressions! (25)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.InstaBreak")) {
				Skript.registerExpression(ExprInstaBreak.class, Boolean.class, ExpressionType.SIMPLE, "[event] inst(ant|a) break [state]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered insta break expression! (26)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.RedstoneCurrent")) {
				Skript.registerExpression(ExprRedstoneOldCurrent.class, Integer.class, ExpressionType.SIMPLE, "old [event] [redstone] current");
				Skript.registerExpression(ExprRedstoneNewCurrent.class, Integer.class, ExpressionType.SIMPLE, "new [event] [redstone] current");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered redstone current expressions! (27)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SpreadSource")) {
				Skript.registerExpression(ExprSpreadSource.class, Block.class, ExpressionType.SIMPLE, "[spread] source [block]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered spread source expression! (28)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Spectate")) {
				Skript.registerExpression(ExprSpectate.class, Entity.class, ExpressionType.SIMPLE, "(spec[tat(e|or|ing)]|view[ing]) [(target|state)] of %player%", "%player%'s (spec[tat(e|or|ing)]|view[ing]) [(target|state)]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered spectate expressions! (29)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.Exhaustion")) {
				Skript.registerExpression(ExprExhaustion.class, Number.class, ExpressionType.SIMPLE, "exhaustion of %player%" ,"%player%'s exhaustion");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered exhaustion expression! (30)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.NextEmptySlot")) {
				Skript.registerExpression(ExprNextEmptySlot.class, Integer.class, ExpressionType.SIMPLE, "(next|first) empty slot of %inventory%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered empty slot expression! (31)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.MathExpressions")) {
				Skript.registerExpression(ExprAbsoluteValue.class, Number.class, ExpressionType.SIMPLE, "absolute [value] of %number%");
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
				Skript.registerExpression(ExprGroundState.class, Boolean.class, ExpressionType.SIMPLE, "[(is|are)] [on] [the] ground [state] [of] [entity] %entity%", "[entity] %entity% [(is|are)] [on] [the] ground [state]");
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
				EnumClassInfo.create(CreatureSpawnEvent.SpawnReason.class, "spawnreason").register();
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
				if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
					Skript.registerExpression(ExprInvulnerableState.class, Boolean.class, ExpressionType.SIMPLE, "invulnerable state of %entity%", "%entity%'s invulnerable state");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered invulnerable state expression! (41)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The invulnerable state expression is only for 1.9+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.GravityState")) {
				if (Bukkit.getVersion().contains("1.10")) {
					Skript.registerExpression(ExprGravityState.class, Boolean.class, ExpressionType.SIMPLE, "gravity [state] [of] [entit(y|ies)] %entitys%");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered gravity state expression! (42)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The gravity state expression is only for 1.10+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.SilentState")) {
				if (Bukkit.getVersion().contains("1.10")) {
					Skript.registerExpression(ExprSilentState.class, Boolean.class, ExpressionType.SIMPLE, "(silent|quiet) [state] [of] [entit(y|ies)] %entitys%");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered silent state expression! (43)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The silent state expression is only for 1.10+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.NearbyEntities")) {
				Skript.registerExpression(ExprNearbyEntities.class, Entity.class, ExpressionType.SIMPLE, "[skellett] [all] [nearby] entit(y|ies) (within|in) [a] radius [of] %number%[(,| and) %-number%(,| and) %-number%] (within|around|near) %location%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered nearby entities expression! (44)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.CropState")) {
				Skript.registerExpression(ExprCropState.class, String.class, ExpressionType.SIMPLE, "crop state");
				Skript.registerExpression(ExprCropStateOfBlock.class, String.class, ExpressionType.SIMPLE, "crop state of %block%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered crop state expression! (45)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.NoNBT")) {
				Skript.registerExpression(ExprNoItemNBT.class, ItemStack.class, ExpressionType.SIMPLE, "%itemstacks% with(out [any]| no) NBT");
				Skript.registerExpression(ExprRemoveItemNBT.class, ItemStack.class, ExpressionType.SIMPLE, "%itemstacks% with [all] removed NBT", "remove[ed] [all] NBT [from] %itemstacks%");
				Skript.registerExpression(ExprHideEnchants.class, ItemStack.class, ExpressionType.SIMPLE, "%itemstacks% with hid(den|ing) enchant[ment][s]", "[skellett] (shiny|hidden enchant[ment][s]|glow|glowing) [item] %itemstack%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered no NBT expressions! (46)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.CustomName")) {
				Skript.registerExpression(ExprCustomName.class, String.class, ExpressionType.SIMPLE, "[skellett] [get] custom name of %entity%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered custom name expression! (47)"));
				}
			
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.InventoryType")) {
				Skript.registerExpression(ExprInventoryType.class, String.class, ExpressionType.SIMPLE, "inventory type of %inventory%", "%inventory%'s [inventory] type");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered inventory type expression! (48)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.EnchantmentLevel")) {
				Skript.registerExpression(ExprEnchantmentNumber.class, Number.class, ExpressionType.COMBINED, "[skellett] [the] enchant[ment] level (from|of) %enchantment% (of|in) %itemstack%", "[skellett] %itemstack%'s enchant[ment] level (from|of) %enchantment%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered enchantment level expression! (49)"));
				}
			}
			if (!config.getBoolean("Addons.ExtraSk")) {
				if (config.getBoolean("Syntax.ExpressionsSyntax.TNTFuseTime")) {
					Skript.registerExpression(ExprFuseTime.class, Number.class, ExpressionType.SIMPLE, "[skellett] [the] (fuse time|time until blowup) of [the] [primed] [tnt] %entity%", "[skellett] [primed] [tnt] %entity%['s] (fuse time|time until blowup)");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered fuse time expression! (50)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.FallDistance")) {
				Skript.registerExpression(ExprFallDistance.class, Number.class, ExpressionType.SIMPLE, "[the] fall distance (from|of) %entity%", "%entity%'s fall distance");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered fall distance expression! (51)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.EntityID")) {
				Skript.registerExpression(ExprEntityID.class, Number.class, ExpressionType.SIMPLE, "[the] [entity] [number] id (of|from) %entities%", "%entities%'s [entity] [number] id");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered entity id expression! (52)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.EntityGlowing")) {
				Skript.registerExpression(ExprEntityGlowing.class, Boolean.class, ExpressionType.SIMPLE, "[skellett] [entity] glow[ing] [state] of %entities%");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered entity glowing expression! (53)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedSlot")) {
				if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
					Skript.registerExpression(ExprClickedSlot.class, Number.class, ExpressionType.SIMPLE, "[skellett] click[ed] slot");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked slot expression! (54)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedSlotType")) {
				EnumClassInfo.create(InventoryType.SlotType.class, "clickedslottype").register();
				Skript.registerExpression(ExprClickedSlotType.class, InventoryType.SlotType.class, ExpressionType.SIMPLE, "click[ed] slot type");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked slot type expression! (55)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedRawSlot")) {
				Skript.registerExpression(ExprClickedRawSlot.class, Number.class, ExpressionType.SIMPLE, "[skellett] click[ed] raw slot");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked slot type expression! (56)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedType")) {
				if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
					if (!Skript.getVersion().toString().contains("2.2-dev")) {
						EnumClassInfo.create(ClickType.class, "clickedtype").register();
					}
					Skript.registerExpression(ExprClickedType.class, ClickType.class, ExpressionType.SIMPLE, "[skellett] click[ed] type");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked type expression! (57)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedCursor")) {
				if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
					Skript.registerExpression(ExprClickedCursor.class, ItemType.class, ExpressionType.SIMPLE, "[skellett] [click[ed]] cursor");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked cusror expression! (58)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedTypeNumber")) {
				Skript.registerExpression(ExprClickedTypeNumber.class, Number.class, ExpressionType.SIMPLE, "click[ed] type num[ber]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked type number expression! (59)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.ClickedItem")) {
				if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
					Skript.registerExpression(ExprClickedItem.class, ItemType.class, ExpressionType.SIMPLE, "[skellett] click[ed] item");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked item expression! (60)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.PlayerInventoryCursor")) {
				Skript.registerExpression(ExprPlayerInventoryCursor.class, ItemType.class, ExpressionType.SIMPLE, "[current [inventory]] cursor of %player%", "%player%'s [current [inventory]] cursor");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked item expression! (60)"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.LlamaInventory")) {
				if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9") && !Bukkit.getVersion().contains("1.10")) {
					Classes.registerClass(new ClassInfo<LlamaInventory>(LlamaInventory.class, "llamainventory")
						.name("llamainventory")
						.description("A getter for llama inventories.")
						.parser(new Parser<LlamaInventory>() {
							@Override
							@Nullable
							public LlamaInventory parse(String obj, ParseContext context) {
								return null;
							}
							@Override
							public String toString(LlamaInventory li, int flags) {
								return li.toString();
							}
							@Override
							public String toVariableNameString(LlamaInventory b) {
								return b.toString();
							}
							public String getVariableNamePattern() {
								return ".+";
						}}));
					Skript.registerExpression(ExprLlamaInventory.class, LlamaInventory.class, ExpressionType.SIMPLE, "inventory of Llama %entity%", "Llama inventory of %entity%", "Llama %entity%'s inventory");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered clicked item expression! (61)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The Llama inventory expression is only for 1.11+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.LlamaInventoryDecor")) {
				if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9") && !Bukkit.getVersion().contains("1.10")) {
					Skript.registerExpression(ExprLlamaInventoryDecor.class, ItemStack.class, ExpressionType.SIMPLE, "[Llama] decor (of|in) [inventory] [of] [Llama] %llamainventory%", "[Llama] %llamainventory%'s [inventory] decor");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered llama inventory decor expression! (62)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The Llama inventory decor expression is only for 1.11+ versions!"));
				}
			}
			if (config.getBoolean("Syntax.ExpressionsSyntax.LlamaColour")) {
				if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9") && !Bukkit.getVersion().contains("1.10")) {
					Classes.registerClass(new ClassInfo<Llama.Color>(Llama.Color.class, "llamacolor")
						.name("llamacolor")
						.description("A getter for llama color.")
						.parser(new Parser<Llama.Color>() {
							@Override
							@Nullable
							public Llama.Color parse(String obj, ParseContext context) {
								return null;
							}
							@Override
							public String toString(Llama.Color lc, int flags) {
								return lc.toString();
							}
							@Override
							public String toVariableNameString(Llama.Color b) {
								return b.toString();
							}
							public String getVariableNamePattern() {
								return ".+";
						}}));
					Skript.registerExpression(ExprLlamaColorType.class, Llama.Color.class, ExpressionType.COMBINED, "Llama colo[u]r of %entity%", "Llama %entity%'s colo[u]r", "%entity%['s] Llama colo[u]r");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered llama colour expression! (63)"));
					}
				} else {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "The Llama colour expression is only for 1.11+ versions!"));
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
			if (config.getBoolean("Syntax.ConditionsSyntax.ScoreboardExists")) {
				Skript.registerCondition(CondScoreboardExists.class, "score[ ][board] %string% (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))");
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
			if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
				if (config.getBoolean("Syntax.ConditionsSyntax.FileExistance")) {
					Skript.registerCondition(CondFileExists.class, "[skellett] [file] exist(s|ance) [(at|of)] %string% [is %-boolean%]");
					if (config.getBoolean("debug")) {
						Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered file existance condition! (6)"));
					}
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.SquidHQ")) {
				Skript.registerCondition(CondSquidHQrunning.class, "%player% is running [client] SquidHQ", "%player%'s client is SquidHQ");
				Skript.registerCondition(CondSquidHQrunning2.class, "%player% (is(n't| not)) running [client] SquidHQ", "%player%'s client (is(n't| not)) SquidHQ");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered SquidHQ condition! (6)"));
				}
			}
			if (config.getBoolean("Syntax.ConditionsSyntax.EventCancelled")) {
				Skript.registerCondition(CondEventCancelled.class, "[(the|this)] event (1¦is|2¦is(n't| not)) cancelled");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered event cancelled condition! (7)"));
				}
			}
		}
		if (config.getBoolean("Syntax.Events")) {
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
				Skript.registerExpression(ExprNewMaterial.class, Material.class, ExpressionType.SIMPLE, "[skellett] new [changed] material");
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
			if (config.getBoolean("Syntax.EventsSyntax.CropGrow")) {
				Skript.registerEvent("[on] [skellett] (block|crop) grow[ing]:", SimpleEvent.class, BlockGrowEvent.class, "[on] [skellett] (block|crop) grow[ing]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on crop grow event! (13)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.BlockExperienceDrop")) {
				Skript.registerEvent("[on] block [break] (xp|exp|experience) [drop]:", SimpleEvent.class, BlockExpEvent.class, "[on] block [break] (xp|exp|experience) [drop]");
				Skript.registerExpression(ExprBlockXP.class, Number.class,ExpressionType.SIMPLE, "[dropped] block[[']s] (xp|experience)");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on block xp drop event! (14)"));
				}
			}
			if (config.getBoolean("Syntax.EventsSyntax.EntityUnleash")) {
				Skript.registerEvent("[on] [entity] (un(leash|lead)|(leash|lead) break):", SimpleEvent.class, EntityUnleashEvent.class, "[on] [entity] (un(leash|lead)|(leash|lead) break)");
				EventValues.registerEventValue(EntityUnleashEvent.class, Block.class, new Getter<Block, EntityUnleashEvent>() {
					@Override
					public Block get(EntityUnleashEvent e) {
						if (((LivingEntity) e.getEntity()).isDead()) {
							return null;
						}
						return ((LivingEntity) e.getEntity()).getLeashHolder().getLocation().getBlock();
					}
				}, 0);
				EnumClassInfo.create(EntityUnleashEvent.UnleashReason.class, "unleashreason").register();
				Skript.registerExpression(ExprUnleashReason.class, EntityUnleashEvent.UnleashReason.class, ExpressionType.SIMPLE, "(un(leash|lead)|(leash|lead) break) reason");
				Skript.registerExpression(ExprUnleashHitch.class, Entity.class, ExpressionType.SIMPLE, "event-hitch");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered on entity unleash event event! (15)"));
				}
			}
		}
		if (config.getBoolean("bungee")) {
			String bungeeprefix = config.getString("BungeeSyntaxPrefix");
			if (bungeeprefix == null) {
				bungeeprefix = "[Skellett[ ][cord]]";
			}
			Skript.registerCondition(CondOnlinePlayer.class, bungeeprefix + "%string% is online bungee[[ ]cord]");
			Skript.registerExpression(ExprBungeeCount.class,Integer.class,ExpressionType.SIMPLE, bungeeprefix + "[Skellett[ ][cord]] [get] online bungee[[ ]cord] players");
			Skript.registerExpression(ExprBungeeCountServer.class,Integer.class,ExpressionType.SIMPLE, bungeeprefix + "[Skellett[ ][cord]] [get] online bungee[[ ]cord] players on [server] %string%");
			Skript.registerExpression(ExprPlayerSlotsOfServer.class,Integer.class,ExpressionType.SIMPLE, bungeeprefix + "[Skellett[ ][cord]] [get] [(player|server)] slot[s] of server %string%");
			Skript.registerExpression(ExprServerOnline.class,Boolean.class,ExpressionType.SIMPLE, bungeeprefix + "[Skellett[ ][cord]] [get] [(online|offline)] status of [bungee[ ][cord]] server %string%");
			Skript.registerEffect(EffBungeeKickAllPlayers.class, bungeeprefix + "[Skellett[ ][cord]] kick [all] [bungee[ ][cord]] players [from bungee[ ][cord]] [(by reason of|because [of]|on account of|due to)] %string%");
			Skript.registerEffect(EffBungeeMessageAllPlayers.class, bungeeprefix + "[Skellett[ ][cord]] (message|send|msg) %string% to [all] bungee[[ ][cord]] players");
			Skript.registerEffect(EffBungeeChat.class, bungeeprefix + "[skellett[cord]] (force|make) %string% [to] (say|chat|(run|execute)[ command]) %string% [on bungee[ ][cord]]");
			Skript.registerEffect(EffBungeeSendServer.class, bungeeprefix + "[skellett[cord]] (send|connect) %string% to [[bungee[ ][cord]] server] %string%");
			Skript.registerEffect(EffExecuteBungeeCommand.class, bungeeprefix + "[Skellett[ ][cord]] (make|run|execute) bungee[[ ][cord]] [console] command %string%");
			Skript.registerEffect(EffStopProxy.class, bungeeprefix + "[Skellett[ ][cord]] (stop|kill|end) [bungee[[ ][cord]] proxy [[with] (msg|string|text)] %string%");
			Skript.registerExpression(ExprBungeeUUID.class,String.class,ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [get] [player] bungee[[ ][cord]] uuid [of] %string%");
			Skript.registerExpression(ExprBungeeServerMOTD.class,String.class,ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [get] MOTD of [server] %string%");
			Skript.registerExpression(ExprBungeePlayerName.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [bungee[ ][cord]] [player] name of [uuid] %string%");
			Skript.registerExpression(ExprBungeePlayerIP.class, InetSocketAddress.class, ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [bungee[ ][cord]] player ip [address] of [uuid] %string%");
			Skript.registerExpression(ExprBungeePlayerServer.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [bungee[ ][cord]] [current] server of [uuid] %string%", "[skellett[cord]] %string%'s [bungee[ ][cord]] [current] server");
			Skript.registerExpression(ExprBungeeServerIP.class, InetSocketAddress.class, ExpressionType.SIMPLE, bungeeprefix + "[skellett[cord]] [bungee[ ][cord]] server ip [address] of [server] %string%");
			Skript.registerEffect(EffBungeeKickPlayer.class, bungeeprefix + "[skellett[cord]] kick %string% from bungee[ ][cord] (by reason of|because [of]|on account of|due to) %string%", "[skellett[cord]] bungee[ ][cord] kick %string% (by reason of|because [of]|on account of|due to) %string%");
			Skript.registerEffect(EffBungeeMessagePlayer.class, bungeeprefix + "[skellett[cord]] (message|send|msg) %string% to bungee[ ][cord] [player] %string%");
			Skript.registerEffect(EffBungeeActionbar.class, bungeeprefix + "[skellett[cord]] (send|display|show) action[ ]bar [with [text]] %string% to bungee[ ][cord] [player] %string%");
			Skript.registerExpression(ExprBungeeGetPlayersServer.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players on server %string%", "%string%'s [skellett[cord]] [bungee[ ][cord]] players");
			Skript.registerExpression(ExprBungeeGetPlayers.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players");
			Skript.registerExpression(ExprBungeeGetServers.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] servers");
		}
		if (config.getBoolean("Main.Bossbars")) {
			if (Bukkit.getServer().getVersion().contains("MC: 1.5") || Bukkit.getServer().getVersion().contains("MC: 1.6")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cBossBars are not compatible with your version."));
			} else if (Bukkit.getServer().getVersion().contains("MC: 1.7") || Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cBossBars are only 1.9+ at the moment!"));
				//return;
			} else {
				if (!getServer().getPluginManager().isPluginEnabled("Umbaska")) {
					EnumClassInfo.create(BarColor.class, "barcolour").register();
					EnumClassInfo.create(BarStyle.class, "barstyle").register();
					EnumClassInfo.create(BarFlag.class, "barflag").register();
				}
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
		if (config.getBoolean("Main.Scoreboards")) {
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
			Skript.registerExpression(ExprGetObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective [(for|from|of)] %string%", "[skellett] (score[ ][board]|board) %string%'s objective");
			Skript.registerExpression(ExprObjectiveCriteria.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective criteria [(for|from|of)] %objective%", "[skellett] (score[ ][board]|board) %objective%'s objective criteria");
			Skript.registerEffect(EffRegisterObjective.class, "[skellett] register [new] (score[ ][board]|board) objective %string% with [criteria] %string%");
			Skript.registerCondition(CondObjectiveExists.class, "[skellett] (score[ ][board]|board) objective %string% (1¦(is set|[does] exist[s])|2¦(is(n't| not) set|does(n't| not) exist[s]))");
			Skript.registerExpression(ExprObjectives.class, Objective.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives");
			Skript.registerExpression(ExprObjectivesByCriteria.class, Objective.class, ExpressionType.SIMPLE, "[the] [skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string%");
			Skript.registerEffect(EffUnregisterObjective.class, "[skellett] unregister (score[ ][board]|board) objective %objective%");
			Skript.registerExpression(ExprObjectiveDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective display name [(for|from|of)] %objective%", "[the] [skellett] (score[ ][board]|board) %objective%['s] objective['s] display name", "[the] [skellett] (score[ ][board]|board) objective %objective%['s] display name");
			Skript.registerExpression(ExprObjectiveName.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective name [(for|from|of)] %objective%", "[the] [skellett] (score[ ][board]|board) %objective%['s] objective['s] name", "[the] [skellett] (score[ ][board]|board) objective %objective%['s] name");
			Skript.registerExpression(ExprObjectiveDisplaySlot.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective [display] slot [(for|from|of)] %objective%", "[the] [skellett] (score[ ][board]|board) %objective%['s] objective['s] [display] slot", "[the] [skellett] (score[ ][board]|board) objective %objective%['s] [display] slot");
			Skript.registerCondition(CondObjectiveIsModifiable.class, "[the] [skellett] (score[ ][board]|board) objective %objective% (1¦is modifiable|2¦is(n't| not) modifiable)");
			Skript.registerExpression(ExprObjectiveGetScore.class, Score.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) [objective] %objective% score [(for|from|of)] [entry] %string%", "[the] [skellett] (score[ ][board]|board) %objective%['s] [objective['s]] score [(for|from|of)] [entry] %string%", "[the] [skellett] (score[ ][board]|board) [objective] %objective%['s] score [(for|from|of)] [entry] %string%");
			Skript.registerEffect(EffScoreboardClearSlot.class, "[skellett] clear (score[ ][board]|board) [display] slot %string%");
			Skript.registerExpression(ExprEntries.class, String.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s])");
			Skript.registerExpression(ExprGetEntryTeam.class, Team.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) team [(from|of)] [entry] %string%", "[skellett] (score[ ][board]|board) [entry] %string%'s team");
			Skript.registerExpression(ExprGetEntryScores.class, Score.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] (score[ ][board]|board) scores [(from|of)] [entry] %string%", "[the] [skellett] [(the|all)] [of] [the] (score[ ][board]|board) [entry] %string%'s scores");
			Skript.registerEffect(EffResetEntryScores.class, "[skellett] reset [(the|all)] [of] [the] (score[ ][board]|board) scores of [entry] %string%", "[skellett] reset [(the|all)] [of] [the] (score[ ][board]|board) [entry] %string%'s scores", "[skellett] (score[ ][board]|board) reset [(the|all)] [of] [the] scores of [entry] %string%");
			Skript.registerEffect(EffRegisterTeam.class, "[skellett] register [a] [new] (score[ ][board]|board) team %string%");
			Skript.registerExpression(ExprTeams.class, Team.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] teams");
			Skript.registerExpression(ExprGetTeam.class, Team.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) %string% team", "[skellett] (score[ ][board]|board) [get] team [(for|from|of)] %string%");
			Skript.registerExpression(ExprScoreEntry.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) [get] entry [(for|from|of)] score %score%", "[skellett] (score[ ][board]|board) %score%'s score entry");
			Skript.registerExpression(ExprScoreObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) objective [(for|from|of)] score %score%", "[the] [skellett] (score[ ][board]|board) %score%'s scores objective");
			Skript.registerExpression(ExprScore.class, Number.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) (score|number|slot) [(for|from|of)] %score%", "[skellett] (score[ ][board]|board) %score%'s (score|number|slot)");
			Skript.registerEffect(EffTeamAddEntry.class, "[skellett] (score[ ][board]|board) add [the] entry [(from|of)] %string% to [the] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyFire.class, Boolean.class, ExpressionType.SIMPLE, "[the] [skellett] [(score[ ][board]|board)] friendly [fire] state [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyInvisibles.class, Boolean.class, ExpressionType.SIMPLE, "[the] [skellett] [(score[ ][board]|board)] [friendly] invisible[s] [state] [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] [(score[ ][board]|board)] team display name [(for|from|of)] %team%");
			Skript.registerExpression(ExprTeamEntries.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] [(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s]) (in|within) [the] [team] %team%");
			Skript.registerExpression(ExprTeamName.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (score[ ][board]|board) [team] name [(for|of)] [team] %team%");
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerExpression(ExprTeamOptions.class, Team.OptionStatus.class, ExpressionType.SIMPLE, "[the] [skellett] [(score[ ][board]|board)] [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%");
			}
			Skript.registerExpression(ExprTeamPrefix.class, String.class, ExpressionType.SIMPLE, "[skellett] [(score[ ][board]|board)] [team] prefix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSuffix.class, String.class, ExpressionType.SIMPLE, "[skellett] [(score[ ][board]|board)] [team] suffix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSize.class, Number.class, ExpressionType.SIMPLE, "[skellett] [(score[ ][board]|board)] [team] size [(for|of)] [team] %team%");
			Skript.registerCondition(CondTeamHasEntry.class, "[skellett] (score[ ][board]|board) (1¦(ha(s|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [the] [entry] %string% [(in|within)] the [team] %team%");
			Skript.registerEffect(EffTeamRemoveEntry.class, "[skellett] (score[ ][board]|board) remove [the] entry [(from|of)] %string% from [the] [team] %team%");
			Skript.registerEffect(EffUnregisterTeam.class, "[skellett] unregister [the] (score[ ][board]|board) team %team%");
		}
		if (config.getBoolean("Main.Books")) {
			//HERE
			Skript.registerExpression(ExprBookTitle.class, String.class,ExpressionType.SIMPLE, "[the] [book['s]] title of %itemstack%", "%itemstack%'s [book] title");
			Skript.registerExpression(ExprBookAuthor.class, String.class,ExpressionType.SIMPLE, "[the] [book['s]] author of %itemstack%", "%itemstack%'s [book] author");
			if (config.getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered book syntax"));
			}
		}
		if (config.getBoolean("PluginHooks.OITB")) {
			if (Bukkit.getPluginManager().getPlugin("OITB").isEnabled()) {
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
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered OITB syntax"));
				}
			}
		}
		if (config.getBoolean("PluginHooks.ProtocolSupport")) {
			if (Bukkit.getPluginManager().getPlugin("ProtocolSupport").isEnabled()) {
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
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered protocolsupport syntax"));
				}
			}
		}
		if (config.getBoolean("PluginHooks.LibsDisguises")) {
			if (Bukkit.getPluginManager().getPlugin("LibsDisguises").isEnabled()) {
				Skript.registerExpression(ExprGetDisguise.class, DisguiseType.class, ExpressionType.SIMPLE, "[skellett] [[Libs]Disguises] Disguise of %entities%[[']s]", "[skellett] [[Libs]Disguises] %entity%'s disguise");
				Skript.registerEffect(EffDisguiseToAll.class, "[skellett] [[Libs]Disguises] [set] Disguise [of] %entities% (as|to) %string% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]");
				Skript.registerEffect(EffUnDisguiseToAll.class, "[skellett] [[Libs]Disguises] Un[( |-)]Disguise %entities%");
				Skript.registerEffect(EffDisguiseNextEntity.class, "[skellett] [[Libs]Disguises] [set] Disguise [of] next [spawned] (as|to) %string% [with block [id] %-integer%] [(and|with) data [id] %-integer%] [with [user[ ]]name %-string%] [(and|with) baby [state] %-boolean%]");
				Skript.registerCondition(CondIsDisguised.class, "[skellett] [[Libs]Disguises] [(entity|player)] %entities% [(is|are)] disguised");
				Skript.registerExpression(ExprSelfViewDisguise.class, Boolean.class, ExpressionType.SIMPLE, "[skellett] [[Libs]Disguises] self view[ing] disguise [state] of %entities%[[']s]", "[skellett] [[Libs]Disguises] %entities%'s self view[ing] disguise [state]");
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered libsdisguise syntax"));
				}
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
				if (config.getBoolean("debug")) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "Registered feudal syntax"));
				}
			}
		}
		if (config.getBoolean("PluginHooks.TabListAPI")) {
			Skript.registerEffect(EffPlayerRemoveTabItem.class, "(delete|remove) tab[list] item [[with] id] %string% from %players%");
			Skript.registerEffect(EffDeleteTabItem.class, "(remove|delete) tab[list] item [[with] id] %string%");		
			Skript.registerEffect(EffNewTabItem.class, "[(make|create)] [a] [new] tab[list] [item] [[with] id] %string% with [display] name %string%[(,| and| with)] [(skin|head|skull) [icon] %-string%][(,| and| with)] [(ping|latency) %-number%][(,| and| with)] [spectator [mode] %-boolean%]");
			Skript.registerEffect(EffPlayerClearList.class, "clear [the] tab[list] [items] (of|for) %players%");
			Skript.registerEffect(EffPlayerRemoveList.class, "(remove|reset|delete) [the] tab[list] [items] (of|for) %players%");
			Skript.registerEffect(EffPlayerAddTabItem.class, "add tab[list] item [[with] id] %string% to %players%");
			Skript.registerEffect(EffPlayerFillDefaults.class, "fill tab[list] of %players% with default[s] [item[s]]");
			Skript.registerEffect(EffPlayerUpdateTab.class, "update [the] tab[list] of %players%");
			Skript.registerExpression(ExprAllTabItems.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] tab[list] item[s] id[[']s]");
		}
		if (config.getBoolean("beta")) {
			Skript.registerEffect(EffSpawnParticle.class, "[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%");
			Skript.registerEffect(EffNewHologram.class, "(create|spawn|summon|place) [a] holo[gram] [(named|with)] [text] %string% at %location% [[with] id] %string%");
			Skript.registerEffect(EffDeleteHologram.class, "(delete|remove|despawn|clear|kill) holo[gram] [with] id %string%");
		}
		if (mysqlData.getBoolean("MySQL")) {
			Skript.registerEffect(EffMySQLConnect.class, "[skellett] connect [to] mysql");
			Skript.registerEffect(EffMySQLDisconnect.class, "[skellett] disconnect [to] mysql");
			Skript.registerEffect(EffMySQLUpdate.class, "[skellett] mysql update %string%");
			Skript.registerExpression(ExprMySQLHost.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] host");
			Skript.registerExpression(ExprMySQLUsername.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] username");
			Skript.registerExpression(ExprMySQLPassword.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] password");
			Skript.registerExpression(ExprMySQLDatabase.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] database");
			Skript.registerExpression(ExprMySQLQuery.class, ResultSet.class, ExpressionType.SIMPLE, "[skellett] mysql result of query %string%");
		}
		if (ceData.getBoolean("CustomEvents")) {
			for(int i = 1; i <= ceData.getInt("CustomEventSetup.NumberOfEvents"); i++) {
				Bukkit.getConsoleSender().sendMessage(cc("&aRegistered custom event: &5" + ceData.getString("CustomEventSetup." + i + ".Syntax")));
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Event> classType = ((Class<? extends Event>) Class.forName(ceData.getString("CustomEventSetup." + i + ".Event")));
					Skript.registerEvent(ceData.getString("CustomEventSetup." + i + ".Syntax"), SimpleEvent.class, classType, ceData.getString("CustomEventSetup." + i + ".Syntax"));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (!Bukkit.getPluginManager().getPlugin("SkQuery").getDescription().getAuthors().contains("LimeGlass")) {
			if (config.getBoolean("Main.Yaml")) {
				Skript.registerExpression(ExprYaml.class, Object.class, ExpressionType.SIMPLE, "[skellett] (file|y[a]ml) [file] (1¦value|2¦node[s]|3¦node[s with] keys|4¦list) %string% (in|at|from) [file] %string%");
			}
		}
		Skript.registerEffect(EffFirework.class, "[skellett] (launch|deploy) [%-strings%] firework[s] at %locations% [with] (duration|timed|time) %number% [colo[u]r[ed] (%-strings%|%-color%)]");
		Skript.registerEvent("[on] entity sho[o]t:", SimpleEvent.class, EntityShootBowEvent.class, "[on] entity sho[o]t");
		if (spData.getBoolean("SkellettProxy")) {
			String bungeeprefix = config.getString("BungeeSyntaxPrefix");
			if (bungeeprefix == null) {
				bungeeprefix = "[Skellett[ ][cord]]";
			}
			Skript.registerEffect(EffSkellettCordTest.class, "skellett[[ ]cord] ping [test]");
			Skript.registerExpression(ExprBungeecordVersion.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "bungee[[ ]cord] version");
			Skript.registerExpression(ExprPlayerLimit.class, Number.class, ExpressionType.SIMPLE, bungeeprefix + "bungee[[ ]cord] player limit");
			Skript.registerExpression(ExprPlayerDisplayName.class, String.class, ExpressionType.SIMPLE, bungeeprefix + "%player%'s bungee[[ ]cord] display name", bungeeprefix + "bungee[[ ]cord] display name of %player%");
			Skript.registerExpression(ExprPlayerPing.class, Number.class, ExpressionType.SIMPLE, bungeeprefix + "%player%'s bungee[[ ]cord] ping", bungeeprefix + "bungee[[ ]cord] ping of %player%");
			Skript.registerCondition(CondUsingForge.class, bungeeprefix + "%player% (1¦(has|is)|2¦(is(n't| not))) [using] [the] forge [client]");
			Skript.registerExpression(ExprBungeeYAML.class, Object.class, ExpressionType.SIMPLE, bungeeprefix + "bungee[[ ]cord] y[a]ml (1¦value|2¦nodes|3¦nodes with keys|4¦list) [of node] %string% (from|in) [file] %string%");
		}
		Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aHas been enabled!"));
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
	public static int getTicks(Timespan time) {
		if (Skript.getVersion().toString().contains("2.2")) {
			Number tick = time.getTicks_i();
			return tick.intValue();
		} else {
			return time.getTicks();
		}
	}
}
/*
New Stuff:

#added particle testing (The effect is very experimental. Not really released or designed to work properly yet)
[skellett] (create|place|spawn|play|make|summon) particle [effect] %string% at %location% [[with] [offset] %number%[(,| and)] %number%[(,| and)] %number%] at speed %number% and amount %integer%

[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players on server %string%
%string%'s [skellett[cord]] [bungee[ ][cord]] players

[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] players

[(the|all)] [of] [the] [skellett[cord]] [bungee[ ][cord]] servers

[skellett] connect [to] mysql

[skellett] disconnect [to] mysql

[skellett] mysql update %string%

[skellett] mysql['s] host

[skellett] mysql['s] username

[skellett] mysql['s] password

[skellett] mysql['s] database

[skellett] mysql result of query %string%

*/