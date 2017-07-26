package com.gmail.thelimeglass;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.gmail.thelimeglass.Events.EvtPacket;
import com.gmail.thelimeglass.Expressions.ExprBlockXP;
import com.gmail.thelimeglass.Expressions.ExprBreedingBreeder;
import com.gmail.thelimeglass.Expressions.ExprBreedingEntity;
import com.gmail.thelimeglass.Expressions.ExprBreedingFather;
import com.gmail.thelimeglass.Expressions.ExprBreedingItem;
import com.gmail.thelimeglass.Expressions.ExprBreedingMother;
import com.gmail.thelimeglass.Expressions.ExprBreedingXP;
import com.gmail.thelimeglass.Expressions.ExprUnleashHitch;
import com.gmail.thelimeglass.Expressions.ExprUnleashReason;
import com.gmail.thelimeglass.Expressions.ExprWorldChangeFrom;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordDisconnect;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordServerSwitch;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.ExprNewMaterial;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.citizensnpcs.api.event.EntityTargetNPCEvent;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCCollisionEvent;
import net.citizensnpcs.api.event.NPCCombustByBlockEvent;
import net.citizensnpcs.api.event.NPCCombustByEntityEvent;
import net.citizensnpcs.api.event.NPCCreateEvent;
import net.citizensnpcs.api.event.NPCDamageByBlockEvent;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCEnderTeleportEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCPushEvent;
import net.citizensnpcs.api.event.NPCRemoveEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSelectEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.event.NPCTeleportEvent;
import net.citizensnpcs.api.event.PlayerCreateNPCEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Kingdom;

public class Register {
	
	@SuppressWarnings("rawtypes")
	private static List<Class> classes = new ArrayList<>();
	
	public static void events(){
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.FireworkExplode")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				registerEvent(FireworkExplodeEvent.class, "firework explo(de|sion)");
				EventValues.registerEventValue(FireworkExplodeEvent.class, Entity.class, new Getter<Entity, FireworkExplodeEvent>() {
					@Override
					public Entity get(FireworkExplodeEvent e) {
						return e.getEntity();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemDespawn")) {
			registerEvent(ItemDespawnEvent.class, "item[ ][stack] (despawn|remove|delete)");
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
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.PrepareEnchant")) {
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
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.Maps")) {
			Skript.registerEvent("[on] [player] map [(initialize|open)]:", SimpleEvent.class, MapInitializeEvent.class, "[on] [player] map [(initialize|open)]");
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.Brewing")) {
			Skript.registerEvent("[on] [skellett] brew[ing]:", SimpleEvent.class, BrewEvent.class, "[on] [skellett] brew[ing]");
			EventValues.registerEventValue(BrewEvent.class, Number.class, new Getter<Number, BrewEvent>() {
				@Override
				public Number get(BrewEvent e) {
					return e.getFuelLevel();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ItemMerge")) {
			if (!Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8")) {
				registerEvent(ItemMergeEvent.class, "item[ ][stack] (merge|combine[d])");
				EventValues.registerEventValue(ItemMergeEvent.class, Entity.class, new Getter<Entity, ItemMergeEvent>() {
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
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The item merge event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.MultiBlockPlace")) {
			registerEvent(BlockMultiPlaceEvent.class, "(multi[ple]|double)[ ][block][ ]place");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.ArrowPickup")) {
			if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				registerEvent(PlayerPickupArrowEvent.class, "arrow pickup");
				EventValues.registerEventValue(PlayerPickupArrowEvent.class, Arrow.class, new Getter<Arrow, PlayerPickupArrowEvent>() {
					@Override
					public Arrow get(PlayerPickupArrowEvent e) {
						return e.getArrow();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The arrow pickup event is only for 1.8+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.OffhandSwitch")) {
			if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12")) {
				registerEvent(PlayerSwapHandItemsEvent.class, "off[ ]hand (switch|move)");
				EventValues.registerEventValue(PlayerSwapHandItemsEvent.class, ItemStack.class, new Getter<ItemStack, PlayerSwapHandItemsEvent>() {
					@Override
					public ItemStack get(PlayerSwapHandItemsEvent e) {
						return e.getMainHandItem();
					}
				}, 0);
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The offhand switch event is only for 1.9+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CreativeInventoryClick")) {
			registerEvent(InventoryCreativeEvent.class, "creative inventory click");
			EventValues.registerEventValue(InventoryCreativeEvent.class, ItemStack.class, new Getter<ItemStack, InventoryCreativeEvent>() {
				@Override
				public ItemStack get(InventoryCreativeEvent e) {
					return e.getCursor();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			registerEvent(EntityTeleportEvent.class, "entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityTeleport")) {
			registerEvent(EntityTeleportEvent.class, "entity teleport");
			EventValues.registerEventValue(EntityTeleportEvent.class, Location.class, new Getter<Location, EntityTeleportEvent>() {
				@Override
				public Location get(EntityTeleportEvent e) {
					return e.getTo();
				}
			}, 1);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.VehicleMove")) {
			registerEvent(VehicleMoveEvent.class, "(vehicle|minecart|boat) move");
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
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityBlockChange")) {
			registerEvent(EntityChangeBlockEvent.class, "entity block (change|modify)");
			Skript.registerExpression(ExprNewMaterial.class, Material.class, ExpressionType.SIMPLE, "[skellett] new [changed] material");
			EventValues.registerEventValue(EntityChangeBlockEvent.class, Block.class, new Getter<Block, EntityChangeBlockEvent>() {
				@Override
				public Block get(EntityChangeBlockEvent e) {
					return e.getBlock();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.HotbarSwitch")) {
			registerEvent(PlayerItemHeldEvent.class, "(hotbar|held [(item|slot)]|inventory slot) (switch|change)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Breeding")) {
			if (!Bukkit.getVersion().contains("1.6") && !Bukkit.getVersion().contains("1.7") && !Bukkit.getVersion().contains("1.8") && !Bukkit.getVersion().contains("1.9") && !Bukkit.getVersion().contains("1.10")) {
				registerEvent(EntityBreedEvent.class, "bre[e]d[ing]");
				Skript.registerExpression(ExprBreedingItem.class, ItemStack.class, ExpressionType.SIMPLE, "bre[e]d[ing] (item|material) [used]");
				Skript.registerExpression(ExprBreedingBreeder.class, LivingEntity.class, ExpressionType.SIMPLE, "breeder");
				Skript.registerExpression(ExprBreedingEntity.class, LivingEntity.class, ExpressionType.SIMPLE, "[final] bre[e]d[ed] entity");
				Skript.registerExpression(ExprBreedingXP.class, Number.class, ExpressionType.SIMPLE, "bre[e]d[ing] (xp|experience)");
				Skript.registerExpression(ExprBreedingFather.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] father");
				Skript.registerExpression(ExprBreedingMother.class, LivingEntity.class, ExpressionType.SIMPLE, "bre[e]d[ing] mother");
			} else {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "The breeding event is only for 1.10+ versions!"));
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.WorldChange")) {
			registerEvent(PlayerChangedWorldEvent.class, "[player] world change");
			Skript.registerExpression(ExprWorldChangeFrom.class, World.class, ExpressionType.SIMPLE, "(previous|past) [changed] world");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.CropGrow")) {
			registerEvent(BlockGrowEvent.class, "(block|crop) grow[ing]");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BlockExperienceDrop")) {
			registerEvent(BlockExpEvent.class, "block [break] (xp|exp|experience) [drop]");
			Skript.registerExpression(ExprBlockXP.class, Number.class,ExpressionType.SIMPLE, "[dropped] block[[']s] (xp|experience)");
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.EntityUnleash")) {
			registerEvent(EntityUnleashEvent.class, "[entity] (un(leash|lead)|(leash|lead) break)");
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
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Elytra")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9")) {
				registerEvent(EntityToggleGlideEvent.class, "[entity] (elytra|glide) [toggle]");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.BrewingFuel")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10") && !Bukkit.getServer().getVersion().contains("MC: 1.11)") && !Bukkit.getServer().getVersion().contains("MC: 1.11.1")) {
				registerEvent(BrewingStandFuelEvent.class, "brew[ing] [stand] fuel [increase]");
				EventValues.registerEventValue(BrewingStandFuelEvent.class, Number.class, new Getter<Number, BrewingStandFuelEvent>() {
					@Override
					public Number get(BrewingStandFuelEvent e) {
						return e.getFuelPower();
					}
				}, 0);
				EventValues.registerEventValue(BrewingStandFuelEvent.class, ItemStack.class, new Getter<ItemStack, BrewingStandFuelEvent>() {
					@Override
					public ItemStack get(BrewingStandFuelEvent e) {
						return e.getFuel();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.AnvilPrepare")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				registerEvent(PrepareAnvilEvent.class, "([item] anvil prepare|prepare [item] anvil)");
				Classes.registerClass(new ClassInfo<AnvilInventory>(AnvilInventory.class, "anvilinventory")
					.name("anvilinventory")
					.description("A getter for AnvilInventory")
					.parser(new Parser<AnvilInventory>() {
						@Override
						@Nullable
						public AnvilInventory parse(String obj, ParseContext context) {
							return null;
						}
						@Override
						public String toString(AnvilInventory e, int flags) {
							return e.toString();
						}
						@Override
						public String toVariableNameString(AnvilInventory b) {
							return b.toString();
						}
						public String getVariableNamePattern() {
							return ".+";
					}}));
				EventValues.registerEventValue(PrepareAnvilEvent.class, ItemStack.class, new Getter<ItemStack, PrepareAnvilEvent>() {
					@Override
					public ItemStack get(PrepareAnvilEvent e) {
						return e.getResult();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, Number.class, new Getter<Number, PrepareAnvilEvent>() {
					@Override
					public Number get(PrepareAnvilEvent e) {
						return e.getInventory().getRepairCost();
					}
				}, 0);
				EventValues.registerEventValue(PrepareAnvilEvent.class, String.class, new Getter<String, PrepareAnvilEvent>() {
					@Override
					public String get(PrepareAnvilEvent e) {
						return e.getInventory().getRenameText();
					}
				}, 0);
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Resurrect")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8") && !Bukkit.getServer().getVersion().contains("MC: 1.9") && !Bukkit.getServer().getVersion().contains("MC: 1.10")) {
				registerEvent(EntityResurrectEvent.class, "[entity] (resurrect|revive)");
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.SlimeSplit")) {
			registerEvent(SlimeSplitEvent.class, "slime split");
		}
		if (Skellett.instance.getConfig().getBoolean("PluginHooks.Npc")) {
			registerEvent(NPCDamageByEntityEvent.class, "(npc|citizen) damage (by|from) [a[n]] entity");
			EventValues.registerEventValue(NPCDamageByEntityEvent.class, Entity.class, new Getter<Entity, NPCDamageByEntityEvent>() {
				@Override
				public Entity get(NPCDamageByEntityEvent e) {
					return e.getDamager();
				}
			}, 0);
			EventValues.registerEventValue(NPCDamageByEntityEvent.class, String.class, new Getter<String, NPCDamageByEntityEvent>() {
				@Override
				public String get(NPCDamageByEntityEvent e) {
					return e.getCause().toString();
				}
			}, 0);
			registerEvent(NPCDamageByBlockEvent.class, "(npc|citizen) damage (by|from) [a] block");
			EventValues.registerEventValue(NPCDamageByBlockEvent.class, Block.class, new Getter<Block, NPCDamageByBlockEvent>() {
				@Override
				public Block get(NPCDamageByBlockEvent e) {
					return e.getDamager();
				}
			}, 0);
			registerEvent(EntityTargetNPCEvent.class, "entity target (npc|citizen)");
			EventValues.registerEventValue(EntityTargetNPCEvent.class, Entity.class, new Getter<Entity, EntityTargetNPCEvent>() {
				@Override
				public Entity get(EntityTargetNPCEvent e) {
					return e.getEntity();
				}
			}, 0);
			registerEvent(NPCClickEvent.class, "(npc|citizen) click");
			EventValues.registerEventValue(NPCClickEvent.class, Player.class, new Getter<Player, NPCClickEvent>() {
				@Override
				public Player get(NPCClickEvent e) {
					return e.getClicker();
				}
			}, 0);
			registerEvent(NPCLeftClickEvent.class, "(npc|citizen) left[(-| )]click");
			EventValues.registerEventValue(NPCLeftClickEvent.class, Player.class, new Getter<Player, NPCLeftClickEvent>() {
				@Override
				public Player get(NPCLeftClickEvent e) {
					return e.getClicker();
				}
			}, 0);
			registerEvent(NPCRightClickEvent.class, "(npc|citizen) right[(-| )]click");
			EventValues.registerEventValue(NPCRightClickEvent.class, Player.class, new Getter<Player, NPCRightClickEvent>() {
				@Override
				public Player get(NPCRightClickEvent e) {
					return e.getClicker();
				}
			}, 0);
			registerEvent(NPCCollisionEvent.class, "(npc|citizen) [entity] colli(sion|de)");
			EventValues.registerEventValue(NPCCollisionEvent.class, Entity.class, new Getter<Entity, NPCCollisionEvent>() {
				@Override
				public Entity get(NPCCollisionEvent e) {
					return e.getCollidedWith();
				}
			}, 0);
			registerEvent(NPCCombustByBlockEvent.class, "(npc|citizen) (combust[ion]|ignition) (by|from) [a] block");
			EventValues.registerEventValue(NPCCombustByBlockEvent.class, Block.class, new Getter<Block, NPCCombustByBlockEvent>() {
				@Override
				public Block get(NPCCombustByBlockEvent e) {
					return e.getCombuster();
				}
			}, 0);
			registerEvent(NPCCombustByBlockEvent.class, "(npc|citizen) (combust[ion]|ignition) (by|from) [a[n]] entity");
			EventValues.registerEventValue(NPCCombustByEntityEvent.class, Entity.class, new Getter<Entity, NPCCombustByEntityEvent>() {
				@Override
				public Entity get(NPCCombustByEntityEvent e) {
					return e.getCombuster();
				}
			}, 0);
			registerEvent(NPCCreateEvent.class, "(npc|citizen) create");
			registerEvent(NPCDamageEntityEvent.class, "(npc|citizen) [entity] damage");
			EventValues.registerEventValue(NPCDamageEntityEvent.class, Entity.class, new Getter<Entity, NPCDamageEntityEvent>() {
				@Override
				public Entity get(NPCDamageEntityEvent e) {
					return e.getDamaged();
				}
			}, 0);
			//TODO Death: Dropped items and XP
			registerEvent(NPCDeathEvent.class, "(npc|citizen) death");
			registerEvent(NPCDespawnEvent.class, "(npc|citizen) despawn");
			Boolean enderTp = true;
			try {
				Class.forName("net/citizensnpcs/api/event/NPCEnderTeleportEvent");
			} catch( ClassNotFoundException e ) {
				enderTp = false;
			}
			if (enderTp) registerEvent(NPCEnderTeleportEvent.class, "(npc|citizen) ender[[ ]pearl] [teleport]");
			//TODO: Return and settable vector
			registerEvent(NPCPushEvent.class, "(npc|citizen) (push|(vector|velocity) change)");
			registerEvent(NPCRemoveEvent.class, "(npc|citizen) remove");
			//TODO: Trait events
			registerEvent(NPCSelectEvent.class, "(npc|citizen) sel[ect[ed]]");
			EventValues.registerEventValue(NPCSelectEvent.class, Player.class, new Getter<Player, NPCSelectEvent>() {
				@Override
				public Player get(NPCSelectEvent e) {
					if (Bukkit.getPlayer(e.getSelector().getName()) != null) {
						return Bukkit.getPlayer(e.getSelector().getName());
					}
					return null;
				}
			}, 0);
			registerEvent(NPCSpawnEvent.class, "(npc|citizen) spawn");
			EventValues.registerEventValue(NPCSpawnEvent.class, Location.class, new Getter<Location, NPCSpawnEvent>() {
				@Override
				public Location get(NPCSpawnEvent e) {
					return e.getLocation();
				}
			}, 0);
			Boolean npcTp = true;
			try {
				Class.forName("net/citizensnpcs/api/event/NPCTeleportEvent");
			} catch( ClassNotFoundException e ) {
				npcTp = false;
			}
			if (npcTp) {
				//TODO: Get from and to location
				registerEvent(NPCTeleportEvent.class, "(npc|citizen) teleport");
				EventValues.registerEventValue(NPCTeleportEvent.class, Location.class, new Getter<Location, NPCTeleportEvent>() {
					@Override
					public Location get(NPCTeleportEvent e) {
						return e.getTo();
					}
				}, 0);
			}
			registerEvent(PlayerCreateNPCEvent.class, "player create (npc|citizen)");
			EventValues.registerEventValue(PlayerCreateNPCEvent.class, Player.class, new Getter<Player, PlayerCreateNPCEvent>() {
				@Override
				public Player get(PlayerCreateNPCEvent e) {
					return e.getCreator();
				}
			}, 0);
		}
		if (Skellett.spData.getBoolean("Events", false)) {
			registerEvent(EvtBungeecordDisconnect.class, "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) player (disconnect|leave)");
			EventValues.registerEventValue(EvtBungeecordDisconnect.class, String.class, new Getter<String, EvtBungeecordDisconnect>() {
				@Override
				public String get(EvtBungeecordDisconnect e) {
					return e.getPlayerName();
				}
			}, 0);
			EventValues.registerEventValue(EvtBungeecordDisconnect.class, UUID.class, new Getter<UUID, EvtBungeecordDisconnect>() {
				@Override
				public UUID get(EvtBungeecordDisconnect e) {
					return e.getUUID();
				}
			}, 0);
			EventValues.registerEventValue(EvtBungeecordDisconnect.class, OfflinePlayer.class, new Getter<OfflinePlayer, EvtBungeecordDisconnect>() {
				@Override
				public OfflinePlayer get(EvtBungeecordDisconnect e) {
					return e.getOfflinePlayer();
				}
			}, 0);
			registerEvent(EvtBungeecordServerSwitch.class, "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] (switch server[s]|server[s] switch)");
			EventValues.registerEventValue(EvtBungeecordServerSwitch.class, String.class, new Getter<String, EvtBungeecordServerSwitch>() {
				@Override
				public String get(EvtBungeecordServerSwitch e) {
					return e.getPlayerName();
				}
			}, 0);
			EventValues.registerEventValue(EvtBungeecordServerSwitch.class, UUID.class, new Getter<UUID, EvtBungeecordServerSwitch>() {
				@Override
				public UUID get(EvtBungeecordServerSwitch e) {
					return e.getUUID();
				}
			}, 0);
			EventValues.registerEventValue(EvtBungeecordServerSwitch.class, OfflinePlayer.class, new Getter<OfflinePlayer, EvtBungeecordServerSwitch>() {
				@Override
				public OfflinePlayer get(EvtBungeecordServerSwitch e) {
					return e.getOfflinePlayer();
				}
			}, 0);
			/*registerEvent(EvtBungeecordPlayerChat.class, "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) [player] chat");
			EventValues.registerEventValue(EvtBungeecordPlayerChat.class, Player.class, new Getter<Player, EvtBungeecordPlayerChat>() {
				@Override
				public Player get(EvtBungeecordPlayerChat e) {
					return e.getPlayer();
				}
			}, 0);
			EventValues.registerEventValue(EvtBungeecordPlayerChat.class, String.class, new Getter<String, EvtBungeecordPlayerChat>() {
				@Override
				public String get(EvtBungeecordPlayerChat e) {
					return e.getMessage();
				}
			}, 0);
			registerEvent(EvtBungeecordDisconnect.class, "(skellett[ ][(cord|proxy)]|bungee[ ][cord]) player (connect|join)");
			EventValues.registerEventValue(EvtBungeecordConnect.class, OfflinePlayer.class, new Getter<OfflinePlayer, EvtBungeecordConnect>() {
				@Override
				public OfflinePlayer get(EvtBungeecordConnect e) {
					return e.getPlayer();
				}
			}, 0);*/
			//TODO: Finish this above
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.SpawnerSpawn")) {
			registerEvent(SpawnerSpawnEvent.class, "spawner spawn");
			EventValues.registerEventValue(SpawnerSpawnEvent.class, Block.class, new Getter<Block, SpawnerSpawnEvent>() {
				@Override
				public Block get(SpawnerSpawnEvent e) {
					return e.getSpawner().getBlock();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.Packets")) {
			registerEvent(EvtPacket.class, "packet [(send|sent|recieve)]");
			EventValues.registerEventValue(EvtPacket.class, String.class, new Getter<String, EvtPacket>() {
				@Override
				public String get(EvtPacket e) {
					return e.getName();
				}
			}, 0);
			EventValues.registerEventValue(EvtPacket.class, Player.class, new Getter<Player, EvtPacket>() {
				@Override
				public Player get(EvtPacket e) {
					return e.getPlayer();
				}
			}, 0);
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Events.Hanging")) {
			registerEvent(HangingBreakEvent.class, "(unhang|[entity] unhung)");
			EventValues.registerEventValue(HangingBreakEvent.class, Entity.class, new Getter<Entity, HangingBreakEvent>() {
				@Override
				public Entity get(HangingBreakEvent e) {
					return e.getEntity();
				}
			}, 0);
			registerEvent(HangingPlaceEvent.class, "(hang|[entity] hung)");
			EventValues.registerEventValue(HangingPlaceEvent.class, Entity.class, new Getter<Entity, HangingPlaceEvent>() {
				@Override
				public Entity get(HangingPlaceEvent e) {
					return e.getEntity();
				}
			}, 0);
			EventValues.registerEventValue(HangingPlaceEvent.class, Player.class, new Getter<Player, HangingPlaceEvent>() {
				@Override
				public Player get(HangingPlaceEvent e) {
					return e.getPlayer();
				}
			}, 0);
			EventValues.registerEventValue(HangingPlaceEvent.class, Block.class, new Getter<Block, HangingPlaceEvent>() {
				@Override
				public Block get(HangingPlaceEvent e) {
					return e.getBlock();
				}
			}, 0);
			registerEvent(HangingBreakByEntityEvent.class, "([un]hang[ing]|[un]hung) [entity] (remove|break|destroy|damage)");
			EventValues.registerEventValue(HangingBreakByEntityEvent.class, Entity.class, new Getter<Entity, HangingBreakByEntityEvent>() {
				@Override
				public Entity get(HangingBreakByEntityEvent e) {
					return ((HangingEvent)e).getEntity();
				}
			}, 0);
		}
		//TODO: registerEvent(PlayerInteractEntityEvent.class, "item[ ]frame rotate");
	}
	public static void types(){
		if (Skellett.syntaxToggleData.getBoolean("Main.ChatComponent")) {
			EnumClassInfo.create(ClickEvent.Action.class, "clickeventaction").register();
			EnumClassInfo.create(HoverEvent.Action.class, "hovereventaction").register();
			Classes.registerClass(new ClassInfo<TextComponent>(TextComponent.class, "textcomponent")
				.name("textcomponent")
				.description("A getter for bungeecord's chat textcomponent.")
				.parser(new Parser<TextComponent>() {
					@Override
					@Nullable
					public TextComponent parse(String obj, ParseContext context) {
						return null;
					}
					@Override
					public String toString(TextComponent t, int flags) {
						return t.toLegacyText();
					}
					@Override
					public String toVariableNameString(TextComponent t) {
						return t.toLegacyText();
					}
					public String getVariableNamePattern() {
						return ".+";
				}}));
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.Bossbars")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				EnumClassInfo.create(BarColor.class, "barcolour").register();
				EnumClassInfo.create(BarStyle.class, "barstyle").register();
				EnumClassInfo.create(BarFlag.class, "barflag").register();
				if (Classes.getExactClassInfo(BossBar.class) == null) {
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
				}
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Main.Scoreboards")) {
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
				if (Bukkit.getPluginManager().getPlugin("skRayFall") == null) {
					EnumClassInfo.create(Team.OptionStatus.class, "optionstatus").register();
				}
			}
		}
		if (Skellett.instance.getConfig().getBoolean("PluginHooks.Npc")) {
			//EnumClassInfo.create(EntityType.class, "skellettentitytype").register();
			if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
				Classes.registerClass(new ClassInfo<NPC>(NPC.class, "citizen")
					.name("npc")
					.description("A getter for npc")
					.defaultExpression(new EventValueExpression<NPC>(NPC.class))
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
		Classes.registerClass(new ClassInfo<UUID>(UUID.class, "uuid")
			.name("uuid")
			.description("A getter for uuid's")
			.defaultExpression(new EventValueExpression<UUID>(UUID.class))
			.parser(new Parser<UUID>() {
				@Override
				@Nullable
				public UUID parse(String obj, ParseContext context) {
					return null;
				}
				@Override
				public String toString(UUID e, int flags) {
					return e.toString();
				}
				@Override
				public String toVariableNameString(UUID e) {
					return e.toString();
				}
				public String getVariableNamePattern() {
					return ".+";
			}}));
		if (Skellett.instance.getConfig().getBoolean("PluginHooks.ProtocolSupport")) {
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
			}
		}
		if (Skellett.syntaxToggleData.getBoolean("Syntax.Effects.Particles")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				if (Classes.getExactClassInfo(Particle.class) == null && !Skript.DEV_BUILD) {
					EnumClassInfo.create(Particle.class, "particle").register();
				}
			}
		}
		if (Bukkit.getPluginManager().getPlugin("SkQuery") == null) {
			EnumClassInfo.create(Sound.class, "sound").register();
		}
		if (Skellett.instance.getConfig().getBoolean("PluginHooks.Feudal")) {
			if (Bukkit.getPluginManager().getPlugin("Feudal") != null) {
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
			}
		}
		if (Skellett.mysqlData.getBoolean("MySQL")) {
			if (Classes.getExactClassInfo(ResultSet.class) == null) {
				//TODO change name read todo.yml
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
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Class> getClasses() {
		return classes;
	}
	
	@SuppressWarnings("rawtypes")
	public static void addEvent(Class c) {
		classes.add(c);
	}
	
	@SuppressWarnings("unchecked")
	public static void registerEvent(@SuppressWarnings("rawtypes") Class clazz, String syntax) {
		Skript.registerEvent(syntax, SimpleEvent.class, clazz, "[skellett] " + syntax);
		classes.add(clazz);
	}
	
	public static void dump() {
		classes.clear();
	}

	public static void metrics(Metrics metrics) {
		metrics.addCustomChart(new Metrics.SimplePie("using_skellettproxy") {
			@Override
			public String getValue() {
				return Skellett.spData.getBoolean("SkellettProxy", false) ? "true" : "false";
			}
		});
		metrics.addCustomChart(new Metrics.SimplePie("using_skellettproxy_events") {
			@Override
			public String getValue() {
				return Skellett.spData.getBoolean("Events", false) ? "true" : "false";
			}
		});
		metrics.addCustomChart(new Metrics.SimplePie("skript_version") {
			@Override
			public String getValue() {
				return Skript.getVersion().toString();
			}
		});
		metrics.addCustomChart(new Metrics.SimplePie("using_customevents") {
			@Override
			public String getValue() {
				return Skellett.ceData.getBoolean("CustomEvents", false) ? "true" : "false";
			}
		});
		metrics.addCustomChart(new Metrics.SimplePie("using_mysql") {
			@Override
			public String getValue() {
				return Skellett.mysqlData.getBoolean("MySQL", false) ? "true" : "false";
			}
		});
		metrics.addCustomChart(new Metrics.SingleLineChart("player_database") {
			@Override
			public int getValue() {
				return Bukkit.getOfflinePlayers().length;
			}
		});
	}
}